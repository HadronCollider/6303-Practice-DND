public class DictPair{
    private String first;
    private String second;
    private boolean flag; // Для обозначения используемого слова

    DictPair(String str1, String str2)
    {
        first = str1;
        second = str2;
        flag = false;
    }

    @Override
    protected DictPair clone() {
        return new DictPair(first, second);
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return first + " - " + second;
    }
}
