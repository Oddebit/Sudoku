package be.od.app;

import be.od.model.Grid;
import be.od.model.GridEmptier;
import be.od.model.GridFiller;
import be.od.model.GridSolver;

import java.time.Instant;
import java.util.Set;

public class App {

    private GridFiller gridFiller;
    private GridEmptier gridEmptier;
    private GridSolver gridSolver;

    private int count;
    private Instant finish;

    private final int GIVEN_VALUES = 20;

    public static void main(String[] args) {

        new App();
    }

    public App() {

        Grid fullGrid = createFullGrid();
        printTitle("COMPLETE GRID");
        printGrid(fullGrid);

        Grid emptyGrid;
        Set<Grid> solvedGrids;

        do {
            emptyGrid = createEmptiedGrid(fullGrid);
            solvedGrids = solveGrid(emptyGrid);
        } while (solvedGrids.size() > 1);

        printTitle("EMPTY GRID");
        printGrid(emptyGrid);

        printTitle("FILLED GRID");
        solvedGrids.forEach(App::printGrid);
    }

    private void generateGrids(int reps) {
        for (count = 0; count < reps; count++) {

            Grid grid = createFullGrid();
            printGrid(grid);
        }
    }

    private void generateGrids(long seconds) {
        count = 0;
        finish = Instant.now().plusSeconds(seconds);
        while (Instant.now().isBefore(finish)) {
            createFullGrid();
            count++;
        }
    }

    private Grid createFullGrid() {
        boolean win = false;
        while (!win) {

            gridFiller = new GridFiller();
            win = gridFiller.fillGrid();

        }
        printWin();
        return gridFiller.getGrid();
    }

    private Grid createEmptiedGrid(Grid grid) {
        gridEmptier = new GridEmptier(grid);
        gridEmptier.emptyGrid(GIVEN_VALUES);
        return gridEmptier.getEmptiedGrid();
    }

    private Set<Grid> solveGrid(Grid grid) {

        gridSolver = new GridSolver(grid);
        gridSolver.solveGrid();
        return gridSolver.getSolutions();
    }

    private void printResults(int size) {
        if(size == 0) {
            System.out.println("Something went wrong...");
        } else if (size == 1) {
            System.out.println("This is a good one!");
        } else {
            System.out.println("This grid has multiple solutions.");
        }
    }

    public static void printGrid(Grid grid) {

        for (int i = 0; i < 41; i++) {
            System.out.print("=");
        }
        System.out.println();
        for (int row = 0; row < 9; row++) {
            System.out.print("|| ");
            for (int col = 0; col < 9; col++) {

                int value = grid.getValueAt(row, col);
                if (value != 0) {
                    System.out.print(value);
                } else {
                    System.out.print(" ");
                }

                if ((col + 1) % 3 == 0) {
                    System.out.print(" || ");
                } else {
                    System.out.print(" | ");
                }
            }

            System.out.println();
            if ((row + 1) % 3 == 0) {
                for (int i = 0; i < 41; i++) {
                    System.out.print("=");
                }
            } else {
                for (int i = 0; i < 41; i++) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        System.out.println();
    }


    private void printWin() {
        printTitle("       Sudoku   " + (count + 1) + "        ");
    }

    public static void printTitle(String title) {
        System.out.println();
        for (int i = 0; i < title.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.println(title);
        for (int i = 0; i < title.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}
