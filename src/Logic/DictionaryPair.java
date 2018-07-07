package Logic;

import java.util.Scanner;

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

    static DictionaryPair readPair(Scanner scanner)
    {
        String line = scanner.nextLine();
        String[] strings = line.split("\t");
        if (strings.length == 2)
            return new DictionaryPair(strings[0], strings[1]);
        else
            return null;
    }

    @Override
    public String toString() {
        return first + "\t" + second;
    }
}
