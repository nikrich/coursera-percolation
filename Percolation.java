/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *
 * @author Jannik
 * @date 2018/03/12
 * @description Percolation Application. The purposes of this application is 
 * to determine whether a grid percolates. Execute by running 
 */

public class Percolation {
    
    private final int n;
    private final int gridSize;
    private final WeightedQuickUnionUF qu;  
    private boolean[][] openSites;
    private int openSitesCount;
    
    /**     
     * @description create n-by-n grid, with all sites blocked 
     * @param n integer : size of grid
     */
    public Percolation(int n) {

        // throw exception if out of range
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.gridSize = n*n+2;
        this.n = n;
        this.qu = new WeightedQuickUnionUF(gridSize);
        this.openSitesCount = 0;
        // including 2 additional virtual sites
        // top virtual index = 0
        // bottom virtual index = (n^2)+1       
        this.openSites = new boolean[n][n];       
    }
    
    /** 
     * @description open site (row, col) if it is not open already 
     * @param row : index of row (1 - n)
     * @param col : index of col (1 - n)
     */  
    public void open(int row, int col) {
        
        // throw exception if out of range
        if (row <= 0 || col <= 0 || n <= row-1 || n <= col-1) {
            throw new IllegalArgumentException();
        }
        
        if (row == 1) {                
            qu.union(xyTo1D(row, col), 0);
            
            if (n == 1) {
                qu.union(xyTo1D(row, col), gridSize-1);
            }
        }          
        // connect bottom set to virtual site
        else if (row == n) {                
            qu.union(xyTo1D(row, col), gridSize-1);
        } 
        
        if (!openSites[row-1][col-1]) {
            this.openSitesCount++;
            openSites[row-1][col-1] = true;
        }
            
      

        // connect to top if top is available and open
        if (row > 1 && isOpen(row - 1, col)) {
            qu.union(xyTo1D(row, col), xyTo1D(row-1, col));
        }

        // connect to right if right is available and open
        if (col < n && isOpen(row, col + 1)) {
            qu.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }

        // connect to bottom if bottom is available and open
        if (row < n && isOpen(row + 1, col)) {
            qu.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }

        // connect to left if left is available and open
        if (col > 1 && isOpen(row, col - 1)) {
            qu.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }       
    }   
    
    /** 
     * @description is site (row, col) open? 
     * @param row : index of row (1 - n)
     * @param col : index of col (1 - n)
     * @return boolean is open     
     */      
    public boolean isOpen(int row, int col) {       
        // throw exception if out of range
        if (row <= 0 || col <= 0 || n <= row-1 || n <= col-1) {
            throw new IllegalArgumentException();
        }
        
        return openSites[row-1][col-1];
    }
    
    /**
     * @description is site (row, col) full? 
     * @param row : index of row (1 - n)
     * @param col : index of col (1 - n)
     * @return boolean is full     
     */   
    public boolean isFull(int row, int col) {
        
        // throw exception if out of range
        if (row <= 0 || col <= 0 || n <= row-1 || n <= col-1) {
            throw new IllegalArgumentException();
        }
        
        return qu.connected(0, xyTo1D(row, col));
    }
    
    /**     
     * @description no of open sites 
     * @return integer number of open sites
     */   
    public int numberOfOpenSites() {  
        return this.openSitesCount;
    }
    
    /**     
     * @description does grid percolate 
     * @return boolean percolates
     */   
    public boolean percolates() {
        return qu.connected(0, gridSize - 1);
    }   
    
    private int xyTo1D(int row, int col) { 
        return n * (row - 1) + col; 
    } 
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        Percolation p = new Percolation(10);
        p.open(5, 5);
        p.open(5, 6);
        p.qu.connected(10 * (5 - 1) + 5, 10 * (5 - 1) + 6);
    }    
}
