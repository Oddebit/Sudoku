package be.od.model;

import lombok.Getter;

import java.util.Random;

@Getter
public class GridEmptier {

    private final Grid fullGrid;
    private final Grid emptiedGrid;

    public GridEmptier(Grid grid) {
        this.fullGrid = grid;
        this.emptiedGrid = new Grid();
    }

    public void emptyGrid(int givenValues) {

        Random random = new Random();

        for (int tile = 0; tile < givenValues; tile++) {
            int row;
            int col;
            do {
                row = random.nextInt(9);
                col = random.nextInt(9);
            } while (!emptiedGrid.isEmptyAt(row, col));
            emptiedGrid.setValueAt(fullGrid.getValueAt(row, col), row, col);
        }
    }
}
