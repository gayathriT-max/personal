import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class MazeSolver {
    int rows;
    int cols;
    int startRow;
    int startCol;
    int endRow;
    int endCol;
    char[][] maze;

    public static void main(String[] args) {
        try {
            MazeSolver mazeSolver = new MazeSolver("maze.txt");
            System.out.println(mazeSolver.solve());
            mazeSolver.writeToFile("solvedMaze.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid filename!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Constructor that reads the given maze into a 2D matrix and identifies the starting and exit points
     * of the maze as well as the total number of rows and columns in the maze
     *
     * @param filename the name of the file where the original (unsolved) maze is located
     * @throws IOException
     */
    public MazeSolver(String filename) throws IOException {
        ArrayList<String> stringMaze = new ArrayList<>();
        BufferedReader bfr = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = bfr.readLine()) != null) {
            stringMaze.add(line);
        }

        bfr.close();

        // total rows = number of lines read
        // total columns = length of any of the lines
        rows = stringMaze.size();
        cols = stringMaze.get(0).length();

        // change the values below to reflect the specifics of the given maze
        startRow = 1;
        startCol = 0;
        endRow = rows - 2;
        endCol = cols - 1;

        // initialize 2D array (default values)
        maze = new char[rows][cols];

        // fill 2D array with appropriate char values from strings
        int i = 0;
        for (String string : stringMaze) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = string.charAt(j);
            }
            i++;
        }
    }

    /**
     * Proxy method that hands the work over to the recursive method solve(int row, int col)
     * @return true if given maze has a solution, false if the maze does not
     */
    public boolean solve() {
        return solve(startRow, startCol);
    }

    /**
     * Recursive method that overloads solve() and does all the hard work of finding (one of) the solutions to
     * the given maze by using the process of backtracking
     *
     * @param row the row of the current location
     * @param col the column of the current location
     * @return true if given maze has a solution, false if the maze does not
     */
    private boolean solve(int row, int col) {
        // handle special cases (out of bounds and walls)
        if (row < 0 || col < 0 || row >= rows || col >= cols || maze[row][col] != ' ')
            return false;

        // mark this location as on the path...
        maze[row][col] = '*';

        // basis case: see if we're done...
        if (row == endRow && col == endCol)
            return true;

        // recursive case: try surrounding spaces...
        if (solve(row-1, col) || solve(row+1, col) || solve(row, col-1) || solve(row, col+1))
            return true;

        // failure case: no solution found from this location; backtrack and return failure...
        maze[row][col] = ' ';
        return false;
    }

    /**
     * Writes the final char[][] maze array into a 2D array in the specified file, complete with the
     * solution for the maze (marked by *). If there is no solution for the maze, this file will look identical
     * to the maze that exists in the file provided to the constructor.
     *
     * @param filename the name of the file where the solved maze will be located
     * @throws IOException
     */
    public void writeToFile(String filename) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(filename));

        for (char[] line : maze) {
            pw.println(line);
        }

        pw.close();
    }
}
