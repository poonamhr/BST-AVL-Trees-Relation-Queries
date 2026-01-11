package edu.cn5005.bst;

/*
- key: the number
- count: stores the duplicates of the key
- left: left child of the Node (smaller than the root Node)
- right: right child of teh Node (greater than the root Node)

Need and usage of count:
count is needed to ensure no duplicates are allowed inside the same node.
So when we insert the same key again it will increase the count by +1 and
when we delete it becomes -1,
that is we do not remove the node until the count = 0
*/

public class Node {
    private int key;
    private int count;
    private Node left;
    private Node right;
    private int height;

    // Constructors
    public Node(int key) {
        this.key = key;
        this.count = 1;
        this.left = null;
        this.right = null;
        this.height = 0;
    }

    // Getters and setters
    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getLeft() {
        return left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getRight() {
        return right;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
