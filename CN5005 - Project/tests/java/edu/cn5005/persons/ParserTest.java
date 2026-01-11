package edu.cn5005.persons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

// Unit tests for PersonParser: It verifies correct loading, indexing, and handling of empty fields.

public class ParserTest {

    // Checks that all persons from persons.csv are loaded. The header line is excluded from the count.
    @Test
    public void testLoadEveryone() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");

        assertEquals(25, parser.getPersonsToId().size(),
                "25 people must be loaded from the CSV file.");
    }

    // Verifies name -> id index for a known person.
    @Test
    public void testNameToIdMapping() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");

        Integer id = parser.getIdToName().get("Ιωάννης Καποδίστριας");

        assertNotNull(id);
        assertEquals(3, id);
    }

    // Checks correct parent links (father_id, mother_id).
    @Test
    public void testParentLinks() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");

        Person xristina = parser.getPersonsToId().get(15);
        assertNotNull(xristina);

        assertEquals(13, xristina.getFatherId());
        assertEquals(14, xristina.getMotherId());
    }

    //Ensures empty spouse_id fields in CSV are stored as null.
    @Test
    public void testSpouseLink() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");

        Person ioannis = parser.getPersonsToId().get(3);
        assertNotNull(ioannis);

        // Since CSV has NO spouse for Ioannis, spouseId must be null
        assertNull(ioannis.getSpouseId(),
                "Ιωάννης Καποδίστριας δεν έχει συζύγο στο CSV.");
    }
}



