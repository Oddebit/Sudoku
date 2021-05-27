package be.od.model;

import lombok.Data;

import java.util.List;

@Data
public class TilePossibilities {

    private Tile tile;
    private List<Integer> possibilities;

    public TilePossibilities(Tile tile, List<Integer> values) {
        this.tile = tile;
        this.possibilities = values;
    }
}
