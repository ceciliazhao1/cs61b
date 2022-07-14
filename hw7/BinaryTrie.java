import java.util.*;
import java.io.*;
import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    //build a Huffman decoding trie
    Node parent;
    public BinaryTrie(Map<Character, Integer> frequencyTable){
        MinPQ<Node> pq = new MinPQ<Node>();
        // initialze priority queue with singleton trees
        for (char c : frequencyTable.keySet())
            if (frequencyTable.get(c)> 0)
                pq.insert(new Node(c, frequencyTable.get(c), null, null));

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left  = pq.delMin();
            Node right  = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        parent =pq.delMin();
    }

    //finds the longest prefix that matches the given querySequence and returns a Match
    public Match longestPrefixMatch(BitSequence querySequence){
        Map<Character, BitSequence> map= buildLookupTable();
        int maxlength=0;
        for(char c: map.keySet()){
            if(map.get(c).length()>maxlength) {
                maxlength = map.get(c).length();
            }
        }
        //当sequence中长度11小于map中最长的000的长度3
        if(maxlength> querySequence.length()){
            maxlength= querySequence.length();
        }
        Match result=null;

        for(int i=maxlength;i>=0;i--){
            BitSequence bt = querySequence.firstNBits(i);
            if(map.containsValue(bt)) {
                for(char c: map.keySet()){
                    if(map.get(c).equals(bt)){
                        result= new Match(bt,c);
                        break;
                    }
                }
            }
        }
        return result;
    }
    //returns the inverse of the coding trie
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> expected = new HashMap<Character, BitSequence>();
        Node node = parent;
        buildCode(expected, node, "");
        return expected;
    }
    public void buildCode(Map<Character, BitSequence> expected, Node node, String s){
        if (!node.isLeaf()) {
            buildCode(expected, node.left, s + '0');
            buildCode(expected, node.right, s + '1');
        } else {
            expected.put(node.ch, new BitSequence(s));
        }
    }
}