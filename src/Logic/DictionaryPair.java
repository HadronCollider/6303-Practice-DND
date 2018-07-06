package Logic;

public class DictionaryPair {
    private String first;
    private String second;

    DictionaryPair(String str1, String str2) {
        first = str1;
        second = str2;
    }

    DictionaryPair(DictionaryPair a) {
        first = a.first;
        second = a.second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return first + "\t" + second;
    }
}
