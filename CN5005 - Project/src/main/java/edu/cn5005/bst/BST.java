package edu.cn5005.bst;

/*
Inserting null or non-integers:
In Java, int can't be null, so if we insert a null value, it will give an error, as
int is a primitive type and they can't be null
If we input non-integers e.g String or double number it will throw compile-time error
as we have defined "key" as int.

We chose the policy: when duplicate count > 1, "delete" decreases count by 1.
Only when count reaches 0 (handled by replacing/removing node) the node is removed.
 */

import java.util.ArrayList;
import java.util.List;

public class BST {
    protected Node root;

    public BST() {
        root = null;
    }

    // Inserting a key into the BST
    public void insert(int key) {
        root = insertRec(root, key);
    }

    // Deleting a key from the BST
    public void delete(int key) {
        root = deleteRec(root, key);
    }

    // Returning preorder traversal (root, left, right)
    public List<String> preorder() {
        List<String> preorder_list = new ArrayList<>();
        preorderRec(root, preorder_list);
        return preorder_list;
    }

    // Returning inorder traversal (sorted order)
    public List<String> inorder() {
        List<String> inorder_list = new ArrayList<>();
        inorderRec(root, inorder_list);
        return inorder_list;
    }

    // Returning postorder traversal (left, right, root)
    public List<String> postorder() {
        List<String> postorder_list = new ArrayList<>();
        postOrderRec(root, postorder_list);
        return postorder_list;
    }

    // Recursive BST insertion
    private Node insertRec(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.getKey()) {
            node.setLeft(insertRec(node.getLeft(), key));
        } else if (key > node.getKey()) {
            node.setRight(insertRec(node.getRight(), key));
        } else {
            node.setCount(node.getCount() + 1); // Duplicate key
        }
        return node;
    }

    // Recursive BST deletion
    private Node deleteRec(Node node, int key) {
        if (node == null) {
            return null;
        }

        if (key < node.getKey()) {
            node.setLeft(deleteRec(node.getLeft(), key));
            return node;
        } else if (key > node.getKey()) {
            node.setRight(deleteRec(node.getRight(), key));
            return node;
        } else {
            // Key found
            if (node.getCount() > 1) {
                node.setCount(node.getCount() - 1);
                return node;
            }

            // Node with one or no children
            if (node.getLeft() == null)
                return node.getRight();

            if (node.getRight() == null)
                return node.getLeft();

            // Node with two children: replacing with inorder successor
            Node successor = findMin(node.getRight());
            node.setKey(successor.getKey());
            node.setCount(1);
            node.setRight(deleteRec(node.getRight(), successor.getKey()));

            return node;
        }
    }

    // Finding the minimum key in the subtree
    private Node findMin(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // Preorder traversal helper method
    private void preorderRec(Node node, List<String> preorder_list) {
        if (node == null) {
            return;
        }
        preorder_list.add(node.getKey() + ":" + node.getCount());
        preorderRec(node.getLeft(), preorder_list);
        preorderRec(node.getRight(), preorder_list);
    }

    // Inorder traversal helper method
    private void inorderRec(Node node, List<String> inorder_list) {
        if (node == null) {
            return;
        }
        inorderRec(node.getLeft(), inorder_list);
        inorder_list.add(node.getKey() + ":" + node.getCount());
        inorderRec(node.getRight(), inorder_list);
    }

    // Postorder traversal helper method
    private void postOrderRec(Node node, List<String> postorder_list) {
        if (node == null) {
            return;
        }
        postOrderRec(node.getLeft(), postorder_list);
        postOrderRec(node.getRight(), postorder_list);
        postorder_list.add(node.getKey() + ":" + node.getCount());
    }
}

