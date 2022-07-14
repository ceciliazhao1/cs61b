import java.util.Comparator;
/**
 * @Description: 通过实现Comparator接口，实现自定义排序
 */
public class StringComparator implements Comparator<String>{
    /**
     * 按字符串长度降序排序
     */
    @Override
    public int compare(String o1, String o2) {
        if(o1.length() > o2.length()) return -1;
        if(o1.length() < o2.length()) return 1;
        for(int i=0;i<o1.length();i++){
            if(o1.charAt(i)-o2.charAt(i)==0){
                continue;
            }else{
                return o1.charAt(i)-o2.charAt(i);
            }
        }
        return 0;
    }
}