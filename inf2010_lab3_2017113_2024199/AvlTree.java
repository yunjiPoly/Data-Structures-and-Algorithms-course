package tp3;

import java.awt.datatransfer.FlavorListener;
import java.util.*;

enum Direction {
    LEFT,
    RIGHT,
    EQUAL
}

public class AvlTree<ValueType extends Comparable<? super ValueType> > {

    private BinaryNode<ValueType> root;

    public AvlTree() { }

    /**
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * @param value value to add to the tree
     */
    public void insert(ValueType value) {
        if (root == null) {
            root = new BinaryNode<ValueType>(value, null);
        } else {
            insert(value, root);
        }
    }

    /**
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * @param value value to add to the tree
     */
    public void remove(ValueType value){
        remove(value, root);
    }

    /**
     * Verifies if the tree contains value
     * @param value value to verify
     * @return if value already exists in the tree
     */
    public boolean contains(ValueType value) {
        return contains(value, root);
    }

    /**
     * Returns the max level contained in our root tree
     * @return Max level contained in our root tree
     */
    public int getHeight() {
        return BinaryNode.getHeight(root);
    }

    /**
     * Returns the minimal value contained in our root tree
     * @return Minimal value contained in our root tree
     */
    public ValueType findMin() {
        BinaryNode<ValueType> minimalNode = findMin(root);
        if (minimalNode == null) return null;
        return minimalNode.value;
    }

    /**
     * Returns all values contained in the root tree in ascending order
     * @return Values contained in the root tree in ascending order
     */
    public List<ValueType> infixOrder() {
        List<ValueType> items = new LinkedList<ValueType>();
        infixOrder(root, items);
        return items;
    }

    /**
     * Returns all values contained in the root tree in level order from top to bottom
     * @return Values contained in the root tree in level order from top to bottom
     */
    public List<ValueType> levelOrder(){
        List<ValueType> items = new LinkedList<ValueType>();

        ArrayDeque<BinaryNode<ValueType>> nodesToCheck = new ArrayDeque<BinaryNode<ValueType>>();

        if (root != null) {
            nodesToCheck.push(root);
            levelOrder(nodesToCheck, items);
        }

        return items;
    }

    private Direction chooseDirection(ValueType value, BinaryNode<ValueType> currentNode){
        int compare = value.compareTo(currentNode.value);
        if (compare < 0) {
            return Direction.LEFT;
        } else if (compare > 0) {
            return Direction.RIGHT;
        }
        return Direction.EQUAL;
    }

    private void changeHeights(BinaryNode<ValueType> currentNode) {
        while (currentNode != null) {
            if (currentNode.left == null && currentNode.right == null) {
                currentNode.height = 0;
            }
            currentNode.height = Math.max(BinaryNode.getHeight(currentNode.right) + 1, BinaryNode.getHeight(currentNode.left) + 1);
            currentNode = currentNode.parent;
        }
    }

    /** Worst case : O ( log n )
     *
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * Should call balance only if insertion succeeds
     * AVL Trees do not contain duplicates
     *
     * @param value value to add to the tree
     * @param currentNode Node currently being accessed in this recursive method
     */
    private void insert (ValueType value, BinaryNode<ValueType> currentNode) {
        BinaryNode<ValueType> newNode = new BinaryNode<ValueType>(value, currentNode);
        Direction direction = chooseDirection(value, currentNode);
        if (direction == Direction.LEFT) {
            if (currentNode.left == null) {
                currentNode.left = newNode;
                changeHeights(newNode);
            } else {
                insert(value,currentNode.left);
            }
        } else if (direction == Direction.RIGHT) {
            if (currentNode.right == null) {
                currentNode.right = newNode;
                changeHeights(newNode);
            } else {
                insert(value, currentNode.right);
            }
        }
        balance(currentNode);
    }

