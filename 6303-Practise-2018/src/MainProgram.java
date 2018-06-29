import java.io.IOException;

public class MainProgram {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Привет, мой дорогой Мир");
        Game MainGame = new Game();
        System.out.println("Загрузка урока");
        MainGame.prepareLesson("data/lesson2.txt");
        MainGame.startLesson();
    }
}

/*
    Старт урока:
        - рассчёт матричного заполнения
        - шаг урока
            - подготовка поля для запоминания на шаге
            - начало шага урока (перемешивание и угадайка)
            - если шаги урока ещё есть, то запуск следующего после завершения старого шага
        - завершение урока
 */
