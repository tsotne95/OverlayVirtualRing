package utils.MQ;

/**
 * @author Tsotne
 */

//Queue info
public class MQQueue {
    private String name;
    public int src;

    public MQQueue(String name,int src)
    {
        this.name=name;
        this.src=src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }
}
