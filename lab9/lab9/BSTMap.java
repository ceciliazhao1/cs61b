package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

          /* Parent of this Node. */
        private Node parent;

        /* The color of this Node. */
        private boolean color;

        private Node(K key, V value, BSTMap<K, V>.Node left, BSTMap<K, V>.Node right,
                BSTMap<K, V>.Node parent, boolean color) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.color = color;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (p == null) {
            return null;
        }
        int cmp=key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }


    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("calls put() with a null key");
        //value 可以是null，可以是val
        Node node = new Node(key, value, null, null, null, false);
        puthelper(node);
        size++;
    }
    private void puthelper(Node node) {
        int cmp;
        Node x = this.root, y = null;
        if(x==null)
            root=node;
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y != null) {
            cmp = node.key.compareTo(y.key);
            if (cmp < 0) {
                y.left = node;
            } else {
                y.right = node;
            }
        }else{
            this.root = node;
        }


        node.color = true;

        putFixUp(node);
    }

    private void putFixUp(Node node) {
        Node parent, gparent;

        // if parent is existed and parent's color is red
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);

            // if parent is left child
            if (parent == gparent.left) {
                // Case 1: uncle is red
                Node uncle = gparent.right;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2: uncle is black and current node is right child
                if (parent.right == node) {
                    Node tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3: uncle is black and current node is left child
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else { // if parent is right child
                // Case 1: uncle is red
                Node uncle = gparent.left;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2: uncle is black and current node is left child
                if (parent.left == node) {
                    Node tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3: uncle is black and current node is right child
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        // set root as black
        setBlack(this.root);
    }
    private void leftRotate(Node x) {
        Node y = x.right;

        // Set x's right child as y's left child.
        // Set the parent of y's left child as x if y's left child is not null.
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        // Set y's parent as x's parent.
        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y; // x's parent is null, set y as root
        } else {
            if (x.parent.left == x) {
                x.parent.left = y; // x is left child, set y as the left child of x's parent.
            } else {
                x.parent.right = y; // x is right child, set y as the right child of x's parent.
            }
        }

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;

        // Set x's left child as y's right child.
        // Set the parent of y's right child as x if y's right child is not null.
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }

        // Set y's parent as x's parent.
        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y; // x's parent is null, set y as root
        } else {
            if (x.parent.right == x) {
                x.parent.right = y; // x is right child, set y as the right child of x's parent.
            } else {
                x.parent.left = y; // x is left child, set y as the left child of x's parent.
            }
        }

        y.right = x;
        x.parent = y;
    }


    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }



    private Node parentOf(Node node) {
        return node != null ? node.parent : null;
    }

    private boolean colorOf(Node node) {
        return node != null ? node.color : false;
    }

    private boolean isRed(Node node) {
        return ((node != null) && (node.color == true)) ? true : false;
    }

    private boolean isBlack(Node node) {
        return !isRed(node);
    }

    private void setBlack(Node node) {
        if (node != null) {
            node.color = true;
        }
    }

    private void setRed(Node node) {
        if (node != null) {
            node.color = true;
        }
    }

    private void setParent(Node node, Node parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    private void setColor(Node node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }
}

