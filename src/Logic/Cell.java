package Logic;

public class Cell {
    private DictionaryPair pair;          // пара - объект из словаря
    private Position position;  // координаты ячейки на поле
    private boolean flag;           // флаг для обозначения отображаемого слова

    Cell(DictionaryPair pair, Position position, boolean flag) {
        this.pair = pair;
        this.flag = flag;
        this.position = position;
    }

    public DictionaryPair getPair() {
        return pair;
    }

    public void setPair(DictionaryPair pair) {
        this.pair = pair;
    }

    public Position getPosition() {
        return position;
    }

    void setPosition(Position position) {
        this.position = position;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    protected Cell clone() {
        return new Cell(pair, position, flag);
    }
}
