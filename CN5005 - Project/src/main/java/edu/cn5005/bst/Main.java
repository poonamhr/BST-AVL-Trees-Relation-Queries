package edu.cn5005.bst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
This class builds a BST(Binary Search Tree) using AM values of team members,
executes preorder, inorder and postorder traversals,
and prints explanatory output.
*/
public class Main {

    // Representing a team member with surname, name and AM.
    static class Member {
        String lastName;
        String firstName;
        int AM;

        Member(String lastName, String firstName, int AM) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.AM = AM;
        }
    }

    public static void main(String[] args) {
        // Creating team members
        List<Member> team = new ArrayList<>();
        team.add(new Member("Kaur", "Poonam Rani", 2879196));
        team.add(new Member("Siamos", "Thomas", 2879220));
        team.add(new Member("Jobs", "Steve", 3456907));

        // Sorting members alphabetically by surname
        team.sort(Comparator.comparing(m -> m.lastName.toLowerCase()));

        // Generating at least 6 using the last 4 digits keys by cycling through AM values
        int[] keys = new int[6];
        for (int i = 0; i < 6; i++) {
            int am = team.get(i % team.size()).AM;
            keys[i] = am % 10000;   // take last 4 digits
        }

        // Building the BST
        BST tree = new BST();


        // Displaying sorted team members
        System.out.println("=== Team Members (Sorted) ===");
        for (Member m : team) {
            System.out.println(m.lastName + " " + m.firstName + " -> AM: " + m.AM);
        }

        // Inserting keys into the BST
        System.out.println("\n=== Inserted Keys in the BST ===");
        for (int key : keys) {
            System.out.print(key + " \n");
            tree.insert(key);
        }
        System.out.println("\n");

        // Explaining traversal outputs
        System.out.println("=== Traversals ===");
        System.out.println("- Preorder  : root -> left -> right");
        System.out.println("- Inorder   : ascending order, displaying as key:count");
        System.out.println("- Postorder : left -> right -> root\n");

        // Printing all traversals
        printTraversals(tree);

        // Demonstrating deletion of an existing key
        System.out.println("=== Deleting the 1st inserted key: " + keys[0] + " ===");
        tree.delete(keys[0]);
        printTraversals(tree);

        // Demonstrating deletion of a specific key
        System.out.println("=== Deleting key 9196 ===");
        tree.delete(9196);
        printTraversals(tree);

        // Demonstrating safe deletion of a non-existing key
        System.out.println("=== Deleting a non-existing key 9999 ===");
        tree.delete(9999);
        printTraversals(tree);
    }

    // Printing preorder, inorder and postorder traversals of the BST.
    private static void printTraversals(BST tree) {
        System.out.println("Preorder  : " + tree.preorder());
        System.out.println("Inorder   : " + tree.inorder());
        System.out.println("Postorder : " + tree.postorder());
        System.out.println();
    }
}


