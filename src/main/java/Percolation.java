import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 *  Name:    Kevin Wayne
 *  Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *  Description:  Modeling Percolation.
 ******************************************************************************/
public class Percolation {

    private WeightedQuickUnionUF grid;
    private boolean[][] arr;
    private int numOfOpenSites;
    private int topNode;
    private int bottomNode;
    private int n;

    public Percolation(int n) {
        arr = new boolean[n][n];
        grid = new WeightedQuickUnionUF(n * n + 2);
        bottomNode =  n * n + 1;
        topNode = n * n;
        numOfOpenSites = 0;
        n = n;
    }

    public void open(int row, int col) {
        // TODO: open site (row, col) if it is not open already
        if (inRange(row, col)) throw new IllegalArgumentException("outside of range");
        int current = mapping2Dto1D(row, col);
        if (isOpen(row, col)) {
            return;
        }
        arr [row-1][col-1] = true;
        numOfOpenSites++;

        // check for connecting nodes
        // Link nodes in first row to "top" node
        if (row == 1) {
            grid.union(topNode, current);
        }
        // Link nodes in bottom row to "bottom" node
        if (row == n) {
            grid.union(bottomNode, current);
        }
        // Link nodes on left colum
        if (inRange(row, col-1) && isOpen(row, col-1)) {
            grid.union(current, mapping2Dto1D(row, col-1));
        }
        // Link nodes on right colum
        if (inRange(row, col+1) && isOpen(row, col+1)) {
            grid.union(current, mapping2Dto1D(row, col+1));
        }
        // Link nodes on top
        if (inRange(row-1, col) && isOpen(row-1, col)) {
            grid.union(current, mapping2Dto1D(row-1, col));
        }
        // Link nodes on bottom
        if (inRange(row+1, col) && isOpen(row+1, col)) {
            grid.union(current, mapping2Dto1D(row+1, col));
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: is site (row, col) open?
        return arr[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        // TODO: is site (row, col) full?
        return grid.find(mapping2Dto1D(row, col)) == grid.find(topNode);
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        // TODO: does the system percolate?
        return grid.find(bottomNode) == grid.find(topNode);
    }

    public static void main(String[] args) {
        // TODO: test client (optional)
    }
    private int mapping2Dto1D(int row, int col) {
        return n * (row-1) + (col-1);
    }
    private boolean inRange(int row, int col) {
        int arrRow = row - 1;
        int arrCol = col - 1;
        return (arrRow >= 0 && arrRow < n) && (arrCol >= 0 && arrCol < n);
    }
}
