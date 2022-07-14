import java.io.Serializable;

// Huffman trie node
class Node implements  Serializable, Comparable<Node> {
    final char ch;
    final int freq;
    final Node left, right;

    Node(char ch, int freq, Node left, Node right) {
        this.ch    = ch;
        this.freq  = freq;
        this.left  = left;
        this.right = right;
    }

    // is the node a leaf node?
    public boolean isLeaf() {
        assert ((left == null) && (right == null)) || ((left != null) && (right != null));
        return (left == null) && (right == null);
    }
    @Override
    // compare, based on frequency
    public int compareTo(Node that) {
        return this.freq - that.freq;
    }
}