package edu.cn5005.bst;

/*
 AVL Tree extension of BST.

 changeKey: change all occurrences of oldKey into newKey, thus, preserving the original count.
 We find node with oldKey (get its count), then delete oldKey completely, then insert newKey count times.
 Then return true if the change was successful (oldKey existed), false otherwise.

 Rotation cases:
 - LL: insertion/deletion in left subtree of left child → single right rotation
 - RR: insertion/deletion in right subtree of right child → single left rotation
 - LR: insertion/deletion in right subtree of left child → left rotation, then right
 - RL: insertion/deletion in left subtree of right child → right rotation, then left

 Example:
 Insert 9220, 9196, 6789 -> balance = +2 at 9220 -> LL case → rotateRight(9220) with root 9196 and children as 6789 and 9220
 */
public class AVL extends BST {

    // Returning height of a node (0 for null)
    private int height(Node node) {
        return (node == null) ? 0 : node.getHeight();
    }

    // Balance factor = height(left) - height(right)
    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.getLeft()) - height(node.getRight());
    }

    // Updating node height based on children
    private void updateHeight(Node node) {
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
    }

    // Right rotation (LL case)
    private Node rotateRight(Node y) {
        Node x = y.getLeft();
        Node temp = x.getRight();

        x.setRight(y);
        y.setLeft(temp);

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left rotation (RR case)
    private Node rotateLeft(Node x) {
        Node y = x.getRight();
        Node temp = y.getLeft();

        y.setLeft(x);
        x.setRight(temp);

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Overrides BST insert/delete to maintain AVL balance
    @Override
    public void insert(int key) {
        root = insertAVL(root, key);
    }

    @Override
    public void delete(int key) {
        root = deleteAVL(root, key);
    }

    // Recursive AVL insertion
    private Node insertAVL(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.getKey()) {
            node.setLeft(insertAVL(node.getLeft(), key));
        } else if (key > node.getKey()) {
            node.setRight(insertAVL(node.getRight(), key));
        } else {
            node.setCount(node.getCount() + 1); // Duplicate key
            return node;
        }

        // Updating height and rebalancing
        updateHeight(node);
        int balance = getBalance(node);

        // LL case
        if (balance > 1 && key < node.getLeft().getKey()) {
            return rotateRight(node);
        }

        // RR case
        if (balance < -1 && key > node.getRight().getKey()) {
            return rotateLeft(node);
        }

        // LR case
        if (balance > 1 && key > node.getLeft().getKey()) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // RL case
        if (balance < -1 && key < node.getRight().getKey()) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }
        return node;
    }

    // Recursive AVL deletion
    private Node deleteAVL(Node node, int key) {
        if (node == null) {
            return null;
        }

        if (key < node.getKey()) {
            node.setLeft(deleteAVL(node.getLeft(), key));
        } else if (key > node.getKey()) {
            node.setRight(deleteAVL(node.getRight(), key));
        } else {
            // Key found
            if (node.getCount() > 1) {
                node.setCount(node.getCount() - 1);
                updateHeight(node); // is needed before returning
                return node;
            }

            // Node with one or no children
            if (node.getLeft() == null) return node.getRight();

            if (node.getRight() == null) return node.getLeft();

            // Node with two children: replacing with inorder successor
            Node successor = findMin(node.getRight());
            node.setKey(successor.getKey());
            node.setCount(successor.getCount());
            successor.setCount(1);
            node.setRight(deleteAVL(node.getRight(), successor.getKey()));
        }

        // Updating height and rebalancing after deletion
        updateHeight(node);
        int balance = getBalance(node);

        // LL case
        if (balance > 1 && getBalance(node.getLeft()) >= 0) {
            return rotateRight(node);
        }

        // LR case
        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // RR case
        if (balance < -1 && getBalance(node.getRight()) <= 0) {
            return rotateLeft(node);
        }

        // RL case
        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    // Finding the minimum key in the subtree
    private Node findMin(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // Changing all occurrences of oldKey to newKey
    public boolean changeKey(int oldKey, int newKey) {
        Node found = search(root, oldKey);
        if (found == null) {
            return false;
        }

        int c = found.getCount();
        for (int i = 0; i < c; i++) {
            delete(oldKey);
        }

        for (int i = 0; i < c; i++) {
            insert(newKey);
        }

        return true;
    }

    // Standard BST search
    private Node search(Node node, int key) {
        if (node == null) return null;
        if (key == node.getKey()) return node;
        if (key < node.getKey()) return search(node.getLeft(), key);
        return search(node.getRight(), key);
    }
}