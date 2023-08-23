package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.logger.BoardLogger;
import io.deeplay.intership.model.Stone;

/**
 * Класс {@code Display} отвечает за отображение игрового поля и различных сообщений пользователю.
 */
public class Display implements UserInterface{

    private static final int MIN_BORDER_VALUE = 1;
    private static final int DEFAULT_BORDER_MAX_VALUE = 9;


    /**
     * Показывает сообщение заголовка для пользовательского ввода.
     */
    public void showMoveRules() {
        System.out.print("Введите координаты нового камня через пробел\n");
        System.out.print("Формат записи: 1 6\n");
        String range = String.format("Диапазон допустимых значений для строки от %d до %d\n",
                MIN_BORDER_VALUE,
                DEFAULT_BORDER_MAX_VALUE);
        System.out.print(range);
        System.out.print("Введите координаты нового камня через пробел:\n");
    }


    /**
     * Показывает доступные действия для пользователя.
     */
    public void showGameActions() {
        showHorizontalLine();
        System.out.print("Выберите следущее действие:\n");
        System.out.print("Чтобы сделать ход нажмите " + 1 + "\n");
        System.out.print("Чтобы пропустить ход нажмите " + 2 + "\n");
        showHorizontalLine();
    }
    public void showLoginActions(){
        showHorizontalLine();
        System.out.print("Добро пожаловать на сервер ГО!\n");
        System.out.print("Выберите следущее действие:\n");
        System.out.print("Чтобы зарегестрироваться нажмите " + 1 + "\n");
        System.out.print("Чтобы авторизоваться нажмите " + 2 + "\n");
        showHorizontalLine();
    }
    public void showRegistration(){
        showHorizontalLine();
        System.out.print("Для регистрации введите новый логин и пароль через пробел:\n");
        showHorizontalLine();
    }
    public void showLogin(){
        showHorizontalLine();
        System.out.print("Для входа введите логин и пароль через пробел:\n");
        showHorizontalLine();
    }
    public void showRoomActions(){
        showHorizontalLine();
        System.out.print("Выберите игровую сессию\n");
        System.out.print("Выберите следующее действие:\n");
        System.out.print("Чтобы подключиться к существующей сессии нажмите " + 1 + "\n");
        System.out.print("Чтобы создать сессию нажмите " + 2 + "\n");
        showHorizontalLine();
    }
    public void showJoin(){
        System.out.print("Для входа в игру введите GameID:\n");
        showHorizontalLine();
    }
    public void showCreating(int gameId){
        showHorizontalLine();
        System.out.print("Создана игровая сессия под номером: " + gameId + "\n");
        showHorizontalLine();
    }
    /**
     * Показывает сообщение о состоянии ожидания.
     */
    public void showAwaitState() {
        showHorizontalLine();
        System.out.println("\nОжидание хода соперника...\n");
        showHorizontalLine();
    }

    /**
     * Показывает текущее состояние игрового поля на консоли с помощью {@link BoardLogger}.
     *
     * @param gameField игровое поле в виде массива{@link Stone}, которое нужно вывести на консоль
     */
    public void showBoard(final Stone[][] gameField) {
        showBoardNumeric(gameField);
        showHorizontalBorder(gameField);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < gameField.length; i++) {
            stringBuilder
                    .append(MIN_BORDER_VALUE + i)
                    .append("#");
            for (int j = 0; j < gameField[i].length; j++) {
                stringBuilder
                        .append(" ")
                        .append(switch (gameField[i][j].getColor()){
                            case BLACK -> "-";
                            case WHITE -> "+";
                            case EMPTY -> "0";
                            default -> "0";
                        })
                        .append(" ");
            }
            stringBuilder.append("#");
            if (i != (gameField.length - 1)) {
                stringBuilder.append("\n");
            }
        }
        System.out.println(stringBuilder);
        showHorizontalBorder(gameField);
    }


    /**
     * Показывает символьные обозначения столбцов игрового поля.
     *
     * @param gameField игровое поле в виде массива {@link Stone} для расчета границы
     */
    public void showBoardNumeric(final Stone[][] gameField) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = MIN_BORDER_VALUE; i < gameField.length + MIN_BORDER_VALUE; i++) {
            stringBuilder
                    .append(" ")
                    .append(i)
                    .append(" ");
        }
        System.out.println("  " + stringBuilder);
    }

    /**
     * Показывает горизонтальную границу игрового поля.
     *
     * @param gameField игровое поле в виде массива {@link Stone} для расчета границы
     */
    public void showHorizontalBorder(final Stone[][] gameField) {
        int symbolsForDisplay = 3;
        System.out.print(" " + "#".repeat(symbolsForDisplay * gameField.length + 2) + "\n");
    }

    /**
     * Показывает горизонтальную линию для разделения различных секций на консоли.
     */
    public void showHorizontalLine() {
        System.out.print("====================================================================\n");
    }

    public void showColorSelection() {
        System.out.print(
                "Для выбора белого цвета введите " + 1 + "\n");
        System.out.print(
                "Для выбора черного цвета введите " + 2 + "\n");
        System.out.print(
                "Для выбора случайного цвета введите " + 3 + "\n");
    }

    /**
     * Показывает результаты игры.
     */
    public void showGameResult(String result) {
        System.out.println("игра закончена.\n");
        System.out.println("Результаты игры:\n");
        System.out.print(result);
    }
}
