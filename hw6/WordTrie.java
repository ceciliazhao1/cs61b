public class WordTrie {
    TrieNode root = new TrieNode();

    void insert(String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            //判断是否存在该字符的节点，不存在则创建
            if (cur.child[c - '\''] == null) {
                cur.child[c - '\''] = new TrieNode();
                cur = cur.child[c - '\''];
            } else
                cur = cur.child[c - '\''];
        }
        //遍历结束后，修改叶子节点的状态，并存储字符串
        cur.isEnd = true;
        cur.word = s;
    }
}
