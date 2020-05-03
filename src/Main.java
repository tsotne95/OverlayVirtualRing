import ring.RingNode;
import routing.Routing;
import utils.MatRead;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tsotne
 */
public class Main {
    private static List<RingNode> nodes=new ArrayList<RingNode>();

    public static void main(String[] args) {
        try
        {
            if(args.length==0)
            {
                System.err.println("provide matrix file");
                System.exit(-1);
            }

            MatRead mr=null;
            try {
                mr=new MatRead(args[0]);
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }

            Routing route=new Routing(mr.getSize(),mr.getMat());

            for(int i=0;i<mr.getSize();i++)
            {
                RingNode rn=null;
                try
                {
                    rn=new RingNode(i,mr.getSize(),mr.getMat(),route);
                }
                catch (Exception e)
                {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
                nodes.add(rn);
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        Scanner stdin = new Scanner(System.in);
        while(stdin.hasNextLine())
        {
            String line = stdin.nextLine();
            String[] tokens = line.split(" ");
            if(tokens[0].equals("help"))
            {
                System.out.println("Help:");
                System.out.println("help                     -- print available commands and its usage");
                System.out.println("broadcast nodeID message -- broadcast message from that node");
                System.out.println("send fromID toID message -- send message from fromID node toID node");
                System.out.println("sendLeft fromID message  -- send message from fromID to left node");
                System.out.println("sendRight fromID message -- send message from fromID to right node");
            }
            else if(tokens[0].equals("broadcast"))
            {
                int nodeId=Integer.parseInt(tokens[1]);
                String msg="";
                for(int i=2;i<tokens.length;i++)
                    msg+=tokens[i]+" ";

                //System.out.println("id: "+nodeId+" msg: "+msg);
                nodes.get(nodeId).broadcast(msg);
            }
            else if(tokens[0].equals("send"))
            {
                int fromId=Integer.parseInt(tokens[1]);
                int toId=Integer.parseInt(tokens[2]);

                String msg="";
                for(int i=3;i<tokens.length;i++)
                    msg+=tokens[i]+" ";

                nodes.get(fromId).sendTo(toId,msg);
            }
            else if(tokens[0].equals("sendLeft"))
            {
                int fromId=Integer.parseInt(tokens[1]);

                String msg="";
                for(int i=2;i<tokens.length;i++)
                    msg+=tokens[i]+" ";

                nodes.get(fromId).sendLeft(msg);
            }
            else if(tokens[0].equals("sendRight"))
            {
                int fromId=Integer.parseInt(tokens[1]);

                String msg="";
                for(int i=2;i<tokens.length;i++)
                    msg+=tokens[i]+" ";

                nodes.get(fromId).sendRight(msg);
            }
            else
            {
                System.out.println("try to see help");
            }
        }
    }
}
