package Logic;

public class Position {
    int vertical;
    int horizontal;

    Position(int a, int b) {
        vertical = a;
        horizontal = b;
    }

    public Position(Position a) {
        vertical = a.vertical;
        horizontal = a.horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public boolean equals(Position coor) {
        return coor.horizontal == horizontal && coor.vertical == vertical;
    }

    @Override
    public String toString() {
        return vertical + " " + horizontal;
    }
}
