package hw3.hash;

import java.util.Hashtable;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        Hashtable<Integer,Integer>  table=new Hashtable();
        for(Oomage o: oomages){
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if(table.containsKey(bucketNum)){
                table.put(bucketNum, table.get(bucketNum)+1);
            } else {
                table.put(bucketNum, 1);
            }
        }
        for(int i=0;i<M;i++){
            if((table.get(i)> oomages.size() / 50) && (table.get(i)<oomages.size() / 2.5))
                continue;
            else return false;
        }
        return true;
    }
}
