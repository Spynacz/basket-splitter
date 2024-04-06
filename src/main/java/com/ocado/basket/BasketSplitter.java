package com.ocado.basket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasketSplitter {
    Map<String, List<String>> deliveryMethods;

    public BasketSplitter(String absolutePathToConfigFile) {
        try {
            deliveryMethods = new ObjectMapper().readValue(new File(absolutePathToConfigFile),
                    new TypeReference<Map<String, List<String>>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, List<String>> result = new HashMap<>();
        Map<String, Integer> methodCount = new HashMap<>();

        for (String item : items) {
            List<String> methods = deliveryMethods.get(item);
            for (String method : methods) {
                methodCount.put(method, methodCount.getOrDefault(method, 0) + 1);
            }
        }

        while (!items.isEmpty()) {
            String bestMethod = Collections.max(methodCount.entrySet(), (Map.Entry<String, Integer> e1,
                    Map.Entry<String, Integer> e2) -> e1.getValue().compareTo(e2.getValue())).getKey();

            ListIterator<String> iter = items.listIterator();
            while (iter.hasNext()) {
                String item = iter.next();
                if (deliveryMethods.get(item).contains(bestMethod)) {
                    result.putIfAbsent(bestMethod, new ArrayList<>());
                    result.get(bestMethod).add(item);

                    for (String method : deliveryMethods.get(item))
                        methodCount.put(method, methodCount.get(method) - 1);

                    iter.remove();
                }
            }
            methodCount.remove(bestMethod);
        }
        return result;
    }
}
