package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;

/**
 * Класс {@code  CheckGameOver} содержит методы проверки окончания игры.
 * Он отслеживает количество черных камней, белых камней и статус пропуска ходов.
 */
public class CheckGameOver {
    private static final int DEFAULT_STONE_COUNT = 160;
    private boolean hasOneSkip;
    private int blackStonesCount;
    private int whiteStonesCount;

    public CheckGameOver() {
        hasOneSkip = false;
        this.blackStonesCount = DEFAULT_STONE_COUNT;
        this.whiteStonesCount = DEFAULT_STONE_COUNT;
    }

    /**
     * Проверяет, можно ли сделать ход указанным цветом. Уменьшает количество камней указанного цвета.
     *
     * @param color {@link Color} цвет игрока, делающего ход
     * @return {@code true}, если ход можно сделать, иначе {@code false}
     * @throws {@link IllegalStateException}, если цвет не черный или белый
     */
    public boolean canMakeMove(Color color) {
        return switch (color) {
            case BLACK -> hasStones(--blackStonesCount);
            case WHITE -> hasStones(--whiteStonesCount);
            default -> throw new IllegalArgumentException("Незарегестрированное значение: " + color);
        };
    }

    /**
     * Проверяет, можно ли пропустить ход.
     *
     * @return {@code true}, если ход можно пропустить, иначе {@code false}
     */
    public boolean canSkipTurn() {
        if (!hasOneSkip) {
            hasOneSkip = true;
            return true;
        }
        return false;
    }

    /**
     * Сбрасывает счетчик пропусков на {@code false}.
     */
    public void resetSkipCount() {
        hasOneSkip = false;
    }

    /**
     * Проверяет, остались ли камни, чтобы походить.
     *
     * @param stoneNumber количество камней
     * @return {@code true}, если остались камни, иначе {@code false}
     */
    private boolean hasStones(int stoneNumber) {
        return stoneNumber >= 0;
    }
}
