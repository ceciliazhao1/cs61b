import java.util.*;
import java.io.*;


public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";
    //"";//output:no dict file
    //"trivial_words.txt";
    //"words.txt";
    public Boggle(){

    }


    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public List<String> solve(int k, String boardFilePath){
        if(k<0){
            throw new IllegalArgumentException("k is non-postive");
        }
        StringBuffer dictfromtxt= new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new FileReader(dictPath));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                dictfromtxt.append(s+",");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("no dict file");
        }


        WordTrie myTrie = new WordTrie();
        TrieNode root = myTrie.root;

        String [] dict= dictfromtxt.toString().split(",");
        for (String word : dict){
            word=word.toLowerCase();//非字母的字符不受影响。
            myTrie.insert(word);
        }

        //构建结果集容器
        List<String> result = new LinkedList<>();
        String [] linebyline= boardFilePath.split(",");
        String pre=linebyline[0];
        for(String s : linebyline){
            if(pre.length()!=s.length()){
                throw new IllegalArgumentException("The input board is not rectangular");
            }
        }
        //矩阵列数
        int n = linebyline.length;
        //矩阵行数
        int m = linebyline[0].length();
        char [][] board= new char [m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                board[i][j]=linebyline[j].charAt(i);
            }
        }

        //存储该节点是否访问
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                find(board, visited, i, j, m, n, result, root);
            }
        }
        Collections.sort(result,new StringComparator());

        return result;
    }
    private void find(char[][] board, boolean[][] visited, int i, int j, int m, int n, List<String> result, TrieNode cur) {
        //边界判断以及是否已经访问判断
        if (i < 0 || i >= m || j < 0 || j >= n || visited[j][i])
            return;
        //获取子节点状态，判断其是否有子节点
        cur = cur.child[board[i][j] - '\''];
        if (cur == null) {
            return;
        }
        //修改节点状态，防止重复访问
        visited[j][i] = true;
        //找到单词加入
        if (cur.isEnd) {
            result.add(cur.word);
            //找到单词后，修改字典树内叶子节点状态为false，防止出现重复单词
            cur.isEnd = false;
        }
        find(board, visited, i + 1, j, m, n, result, cur);
        find(board, visited, i - 1, j, m, n, result, cur);
        find(board, visited, i, j + 1, m, n, result, cur);
        find(board, visited, i, j - 1, m, n, result, cur);
        find(board, visited, i + 1, j+1, m, n, result, cur);
        find(board, visited, i + 1, j-1, m, n, result, cur);
        find(board, visited, i-1, j + 1, m, n, result, cur);
        find(board, visited, i-1, j - 1, m, n, result, cur);
        //最后修改节点状态为未访问状态
        visited[j][i] = false;
    }
    public static void main(String[] args){

    }

}
