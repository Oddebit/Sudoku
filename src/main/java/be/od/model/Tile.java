package be.od.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile {

    private int value;
    private int row;
    private int col;

    public Tile(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        return  String.format("(%d, %d) %d", row, col, value);
    }
}
