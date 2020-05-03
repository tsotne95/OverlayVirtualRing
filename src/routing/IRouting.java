package routing;

/**
 * @author Tsotne
 */
public interface IRouting {
    //the name of the queue on which to send the message
    public String getChannel(int fromId,int destID);
}
