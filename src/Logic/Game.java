package Logic;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Game {
    private Cell[][] Field;                                        // Поле
    private Position FieldSize;                                    // Размеры поля
    private Lesson curLesson;                                      // Текущий урок
    private int NumberOfSteps;                                     // Количество этапов в уроке
    private LinkedList<DictionaryPair> LessonMistakes1;            // Список ошибок (верные пары левых)
    private LinkedList<DictionaryPair> LessonMistakes2;            // Список ошибок (верные пары правых)
    private int NumCorrectAnsw;                                    // Кол-во верных ответов на уроке
    private int localMistakes;                                         // Кол-во ошибок на этапе
    private int offset;                                            // Смещение относительно начала словаря
    private LinkedList<Integer> NumElemOfMatrix;                   // Распределение элементов по матрицам
    private Move LastMove;                                         // Последный ход
    private boolean MixFlag;                                       // Флаг перемешивания (при загрузке этапа)
    private boolean OddFlagForDistribution;                        // Флаг "плохого" размера поля (нечетная вертикаль)

    public Game(int vertical, int horizontal) {
        setSize(vertical, horizontal);
    }

    /**
     * @return - игровое поле
     */
    public Cell[][] getField() {
        return Field;
    }

    /**
     * @return - номер текущего этапа
     */
    public int getNumOfCurStep() {
        return !NumElemOfMatrix.isEmpty() ? NumberOfSteps - NumElemOfMatrix.size() + 1 : NumberOfSteps;
    }

    /**
     * @return - количество этапов урока
     */
    public int getNumberOfSteps() {
        return NumberOfSteps;
    }

    /**
     * @return - количество совершенных ошибок за урок
     */
    public int getNumMistakes() {
        return LessonMistakes1.size();
    }

    /**
     * @return - список пар левых ошибок
     */
    public LinkedList<DictionaryPair> getLessonMistakes1() {
        return LessonMistakes1;
    }

    /**
     * @return - список пар правых ошибок
     */
    public LinkedList<DictionaryPair> getLessonMistakes2() {
        return LessonMistakes2;
    }

    /**
     * Сеттер флага автоматического перемешивания
     *
     * @param mixFlag - true/false - перемешивать / не перемешивать
     */
    public void setMixFlag(boolean mixFlag) {
        MixFlag = mixFlag;
    }

    /**
     * Сеттер последнего хода
     *
     * @param lastMove - последний ход
     */
    private void setLastMove(Move lastMove) {
        LastMove = lastMove;
    }

    public Position getFieldSize() {
        return FieldSize;
    }

    /**
     * Сеттер размеров игрового поля
     *
     * @param horizontal - ширина
     * @param vertical   - высота
     */
    public void setSize(int vertical, int horizontal) {
        FieldSize = new Position(vertical, horizontal);
        OddFlagForDistribution = vertical % 2 == 1;

    }

    /**
     * Установка нового урока из файла
     *
     * @param file - файл-словарь
     */
    public boolean newLesson(File file) {

        if (curLesson == null) {
            curLesson = new Lesson();
        }
        if (curLesson.Init(file)) {
            prepareLesson();
            curLesson.MixDictionary();
            return true;
        } else
            return false;
    }

    /**
     * Подготовка урока к запуску
     */
    private void prepareLesson() {
        LessonMistakes1 = new LinkedList<>();
        LessonMistakes2 = new LinkedList<>();
        NumCorrectAnsw = 0;
        offset = 0;
        localMistakes = 0;
        NumElemOfMatrix = new LinkedList<>();
        NumberOfSteps = CalculateFields();  // рассчёт матричного заполнения
    }

    /**
     * Вычисление оптимального кол-ва элементов в матрицах, для умещения всего урока на "доске"
     */
    private int CalculateFields() {
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

    /**
     * Контроль установки потока полей урока - устанавливет следующее, если оно существует
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
            } else {
                int j = 0;
                while (j < FieldSize.vertical * FieldSize.horizontal) {
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
     * Перемешивание поля
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
     * Сравнение ячеек по поданным координатам
     *
     * @param a - первая ячейка
     * @param b - вторая ячейка
     * @return - true - если ячейки совместимы
     */
    public boolean compareCell(Position a, Position b) {
        DictionaryPair first = Field[a.vertical][a.horizontal].getPair();
        DictionaryPair second = Field[b.vertical][b.horizontal].getPair();
        boolean compareFlag = isCompatible(Field[a.vertical][a.horizontal], Field[b.vertical][b.horizontal]);
        setLastMove(new Move(Field[a.vertical][a.horizontal], Field[b.vertical][b.horizontal]));
        if (compareFlag) {
            Field[a.vertical][a.horizontal] = null;
            Field[b.vertical][b.horizontal] = null;
            NumCorrectAnsw++;
            if (NumCorrectAnsw == NumElemOfMatrix.getFirst()) {
                NumElemOfMatrix.removeFirst();
                localMistakes = 0;
                NumCorrectAnsw = 0;
                setLastMove(null);
                return true;
            }
        } else {
            localMistakes++;
            LessonMistakes1.addLast(first);
            LessonMistakes2.addLast(second);
        }
        return compareFlag;
    }

    /**
     * Проверяет совместимость ячеек
     *
     * @param first  - первая ячейка
     * @param second - вторая ячейка
     * @return - true - если совместимы
     */
    private boolean isCompatible(Cell first, Cell second) {
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        String firstStr;
        String secondStr;
        boolean flag1 = false;
        boolean flag2 = false;
        if (first.getFlag()) {
            // Если первая ячейка отображает правое слово
            firstStr = StrTransform.toNormalString(first.getPair().getSecond());
            secondStr = StrTransform.toNormalString(second.getPair().getFirst());
            for (int i = 0; i < curLesson.Dictionary.size(); i++) {
                String str1 = StrTransform.toNormalString(curLesson.Dictionary.get(i).getFirst());
                String str2 = StrTransform.toNormalString(curLesson.Dictionary.get(i).getSecond());
                if (firstStr.equals(str2)) {
                    left.add(str1);
                }
                if (secondStr.equals(str1)) {
                    right.add(str2);
                }
            }
            for (String a : left) {
                if (a.equals(secondStr)) {
                    flag1 = true;
                    break;
                }
                flag1 = false;
            }
            for (String a : right) {
                if (a.equals(firstStr)) {
                    flag2 = true;
                    break;
                }
                flag2 = false;
            }
        } else {
            // Если первая ячейка отображает левое слово
            firstStr = StrTransform.toNormalString(first.getPair().getFirst());
            secondStr = StrTransform.toNormalString(second.getPair().getSecond());
            for (int i = 0; i < curLesson.Dictionary.size(); i++) {
                String str1 = StrTransform.toNormalString(curLesson.Dictionary.get(i).getFirst());
                String str2 = StrTransform.toNormalString(curLesson.Dictionary.get(i).getSecond());
                if (firstStr.equals(str1)) {
                    right.add(str2);
                }
                if (secondStr.equals(str2)) {
                    left.add(str1);
                }
            }
            for (String a : left) {
                if (a.equals(firstStr)) {
                    flag1 = true;
                    break;
                }
                flag1 = false;
            }
            for (String a : right) {
                if (a.equals(secondStr)) {
                    flag2 = true;
                    break;
                }
                flag2 = false;
            }
        }
        return flag1 && flag2;
    }

    /**
     * Отмена последнего хода
     * - верного - уменьшение кол-ва верных ответов
     * - неверного - удаление из таблицы ошибок (уменьшение кол-ва ошибок)
     */
    public void undo() {
        if (LastMove != null) {
            Position f = LastMove.first.getPosition();
            Position s = LastMove.second.getPosition();
            Field[f.vertical][f.horizontal] = LastMove.first;
            Field[s.vertical][s.horizontal] = LastMove.second;
            if (isCompatible(LastMove.first, LastMove.second)) {
                NumCorrectAnsw--;
            } else {
                LessonMistakes1.removeLast();
                LessonMistakes2.removeLast();
                localMistakes--;
            }
            setLastMove(null);
        }
    }


    /**
     * Сохранение совершенных ошибок в файл
     */
    public void SaveMistakes(String fileName) {
        if (curLesson != null) {
            StringBuilder build = new StringBuilder();
            build.append(fileName + "-");
            build.append(curLesson.getLessonName());
            build.setLength(build.length() - 4);
            try {
                MistakesToFile(LessonMistakes1, build.toString() + "(err1).txt");
                MistakesToFile(LessonMistakes2, build.toString() + "(err2).txt");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Не удалось сохранить ошибки", "Ошибка сохранения ошибок", JOptionPane.ERROR_MESSAGE, null);
            }
        }
    }

    /**
     * @param a        - сохраняемый список
     * @param fileName - имя файла
     */
    private void MistakesToFile(LinkedList<DictionaryPair> a, String fileName) throws IOException {
        if (a != null) {
            //BufferedWriter out = null;
            try(Writer out = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)) {
                for (DictionaryPair err : a)
                    out.write(err.getFirst() + "\t" + err.getSecond() + "\n");
            }
        }
    }

    /**
     * Создает урок из поданного списка
     * @param list - список для создания урока
     * @param numType - тип списка
     */
    public void MistakesToLesson(LinkedList<DictionaryPair> list, NumMistakeType numType) {
        StringBuilder build = new StringBuilder();
        build.append(curLesson.getLessonName());
        build.setLength(build.length() - 4);
        switch (numType) {
            case FIRST: {
                build.append("(err1).txt");
                break;
            }
            case SECOND: {
                build.append("(err2).txt");
                break;
            }
            case BOTH: {
                build.append("(allerr).txt");
                break;
            }
        }
        curLesson.LessonFromList(list, build.toString());
        prepareLesson();
    }

    public enum NumMistakeType {
        FIRST,
        SECOND,
        BOTH
    }

    /**
     * Сохранение текущего прогресса, для дальнейшего продолжение
     * // принимает fileName поданное пользователем?
     */
    public boolean SaveProgress(String fileName, int TimeSec) {
        String saveName = fileName + ".savepr";
        try(Writer save = new OutputStreamWriter(new FileOutputStream(saveName), StandardCharsets.UTF_8)) {
            save.write(curLesson.getLessonName() + "\n");                   // Название урока
            save.write(curLesson.Dictionary.size() + " " + offset + "\n");      // Кол-во слов словаря, смещение в словаре
            for (int i = 0; i < curLesson.Dictionary.size(); i++)          // Пары словарь
                save.write(curLesson.Dictionary.get(i).toString() + "\n");
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
            SaveLastMove(save);
            save.write("\n");
            save.write(TimeSec + " " + NumCorrectAnsw + " " + LessonMistakes1.size() + "\n");  // Значение таймера, кол-во верных ответов, ошибок
            for (DictionaryPair a : LessonMistakes1) // Сохранение первого списка ошибок
                save.write(curLesson.Dictionary.indexOf(a) + " ");
            save.write("\n");
            for (DictionaryPair a : LessonMistakes2) // Сохранение второго списка ошибок
                save.write(curLesson.Dictionary.indexOf(a) + " ");
            save.write("\n");
        } catch (IOException e) {
            // Сообщение о неудачном сохранении
            JOptionPane.showMessageDialog(null, "Не удалось сохранить прогресс текущего урока", "Ошибка сохранения прогресса", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        return true;
    }

    /**
     * Сохраняет последний ход игрока
     *
     * @param save - FileWriter
     * @throws IOException - Input/Output Exception
     */
    private void SaveLastMove(Writer save) throws IOException {
        if (LastMove != null) {
            Cell cell = LastMove.first;
            if (cell.getFlag())
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 1 ");
            else
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 0 ");
            save.write(cell.getPosition().toString() + " ");
            cell = LastMove.second;
            if (cell.getFlag())
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 1 ");
            else
                save.write(curLesson.Dictionary.indexOf(cell.getPair()) + " 0 ");
            save.write(cell.getPosition().toString() + " ");
        } else
            save.write("-1"); // В случае, если последнего хода нет
    }

    /**
     * Загрузка сохраненного прогресса
     * // принимает fileName поданное пользователем?
     */
    public int LoadProgress(String fileName) {
        int TimeSec;
        try (Scanner load = new Scanner(new File(fileName), "UTF-8")) {
            curLesson = new Lesson();
            curLesson.setLessonName(load.nextLine());               // Название урока
            curLesson.Dictionary = new ArrayList<>();
            prepareLesson();
            int SizeDict = load.nextInt();                          // Кол-во слов словаря
            offset = load.nextInt();                                // Смещение в словаре
            load.nextLine();
            for (int i = 0; i < SizeDict; i++)                      // Пары словаря
                curLesson.Dictionary.add(DictionaryPair.readPair(load));
            NumberOfSteps = load.nextInt();                         // Кол-во шагов
            NumElemOfMatrix.clear();
            for (int i = 0; i < NumberOfSteps; i++)                     // Матричное распределение
                NumElemOfMatrix.addLast(load.nextInt());
            load.nextLine();
            FieldSize.vertical = load.nextInt();
            FieldSize.horizontal = load.nextInt();
            load.nextLine();
            Field = new Cell[FieldSize.vertical][FieldSize.horizontal];
            for (int i = 0; i < FieldSize.vertical; i++)                      // Считывание поля
            {
                for (int j = 0; j < FieldSize.vertical; j++) {
                    int index = load.nextInt();
                    int flag = load.nextInt();
                    if (index != -1) {                                                        // Индекс пары в словаре + флаг
                        if (flag == 1)
                            Field[i][j] = new Cell(curLesson.Dictionary.get(index), new Position(i, j), true);
                        else
                            Field[i][j] = new Cell(curLesson.Dictionary.get(index), new Position(i, j), false);
                    } else
                        Field[i][j] = null;
                }
            }
            LastMove = readSaveMove(load);          // Последний сделанный ход
            load.nextLine();
            TimeSec = load.nextInt();
            NumCorrectAnsw = load.nextInt();
            int NumAllMistakes = load.nextInt();
            for (int i = 0; i < NumAllMistakes; i++)                         // Загрука первого списка ошибок
                LessonMistakes1.add(curLesson.Dictionary.get(load.nextInt()));
            load.nextLine();
            for (int i = 0; i < NumAllMistakes; i++)                         // Загрузка второго списка ошибок
                LessonMistakes2.add(curLesson.Dictionary.get(load.nextInt()));
        } catch (IOException | InputMismatchException e) {
            return -1;
        }
        return TimeSec;
    }

    /**
     * Считывает сохраненный последний ход игрока из потока
     *
     * @param load - сканнер потока
     * @return - объект класса Move
     */
    private Move readSaveMove(Scanner load) {
        Move move = null;
        int index = load.nextInt();
        if (index != -1) {
            move = new Move();
            int flag = load.nextInt();
            int row = load.nextInt();
            int column = load.nextInt();
            if (flag == 1)
                move.first = new Cell(curLesson.Dictionary.get(index), new Position(row, column), true);
            else
                move.first = new Cell(curLesson.Dictionary.get(index), new Position(row, column), false);
            index = load.nextInt();
            flag = load.nextInt();
            row = load.nextInt();
            column = load.nextInt();
            if (flag == 1)
                move.second = new Cell(curLesson.Dictionary.get(index), new Position(row, column), true);
            else
                move.second = new Cell(curLesson.Dictionary.get(index), new Position(row, column), false);
        }
        return move;
    }
}
