package be.od.model;

import lombok.Getter;

import java.util.*;

@Getter
public class GridSolver {

    private final Grid originalGrid;
    private static final Set<Grid> solutions = new HashSet<>();


    public GridSolver(Grid grid) {
        this.originalGrid = grid;
    }

    public boolean isSolutionUnique() {
        if (solutions.size() > 1) {
            return false;
        }

        Grid solvedGrid = solveGrid();

        if (!solvedGrid.isCorrect()) {
            return true;
        }

        if (solvedGrid.isComplete()) {
            solutions.add(solvedGrid);
            return true;

        } else {
            Set<Grid> gridsToTry = getGridsToTry(solvedGrid);

            for (Grid grid : gridsToTry) {
                GridSolver gridSolver = new GridSolver(grid);
                boolean uniqueSolutions = gridSolver.isSolutionUnique();
                if (!uniqueSolutions) return false;
            }
        }
        return true;
    }

    public Grid solveGrid() {
        Grid solution = Grid.copyOf(originalGrid);

        boolean isModified;
        do {
            isModified = false;
            for (int row = 0; row < 9; ++row) {
                for (int col = 0; col < 9; ++col) {

                    if (!solution.getTileAt(row, col).isEmpty()) continue;

                    int value = solveTile(solution, row, col);
                    if (value == -1) {
                        solution.setCorrect(false);
                        return solution;

                    } else if (value != 0) {
                        solution.setValueAt(value, row, col);
                        isModified = true;
                    }
                }
            }
        } while (isModified);

        return solution;
    }

    private Set<Grid> getGridsToTry(Grid grid) {

        TilePossibilities possibilities = grid.getTileToTry();
        Tile tile = possibilities.getTile();
        List<Integer> values = possibilities.getPossibilities();

        Set<Grid> gridsToTry = new HashSet<>();

        for (Integer value : values) {

            Grid gridToTry = Grid.copyOf(grid);

            int row = tile.getRow();
            int col = tile.getCol();

            gridToTry.setValueAt(value, row, col);
            gridToTry.setChangedTile(row, col);
            gridsToTry.add(gridToTry);
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

    public static Set<Grid> getSolutions() {
        Set<Grid> solutions = new HashSet<>();
        GridSolver.solutions.forEach(grid -> solutions.add(Grid.copyOf(grid)));
        return solutions;
    }

    public static void clearSolutions() {
        solutions.clear();
    }
}
