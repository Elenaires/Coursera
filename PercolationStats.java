import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class PercolationStats
{
    public double[] probability;
    public int T;
    public int N;
    public Percolation p;
    
    
    public PercolationStats (int n, int trials)
    {
        T = trials;
        N = n;
        probability = new double[T];
        
        if (N <=0 || T <=0)
        {
            throw new java.lang.IllegalArgumentException();
        }
        
        for (int i=0; i < T; i++)
        {
            p = new Percolation(N);
            
            while(!p.percolates())
            {
                int row = StdRandom.uniform(1, N+1);
                int col = StdRandom.uniform(1, N+1);
                
                p.open(row, col);
            }
            
            probability[i] = (double) p.numberOfOpenSites() / (double) (N*N); 
        }
        
        
    }
    
    public double mean()
    {
        return StdStats.mean(probability);
    }
    
    public double stddev()
    {
        return StdStats.stddev(probability);
    }
    
    public double confidenceLo()
    {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }
    
    public double confidenceHi()
    {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }
    
    
    public static void main (String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        
        PercolationStats percStats = new PercolationStats(n, t);
        System.out.println("% java PercolationStats " + n + " " + t);
        System.out.println("mean = " + percStats.mean());
        System.out.println("stddev = " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
    }
}