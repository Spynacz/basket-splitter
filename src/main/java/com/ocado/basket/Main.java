package com.ocado.basket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("./basket-split config basket");
            return;
        }

        BasketSplitter bSplitter = new BasketSplitter(args[0]);
        List<String> items = new ArrayList<>();

        try {
            items = new ObjectMapper().readValue(
                    new File(args[1]),
                    new TypeReference<List<String>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<String>> res = bSplitter.split(items);
        for (Map.Entry<String, List<String>> entry : res.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (String s : entry.getValue())
                System.out.print(s + ", ");
            System.out.println();
        }
    }
}
