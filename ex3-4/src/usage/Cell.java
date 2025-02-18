package usage;
import java.awt.*;

import graph.Edge;
import java.util.Objects;

public class Cell {
    int row, col;
    Color color;

    Cell(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(row, cell.row) &&
               Objects.equals(col, cell.col); //&&
           //    Objects.equals(label, edge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}