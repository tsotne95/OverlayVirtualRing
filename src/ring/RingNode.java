package ring;

import com.google.gson.JsonParseException;
import routing.Routing;
import utils.IReceive;
import utils.Message;
import utils.NetworkManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Tsotne
 */
public class RingNode implements IReceive {
    private int id;
    private NetworkManager netman;

    public RingNode(int id, int size, int [][]mat, Routing route) throws IOException, TimeoutException
    {
        this.id=id;
        this.netman=new NetworkManager(id,size,mat,route);
        this.netman.addReceiver(this);
    }

    public void printMsg(Message msg)
    {
        System.out.println("Node ID = " + this.id + " received from NODE " + msg.getFrom() + " : " + msg.getMsg());
    }

    public void sendLeft(String msg)
    {
        Message message=new Message(this.id,-2,msg);
        this.netman.sendLeft(message.do_serialize());
    }

    public void sendRight(String msg)
    {
        Message message=new Message(this.id,-2,msg);
        this.netman.sendRight(message.do_serialize());
    }

    public void sendTo(int to,String msg)
    {
        Message m=new Message(this.id,to,msg);
        this.netman.sendRight(m.do_serialize());
    }

    public void broadcast(String msg)
    {
        //System.out.println("broad "+this.id+" msg "+ msg);
        Message m=new Message(this.id,-1,msg);
        this.netman.sendRight(m.do_serialize());
    }

    private void broadcastHandle(Message m)
    {
        if(m.getTo()==-1 && m.getFrom()!=this.id)
        {
	        System.out.println("Node ID = " + this.id + " ring-forward broadcast message from NODE " + m.getFrom());
            this.netman.sendRight(m.do_serialize());
            printMsg(m);
        }
    }

    void sendToHandle(Message m)
    {
        if(m.getTo()!=this.id)
        {
            System.out.println("Node ID = " + this.id + " ring-forward from NODE " + m.getFrom() + " to NODE " + m.getTo());
            this.netman.sendRight(m.do_serialize());
        }
        else
            printMsg(m);
    }

    @Override
    public void receive(String str) {
        Message m=null;
        try
        {
            m=Message.do_deserialize(str);
        }
        catch (JsonParseException ex)
        {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

        if(m!=null)
        {
            int to=m.getTo();
            if(to==-1)
            {
                broadcastHandle(m);
            }
            else if(to==-2)
            {
                printMsg(m);
            }
            else
                sendToHandle(m);
        }
    }
}
