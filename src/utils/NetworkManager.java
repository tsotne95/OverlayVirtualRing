package utils;

import routing.IRouting;
import routing.Routing;
import utils.MQ.MQNode;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Tsotne
 */
public class NetworkManager {
    private int[][] mat; //represent physic connection
    int size;
    IRouting routing;
    private MQNode node;
    private final int id;

    public NetworkManager(int id, int size, int [][]mat, Routing route) throws IOException, TimeoutException
    {
        this.id=id;
        this.mat=mat;
        this.size=size;
        this.routing=route;
        this.node=new MQNode(id,mat,this.routing);
    }

    public void addReceiver(IReceive r)
    {
        this.node.addReceiver(r);
    }

    public void sendLeft(String msg)
    {
        int dest=(this.id-1+this.size)%this.size;
        String queue=this.routing.getChannel(this.id,dest);
        this.node.sendMessage(queue,new Message(this.id,dest,msg));
    }

    public void sendRight(String msg)
    {
//        System.out.println("netman send right id="+this.id+" dest="+(this.id+1)%this.size);
        int dest=(this.id+1)%this.size;
        String queue=this.routing.getChannel(this.id,dest);
//        System.out.println("netman queue " +queue);
        this.node.sendMessage(queue,new Message(this.id,dest,msg));
    }


}
