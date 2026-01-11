package edu.cn5005.relations;

import edu.cn5005.persons.PersonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RelationTest {

    // Part D
    @Test
    public void testFatherRelation() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");
        RelationEngine engine = new RelationEngine(parser.getPersonsToId(), parser.getIdToName());

        // From CSV: Αυγουστίνος (1) is father of Ιωάννης (3)
        assertTrue(engine.isFather(1, 3));
    }

    @Test
    public void testSiblingRelation() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");
        RelationEngine engine = new RelationEngine(parser.getPersonsToId(), parser.getIdToName());

        // From CSV: Ιωάννης (3) and Ελένη (4) share both parents
        assertTrue(engine.isSibling(3, 4));
    }

    @Test
    public void testGrandparentRelation() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");
        RelationEngine engine = new RelationEngine(parser.getPersonsToId(), parser.getIdToName());

        // From CSV: Αυγουστίνος (1) is grandparent of Αλέξανδρος (6)
        assertTrue(engine.isGrandparent(1, 6));
    }


    // Part E
    @Test
    public void testHalfSiblings() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");
        RelationEngine engine =
                new RelationEngine(parser.getPersonsToId(), parser.getIdToName());

        // According to the CSV, these people do NOT share exactly one parent
        assertFalse(engine.isHalfSibling(10, 12));
        assertFalse(engine.isHalfSibling(11, 12));
    }

    @Test
    public void testSpouse() throws IOException {
        PersonParser parser = new PersonParser();
        parser.loadCsv("persons.csv");
        RelationEngine engine =
                new RelationEngine(parser.getPersonsToId(), parser.getIdToName());

        // No spouse relationship is defined for person 3 in the CSV
        assertFalse(engine.isSpouse(3, 4));
        assertFalse(engine.isSpouse(3, 5));
    }
}

