package Logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Lesson curLesson;                                       // Текущий урок
    private LinkedList<DictionaryPair> LessonErr1;                        // Список ошибок (верные пары левых)
    private LinkedList<DictionaryPair> LessonErr2;                        // Список ошибок (верные пары левых)
    private int NumCorrectAnsw;                                 // Кол-во верных ответов на уроке
    private int offset;                                         // Смещение относительно начала словаря
    private Cell[][] Field;                                         // Поле
    private LinkedList<Integer> NumElemOfMatrix;                   // Распределение элементов по матрицам
    private Position FieldSize;      // Размеры поля
    private Position curCellCoor;
    private Move LastMove;
    private int localErrs = 0;

    public Game(int horizontal, int vertical) {
        FieldSize = new Position(horizontal, vertical);
    }

    /**
     * Делает текущим урок из поданного файла
     *
     * @param file
     * @throws IOException
     */
    public boolean newLesson(File file) throws IOException {
        if (curLesson == null) {
            curLesson = new Lesson();
        }
        if (curLesson.Init(file)) {
            LessonErr1 = new LinkedList<>();
            LessonErr2 = new LinkedList<>();
            NumCorrectAnsw = 0;
            offset = 0;
            NumElemOfMatrix = new LinkedList<>();
            CalculateFields();  // рассчёт матричного заполнения
            return true;
        } else
            return false;
    }


    // "Получение" первой кнопки
    private Position getFirstCell() {
        Random rand = new Random();
        int a = rand.nextInt(FieldSize.vertical);
        int b = rand.nextInt(FieldSize.horizontal);
        while (Field[a][b] == null) {
            a = rand.nextInt(FieldSize.vertical);
            b = rand.nextInt(FieldSize.horizontal);
        }
        return new Position(a, b);
    }

    // "Получение" второй кнопки
    private void getSecondCell() {
        Position a = getFirstCell();
        Cell first = Field[curCellCoor.vertical][curCellCoor.horizontal];
        Cell second = Field[a.vertical][a.horizontal];
        if (!curCellCoor.equals(a))             // Нажатие саму себя
        {
            if (first.getFlag() == second.getFlag())   // проверка на "одинаковость стороны" стображаемых слов
            {
                curCellCoor = a;               // Замена кнопки
                return;
            } else {
                LastMove = new Move(first, second);
                // Если стороны отображаемых слов отличаются
                if (compareCell(first.getPosition(), second.getPosition())) {
                    // Если поля совпали
                    NumCorrectAnsw++;
                    Field[curCellCoor.vertical][curCellCoor.horizontal] = null;
                    Field[a.vertical][a.horizontal] = null;
                    System.out.println(this);
                } else {
                    // Не совпали, ошибка - увеличение счетчика, сохранение правильных пар
                    localErrs++;
                    LessonErr1.addLast(first.getPair());
                    LessonErr2.addLast(second.getPair());
                    System.out.println("В первый " + first.getPair() + "\t Во второй " + second.getPair());
                    /*System.out.println("Ошибка - 1 сек");
                    Thread.sleep(1000);                             // окраска в красный
                    System.out.println("Время ошибки прошло");*/
                }
            }
        }
    }

    /**
     * Сравнивает выбранные ячейки
     *
     * @param a
     * @param b
     * @return
     */
    public boolean compareCell(Position a, Position b) {
        DictionaryPair A = Field[a.vertical][a.horizontal].getPair();
        DictionaryPair B = Field[b.vertical][b.horizontal].getPair();
        return A.getFirst().equals(B.getFirst()) || A.getSecond().equals(B.getSecond());
    }

    /**
     * Геттер поля
     *
     * @return поле
     */
    public Cell[][] getField() {
        return Field;
    }

    /**
     * Вычисляет оптимальное кол-во элементов в матрицах, для умещения всего урока на "доске"
     */
    void CalculateFields() {
        int NumElem = curLesson.Dictionary.size();                          // всего элементов
        int MatrixElem = FieldSize.horizontal * FieldSize.vertical / 2;         // пар в матрице заданного размера
        int NumOfMatrix = (int) Math.ceil((double) NumElem / MatrixElem);    // количество необходимых матриц
        int AverageNumberOfCells = NumElem;
        if (NumElem > MatrixElem)
            AverageNumberOfCells = NumElem / NumOfMatrix;
        int delta = NumElem - AverageNumberOfCells * NumOfMatrix;
        for (int i = 0; i < NumOfMatrix; i++) {
            int Num = AverageNumberOfCells;                                 // Рассчёт оптимального кол-ва элементов в матрице
            if (delta > 0) {
                Num++;
                delta--;
            }
            NumElemOfMatrix.addLast(Num);
        }

    }

    public LinkedList<Integer> getNumElemOfMatrix() {
        return NumElemOfMatrix;
    }

    /**
     * Контролирует установку потока полей урока - устанавливет следующее, если оно существует
     *
     * @return true - если поля ещё есть (и после выполнения очередное поле выставлено), false - если полей урока больше нет
     */
    public boolean nextField() {
        if (!NumElemOfMatrix.isEmpty()) {
            Field = new Cell[FieldSize.vertical][FieldSize.horizontal];
            int NumElem = NumElemOfMatrix.getFirst();                       // необходимое кол-во пар в текущей матрице
            int i = 0;
            for (int j = 0; j < Field.length / 2; j++) {
                for (int k = 0; k < Field[j].length; k++) {
                    if (i < NumElem) {
                        Cell first = new Cell(curLesson.Dictionary.get(offset + i), new Position(j, k), false);
                        Cell second = new Cell(curLesson.Dictionary.get(offset + i), new Position(Field.length / 2 + j, k), true); // Для второй(парной) ячейки в матрице
                        i++;
                        Field[j][k] = first;
                        Field[Field.length / 2 + j][k] = second;
                    } else
                        break;
                }
            }

            offset = offset + NumElem;
            return true;
        }
        return false;
    }

    /**
     * Перемешивание
     */
    public void mixField() {
        for (int i = 0; i < Field.length; i++)
            for (int j = 0; j < Field[i].length; j++) {
                Random rand = new Random();
                int a = rand.nextInt(Field.length);
                int b = rand.nextInt(Field[i].length);
                while (a == i && b == j) {
                    a = rand.nextInt(Field.length);
                    b = rand.nextInt(Field[i].length);
                }
                Cell tmp = Field[a][b];
                Field[a][b] = Field[i][j];
                Field[i][j] = tmp;
                if (Field[a][b] != null)
                    Field[a][b].setPosition(new Position(a, b));
                if (Field[i][j] != null)
                    Field[i][j].setPosition(new Position(i, j));
            }
    }

    /**
     * Устанавливает размеры поля
     *
     * @param horizontal
     * @param vertical
     */
    public void setSize(int horizontal, int vertical) {
        /*if (horizontal*vertical % 2 == 1) {
            System.out.println("Указанный размер поля невозможен - нечетное количество ячеек");
            FieldSize.horizontal = FieldSize.vertical = 4;
        }
        else
        {
        }*/
        FieldSize.horizontal = horizontal;
        FieldSize.vertical = vertical;
    }

    /**
     * -------------- МЕТОД НАХОДИТСЯ В РАЗРАБОТКЕ --------------
     * Запись ошибок в файл для возможности повторного запуска
     *
     * @param a
     * @param fileName
     * @throws IOException
     */
    void ErrorToStr(LinkedList<DictionaryPair> a, String fileName) throws IOException {
        try (FileWriter out = new FileWriter(fileName)) {
            for (DictionaryPair err : a)
                out.write(err.getFirst() + "\t" + err.getSecond() + "\n");
        }
    }


    void Сancellation() {
        if (LastMove != null) {
            System.out.println("Отменa " + LastMove.first.getPosition() + " " + LastMove.second.getPosition());
            Position f = LastMove.first.getPosition();
            Position s = LastMove.second.getPosition();
            Field[f.vertical][f.horizontal] = LastMove.first;
            Field[s.vertical][s.horizontal] = LastMove.second;
            if (compareCell(LastMove.first.getPosition(), LastMove.second.getPosition()))
                NumCorrectAnsw--;
            LastMove = null;
        }
    }

    // принимает fileName поданное пользователем

    /**
     * -------------- МЕТОД НАХОДИТСЯ В РАЗРАБОТКЕ --------------
     *
     * @throws IOException
     */
    void SaveProgress() throws IOException {
        StringBuilder build = new StringBuilder();
        build.append(curLesson.LessonName);
        build.setLength(build.length() - 4);
        build.append("(save).savepr");
        try (FileWriter save = new FileWriter(build.toString())) {
            save.write(curLesson.LessonName + "\n");                            // Путь к используемому словарю
            save.write(offset + "\n");                                          // Смещение в словаре
            for (int a : NumElemOfMatrix)                                            // Матричное распределение
                save.write(a + " ");
            save.write("\n");
            save.write(FieldSize.vertical + " " + FieldSize.horizontal);        // Размер поля
            save.write("\n");
            for (Cell[] arr : Field)                                                 // Запись поля
            {
                for (Cell a : arr) {
                    if (a != null) {                                                // Индекс пары в словаре + флаг
                        if (a.getFlag())
                            save.write(curLesson.Dictionary.indexOf(a.getPair()) + " 1 ");
                        else
                            save.write(curLesson.Dictionary.indexOf(a.getPair()) + " 0 ");
                    } else
                        save.write("-1 ");                                      // Если ячейка пуста, то -1
                }
                save.write("\n");
            }
            // Последний сделанный ход
            save.write(NumCorrectAnsw + " " + LessonErr1.size() + "\n");   // Кол-во верных ответов, ошибок
            for (DictionaryPair a : LessonErr1)                        // Сохранение первого списка ошибок
                save.write(curLesson.Dictionary.indexOf(a.getFirst()) + " ");
            save.write("\n");
            for (DictionaryPair a : LessonErr2)                        // Сохранение второго списка ошибок
                save.write(curLesson.Dictionary.indexOf(a.getFirst()) + " ");
            save.write("\n");

        }
    }

    /*@Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        for (Cell[] array : Field) {
            for (Cell a : array) {
                if (a != null) {
                    if (!a.getFlag())
                        build.append(a.getPair().getFirst() + "\t");
                    else
                        build.append(a.getPair().getSecond() + "\t");
                } else
                    build.append(0 + "\t");
            }
            build.append("\n");
        }
        return build.toString();
    }*/
}
