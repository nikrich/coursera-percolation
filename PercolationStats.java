/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jannik
 * @date 2018/03/12
 * @description PercolationStats determines various stats regarding the Percolation of a grid
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private final int trials;    
    private double[] means;    
    private static final double PERC = 1.96;
    
    /**
     * perform trials independent experiments on an n-by-n grid
     * @param n
     * @param trials 
     */
    
    public PercolationStats(int n, int trials) {
        
        // throw exception if out of range
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }        
        
        this.trials = trials;
        this.means = new double[trials];
    }
    
    /**
     * sample mean of percolation threshold
     * @return double
     */
    
    public double mean() {
        return StdStats.mean(means);
    }
    
    /**
     * sample standard deviation of percolation threshold
     * @return double
     */
    public double stddev() {
        return StdStats.stddev(means);
    }
    
    /**
     * low  endpoint of 95% confidence interval
     * @return double
     */
    public double confidenceLo() {
        return mean() - ((PERC*stddev())/Math.sqrt(trials));
    }
    
    /**
     * high endpoint of 95% confidence interval
     * @return double
     */
    public double confidenceHi() {
        return mean() + ((PERC*stddev())/Math.sqrt(trials));
    }
    
    /**
     * test client (described below)
     * @param args 
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {               
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }    
            
            ps.means[i] =  (double) p.numberOfOpenSites()/(n*n);
        }        
       
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
