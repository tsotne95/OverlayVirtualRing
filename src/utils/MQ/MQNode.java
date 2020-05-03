package utils.MQ;

import com.google.gson.JsonParseException;
import com.rabbitmq.client.*;
import routing.IRouting;
import utils.IReceive;
import utils.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Tsotne
 */
public class MQNode implements Consumer {
    private List<MQQueue> queues;
    private List<IReceive> receivers;
    private final int id;
    private Channel channel;
    private IRouting routing;

    public MQNode(int id, int[][]mat, IRouting routing) throws IOException, TimeoutException
    {
        this.id=id;
        this.receivers=new ArrayList<IReceive>();
        this.routing=routing;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn=factory.newConnection();
        this.channel=conn.createChannel();
        this.queues=new ArrayList<MQQueue>();

        init(mat[id]);

        try {
            for(MQQueue q:this.queues)
            {
                this.channel.queueDeclare(q.getName(),false,false,false,null);
                if(q.getSrc()!=this.id)
                {
                    this.channel.basicConsume(q.getName(),true,this);
                }
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void init(int[] listQueue)
    {
        for(int i=0;i<listQueue.length;i++)
        {
            if(listQueue[i]>0 && this.id!=i)
            {
                this.queues.add(new MQQueue(this.id + "QUEUE"+i,this.id));
                this.queues.add(new MQQueue(i+"QUEUE"+this.id,i));
            }
        }
    }

    public int getId() {
        return id;
    }

    public void addReceiver(IReceive r)
    {
        this.receivers.add(r);
    }

    public void sendMessage(String queueName, Message msg)
    {
//        System.out.println("MQnode id"+this.id+ " queue "+ queueName);
        if(queueName !=null)
        {
            try
            {
//                System.out.println(msg.do_serialize());
                this.channel.basicPublish("",queueName,null,msg.do_serialize().getBytes());
            }
            catch (IOException ex)
            {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    @Override
    public void handleConsumeOk(String s) {

    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        Message msg;
        try
        {
            msg=Message.do_deserialize(new String(bytes,"UTF-8"));

            for(IReceive r:this.receivers)
            {
                if(msg.getTo()==this.id)
                    r.receive(msg.getMsg());
                else
                {
                    String q=this.routing.getChannel(this.id,msg.getTo());
                    System.out.println("Node id = "+this.id+" forward from NODE "+msg.getFrom()+ " to NODE"+ msg.getTo());
                    sendMessage(q,msg);
                }
            }
        }
        catch (JsonParseException ex)
        {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
