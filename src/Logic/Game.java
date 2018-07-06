package Logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Lesson curLesson;                                      // Текущий урок
    private LinkedList<DictionaryPair> LessonErr1;                 // Список ошибок (верные пары левых)
    private LinkedList<DictionaryPair> LessonErr2;                 // Список ошибок (верные пары левых)
    private int NumCorrectAnsw;                                    // Кол-во верных ответов на уроке
    private int offset;                                            // Смещение относительно начала словаря
    private Cell[][] Field;                                        // Поле
    private LinkedList<Integer> NumElemOfMatrix;                   // Распределение элементов по матрицам
    private Position FieldSize;                                    // Размеры поля
    private Move LastMove;                                         // Последный ход
    private int localErrs;                                         // Кол-во ошибок на этапе
    private boolean MixFlag;                                       // Флаг перемешивания (при загрузке этапа)
    private boolean OddFlagForDistribution;                                 //
    private int NumberOfSteps;


    public Game(int vertical, int horizontal) {
        setSize(vertical, horizontal);
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
            prepareLesson();
            return true;
        } else
            return false;
        return true;
    }

    public void prepareLesson()
    {
        LessonErr1 = new LinkedList<>();
        LessonErr2 = new LinkedList<>();
        NumCorrectAnsw = 0;
        offset = 0;
        localErrs = 0;
        NumberOfSteps = 0;
        NumElemOfMatrix = new LinkedList<>();
        curLesson.MixDictionary();
        NumberOfSteps = CalculateFields();  // рассчёт матричного заполнения
    }

    /**
     * Сравнивает выбранные ячейки
     *
     * @param a
     * @param b
     * @return
     */
    public boolean compareCell(Position a, Position b) {
        DictionaryPair first = Field[a.vertical][a.horizontal].getPair();
        DictionaryPair second = Field[b.vertical][b.horizontal].getPair();
        boolean compareFlag = comparePair(first, second);
        setLastMove(new Move(Field[a.vertical][a.horizontal], Field[b.vertical][b.horizontal]));
        if (compareFlag) {
            Field[a.vertical][a.horizontal] = null;
            Field[b.vertical][b.horizontal] = null;
            NumCorrectAnsw++;
            if (NumCorrectAnsw == NumElemOfMatrix.getFirst()) {
                NumElemOfMatrix.removeFirst();
                localErrs = 0;
                NumCorrectAnsw = 0;
                setLastMove(null);
                return true;
            }
        } else {
            localErrs++;
            LessonErr1.addLast(first);
            LessonErr2.addLast(second);
        }
/// ПРОВЕРКА СОХРАНЕНИЯ
        if (new Random().nextInt() % 5 == 0)
            SaveProgress();
        return compareFlag;
    }

    /**
     * Сравнивает поданные пары
     *
     * @param A - первая пара
     * @param B - вторая пара
     * @return - true - если пары совместимы
     */
    boolean comparePair(DictionaryPair A, DictionaryPair B) {
        return A.getFirst().equals(B.getFirst()) || A.getSecond().equals(B.getSecond());
    }

    /**
     * Сеттер последнего хода
     *
     * @param lastMove - последний ход
     */
    void setLastMove(Move lastMove) {
        LastMove = lastMove;
    }

    /**
     *
     * @return - количество совершенных ошибок за этап
     */
    public int getLocalErrs() {
        return localErrs;
    }

    /**
     *
     * @return - количество совершенных ошибок за урок
     */
    public int getNumErrors() {
        return LessonErr1.size();
    }

    /**
     * Отмена хода
     * - верного - уменьшение кол-ва верных ответов
     * - неверного - удаление из таблицы ошибок (уменьшение кол-ва ошибок)
     */
    public void undo() {
        if (LastMove != null) {
//            System.out.println("Отменa " + LastMove.first.getPosition() + " " + LastMove.second.getPosition());
            Position f = LastMove.first.getPosition();
            Position s = LastMove.second.getPosition();
            Field[f.vertical][f.horizontal] = LastMove.first;
            Field[s.vertical][s.horizontal] = LastMove.second;
            if (comparePair(LastMove.first.getPair(), LastMove.second.getPair())) {
                NumCorrectAnsw--;
            } else {
                LessonErr1.removeLast();
                LessonErr2.removeLast();
                localErrs--;
            }
            setLastMove(null);
        }
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
    int CalculateFields() {
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
        return NumOfMatrix;
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
            if (!OddFlagForDistribution) {
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
            }
            else
            {
                int j = 0;
                while ( j < FieldSize.vertical*FieldSize.horizontal) {
                    int row1 = j / FieldSize.horizontal;
                    int column1 = j % FieldSize.horizontal;
                    j++;
                    int row2 = j / FieldSize.horizontal;
                    int column2 = j % FieldSize.horizontal;
                    j++;
                    if (i < NumElem) {
                        Cell first = new Cell(curLesson.Dictionary.get(offset + i), new Position(row1, column1), false);
                        Cell second = new Cell(curLesson.Dictionary.get(offset + i), new Position(row2, column2), true); // Для второй(парной) ячейки в матрице
                        i++;
                        Field[row1][column1] = first;
                        Field[row2][column2] = second;
                    } else
                        break;
                }
            }
            if (MixFlag)
                mixField();
            offset = offset + NumElem;
            return true;
        }
        return false;
    }

    /**
     * Перемешивание
     */
    public void mixField() {
        if (Field != null) {
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
    }

    /**
     * Сеттер флага автоматического перемешивания
     * @param mixFlag - true/false - перемешивать / не перемешивать
     */
    public void setMixFlag(boolean mixFlag) {
        MixFlag = mixFlag;
    }

    /**
     *
     * @return - количество этапов урока
     */
    public int getNumberOfSteps() {
        return NumberOfSteps;
    }

    /**
     * Устанавливает размеры поля
     *
     * @param horizontal - ширина
     * @param vertical - высота
     */
    public void setSize(int vertical, int horizontal) {
        FieldSize = new Position(vertical, horizontal);
        if (vertical % 2 == 1) {
            OddFlagForDistribution = true;
        }
        else {
            OddFlagForDistribution = false;
        }
    }

    /**
     * -------------- МЕТОД НАХОДИТСЯ В РАЗРАБОТКЕ --------------
     * Запись ошибок в файл для возможности повторного запуска
     *
     * @param a - список для сохранения
     * @param fileName - имя файла
     * @throws IOException - Input/Output Exception
     */
    private void ErrorsToFile(LinkedList<DictionaryPair> a, String fileName) throws IOException {
        if (a != null) {
            try (FileWriter out = new FileWriter(fileName)) {
                for (DictionaryPair err : a)
                    out.write(err.getFirst() + "\t" + err.getSecond() + "\n");
            }
        }
    }


    /**
     * Сохранение текущего прогресса, для дальнейшего продолжение
     *     // принимает fileName поданное пользователем?
     */
    void SaveProgress(){
        StringBuilder build = new StringBuilder();
        build.append(curLesson.getLessonName());
        build.setLength(build.length() - 4);
        build.append("(save).savepr");
        try (FileWriter save = new FileWriter(build.toString())) {
            save.write(curLesson.getLessonName() + "\n");           // Путь к используемому словарю
            save.write(offset + "\n");                              // Смещение в словаре
            save.write(NumElemOfMatrix.size() + " ");               // Количество матриц
            for (int a : NumElemOfMatrix)                               // Матричное распределение
                save.write(a + " ");
            save.write("\n");
            save.write(FieldSize.vertical + " " + FieldSize.horizontal); // Размер поля
            save.write("\n");
            for (Cell[] arr : Field) // Запись поля
            {
                for (Cell a : arr) {
                    if (a != null) { // Индекс пары в словаре + флаг
                        if (a.getFlag())
                            save.write(curLesson.Dictionary.indexOf(a.getPair()) + " 1 ");
                        else
                            save.write(curLesson.Dictionary.indexOf(a.getPair()) + " 0 ");
                    } else
                        save.write("-1 0 "); // Если ячейка пуста, то -1
                }
                save.write("\n");
            }
            Cell cell = LastMove.first;
// Последний сделанный ход
//--- Заменить на вызов функции
            if(cell.getFlag())
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 1 ");
            else
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 0 ");
            save.write(cell.getPosition().toString() + " ");
            cell = LastMove.second;
            if(cell.getFlag())
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 1 ");
            else
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 0 ");
            save.write(cell.getPosition().toString() + " ");
//---
            save.write("\n");
            save.write(NumCorrectAnsw + " " + LessonErr1.size() + "\n"); // Кол-во верных ответов, ошибок
            for (DictionaryPair a : LessonErr1) // Сохранение первого списка ошибок
                save.write(curLesson.Dictionary.indexOf(a) + " ");
            save.write("\n");
            for (DictionaryPair a : LessonErr2) // Сохранение второго списка ошибок
                save.write(curLesson.Dictionary.indexOf(a) + " ");
            save.write("\n");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка сохраненного прогресса
     *     // принимает fileName поданное пользователем?
     */
    void LoadProgress(String fileName){
        try (Scanner load = new Scanner(new File(fileName))) {
            if (curLesson == null) {
                curLesson = new Lesson();
            }
            curLesson.setLessonName(load.nextLine());                   // Путь к используемому словарю
            curLesson.Init(new File(curLesson.getLessonName()));        // Загрузка словаря
            prepareLesson();
            offset = load.nextInt();                                    // Смещение внутри словаря
            NumberOfSteps = load.nextInt();                             // Кол-во шагов
            NumElemOfMatrix.clear();
            for (int i = 0; i < NumberOfSteps; i++)                     // Матричное распределение
                NumElemOfMatrix.addLast(load.nextInt());
//            save.write("\n");
            FieldSize.vertical = load.nextInt();
            FieldSize.horizontal = load.nextInt();
//            save.write("\n");
            Field = new Cell[FieldSize.vertical][FieldSize.horizontal];
            for (int i = 0; i < FieldSize.vertical; i++)                      // Считывание поля
            {
                for (int j = 0; j < FieldSize.vertical; j++)
                {
                    int index = load.nextInt();
                    int flag = load.nextInt();
                    if (index != -1)
                    {                                                        // Индекс пары в словаре + флаг
                        if (flag == 1)
                            Field[i][j] = new Cell (curLesson.Dictionary.get(index), new Position(i, j), true);
                        else
                            Field[i][j] = new Cell (curLesson.Dictionary.get(index), new Position(i, j), false);
                    } else
                        Field[i][j] = null;
                }
//                save.write("\n");
            }
// Последний сделанный ход
//--- Заменить на вызов функции
            int index = load.nextInt();
            int flag = load.nextInt();
            int row = load.nextInt();
            int column = load.nextInt();
            if (flag == 1)
                Field[row][column] = new Cell (curLesson.Dictionary.get(index), new Position(row, column), true);
            else
                Field[row][column] = new Cell (curLesson.Dictionary.get(index), new Position(row, column), false);
            index = load.nextInt();
            flag = load.nextInt();
            row = load.nextInt();
            column = load.nextInt();
            if (flag == 1)
                Field[row][column] = new Cell (curLesson.Dictionary.get(index), new Position(row, column), true);
            else
                Field[row][column] = new Cell (curLesson.Dictionary.get(index), new Position(row, column), false);
//---
//            save.write("\n");
            NumCorrectAnsw = load.nextInt();
            int NumAllErr = load.nextInt();
            for (int i = 0; i < NumAllErr; i++)                         // Загрука первого списка ошибок
                LessonErr1.add(curLesson.Dictionary.get(load.nextInt()));
//            save.write("\n");
            for (int i = 0; i < NumAllErr; i++)                         // Загрузка второго списка ошибок
                LessonErr2.add(curLesson.Dictionary.get(load.nextInt()));
//            save.write("\n");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    void ErrorsTo(ActionErrorType actionType, NumErrorType numType) throws IOException {
        StringBuilder build = new StringBuilder();
        build.append(curLesson.getLessonName());
        build.setLength(build.length() - 4);
        LinkedList<DictionaryPair> LList = null;
        switch (numType) {
            case FIRST: {
                build.append("(err1).txt");
                LList = LessonErr1;
                break;
            }
            case SECOND: {
                build.append("(err2).txt");
                LList = LessonErr2;
                break;
            }
            case BOTH: {
                build.append("(allerr).txt");
                LList = LessonErr1;
                LList.addAll(LessonErr2);
                break;
            }
        }
        switch (actionType)
        {
            case SAVE:
            {
                ErrorsToFile(LList, build.toString());
            }
            case LOAD:
            {
                curLesson.LessonFromErrors(LList, build.toString());
                prepareLesson();
            }
        }
    }

    enum NumErrorType
    {
        FIRST,
        SECOND,
        BOTH
    }

    enum ActionErrorType
    {
        SAVE,
        LOAD
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