    /** Worst case : O ( log n )
     *
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * Should call balance only if removal succeeds
     *
     * @param value value to remove from the tree
     * @param currentNode Node currently being accessed in this recursive method
     */
    private void remove(ValueType value, BinaryNode<ValueType> currentNode) {
        if (currentNode != null) {
            Direction compareNode = chooseDirection(value, currentNode);
            if (compareNode == Direction.RIGHT) {
                remove(value, currentNode.right);
            } else if (compareNode == Direction.LEFT) {
                remove(value, currentNode.left);
            } else {
                if (currentNode.parent != null) {
                    if (currentNode.left != null && currentNode.right != null) {
                        currentNode.value = findMin(currentNode.right).value;
                        remove(currentNode.value, currentNode.right);
                        changeHeights(currentNode.parent);
                    } else if (currentNode.left != null || currentNode.right != null) {
                        currentNode = currentNode.right != null ? currentNode.right : currentNode.left;
                        changeHeights(currentNode);
                    } else {
                        if (chooseDirection(currentNode.value,currentNode.parent) == Direction.LEFT) {
                            currentNode.parent.left = null;
                        } else {
                            currentNode.parent.right =null;
                        }
                        changeHeights(currentNode.parent);
                        balance(currentNode.parent);
                        currentNode.parent = null;
                    }
                } else {
                    root = null;
                }
            }
        }
    }

    private Direction checkBalance(BinaryNode<ValueType> node) {
        if (BinaryNode.getHeight(node.right) - BinaryNode.getHeight(node.left) > 1) {
            return Direction.RIGHT;
        } else if (BinaryNode.getHeight(node.left) - BinaryNode.getHeight(node.right) > 1) {
            return Direction.LEFT;
        }
        return Direction.EQUAL;
    }

    /** Worst case : O( log n )
     *
     * Recursively balances the whole tree
     * @param subTree SubTree currently being accessed to verify if it respects the AVL balancing rule
     */
    private void balance(BinaryNode<ValueType> subTree) {
        if(subTree != null) {
            Direction checkBalance = checkBalance(subTree);
            if (checkBalance == Direction.EQUAL) {
                balance(subTree.parent);
            } else if (checkBalance == Direction.LEFT) {
                if (subTree.left != null && BinaryNode.getHeight(subTree.left.right) - BinaryNode.getHeight(subTree.left.left) > 0) {
                    doubleRotateOnLeftChild(subTree);
                } else if (subTree != root && checkBalance(subTree.parent) == Direction.RIGHT) {
                    doubleRotateOnRightChild(subTree.parent);
                } else {
                    rotateRight(subTree);
                }
            } else {
                if (subTree.right != null && BinaryNode.getHeight(subTree.right.left) - BinaryNode.getHeight(subTree.right.right) > 0) {
                    doubleRotateOnRightChild(subTree);
                } else if (subTree != root && checkBalance(subTree.parent) == Direction.LEFT) {
                    doubleRotateOnLeftChild(subTree.parent);
                } else {
                    rotateLeft(subTree);
                }
            }
            if (subTree.parent != null){
                balance(subTree.parent);
            }
        }
    }

    /** Worst case : O ( 1 )
     *
     * Single rotation to the left child, AVR Algorithm
     * @param node1 Node to become child of its right child
     */
    private void rotateLeft(BinaryNode<ValueType> node1){
        BinaryNode<ValueType> node2 = node1.right;
        if (node2.left != null) {
            node1.right = node2.left;
            node2.left.parent = node2.parent;
        } else {
            node1.right = null;
        }
        if (node1.parent != null) {
            if(chooseDirection(node2.value, node1.parent) == Direction.RIGHT) {
                node1.parent.right = node2;
            } else {
                node1.parent.left = node2;
            }
            node2.parent = node1.parent;
        } else {
            node2.parent = null;
        }
        node2.left = node1;
        node1.parent = node2;
        if (root == node1) {
            root = node2;
        }
        changeHeights(node1);
    }

