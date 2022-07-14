import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class HuffmanDecoder {
    public static void main(String[] args){
        //1: Read the Huffman coding trie.
        //2: Read the massive bit sequence corresponding to the original txt.
        BinaryTrie bt =null;
        BitSequence all = null;
        try {
            ObjectReader or = new ObjectReader(args[0]);
            /* Read first object from the file. */
            bt = (BinaryTrie)or.readObject();
            /* Read second object from the file. */
            all = (BitSequence)or.readObject();

        }catch (Exception e){
            e.printStackTrace();
        }
        //4: Repeat until there are no more symbols:
        //    4a: Perform a longest prefix match on the massive sequence.
        //    4b: Record the symbol in some data structure.
        //    4c: Create a new bit sequence containing the remaining unmatched bits.
        List<Character> list = new ArrayList<>();
        while(all.length()>0) {

            Match match = bt.longestPrefixMatch(all);
            list.add(match.getSymbol());
            all=all.allButFirstNBits(match.getSequence().length());
        }
        //5: Write the symbols in some data structure to the specified file.
        char[] ch=new char[list.size()];
        for(int i=0;i< list.size();i++){
            ch[i]=list.get(i);
        }
        FileUtils.writeCharArray(args[1], ch);


    }
}