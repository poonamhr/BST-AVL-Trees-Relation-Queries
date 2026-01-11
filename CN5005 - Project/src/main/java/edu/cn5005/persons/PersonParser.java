package edu.cn5005.persons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// This code parses persons.csv and loads Person objects into indexed data structures.

public class PersonParser {
    // person id -> Person object
    private final Map<Integer, Person> personsToId = new HashMap<>();

    // person name -> person id (for fast lookup by name)
    private final Map<String, Integer> idToName = new HashMap<>();

   // Loads the CSV & Logs malformed or incomplete lines and skips them safely.
    public void loadCsv(String csvPath) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {

            String line = br.readLine(); // skips header

            // Reading each line
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                // Splitting the line into columns
                String[] parts = line.split(",", -1);

                if (parts.length < 3) {
                    System.err.println("Skipping malformed line (expected at least 3 columns): " + line);
                    continue;
                }

                // Reading id, name, gender
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String gender = parts[2].trim();

                if (name.isEmpty()) {
                    System.err.println("Skipping line with empty name: " + line);
                    continue;
                }

                // Parsing parent and spouse IDs
                Integer fatherId = parseNullableInt(parts, 3);
                Integer motherId = parseNullableInt(parts, 4);
                Integer spouseId = parseNullableInt(parts, 5);

                // Creating the Person object
                Person p = new Person(id, name, gender, fatherId, motherId, spouseId);
                personsToId.put(id, p);
                idToName.put(name, id);
            }
        }

        // Printing loaded names
        for (Person p : personsToId.values()) {
            System.out.println("Loaded person: " + p.getName() + " (" + p.getGender() + ")");
        }
    }

     // Parses an integer field that may be empty in the CSV.
     // Returns null if the field is missing or blank.
    private Integer parseNullableInt(String[] parts, int index) {
        if (index >= parts.length) {
            return null;
        }
        String s = parts[index].trim();
        if (s.isEmpty()) {
            return null;
        }
        return Integer.parseInt(s);
    }

    // Used by tests
    public Map<Integer, Person> getPersonsToId() { return personsToId; }

    public Map<String, Integer> getIdToName() {
        return idToName;
    }
}
