package io.deeplay.intership.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestCounterOfStones {
    @Test
    public void testCountCapturedEmptyStones(){
        String s = "EEBEEEEEWEEBEEEEEWEEBBEEWWEEWEEEEWEEWEEWEEEEWEWEEEBBEEEWEEEBEBEEEEEEBEEEBEEEEBEEEEEEEEEEEEEEEEEEEEEE";
        CounterOfStones counterOfStones = new CounterOfStones(s, 10);
        counterOfStones.countCapturedEmptyStones();
        assertEquals(3, counterOfStones.getBlackPoints());
        assertEquals(7, counterOfStones.getWhitePoints());
    }
}
