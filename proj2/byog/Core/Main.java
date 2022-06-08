package byog.Core;

import byog.TileEngine.TETile;

/** 2D伪随机生成。就是只要seed是确定的，世界就是确定的；seed改变，世界重新随机生成。
 包括rooms和hallways，也可以有外部空间（NOTHING)。
 房间需要是长方形的，也可以有其他形状。
 房间、走廊大小、长度、数量、位置随机。
 房间和走廊需要相连
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(args[0]);
            System.out.println(TETile.toString(worldState));
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }
    }
}
/**args是在编译的时候输入的字符串，当只有一个字符串时，调用game.playWithInputString(String s)方法。
 * 注意这个args不等于交互，是在编译的时候默认输入的。
 * worldState接收了生成好的TETile世界，然后下一行
 * TETile.toString将世界的二维TETile数组转化为字符串，
 * 输出在控制台。这个方法主要用于phase 1的测试，
 * 并没有调用stdDraw库中的函数。当没有args时，
 * 进入game.playWithKeyboard()方法，包含欢迎界面，用户交互。
 * 所以在phase 1，只需要注意第一种方法。
 *
 */
