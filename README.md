# Basket splitter
Basket splitter is designed to split a list of items from customer's basket into multiple groups based on predefined delivery methods specified in a configuration file. It aims to minimize the number of groups while also maximizing the size of the largest group.

## Usage
1. Instantiate the BasketSplitter: Create an instance of BasketSplitter by providing the absolute path to the configuration file containing delivery methods.
```java
BasketSplitter basketSplitter = new BasketSplitter("path/to/config.json");
```

2. Split the items: Use the `split()` method to split a list of items based on the configured delivery methods.
```java
List<String> items = new ArrayList<>(Arrays.asList("Carrots (1kg)", "Cold Beer (330ml)", "Steak (300g)"));
Map<String, List<String>> splitItems = basketSplitter.split(items);
```

## Config file
The configuration file (specified by the absolute path during instantiation) should be in JSON format, defining delivery methods for each item. Example:
```json
{
  "Carrots (1kg)": ["Express Delivery", "Click&Collect"],
  "Cold Beer (330ml)": ["Express Delivery"],
  "Steak (300g)": ["Express Delivery", "Click&Collect"],
  "AA Battery (4 Pcs.)": ["Express Delivery", "Courier"],
  "Espresso Machine": ["Courier", "Click&Collect"],
  "Garden Chair": ["Courier"]
}
```

## Output
The `split()` method of the class returns a list of items grouped by delivery methods.

## Dependencies
Jackson - JSON parsing

### Java Version
Basket splitter is written in Java 17.

## Build
Basket splitter uses Maven as a build tool.
```console
mvn install
```
to install it into your local maven repository or
```console
mvn package
```
to package it into a jar with all the dependencies
