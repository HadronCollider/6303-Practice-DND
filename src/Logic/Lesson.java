package Logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lesson {
    ArrayList<DictionaryPair> Dictionary;
    String LessonName;

    /**
     * Инициирует урок, считывая пары из файла
     *
     * @param file - файл
     * @return - удачная инициализация - true
     * @throws IOException
     */
    boolean Init(File file) throws IOException {
        Dictionary = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] strings = line.split("\t");
                if (strings.length == 2)
                    Dictionary.add(new DictionaryPair(strings[0], strings[1]));
                else
                    return false;
            }
        }
        setLessonName(file.getName());

        return true;
    }

    void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public String getLessonName() {
        return LessonName;
    }
}