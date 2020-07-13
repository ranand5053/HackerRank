import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class QueenAttack {

    // Complete the queensAttack function below.
    public static void main(String[] args){
        int n = 1000;
        int k = 10000;
        int r_q = 400;
        int c_q = 477;
        int[][] obstacles = new int[][]{
                {420,497},{430,507},{380,497},{370,507},{400,677},{600,477},{819,706},{698,710},{704,430},{897,652},{704,506},{124,591},{510,206},{975,105},{761,351},{467,620},{687,123},{537,432},{907,450},{759,634},{156,549},{178,930},{814,427},{165,590},{537,129},{415,647},{530,675}};

        int xp = n-r_q;
        int xn = r_q-1;
        int yp = n-c_q;
        int yn = c_q-1;
        int xpyp = Math.min(n-r_q, n-c_q);
        int xpyn = Math.min(n-r_q, c_q-1);
        int xnyp = Math.min(r_q-1, n-c_q);
        int xnyn = Math.min(r_q-1, c_q-1);

        for(int i = 0; i < k; i++){
            int x = obstacles[i][0];
            int y = obstacles[i][1];

            int rowDiff = r_q - x;
            int colDiff = c_q - y;

            if( (Math.abs(rowDiff) == Math.abs(colDiff)) && rowDiff > 0 && colDiff > 0) {
                int diff = rowDiff -1;
                if(diff < xpyp){
                    xpyp = diff;
                }
            } else if( (Math.abs(rowDiff) == Math.abs(colDiff))
                    && rowDiff > 0 && colDiff < 0) {
                int diff = rowDiff -1;
                if(diff < xpyn){
                    xpyn = diff;
                }
            } else if( (Math.abs(rowDiff) == Math.abs(colDiff))
                    && rowDiff < 0 && colDiff < 0) {
                int diff = rowDiff -1;
                if(diff < xnyn){
                    xnyn = diff;
                }
            } else if( (Math.abs(rowDiff) == Math.abs(colDiff))
                    && rowDiff < 0 && colDiff > 0) {
                int diff = rowDiff -1;
                if(diff < xnyp){
                    xnyp = diff;
                }
            } else if(c_q == y && r_q < x){
                int diff = x - r_q -1;
                if(diff < xp){
                    xp = diff;
                }
            } else if(c_q == y && r_q > x){
                int diff = r_q - x -1;
                if(diff < xn){
                    xn = diff;
                }
            } else if(r_q == x && c_q < y){
                int diff = y - c_q -1;
                if(diff < yp){
                    yp = diff;
                }
            } else if(r_q == x && c_q > y){
                int diff =  c_q -y -1;
                if(diff < yn){
                    yn = diff;
                }
            }
        }

        int total = xp + xn + yp + yn + xpyn + xpyp + xnyn + xnyp;
        System.out.println("Total :: " + total);

    }
}