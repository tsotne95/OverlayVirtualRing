package utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.Serializable;

/**
 * @author Tsotne
 */

//using serialization to convert the state of an object into a byte stream
public class Message implements Serializable {

    private int from;
    private int to;
    private String msg;

    private static final long serialVersionUID=99999L;

    public Message(int from, int to, String msg)
    {
        this.from=from;
        this.to=to;
        this.msg=msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getMsg() {
        return msg;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String do_serialize()
    {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);

        return jsonString;
    }

    public static Message do_deserialize(String gson) throws JsonParseException
    {
        Gson gsonObj = new Gson();

        return gsonObj.fromJson(gson,Message.class);

    }

}
