/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] isOpen;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private final int[] dx = { 1, -1, 0, 0 };
    private final int[] dy = { 0, 0, 1, -1 };
    private int nOpen;


    public Percolation(int N) {
        // 0 ... top
        // N*N + 1 ... bottom;
        // (row, column) ... (row - 1) * N + column
        if (N > 0) {
            uf = new WeightedQuickUnionUF(N * N + 2);
            uf2 = new WeightedQuickUnionUF(N * N + 2);
            isOpen = new boolean[N][N];
            nOpen = 0;

            for (int i = 0; i < N; i++) {
                // uf.union(0, i + 1);
                // uf.union(N * N - i, N * N + 1);
                for (int j = 0; j < N; j++) {
                    isOpen[i][j] = false;
                }
            }

        }
        else {
            throw new IllegalArgumentException("specify positive number");
        }
    }

    public void open(int row, int column) {
        if (row > 0 && column > 0 && row <= isOpen[0].length && column <= isOpen[0].length) {
            if (!isOpen[row - 1][column - 1]) {
                isOpen[row - 1][column - 1] = true;
                nOpen += 1;
                if (row == 1) {
                    uf.union(0, (row - 1) * isOpen[0].length + column);
                    uf2.union(0, (row - 1) * isOpen[0].length + column);
                }
                if (row == isOpen[0].length) {
                    uf.union((row - 1) * isOpen[0].length + column,
                             isOpen[0].length * isOpen[0].length + 1);
                }
                for (int i = 0; i < 4; i++) {
                    if ((row + dx[i]) > 0 && (column + dy[i]) > 0
                            && (row + dx[i]) <= isOpen[0].length
                            && (column + dy[i]) <= isOpen[0].length) {
                        if (isOpen[row + dx[i] - 1][column + dy[i] - 1]) {
                            uf.union((row - 1) * isOpen[0].length + column,
                                     (row + dx[i] - 1) * isOpen[0].length + column + dy[i]);
                            uf2.union((row - 1) * isOpen[0].length + column,
                                      (row + dx[i] - 1) * isOpen[0].length + column + dy[i]);
                        }
                    }
                }
            }


        }
        else {
            throw new IllegalArgumentException("specify positive number");
        }
    }

    public boolean isOpen(int row, int column) {
        if (row > 0 && column > 0 && row <= isOpen[0].length && column <= isOpen[0].length) {
            return isOpen[row - 1][column - 1];
        }
        else {
            throw new IllegalArgumentException("specify positive number");
        }
    }

    public boolean isFull(int row, int column) {
        if (row > 0 && column > 0 && row <= isOpen[0].length && column <= isOpen[0].length) {
            return uf2.connected(0, (row - 1) * isOpen[0].length + column);
        }
        else {
            throw new IllegalArgumentException("specify positive number");
        }
    }

    public int numberOfOpenSites() {
        return this.nOpen;
    }


    public boolean percolates() {

        if (isOpen[0].length == 1 && numberOfOpenSites() == 0) {

            return false;
        }
        return uf.connected(0, isOpen[0].length * isOpen[0].length + 1);
    }

    public static void main(String[] args) {
        /*
        Percolation p = new Percolation(Integer.parseInt(args[0]));
        int n = Integer.parseInt(args[0]);
        while (true) {
            int tmpRow = scanner.nextInt();
            int tmpColumn = scanner.nextInt();
            p.open(tmpRow, tmpColumn);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!p.isOpen(i + 1, j + 1)) {
                        System.out.print("*");
                    }
                    else {
                        System.out.print("_");
                    }
                }
                System.out.println();
            }
        }
        */
    }
}
