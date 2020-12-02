import java.io.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
/**
 * Main
 */
class Main {
    
    int n = 10, L = 50;
    boolean[][] visited;
    int bestK;
    int[] currX;
    int[] bestX;
    int[] length;
    
    /**
     * Constructor.
     * @param data trucks to load
     */    
    Main(String data) {
        String[] info = data.split("\n");
        n = info.length - 1;
        L = Integer.parseInt(info[0].trim()); // Collect Ferry length in meters
        L = L * 100; // Convert in cm
            
        currX = new int[n];
        bestX = new int[n];
        length = new int[n];
        visited = new boolean[n+1][L+1];

        for (int i = 1; i <= n; i++) { // Store data
            length[i-1] = Integer.parseInt(info[i]);
        }

        bestK = -1;
    }

    /**
     * Computes the sum of the k first elements in an array
     * @param length the array of the lengths of trucks to be loaded
     * @param currK the amount of trucks already loaded
     * @return sum of the k first elements in an array
     */
    int kth_sum(int[] length, int currK) {
        int sum = 0;
        for (int i = 0; i < currK; i++) {
            sum += length[i];
        }
        return sum;
    }

    /**
     * Checks if there's space available to load the next truck on the right hand side of the ferry 
     * @param currK the amount of trucks already loaded
     * @param currS the space available on the left hand side of the ferry
     * @return true if space is available, false otherwise
     */
    boolean is_space_available_right_side (int currK, int currS) {
        int total_length = kth_sum(length, currK);
        int occupied_space_left = L - currS;
        int occupied_space_right = total_length - occupied_space_left;
        int available_space_right = L - occupied_space_right;
        return available_space_right >= length[currK];
    }

    /**
     * Checks if there's space available to load the next truck on the left hand side of the ferry
     * @param currK the amount of trucks already loaded
     * @param currS the space available on the left hand side of the ferry
     * @return true if space is available, false otherwise
     */
    boolean is_space_available_left_side (int currK, int currS) {
        return currS >= length[currK];
    }

    /**
     * Computes the maximum number of trucks that can be loaded onto the ferry
     * @param currK number of trucks already loaded onto the ferry  
     * @param currS the space available on the left hand side of the ferry
     */
    void BacktrackSolve (int currK, int currS) {
        // currK cars have been added; currS space remains at the left side
        if (currK > bestK) {
            // then update bestK, bestX with currK, currX
            bestK = currK;
            System.arraycopy(currX, 0, bestX, 0, bestK);
        }

        if (currK < n) {// there are cars left to consider
            if (is_space_available_left_side(currK, currS) && !visited[currK+1][currS-length[currK]]) {
                currX[currK] = 1;
                int newS = currS-length[currK];
                BacktrackSolve(currK+1, newS);
                visited[currK+1][newS] = true; //**//
            }

            if (is_space_available_right_side(currK, currS) && !visited[currK+1][currS]) {
                currX[currK] = 0;
                BacktrackSolve(currK+1, currS);
                visited[currK+1][currS] = true;
            }
        }
    }

    /**
     * Transforms the solution into a more readable format
     * @return the solution of a given ferry-loading problem
     */
    String solution() {
        String sol = "";
        if (bestK == 0) {
            sol += bestK;
            return sol;
        }
        
        sol += bestK + System.getProperty("line.separator");
        for (int i = 0; i < bestK; i++) {
            if (bestX[i] == 1) {
                sol += "port";
            } else if (bestX[i] == 0) {
                sol += "starboard";
            }
            
            if (i < bestK - 1) {
                sol += System.getProperty("line.separator");
            }
            
        }
        return sol;
    }

    public static void main(String[] args) {
        Scanner    scanner = new Scanner ( System.in );
        String input = "";
        int cases = 0;
        String[] arr;
        String output = "";
        Main problem;
        String data = "";
        String NEW_LINE = System.getProperty("line.separator");
        
        while (scanner.hasNext()) {
            input += scanner.next()+"\n";
        }

        arr = input.split("\n");
        cases = Integer.parseInt(arr[0].trim());

        for (int i = 1, c = 0; i < arr.length && c < cases; i++) {
           if (arr[i].equals("0")) {
               problem = new Main(data);
               problem.BacktrackSolve(0, problem.L);
               if (c != 0) {
                   output += NEW_LINE;
                }
               output += problem.solution();
               data = "";
               c++;
               if (c != cases) {
                   output += NEW_LINE;
               }
           } else {
               data += arr[i]+NEW_LINE;
           }

        }
        System.out.println(output);
    }
}
