import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Game{
    private Lesson curLesson;   // Текущий урок
    private int NumError = 0;
    private int NumCorrectAnsw = 0;
    private int offset = 0;
    DictPair[][] Field;
    LinkedList <Integer> NumElemOfMatrix;
    private int horizontal = 4;
    private int vertical = 4;

    /**
     * Делает текущим урок из поданного файла
     * @param fileName
     * @throws IOException
     */
    public void prepareLesson(String fileName) throws IOException {
        if (curLesson == null) {
            curLesson = new Lesson();
        }
        curLesson.Init(fileName);
        CalculateFields();  // рассчёт матричного заполнения
        NumError = 0;
        NumCorrectAnsw = 0;
    }

    /**
     * Запуск шага урока - перемешивание + угадайка
     */
    void startLesson() throws InterruptedException {
        System.out.println("Запуск урока");
        while (nextField()) {
            System.out.println("Загрузка шага урока (этап запоминание)");
            System.out.println(this);
            System.out.println("Шаг урока (этап угадайка)");
            randomField();
            System.out.println(this);
            NumCorrectAnsw = 0;
            int Errors = 0;
            int NumElem = NumElemOfMatrix.getFirst();
            NumElemOfMatrix.removeFirst();
            Scanner input = new Scanner(System.in);
            while (NumCorrectAnsw != NumElem) {
                int a1 = input.nextInt();
                int b1 = input.nextInt();
                int a2 = input.nextInt();
                int b2 = input.nextInt();
                if (Field[a1][b1] != null && Field[a2][b2] != null) {   // Если хотел соединить с пустой - ничего не делаем
                    if (compareCell(Field[a1][b1], Field[a2][b2])) {
                        NumCorrectAnsw++;
                        Field[a1][b1] = Field[a2][b2] = null;
                        System.out.println(this);
                    } else {
                        Errors++;
                        System.out.println("Ошибка - 1 сек");
                        Thread.sleep(1000);
                        System.out.println("Время ошибки прошло");
                    }
                }
            }
            System.out.println("Количество ошибок на данном шаге = " + Errors + "\n");
            NumError += Errors;
            // Конец шага урока
        }
        System.out.println("Количество ошибок за урок = " + NumError);
        // Конец урока
    }

    boolean compareCell(DictPair a, DictPair b)
    {
        return (a != null && b != null)&&(a.getFirst().equals(b.getFirst()))||(a.getSecond().equals(b.getSecond()));
    }

    /**
     * Вычисляет оптимальное кол-во элементов в матрицах, для умещения всего урока на "доске"
     */
    void CalculateFields()
    {
        int NumElem = curLesson.Dictionary.size();  // всего элементов
        int MatrixElem = horizontal*vertical/2;       // пар в матрице заданного размера
        int NumOfMatrix = (int)Math.ceil((double) NumElem / MatrixElem);     // количество необходимых матриц
        int AverageNumberOfCells = NumElem;
        if (NumElem > MatrixElem)
            AverageNumberOfCells = NumElem/NumOfMatrix;
        this.NumElemOfMatrix = new LinkedList<>();
        int delta = NumElem - AverageNumberOfCells*NumOfMatrix;
        for (int i =0; i < NumOfMatrix; i++)
        {
            int Num = AverageNumberOfCells;     // Рассчёт оптимального кол-ва элементов в матрице
            if (delta > 0) {
                Num++;
                delta--;
            }
            this.NumElemOfMatrix.add(Num);
        }

    }

    /**
     * Контролирует уставноку потока полей урока - устанавливет следующее, если оно существует
     * @return true - если поля ещё есть (и после выполнения очередное поле выставлено), false - если полей урока больше нет
     */
    public boolean nextField()
    {
        if (!NumElemOfMatrix.isEmpty())
        {
            Field = new DictPair[vertical][horizontal];
            int NumElem = NumElemOfMatrix.getFirst();
            //NumElemOfMatrix.removeFirst();        -- удаление происходит при начале шага урока
            int i = offset;
            for (int j = 0; j < Field.length/2; j++)
                for (int k = 0; k < Field[j].length; k++)
                {
                    if (i < curLesson.Dictionary.size()) {
                        DictPair first = curLesson.Dictionary.get(i);
                        i++;
                        DictPair second = first.clone(); // Для второй ячейки в матрице
                        second.setFlag(true);
                        Field[j][k] = first;
                        Field[Field.length/2 + j][k] = second;
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
    void randomField()
    {
        for (int i = 0; i < Field.length; i++)
            for (int j = 0; j < Field[i].length; j++)
            {
                Random rand = new Random();
                int a = rand.nextInt(Field.length);
                int b = rand.nextInt(Field[i].length);
                DictPair tmp = Field[a][b];
                Field[a][b] = Field[i][j];
                Field[i][j] = tmp;
            }
    }

    /**
     * Устанавливает размеры поля
     * @param horizontal
     * @param vertical
     */
    public void setSize(int horizontal, int vertical) {
        if (horizontal*vertical % 2 == 1) {
            System.out.println("Указанный размер поля невозможен - нечетное количество ячеек");
            this.horizontal = this.vertical = 4;
        }
        else
        {
            this.horizontal = horizontal;
            this.vertical = vertical;
        }
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        for (DictPair[] array: Field) {
            for (DictPair a: array) {
                if (a != null) {
                    if (!a.getFlag())
                        build.append(a.getFirst() + "\t");
                    else
                        build.append(a.getSecond() + "\t");
                }
                else
                    build.append(0 + "\t");
            }
            build.append("\n");
        }
        return build.toString();
    }
}
