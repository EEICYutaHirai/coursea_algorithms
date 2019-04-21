/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialData;
    private double mean;
    private double stddev;
    private final double gauss = 1.96;

    public PercolationStats(int n, int trial) {
        if (n > 0 && trial > 0) {
            trialData = new double[trial];
            for (int i = 0; i < trial; i++) {
                Percolation p = new Percolation(n);
                int[] indexArray = new int[n * n];
                for (int j = 0; j < n * n; j++) {
                    indexArray[j] = j;
                }
                StdRandom.shuffle(indexArray);
                for (int j = 0; j < n * n; j++) {
                    p.open(indexArray[j] / n + 1, indexArray[j] % n + 1);
                    if (p.percolates()) break;
                }
                /*
                while (!p.percolates()) {

                    int tmpRow = StdRandom.uniform(1, n + 1);
                    int tmpColumn = StdRandom.uniform(1, n + 1);
                    while (p.isOpen(tmpRow, tmpColumn)) {
                        tmpRow = StdRandom.uniform(1, n + 1);
                        tmpColumn = StdRandom.uniform(1, n + 1);
                    }

                }*/
                trialData[i] = (double) p.numberOfOpenSites() / (double) (n * n);
            }
            mean = StdStats.mean(trialData);
            stddev = StdStats.stddev(trialData);
        }
        else {
            throw new IllegalArgumentException("specufy positive number for n and trial");
        }

    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - gauss * stddev / Math.sqrt(trialData.length);
    }

    public double confidenceHi() {
        return mean + gauss * stddev / Math.sqrt(trialData.length);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
