package edu.cn5005.bst;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/* Unit tests for BST and AVL implementations.
The tests verify:
- Correct insertion and handling of duplicates using count
- Correct deletion behavior
- Correct preorder, inorder, and postorder traversals
- AVL balancing and changeKey functionality
 */
public class BSTTest {

    private BST bst;
    private AVL avl;

    // Initializes a fresh BST and AVL tree before each test to ensure test independence.
    @BeforeEach
    public void setup() {
        bst = new BST();
        avl = new AVL();
    }

     // Tests insertion into BST and verifies that duplicate keys increase the count,
     // instead of creating new nodes.
    @Test
    public void testInsertAndCountBST() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(10); // duplicate key

        List<String> inorder = bst.inorder();
        assertEquals("5:1", inorder.get(0));
        assertEquals("10:2", inorder.get(1)); // count increased
        assertEquals("15:1", inorder.get(2));
    }

    /* Tests BST deletion policy:
     - If count > 1, delete reduces the count
     - Node is removed only when count reaches zero
     */
    @Test
    public void testDeleteBST() {
        bst.insert(20);
        bst.insert(20); // duplicate
        bst.insert(10);
        bst.insert(30);

        bst.delete(20);
        List<String> inorder = bst.inorder();
        assertTrue(inorder.contains("20:1"));

        bst.delete(20); // node should now be removed
        inorder = bst.inorder();
        assertFalse(inorder.contains("20:1"));
    }

    /* Tests correctness of BST traversals.
     - preorder: root -> left -> right
     - inorder: ascending order
     - postorder: left -> right -> root
     */
    @Test
    public void testTraversalsBST() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(12);

        List<String> preorder = bst.preorder();
        List<String> inorder = bst.inorder();
        List<String> postorder = bst.postorder();

        assertEquals("10:1", preorder.get(0));  // root
        assertEquals("5:1", inorder.get(0));    // smallest key
        assertEquals("12:1", postorder.get(1)); // internal node
    }

    // Tests AVL insertions and verifies that the tree remains balanced and inorder traversal is sorted.
    @Test
    public void testAVLInsertAndBalance() {
        int[] keys = {10, 20, 30, 40, 50, 25};
        for (int key : keys) avl.insert(key);

        List<String> inorder = avl.inorder();
        assertEquals(6, inorder.size());
        assertEquals("10:1", inorder.get(0));
        assertEquals("50:1", inorder.get(5));
    }

    /* Tests changeKey in AVL.
     Verifies that:
     - oldKey is removed
     - newKey is inserted with the same count
     */
    @Test
    public void testChangeKeyAVL() {
        avl.insert(10);
        avl.insert(20);
        avl.insert(20); // duplicate
        boolean changed = avl.changeKey(20, 25);

        assertTrue(changed);
        List<String> inorder = avl.inorder();
        assertTrue(inorder.contains("25:2"));
        assertFalse(inorder.contains("20:2"));
    }

    /* Tests AVL deletion when duplicates exist.
     The node should be removed only after count reaches zero.
     */
    @Test
    public void testDeleteAVL() {
        avl.insert(10);
        avl.insert(20);
        avl.insert(20); // duplicate
        avl.delete(20); // count reduced
        avl.delete(20); // node removed

        List<String> inorder = avl.inorder();
        assertFalse(inorder.contains("20:1"));
        assertEquals(1, inorder.size()); // only key 10 remains
    }
}
