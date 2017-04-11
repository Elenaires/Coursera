import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.*;

public class Percolation 
{
    private int[][] grid;
    private int N;
    public WeightedQuickUnionUF wQU;
    public int openSites = 0;
    public int imaginaryTopNode = 0;
    public int imaginaryBottomNode;
    public final int OPEN = 1;
    public int firstRow;
    public int lastRow;
    public int firstCol;
    public int lastCol;
    

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    { 
          N = n;
          
          if (N <= 0)
          {
              throw new java.lang.IllegalArgumentException();
          }
          
          grid = new int[N][N];
          wQU = new WeightedQuickUnionUF(N*N+2);
          imaginaryBottomNode = N*N + 1;
          firstRow = 0;
          lastRow = N-1;
          firstCol = 0;
          lastCol = N-1;
     }
    
    public int convertCellToNode (int row, int col)
    {
        int node = row * N + col + 1; 
        return node;
    }
    
    public void intChecker (int i, int j)
    {
        if(i <= 0 || i > N || j <= 0 || j > N)
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
    
    public void open (int i, int j)
    {
        
        intChecker(i, j);
        
        int row = i - 1;
        int col = j - 1;
        
        int thisCell = grid[row][col];
     
        if (thisCell != OPEN)
        {
            grid[row][col] = OPEN;
        
            openSites++;
            
            if (row == firstRow)
            {
                wQU.union(imaginaryTopNode, convertCellToNode (row, col));
                
            }
            
            else if (row == lastRow)
            {
                wQU.union(convertCellToNode (row, col), imaginaryBottomNode);
            }
            
            if (col + 1 <= lastCol)
            {
                int adjRight = grid[row][col+1];
                if (adjRight == OPEN)
                {
                    wQU.union(convertCellToNode (row, col+1), convertCellToNode (row, col));
                }
            }
            
            if (col - 1 >= firstCol)
            {
                int adjLeft = grid[row][col-1];
                if (adjLeft == OPEN)
                {
                    wQU.union(convertCellToNode (row, col-1), convertCellToNode (row, col));
                }
            }
                           
            if (row + 1 <= lastRow)
            {   
                int adjBottom = grid[row+1][col];
     
                if (adjBottom == OPEN)
                {
                    wQU.union(convertCellToNode (row, col), convertCellToNode (row+1, col));
                }
            }
            
            if(row - 1 >= firstRow)
            {   
                int adjTop = grid[row-1][col];
                if (adjTop == OPEN)
                {
                    wQU.union(convertCellToNode (row-1, col), convertCellToNode (row, col));
                }
            }
        }
    }
    
    public boolean isOpen (int i, int j)
    {
        intChecker(i,j);
        int row = i - 1;
        int col = j - 1;
        int thisCell = grid[row][col];
        return (thisCell == OPEN);
    }
    
    public boolean isFull (int i, int j)
    {   
        intChecker(i,j);
        int row = i - 1;
        int col = j - 1;
        return wQU.connected(convertCellToNode (row, col), imaginaryTopNode);
    }  
        
    public int numberOfOpenSites()
    {
        return openSites;
    }  
        
    public boolean percolates()
    {
        return wQU.connected(imaginaryTopNode, imaginaryBottomNode);
    }
}