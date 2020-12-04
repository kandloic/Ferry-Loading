import java.util.ArrayList;
import java.util.Scanner;
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
    // Entry<Integer, Integer> ks = new Entry<Integer,Integer>(0, 0);
    /**
     * Constructor.
     * @param data trucks to load
     */    
    Main(ArrayList<Integer> data) {
        // String[] info = data.split("\n");
        n = data.size() - 1;
        L = data.get(0); // Collect Ferry length in meters
        L = L * 100; // Convert in cm
            
        currX = new int[n];
        bestX = new int[n];
        length = new int[n];
        visited = new boolean[n+1][L+1];

        for (int i = 1; i <= n; i++) { // Store data
            length[i-1] = data.get(i);
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
        int totalCars = kth_sum(length, currK);
        int occupied_space_left = L - currS;
        int occupied_space_right = totalCars - occupied_space_left;
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

    int hash(int key, int value) {
        return key + value;
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

        if (currK < n) {        // there are cars left to consider new Entry<Integer, Integer> (currk, currS-length[currK])
            // ks.key = currK+1;
            // ks.value = currS-length[currK];
            // int ks = hash(currK+1, currS-length[currK]);
            if (is_space_available_left_side(currK, currS) && !visited[currK+1][currS-length[currK]]) {
                currX[currK] = 1;
                // totalCars += length[currK];
                int newS = currS-length[currK];
                BacktrackSolve(currK+1, newS);
                // ks.key = currK+1;
                // ks.value = newS;
                // totalCars += length[currK];
                visited[currK+1][newS] = true; //**//
            }

            // ks.key = currK+1;
            // ks.value = currS;
            // ks = hash(currK+1, currS);
            if (is_space_available_right_side(currK, currS) && !visited[currK+1][currS]) {
                currX[currK] = 0;
                // totalCars += length[currK];
                BacktrackSolve(currK+1, currS);
                // ks.key = currK+1;
                // ks.value = currS;
                // totalCars += length[currK];
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
        /* while (scanner.hasNext()) {
            input += scanner.next()+"\n";
        } */

        int line = 0;
        int counter = 0;
        ArrayList<Integer> lengths = new ArrayList<Integer>();

        cases = scanner.nextInt();
        int c = 0;

        for (int i = 0; scanner.hasNext(); i++) {
            line = scanner.nextInt();
            // System.out.println(line);
            if (line == 0) {
                problem = new Main(lengths);
                problem.BacktrackSolve(0, problem.L);
                output += problem.solution();
                c++;
                if (c < cases) output += NEW_LINE+NEW_LINE;
                lengths = new ArrayList<Integer>();
            } else {
                lengths.add(line);
            }
        }

        /* arr = input.split("\n");
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

        } */
        System.out.println(output);
    }
}
