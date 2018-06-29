import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lesson {
    public ArrayList <DictPair> Dictionary;

    /**
     * Инициирует урок, считывая пары из файла
     * @param fileName
     * @throws IOException
     */
    void Init(String fileName) throws IOException {
        Dictionary = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] strings = line.split("\t");
                Dictionary.add(new DictPair(strings[0], strings[1]));
            }
        }
    }
}
