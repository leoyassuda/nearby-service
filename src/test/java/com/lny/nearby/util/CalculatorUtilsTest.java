package com.lny.nearby.util;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class CalculatorUtilsTest {

    @Test
    void distanceOfTwoPointsInPaulistaAvenueRegion() {
        // For this test, it was used 2 points next to Av. Paulista, as you can see in the link below.
        // https://www.google.com.br/maps/dir/'-23.5668698,-46.6608874'/Av.+Paulista,+1499+-+Bela+Vista,+S%C3%A3o+Paulo+-+SP,+01311-200/@-23.5644934,-46.6605196,17z/am=t/data=!4m12!4m11!1m3!2m2!1d-46.6608874!2d-23.5668698!1m5!1m1!1s0x94ce59c92c6198bb:0x2be6435aa0a510ff!2m2!1d-46.6558034!2d-23.5622624!3e3

        double result = CalculatorUtils.distance(-23.5668698, -46.6608874, -23.5622867, -46.65593250000001, "K")
                * 1000;
        result = CalculatorUtils.roundDouble(result, 2);

        double expect = 717.43;

        assertEquals(result, expect, "the distance assertion is wrong");
    }

    @Test
    void validateRoundingDouble() {
        double input = 3.12345;
        double expect = 3.12;
        assertEquals(CalculatorUtils.roundDouble(input, 2), expect, "the rounding is wrong");
    }

}