package Logic;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

class Lesson {
    ArrayList<DictionaryPair> Dictionary;
    private String LessonName;

    /**
     * Инициализация урока считыванием пар из файла
     *
     * @param file - файл
     * @return - удачная инициализация - true
     */
    boolean Init(File file) {
        try (Scanner scanner = new Scanner(file)) {
            Dictionary = new ArrayList<>();
            while (scanner.hasNext()) {
                DictionaryPair pair = DictionaryPair.readPair(scanner);
                if (pair != null)
                {
                    if (StrTransform.isFormatCorrect(pair.getFirst()) && StrTransform.isFormatCorrect(pair.getSecond()))
                        Dictionary.add(pair);
                    else {
                        // Сообщение об ошибке в строке словаря ("\e")
                        JOptionPane.showMessageDialog(null, "Неизвестная управляющая конструкция в строке №" + (Dictionary.size() + 1), "Ошибка чтения словаря", JOptionPane.ERROR_MESSAGE, null);
                        return false;
                    }
                }
                else
                {
                    // Сообщение об ошибке в словаре
                    JOptionPane.showMessageDialog(null, "Некорректная строка №" + (Dictionary.size() + 1), "Ошибка чтения словаря", JOptionPane.ERROR_MESSAGE, null);
                    System.out.println("Ошибка в словаре. Строка №" + (Dictionary.size() + 1) + "\n");
                    Dictionary.clear();
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            // Сообщение о ненайденном файле
            JOptionPane.showMessageDialog(null, "Не удалось открыть файл словаря", "Ошибка открытия словаря", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        if (Dictionary.isEmpty())
        {
            // Сообщение о ненайденном файле
            JOptionPane.showMessageDialog(null, "Пустой словарь", "Ошибка словаря словаря", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        setLessonName(file.getName());
        return true;
    }

    void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    String getLessonName() {
        return LessonName;
    }

    /**
     * Инициализация урока из списка
     *
     * @param dictionary - список пар
     * @param lessonName - название урока
     */
    void LessonFromList(LinkedList<DictionaryPair> dictionary, String lessonName) {
        Dictionary = LinkedToArrayList(dictionary);
        setLessonName(lessonName);
    }

    /**
     * Создание ArrayList из LinkedList
     *
     * @param Llist - LinkedList
     * @return - ArrayList
     */
    private ArrayList<DictionaryPair> LinkedToArrayList(LinkedList<DictionaryPair> Llist) {
        return new ArrayList<>(Llist);
    }

    /**
     * Перемешивание пар урока
     */
    void MixDictionary() {
        Dictionary.sort((o1, o2) -> (new Random()).nextInt() % 3 - 1);
    }

}