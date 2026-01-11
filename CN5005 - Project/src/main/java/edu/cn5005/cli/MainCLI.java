package edu.cn5005.cli;

import edu.cn5005.persons.PersonParser;
import edu.cn5005.relations.RelationEngine;

import java.io.IOException;

public class MainCLI {
    public static void main(String[] args) throws IOException {

        // Print usage instructions
        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println(" relation \"Name1\" \"Name2\"");
            System.out.println(" insert <id> <name> <gender>");
            System.out.println(" delete <id>");
            System.out.println(" changeKey <oldId> to <newId>");
            return;
        }

        // First argument defines the command
        String cmd = args[0];

        // Insert
        if (cmd.equalsIgnoreCase("insert")) {
            if (args.length < 4) {
                System.out.println("Usage: insert <id> <name> <gender>");
                return;
            }
            System.out.println("Inserted person:");
            System.out.println("ID = " + args[1] + "\nName = " + args[2] + "\nGender = " + args[3]);
            return;
        }

        // Delete
        if (cmd.equalsIgnoreCase("delete")) {
            if (args.length < 2) {
                System.out.println("Usage: delete <id>");
                return;
            }
            System.out.println("Deleted person with ID = " + args[1]);
            return;
        }

        // Change key
        if (cmd.equalsIgnoreCase("changeKey")) {
            if (args.length < 3) {
                System.out.println("Usage: changeKey <oldId> to <newId>");
                return;
            }
            System.out.println("Changed key from " + args[1] + " to " + args[2]);
            return;
        }

        // Relation
        if (!cmd.equalsIgnoreCase("relation")) {
            System.out.println("Unknown command");
            return;
        }

        // Relation requires two names
        if (args.length < 3) {
            System.out.println("Usage: relation \"Name1\" \"Name2\"\n");
            return;
        }

        String nameA = args[1];
        String nameB = args[2];

        // Load persons data from CSV
        PersonParser p = new PersonParser();
        p.loadCsv("persons.csv");

        // Initialize relation engine with lookup maps
        RelationEngine engine = new RelationEngine(
                p.getPersonsToId(),
                p.getIdToName()
        );

        System.out.println(engine.relation(nameA, nameB));
    }
}



