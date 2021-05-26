package be.od.model;

import lombok.Data;

import java.util.List;

@Data
public class TileDto {

    private Tile tile;
    private List<Integer> possibilities;

    public TileDto(Tile tile, List<Integer> values) {
        this.tile = tile;
        this.possibilities = values;
    }
}
