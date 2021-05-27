package be.od.model;

import lombok.Getter;

import java.util.*;

@Getter
public class Grid {

    private final Tile[][] grid;
    private boolean correct;
    private Tile changedTile;

    public Grid() {
        grid = new Tile[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col] = new Tile(row, col);
            }
        }
        correct = true;
    }

    public static Grid copyOf(Grid grid) {
        Grid outputGrid = new Grid();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                outputGrid.setValueAt(grid.getValueAt(row, col), row, col);
            }
        }
        return outputGrid;
    }

    private int getSquareOf(int tile) {
        return tile / 3 * 3;
    }

    public void setValueAt(int value, int row, int col) {
        getTileAt(row, col).setValue(value);
    }

    public int getValueAt(int row, int col) {
        return getTileAt(row, col).getValue();
    }

    public boolean isEmptyAt(int row, int col) {
        return getTileAt(row, col).isEmpty();
    }

    public Tile getTileAt(int row, int col) {
        return grid[row][col];
    }

    public List<Integer> getPossibleValuesAt(int row, int col) {
        List<Integer> possibleValues = new ArrayList<>();

        for (int value = 1; value < 10; value++) {
            if (isValuePossibleAt(value, row, col)) possibleValues.add(value);
        }

        return possibleValues;
    }

    public Map<Tile, List<Integer>> getPossibleValues() {
        Map<Tile, List<Integer>> possibleValues = new HashMap<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (isEmptyAt(row, col)) {
                    possibleValues.put(getTileAt(row, col), getPossibleValuesAt(row, col));
                }
            }
        }
        return possibleValues;
    }

    public TilePossibilities getTileToTry() {
        Map<Tile, List<Integer>> possibleValuesToTry = getPossibleValues();

        Tile tileToTry = possibleValuesToTry.keySet()
                .stream()
                .min(Comparator.comparingInt(a -> possibleValuesToTry.get(a).size()))
                .orElseThrow();
        List<Integer> valuesToTry = possibleValuesToTry.get(tileToTry);
        return new TilePossibilities(tileToTry, valuesToTry);
    }

    public boolean isValuePossibleAt(int value, int row, int col) {
        return isEmptyAt(row, col)
                && isRowFreeFromValue(value, row)
                && isColFreeFromValue(value, col)
                && isSquareFreeFromValue(value, row, col);
    }

    private boolean isColFreeFromValue(int value, int col) {

        for (int row = 0; row < 9; row++) {

            if (getValueAt(row, col) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean isRowFreeFromValue(int value, int row) {

        for (int col = 0; col < 9; col++) {

            if (getValueAt(row, col) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean isSquareFreeFromValue(int value, int row, int col) {
        row = getSquareOf(row);
        col = getSquareOf(col);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (getValueAt(row + i, col + j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isComplete() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (getTileAt(row, col).isEmpty()) return false;
            }
        }
        return true;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean b) {
        this.correct = b;
    }

    public void setChangedTile(int row, int col) {
        this.changedTile = getTileAt(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid toTest = (Grid) o;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (getValueAt(row, col) != toTest.getValueAt(row, col))
                    return false;
            }
        }
        return true;
    }
}