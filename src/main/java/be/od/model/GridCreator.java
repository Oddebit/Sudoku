package be.od.model;

import be.od.util.Printer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class GridCreator {

    private GridFiller gridFiller;
    private GridEmptier gridEmptier;
    private GridSolver gridSolver;

    private int count;
    private Instant finish;

    private final int GIVEN_VALUES = 27;

    public GridCreator() {
        generateGrids(10);
    }

    private void generateGrids(int reps) {
        Instant start = Instant.now();
        for (count = 0; count < reps; count++) {
            Printer.printGridNumber(count);
            if (generateGrid() == null) count--;
        }
        long period = ChronoUnit.SECONDS.between(start, Instant.now());
        System.out.printf("%n---> Found %d valid grids in %d s.", reps, period);
        System.out.printf("%n---> Average time per grid : %d s.", period/reps);
    }

    private void generateGrids(long seconds) {
        count = 0;
        finish = Instant.now().plusSeconds(seconds);
        while (Instant.now().isBefore(finish)) {
            generateGrid();
            count++;
        }
    }

    private Grid generateGrid() {
        Instant start = Instant.now();

        Grid fullGrid = createFullGrid();
        Printer.printTitle("  COMPLETE GRID  ");
        Printer.printGrid(fullGrid);

        Grid emptyGrid;
        Set<Grid> solvedGrids;

        do {
            emptyGrid = createEmptiedGrid(fullGrid);
            solvedGrids = solveGrid(emptyGrid);
        } while (solvedGrids.size() > 1);

        Printer.printTitle("  EMPTY GRID  ");
        Printer.printGrid(emptyGrid);

        long period = ChronoUnit.SECONDS.between(start, Instant.now());
        System.out.printf("%nFound valid grid in %d s", period);
        return emptyGrid;
    }

    private Grid createFullGrid() {
        boolean win = false;
        while (!win) {

            gridFiller = new GridFiller();
            win = gridFiller.fillGrid();

        }
        return gridFiller.getGrid();
    }

    private Grid createEmptiedGrid(Grid grid) {
        gridEmptier = new GridEmptier(grid);
        gridEmptier.emptyGrid(GIVEN_VALUES);
        return gridEmptier.getEmptiedGrid();
    }

    private Set<Grid> solveGrid(Grid grid) {

        gridSolver = new GridSolver(grid);
        gridSolver.isSolutionUnique();
        Set<Grid> solutions = GridSolver.getSolutions();
        GridSolver.clearSolutions();
        return solutions;
    }
}
