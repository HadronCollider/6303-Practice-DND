package Logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
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
    boolean Init(File file) throws FileNotFoundException {
        Dictionary = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] strings = line.split("\t");
                if (strings.length == 2)
                    Dictionary.add(new DictionaryPair(strings[0], strings[1]));
                else {
                    // Сообщение об ошибке в словаре
                    System.out.println("Ошибка в словаре. Строка №" + (Dictionary.size()+1) + "\n");
                    return false;
                }
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

    void LessonFromErrors(LinkedList <DictionaryPair> dictionary, String lessonName)
    {
        Dictionary = LinkedToArrayList(dictionary);
        setLessonName(lessonName);
    }

    void MixDictionary()
    {
        Dictionary.sort((o1, o2) -> (new Random()).nextInt()%3-1);
    }

    private ArrayList<DictionaryPair> LinkedToArrayList(LinkedList<DictionaryPair> Llist)
    {
        return new ArrayList<>(Llist);
    }

}