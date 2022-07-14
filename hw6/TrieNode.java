
class TrieNode {
    /**
     * 存储最后节点的字符串
     */
    String word;
    /**
     * 根据字符排序，[',a,b,c,……,z]
     */
    TrieNode[] child = new TrieNode[200];
    /**
     * 是否是最后叶子节点
     */
    boolean isEnd = false;
}