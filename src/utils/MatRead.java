package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Tsotne
 */
public class MatRead {
    private int size;
    private int[][] mat;

    public int getSize() {
        return size;
    }

    public int[][] getMat() {
        return mat;
    }

    public MatRead(String file) throws Exception
    {
        File f=new File(file);
        if(!f.exists())
            throw new FileNotFoundException(file+ " does not exist");

        Scanner in=new Scanner(f);
        this.size=in.nextInt();

        this.mat=new int[this.size][this.size];

        for(int i=0;i<this.size;i++)
            for(int j=0;j<this.size;j++)
                this.mat[i][j]=in.nextInt();
    }
}
