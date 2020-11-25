package BigTable;
import java.io.*;

/**
 * Main
 */
public class Main {
    private int n = 10, L = 50;
    private boolean[][] visited = new boolean[n+1][L+1];
    private int bestK;
    int[] currX = new int[n];
    int[] bestX = new int[n];
    int[] length = new int[n];

    public Main(String filename) {
        initialize_visits();
        bestK = -1;
    }

    private void initialize_visits() {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
    }

    private int kth_sum(int[] length, int currK) {
        int sum = 0;
        for (int i = 0; i < currK; i++) {
            sum += length[i];
        }
        return sum;
    }

    private boolean is_space_available_right_side (int currK, int currS) {
        int total_length = kth_sum(length, currK);
        int occupied_space_left = L - currS;
        int occupied_space_right = total_length - occupied_space_left;
        return L - (occupied_space_right + length[currK]) >= 0;
    }

    private boolean is_space_available_left_side (int currK, int currS) {
        return currS >= length[currK + 1];
    }

    public void BacktrackSolve (int currK, int currS) {
        // currK cars have been added; currS space remains at the left side
        if (currK > bestK)
            // then update bestK, bestX with currK, currX
            bestK = currK;
            bestX = currX;

        if (currK < n) // there are cars left to consider
            if (is_space_available_left_side(currK, currS) && !visited[currK+1][currS-length[currK]])
                currX[currK] = 1;
                int newS = currS-length[currK];
                BacktrackSolve(currK+1, newS);
                visited[currK+1][newS] = true; //**//

        if (is_space_available_right_side(currK, currS) && !visited[currK+1][currS])
            currX[currK] = 0;
            BacktrackSolve(currK+1, currS);
            visited[currK+1][currS] = true;
        }

    public static void main(String[] args) {
        
    }
}