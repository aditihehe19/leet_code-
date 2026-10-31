
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        int[][] right = new int[m][n];
        int[][] down = new int[m][n];

        // Robot starts at (0, 0)
        right[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int r = right[i][j];
                int d = down[i][j];

                if (grid[i][j] == 1) {

                    // Entering mirror while moving right:
                    // Redirect downward.
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + r) % MOD;
                    }

                    // Entering mirror while moving down:
                    // Redirect right.
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + d) % MOD;
                    }

                } else {

                    // Empty cell: combine both incoming directions.
                    int total = (r + d) % MOD;

                    // Reached destination.
                    if (i == m - 1 && j == n - 1) {
                        return total;
                    }

                    // Move right.
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + total) % MOD;
                    }

                    // Move down.
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + total) % MOD;
                    }
                }
            }
        }

        return 0;
    }
}

