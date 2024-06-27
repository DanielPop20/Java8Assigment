package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    @Test
    public void testLoadPersons() throws IOException {
        PersonService service = new PersonService();
        List<Person> persons = service.loadPersons("input.txt");

        assertEquals(3, persons.size());
        assertEquals("Ion", persons.get(0).getFirstName());
        assertEquals("Ionescu", persons.get(0).getLastName());
        assertEquals(LocalDate.of(1990, 1, 15), persons.get(0).getDateOfBirth());
    }

    @Test
    public void testFilterAndSortPersonsByMonth() {
        PersonService service = new PersonService();
        List<Person> persons = List.of(
                new Person("Ion", "Ionescu", LocalDate.of(1990, 1, 15)),
                new Person("Nicu", "Niculescu", LocalDate.of(1992, 1, 25)),
                new Person("Vasile", "Vasilescu", LocalDate.of(1991, 2, 20))
        );

        List<Person> filtered = service.filterAndSortPersonsByMonth(persons, 1);

        assertEquals(2, filtered.size());
        assertEquals("Nicu Niculescu", filtered.get(0).toString());
        assertEquals("Ion Ionescu", filtered.get(1).toString());
    }

    @Test
    public void testWritePersonsToFile() throws IOException {
        PersonService service = new PersonService();
        List<Person> persons = List.of(
                new Person("Ion", "Ionescu", LocalDate.of(1990, 1, 15)),
                new Person("Nicu", "Niculescu", LocalDate.of(1992, 1, 25))
        );

        service.writePersonsToFile(persons, "output.txt");

        List<String> lines = Files.readAllLines(Paths.get("output.txt"));
        assertEquals(2, lines.size());
        assertEquals("Ion Ionescu", lines.get(0));
        assertEquals("Nicu Niculescu", lines.get(1));
    }

    @Test
    public void testInvalidMonth() {
        PersonService service = new PersonService();
        List<Person> persons = List.of(
                new Person("Ion", "Ionescu", LocalDate.of(1990, 1, 15)),
                new Person("Nicu", "Niculescu", LocalDate.of(1992, 1, 25)),
                new Person("Vasile", "Vasilescu", LocalDate.of(1991, 2, 20))
        );

        assertThrows(IllegalArgumentException.class, () -> {
            service.filterAndSortPersonsByMonth(persons, 13);
        });
    }

    @Test
    public void testInvalidDataFormat() {
        PersonService service = new PersonService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.loadPersons("invalid_format.txt");
        });
    }
}