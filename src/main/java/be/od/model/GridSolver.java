package be.od.model;

import be.od.app.App;
import lombok.Getter;

import java.util.*;

@Getter
public class GridSolver {

    private final Grid originalGrid;
    private Set<Grid> solutions;

    public GridSolver(Grid grid) {
        this.originalGrid = grid;
        this.solutions = new HashSet<>();
    }

    public boolean solveGrid() {
        Grid solvedGrid = solveThisGrid();

        if(!solvedGrid.isCorrect()) {
            return false;
        }

        if (solvedGrid.isComplete()) {
            solutions.add(solvedGrid);
            return true;

        } else {
            Set<Grid> gridsToTry = getGridsToTry(solvedGrid);

            for (Grid grid : gridsToTry) {
                if (solutions.size() > 1) return false;
                GridSolver gridSolver = new GridSolver(grid);
                gridSolver.solveGrid();
                solutions.addAll(gridSolver.getSolutions());
            }
        }
        return false;
    }

    public Grid solveThisGrid() {
        Grid solution = Grid.copyOf(originalGrid);
        //System.out.println("\n *** NEW TRY *** ");
        //System.out.println("\nTrying grid : " + originalGrid.getChangedTile());

        boolean isModified;
        do {
            isModified = false;
            for (int row = 0; row < 9; ++row) {
                for (int col = 0; col < 9; ++col) {

                    if (!solution.getTileAt(row, col).isEmpty()) continue;

                    int value = solveTile(solution, row, col);
                    if (value == -1) {
                        //System.out.println("Abort");
                        solution.setCorrect(false);
                        return solution;
                    } else if (value != 0) {
                        solution.setValueAt(value, row, col);
                        //System.out.printf("Found value : %d (%d, %d)%n", value, row, col);
                        isModified = true;
                    }
                }
            }
        } while (isModified);

        return solution;
    }

    private Set<Grid> getGridsToTry(Grid grid) {

        TileDto possibilities = grid.getTileToTry();
        Tile tile = possibilities.getTile();
        List<Integer> values = possibilities.getPossibilities();

        Set<Grid> gridsToTry = new HashSet<>();

        for (Integer value : values) {

            Grid gridToTry = Grid.copyOf(grid);

            int row = tile.getRow();
            int col = tile.getCol();
            //System.out.printf("Try value %d at (%d, %d)%n", value, row, col);

            gridToTry.setValueAt(value, row, col);
            gridToTry.setChangedTile(row, col);
            gridsToTry.add(gridToTry);
        }

        return gridsToTry;
    }

    private Set<Grid> getGridsToTryAncient(Grid grid) {
        Map<Tile, List<Integer>> possibleValues = grid.getPossibleValues();
        Set<Grid> gridsToTry = new HashSet<>();

        for (Tile tile : possibleValues.keySet()) {
            for (Integer value : possibleValues.get(tile)) {

                Grid gridToTry = Grid.copyOf(grid);

                int row = tile.getRow();
                int col = tile.getCol();
                gridToTry.setValueAt(value, row, col);

                gridsToTry.add(gridToTry);
            }
        }

        return gridsToTry;
    }

    private int solveTile(Grid grid, int row, int col) {
        List<Integer> solutions = grid.getPossibleValuesAt(row, col);
        if (solutions.size() == 0) {
            return -1;
        } else if (solutions.size() == 1) {
            return solutions.get(0);
        } else {
            return 0;
        }
    }

    public int getNext(int index) {
        if (index < 8) return ++index;
        else return 0;
    }

    public enum State {
        OOPS(0, "Something went wrong"),
        YEAP(1, "This sudoku is feasable"),
        NOPE(2, "This sudoku has multiple solutions");

        private final int solutions;
        private final String message;

        State(int solutions, String message) {
            this.solutions = solutions;
            this.message = message;
        }

        public int getSolutions() {
            return solutions;
        }

        public String getMessage() {
            return message;
        }
    }
}
