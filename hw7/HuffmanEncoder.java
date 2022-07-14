import java.util.*;
import java.io.*;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        Map<Character, Integer> map= new HashMap<>();
        for(char c: inputSymbols){
            map.put(c,map.getOrDefault(c,0)+1);
        }
        return map;
    }
    public static void main(String[] args){
        //1: Read the file as 8 bit symbols.
        char[] ch = null;
        try {
            ch= FileUtils.readFile(args[0]);
        }catch (Exception e){
            e.printStackTrace();
        }
        //2: Build frequency table.
        Map<Character, Integer> bs =buildFrequencyTable(ch);
        //3: Use frequency table to construct a binary decoding trie.
        BinaryTrie bt= new BinaryTrie(bs);

        //4: Write the binary decoding trie to the .huf file.
        /** Create a file called .huf that ObjectWriter ow will write to. */
        ObjectWriter ow = new ObjectWriter(args[0]+".huf");
        ow.writeObject(bt);

        //6: Use binary trie to create lookup table for encoding.
        Map<Character, BitSequence> map=bt.buildLookupTable();
        //7: Create a list of bitsequences.
        List<BitSequence> list= new ArrayList<>();
        //8: For each 8 bit symbol:
        //    Lookup that symbol in the lookup table.
        //    Add the appropriate bit sequence to the list of bitsequences.
        //9: Assemble all bit sequences into one huge bit sequence.
        for(char c:ch){
            if(map.containsKey(c))
                list.add(map.get(c));
        }
        BitSequence all= BitSequence.assemble(list);
        //10: Write the huge bit sequence to the .huf file.
        ow.writeObject(all);

    }
}