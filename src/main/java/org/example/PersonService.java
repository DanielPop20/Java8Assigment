package org.example;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PersonService {

    private static final Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> loadPersons(String inputFile) throws IOException {
        try {
            return Files.lines(Paths.get(inputFile))
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length != 3) {
                            throw new IllegalArgumentException("Invalid data format: " + line);
                        }
                        return new Person(parts[0], parts[1], LocalDate.parse(parts[2]));
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.severe("Invalid data format: " + e.getMessage());
            throw e;
        }
    }

    public List<Person> filterAndSortPersonsByMonth(List<Person> persons, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }

        return persons.stream()
                .filter(person -> person.getDateOfBirth().getMonth() == Month.of(month))
                .sorted(Comparator.comparing(Person::getFirstName)
                        .thenComparing(Person::getLastName))
                .collect(Collectors.toList());
    }

    public void writePersonsToFile(List<Person> persons, String outputFile) throws IOException {
        try {
            Files.write(Paths.get(outputFile),
                    persons.stream()
                            .map(person -> person.getFirstName() + " " + person.getLastName())
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            logger.severe("Error writing to file: " + e.getMessage());
            throw e;
        }
    }
}