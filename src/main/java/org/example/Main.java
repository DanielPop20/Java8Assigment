package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Main <inputFile> <month> <outputFile>");
            return;
        }

        String inputFile = args[0];
        int month;
        try {
            month = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid month. It must be an integer between 1 and 12.");
            return;
        }
        String outputFile = args[2];

        PersonService service = new PersonService();

        try {
            List<Person> persons = service.loadPersons(inputFile);
            List<Person> filteredAndSortedPersons = service.filterAndSortPersonsByMonth(persons, month);
            service.writePersonsToFile(filteredAndSortedPersons, outputFile);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}