package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.logger.BoardLogger;

/**
 * Класс {@code Display} отвечает за отображение игрового поля и различных сообщений пользователю.
 * Он использует {@link Converter} преобразования данных.
 */
public class Display {

    private static final int MIN_BORDER_VALUE = 1;
    private static final int DEFAULT_BORDER_MAX_VALUE = 9;
    private final Converter converter;

    public Display() {
        this.converter = new Converter();
    }

    /**
     * Показывает сообщение заголовка для пользовательского ввода.
     */
    public void showMoveRules() {
        System.out.print("Введите координаты нового камня через пробел\n");
        System.out.print("Формат записи: 1 d\n");
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
        System.out.print("Чтобы сделать ход нажмите " + UserAction.MOVE.getActionCode() + "\n");
        System.out.print("Чтобы пропустить ход нажмите " + UserAction.SKIP.getActionCode() + "\n");
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
     * @param board игровое поле {@link Board}, которое нужно вывести на консоль
     */
    public void showBoard(final Board board) {
        showBoardNumeric(board);
        showHorizontalBorder(board);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < board.getField().length; i++) {
            stringBuilder
                    .append(MIN_BORDER_VALUE + i)
                    .append("#");
            for (int j = 0; j < board.getField()[i].length; j++) {
                stringBuilder
                        .append(" ")
                        .append(converter.getStoneSymbol(board.getField()[i][j]))
                        .append(" ");
            }
            stringBuilder.append("#");
            if (i != (board.getField().length - 1)) {
                stringBuilder.append("\n");
            }
        }
        System.out.println(stringBuilder);
        showHorizontalBorder(board);
    }


    /**
     * Показывает символьные обозначения столбцов игрового поля.
     *
     * @param board игровое поле {@link Board} для расчета границы
     */
    public void showBoardNumeric(final Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = MIN_BORDER_VALUE; i < board.getField().length + MIN_BORDER_VALUE; i++) {
            stringBuilder
                    .append(" ")
                    .append(converter.convertIntToChar(i))
                    .append(" ");
        }
        System.out.println("  " + stringBuilder);
    }

    /**
     * Показывает горизонтальную границу игрового поля.
     *
     * @param board игровое поле {@link Board} для расчета границы
     */
    public void showHorizontalBorder(final Board board) {
        int symbolsForDisplay = 3;
        System.out.print(" " + "#".repeat(symbolsForDisplay * board.getField().length + 2) + "\n");
    }

    /**
     * Показывает горизонтальную линию для разделения различных секций на консоли.
     */
    public void showHorizontalLine() {
        System.out.print("====================================================================\n");
    }

    public void showColorSelection() {
        System.out.print(
                "Для выбора белого цвета введите " + UserAction.CHOOSE_WHITE_COLOR.getActionCode() + "\n");
        System.out.print(
                "Для выбора черного цвета введите " + UserAction.CHOOSE_BLACK_COLOR.getActionCode() + "\n");
        System.out.print(
                "Для выбора случайного цвета введите " + UserAction.CHOOSE_EMPTY_COLOR.getActionCode() + "\n");
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