    /** Worst case : O ( 1 )
     *
     * Single rotation to the right, AVR Algorithm
     * @param node1 Node to become child of its right child
     */
    private void rotateRight(BinaryNode<ValueType> node1){
        BinaryNode<ValueType> node2 = node1.left;
        if (node2.right != null) {
            node1.left = node2.right;
            node2.right.parent = node2.parent;
        } else {
            node1.left = null;
        }
        if (node1.parent != null) {
            if(chooseDirection(node2.value, node1.parent) == Direction.RIGHT) {
                node1.parent.right = node2;
            } else {
                node1.parent.left = node2;
            }
            node2.parent = node1.parent;
        } else {
            node2.parent = null;
        }
        node2.right = node1;
        node1.parent = node2;
        if (root == node1) {
            root = node2;
        }
        changeHeights(node1);
    }

    /** Worst case : O ( 1 )
     *
     * Double rotation on left child, AVR Algorithm
     * @param node Node to become child of the right child of its left child
     */
    private void doubleRotateOnLeftChild(BinaryNode<ValueType> node){
        rotateLeft(node.left);
        rotateRight(node);
    }

    /** Worst case : O ( 1 )
     *
     * Double rotation on right child, AVR Algorithm
     * @param node Node to become child of the left child of its right child
     */
    private void doubleRotateOnRightChild(BinaryNode<ValueType> node){
        rotateRight(node.right);
        rotateLeft(node);
    }

    /** Worst case : O ( log n )
     *
     * Verifies if the root tree contains value
     * @param value value to verify
     * @param currentNode Node currently being accessed in this recursive method
     * @return if value already exists in the root tree
     */
    private boolean contains(ValueType value, BinaryNode<ValueType> currentNode) {
        if (currentNode != null) {
            if (chooseDirection(value, currentNode) == Direction.EQUAL) {
                return true;
            } else if (chooseDirection(value, currentNode) == Direction.RIGHT) {
                return contains(value, currentNode.right);
            }
            return contains(value, currentNode.left);
        }
        return false;
    }

    /** O( log n )
     *
     * Returns the node which has the minimal value contained in our root tree
     * @return Node which has the minimal value contained in our root tree
     */
    private BinaryNode<ValueType> findMin(BinaryNode<ValueType> currentNode) {
        if (currentNode == null) {
            return null;
        } else if (currentNode.left == null) {
            return currentNode;
        }
        return findMin( currentNode.left );
    }


    /** O( n )
     *
     * Builds items which should contain all values within the root tree in ascending order
     * @param currentNode Node currently being accessed in this recursive method
     * @param items List being modified to contain all values in the root tree in ascending order
     */
    private void infixOrder(BinaryNode<ValueType> currentNode, List<ValueType> items){
        if (currentNode == null) {
            return;
        }
        infixOrder(currentNode.left,items);
        items.add(currentNode.value);
        infixOrder(currentNode.right,items);
    }

    /** O( n )
     * Builds items which should contain all values within the root tree in level order from top to bottom
     * @param nodesToCheck Queue for non-recursive algorithm
     * @param items List being modified to contain all values in the root tree in level order from top to bottom
     */
    private void levelOrder(ArrayDeque<BinaryNode<ValueType>> nodesToCheck, List<ValueType> items) {
        while( ! nodesToCheck.isEmpty()){
            BinaryNode<ValueType> currentNode = nodesToCheck.removeFirst();
            if(currentNode.left != null){
                nodesToCheck.addLast(currentNode.left);
            }
            if(currentNode.right != null){
                nodesToCheck.addLast(currentNode.right);
            }
            items.add(currentNode.value);
        }
    }
    
    static class BinaryNode<ValueType> {
        ValueType value;

        BinaryNode<ValueType> parent; // Pointer to the node containing this node

        BinaryNode<ValueType> left = null; // Pointer to the node on the left which should contain a value below this.value
        BinaryNode<ValueType> right = null; // Pointer to the node on the right which should contain a value above this.value

        int height = 0;

        // Null-safe height accessor
        // Raw use of parameterized class is justified because we do not care about the values in it, only the height
        static public int getHeight(BinaryNode node) {
            return node != null ? node.height : -1;
        }

        BinaryNode(ValueType value, BinaryNode<ValueType> parent)
        {
            this.value = value;
            this.parent = parent;
        }
    }
}
