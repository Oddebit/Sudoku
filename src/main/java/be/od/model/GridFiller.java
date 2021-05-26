package be.od.model;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class GridFiller {

    private final Grid grid;

    public GridFiller() {
        this.grid = new Grid();
    }


    public boolean fillGrid() {
        boolean isGridFillable = true;
        for (int value = 1; value < 10 && isGridFillable; value++) {

            isGridFillable = fillGrid(value);
        }
        return isGridFillable;
    }

    private boolean fillGrid(int value) {
        boolean isRowFillable = true;
        for (int row = 0; row < 9 && isRowFillable; row++) {

            isRowFillable = fillRow(value, row);
        }
        return isRowFillable;
    }

    private boolean fillRow(int value, int row) {
        List<Integer> cols = Stream.iterate(0, n -> n < 9, n -> n + 1).collect(Collectors.toList());
        Collections.shuffle(cols);

        for (Integer col : cols) {

            if(grid.isValuePossibleAt(value, row, col)) {

                grid.setValueAt(value, row, col);
                return true;
            }
        }

        return false;
    }
}
