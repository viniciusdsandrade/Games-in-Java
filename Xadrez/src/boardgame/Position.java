package boardgame;

public class Position {

    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }


    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return getRow() == position.getRow() &&
                getColumn() == position.getColumn();
    }

    // copy constructor
    public Position(Position position) {
        this.row = position.row;
        this.column = position.column;
    }

    // clone
    public Object clone() {

        Position clone = null;

        try {
            clone = (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
        }

        return clone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;

        hash *= prime + Integer.hashCode(row);
        hash *= prime + Integer.hashCode(column);

        if (hash < 0)
            hash = -hash;

        return hash;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}