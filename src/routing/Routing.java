package routing;

/**
 * @author Tsotne
 */
public class Routing implements IRouting{
    private int[][] mat; //matrix
    private int[][]predecessors; //to get the shortest path from one point to another
    private int size; //matrix size
    private int nodeId; //node id
    private final static int UNREACHABLE=Integer.MAX_VALUE; //constant to mark that between nodes there is no directly path

    public Routing(int size, int[][]mat)
    {
        this.size=size;
        this.mat=new int[this.size][this.size];
        copyMat(mat,size);
        this.predecessors=new int[this.size][this.size];
        init();
    }

    //copy matrix
    private void copyMat(int[][] mat,int size)
    {
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                this.mat[i][j]=mat[i][j];
    }

    //All Pairs Shortest Path (Floyd-Warshall)
    //save shortest path into predecessors
    private void init(){
        for(int i = 0; i<this.size; i++){
            for(int j = 0; j < this.size; j++){
                if(this.mat[i][j] == 0){
                    this.mat[i][j] = UNREACHABLE;
                }
                if(i != j && this.mat[i][j] < UNREACHABLE){
                    this.predecessors[i][j] = i;
                }
                else{
                    this.predecessors[i][j] = -1;
                }
            }
        }

//        System.out.println("first step finished");
//        print(this.mat,this.size);
//        System.out.println();
//        print(this.predecessors,this.size);

        for(int k = 0; k < this.size; k++){
            for(int i = 0; i < this.size; i++){
                for(int j = 0; j < this.size; j++){
                    if(i!=j && this.mat[i][j] > infSum(this.mat[i][k], this.mat[k][j])){
                        this.mat[i][j] = infSum(this.mat[i][k],this.mat[k][j]);
                        this.predecessors[i][j] = this.predecessors[k][j];
                    }
                }
            }

//            System.out.println(k+" step finished");
//            print(this.mat,this.size);
//            System.out.println();
//            print(this.predecessors,this.size);
        }
    }

    @Override
    public String getChannel(int fromId,int destID) {
        int res;
        int locDestID;
        res=locDestID=destID;

        while(this.predecessors[fromId][locDestID]>-1)
        {
            res=locDestID;
            locDestID=this.predecessors[fromId][locDestID];
        }

        if(this.predecessors[fromId][res]>-1)
        {
            String s=fromId+"QUEUE"+res;
            return s;
        }

        return null;
    }

    public void print(int[][]mat, int size)
    {
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++)
                System.out.print(mat[i][j]+" ");
            System.out.println();
        }
    }

    int infSum(int a, int b)
    {
        long r=(long)a+(long)b;
        if(r>UNREACHABLE)
            return UNREACHABLE;

        return a+b;
    }

}
