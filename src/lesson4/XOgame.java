package lesson4;

import java.util.Random;
import java.util.Scanner;

public class XOgame {
    static final int SIZE = 7;
    static final int DOT_TO_WIN = 4;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    static char[][] map;

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Вы победили! Поздравляем!");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья");
                break;
            }

            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Компьютер победил.");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья");
                break;
            }
        }

    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        System.out.print("  ");
        for (int i = 1; i <= map.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.printf("%c ", map[i][j]);
            }
            System.out.println();
        }
    }

    public static void humanTurn() {
        int x;
        int y;

        do {
            System.out.println("input X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(y, x));

        map[y][x] = DOT_X;
    }

    public static boolean isCellValid(int y, int x) {
        if (y < 0 || x < 0 || y >= SIZE || x >= SIZE) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    public static void aiTurn() {
        int x;
        int y;

        if (isChoicePlace()) {
            return;
        } else {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            }
            while (!isCellValid(y, x));
            map[y][x] = DOT_O;
        }
    }

    /**
     * 3. *** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.
     */

    private static boolean isChoicePlace() { // ставит нолик, если находит (DOT_TO_WIN - 1) символов соперника и 1 пустую ячейку в ряду
        int count = 0;
        int countEmpty = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // считаем количество символов соперника ВПРАВО ВВЕРХ и чтобы был 1 символ пустой
                if (i + 1 - DOT_TO_WIN >= 0 && j + DOT_TO_WIN <= SIZE) {
                    for (int k = 0; k < DOT_TO_WIN; k++) {
                        if (map[i - k][j + k] == DOT_X) {
                            count++;
                        }
                        if (map[i - k][j + k] == DOT_EMPTY) {
                            countEmpty++;
                            x = i - k;
                            y = j + k;
                        }
                        if (count == DOT_TO_WIN - 1 && countEmpty == 1) {
                            map[x][y] = DOT_O;
                            return true;
                        }
                    }
                    count = 0;
                    countEmpty = 0;
                }
                // считаем количество символов соперника ВПРАВО и чтобы был 1 символ пустой
                if (j + DOT_TO_WIN <= SIZE) {
                    for (int k = 0; k < DOT_TO_WIN; k++) {
                        if (map[i][j + k] == DOT_X) {
                            count++;
                        }
                        if (map[i][j + k] == DOT_EMPTY) {
                            countEmpty++;
                            x = i;
                            y = j + k;
                        }
                        if (count == DOT_TO_WIN - 1 && countEmpty == 1) {
                            map[x][y] = DOT_O;
                            return true;
                        }
                    }
                    count = 0;
                    countEmpty = 0;
                }
                // считаем количество символов соперника ВПРАВО ВНИЗ и чтобы был 1 символ пустой
                if (i + DOT_TO_WIN <= SIZE && j + DOT_TO_WIN <= SIZE) {
                    for (int k = 0; k < DOT_TO_WIN; k++) {
                        if (map[i + k][j + k] == DOT_X) {
                            count++;
                        }
                        if (map[i + k][j + k] == DOT_EMPTY) {
                            countEmpty++;
                            x = i + k;
                            y = j + k;
                        }
                        if (count == DOT_TO_WIN - 1 && countEmpty == 1) {
                            map[x][y] = DOT_O;
                            return true;
                        }
                    }
                    count = 0;
                    countEmpty = 0;
                }
                // считаем количество символов соперника ВНИЗ и чтобы был 1 символ пустой
                if (i + DOT_TO_WIN <= SIZE) {
                    for (int k = 0; k < DOT_TO_WIN; k++) {
                        if (map[i + k][j] == DOT_X) {
                            count++;
                        }
                        if (map[i + k][j] == DOT_EMPTY) {
                            countEmpty++;
                            x = i + k;
                            y = j;
                        }
                        if (count == DOT_TO_WIN - 1 && countEmpty == 1) {
                            map[x][y] = DOT_O;
                            return true;
                        }
                    }
                    count = 0;
                    countEmpty = 0;
                }

            }
        }
        return false;
    }

    public static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ниже реализация задачи №1: Переделать проверку победы, чтобы она не была реализована просто набором условий,
     * например, с использованием циклов.
     * Будет скрыта, потому что есть Задача №2.
     * @param c
     * @return
     */
//    public static boolean checkWin(char c) {
//        int countRoll = 0; //счетчик символов "char c" в строке
//        int countColumn = 0; //счетчик символов "char c" в столбце
//        int countDiagMain = 0; //счетчик символов "char c" в главной диагонали
//        int countDiagSide = 0; //счетчик символов "char c" в побочной диагонали
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                if (map[i][j] == c) {
//                    countRoll++;
//                }
//                if (map[j][i] == c) {
//                    countColumn++;
//                }
//                if (countColumn == SIZE || countRoll == SIZE) {
//                    return true;
//                }
//            }
//            countColumn = 0;
//            countRoll = 0;
//            if (map[i][i] == c) {
//                countDiagMain++;
//            }
//            if (map[i][SIZE - 1 - i] == c) {
//                countDiagSide++;
//            }
//            if (countDiagMain == SIZE || countDiagSide == SIZE) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    /**
     * 2. * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4
     *
     * @param c
     * @return
     */
    public static boolean checkWin(char c) {
        int count = 0; // счетчик количества символов
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i + 1 - DOT_TO_WIN >= 0 && j + DOT_TO_WIN <= SIZE) { // считаем количество символов вправо вверх
                    for (int k = 0; k < DOT_TO_WIN; k++) {
                        if (map[i - k][j + k] == c) {
                            count++;
                        }
                        if (count == DOT_TO_WIN) {
                            return true;
                        }
                    }
                    count = 0;
                }
                if (j + DOT_TO_WIN <= SIZE) { // считаем количество символов вправо
                    for (int k = 0; k < DOT_TO_WIN; k++) {
                        if (map[i][j + k] == c) {
                            count++;
                        }
                        if (count == DOT_TO_WIN) {
                            return true;
                        }
                    }
                    count = 0;
                }
                if (i + DOT_TO_WIN <= SIZE && j + DOT_TO_WIN <= SIZE) { // считаем количество символов вправо вниз
                    for (int k = 0; k < DOT_TO_WIN; k++) {
                        if (map[i + k][j + k] == c) {
                            count++;
                        }
                        if (count == DOT_TO_WIN) {
                            return true;
                        }
                    }
                    count = 0;
                }
                if (i + DOT_TO_WIN <= SIZE) {
                    for (int k = 0; k < DOT_TO_WIN; k++) { // считаем количество символов вниз
                        if (map[i + k][j] == c) {
                            count++;
                        }
                        if (count == DOT_TO_WIN) {
                            return true;
                        }
                    }
                    count = 0;
                }
            }
        }
        return false;
    }

}
