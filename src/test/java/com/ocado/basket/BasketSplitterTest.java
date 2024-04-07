package com.ocado.basket;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasketSplitterTest {
    private BasketSplitter basketSplitter;
    private File resourcesDir = new File("src/test/resources/com/ocado/basket");

    @BeforeEach
    void setUp() {
        String configFilePath = resourcesDir.getAbsolutePath() + "/config.json";
        basketSplitter = new BasketSplitter(configFilePath);
    }

    @Test
    void testSplit_EmptyItemList() {
        List<String> items = new ArrayList<>();
        Map<String, List<String>> result = basketSplitter.split(items);

        assertTrue(result.isEmpty(), "Expected result to be empty");
    }

    @Test
    void testSplit_SingleItemSingleMethod() {
        List<String> items = new ArrayList<>(Arrays.asList("Cheese - Sheep Milk"));
        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(1, result.size(), "Expected only one method in the result");
        assertTrue(result.containsKey("Mailbox delivery"), "Expected result to contain Mailbox delivery");
    }

    @Test
    void testSplit_BasketOne() {
        List<String> items = new ArrayList<>();
        try {
            items = new ObjectMapper().readValue(new File(resourcesDir.getAbsolutePath() + "/basket-1.json"),
                    new TypeReference<List<String>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(2, result.size(), "Expected two methods in the result");
        assertAll(
                () -> assertTrue(result.containsKey("Pick-up point"), "Expected result to contain Pick-up point"),
                () -> assertTrue(result.containsKey("Courier"), "Expected result to contain Courier"));
        assertAll(
                () -> assertEquals(1, result.get("Pick-up point").size()),
                () -> assertEquals(5, result.get("Courier").size()));
    }

    @Test
    void testSplit_BasketTwo() {
        List<String> items = new ArrayList<>();
        try {
            items = new ObjectMapper().readValue(new File(resourcesDir.getAbsolutePath() + "/basket-2.json"),
                    new TypeReference<List<String>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<String>> result = basketSplitter.split(items);

        assertEquals(3, result.size(), "Expected three methods in the result");
        assertAll(
                () -> assertTrue(result.containsKey("Same day delivery"), "Expected result to contain Pick-up point"),
                () -> assertTrue(result.containsKey("Courier"), "Expected result to contain Courier"),
                () -> assertTrue(result.containsKey("Express Collection"),
                        "Expected result to contain Express Collection"));
        assertAll(
                () -> assertEquals(3, result.get("Same day delivery").size()),
                () -> assertEquals(1, result.get("Courier").size()),
                () -> assertEquals(13, result.get("Express Collection").size()));
    }
}
