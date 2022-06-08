package byog.lab5;
import edu.princeton.cs.algs4.StdRandom;
import javafx.geometry.Pos;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.awt.*;
import java.util.Random;

public class HexWorld {
    static final int WIDTH = 80;
    static final int HEIGHT = 50;
    public static final long SEED = 2873123;
    public static Random RANDOM = new Random(SEED);

    public static class Position {
        public int x;
        public int y;

        public  Position (int x_pos, int y_pos) {
            x = x_pos;
            y = y_pos;
        }
    }

    public static int hexRowWidth(int s, int r) {
        int RowExact = r;
        if(r >= s){     //When row bigger than size, we need do some calculate, otherwise, we need use s + (RowExact * 2)
            RowExact = (s * 2) - 1 - RowExact;
        }
        return (s + (RowExact * 2));
    }

    public static int hexRowOffset (int s, int r) {
        int RowExact = r;
        if(r >= s){     //When row bigger than size, we need do some calculate, otherwise, we need use s + (RowExact * 2)
            RowExact = (s * 2) - 1 - RowExact;
        }
        return -RowExact;
    }

    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for(int xi = 0; xi < width; xi++){
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t){
        if(s < 2){
            throw new IllegalArgumentException("Hexagon must at least size 2");
        }
        //the total rows in the hex is 2 * s.
        //the bottom row's number is zero

        for(int yi = 0; yi < 2 * s; yi++) {
            int currentRowY = p.y + yi;
            int currentRowX = p.x + hexRowOffset(s, yi);
            Position rowStartUp = new Position(currentRowX, currentRowY);
            int rowWidth = hexRowWidth(s, yi);
            addRow(world, rowStartUp, rowWidth, t);
        }
    }

    public static Position getPositionOfNext_L(Position p, int s) {
        int xl = p.x + hexRowOffset(s, s) - s;
        int yl = p.y + s;
        Position p_new = new Position(xl, yl);
        return p_new;
    }

    public static Position DrawNext_L_N(TETile[][] world, Position p, int s, int n) {
        TETile[][] randomTiles = world;
        Position p_new = p;
        for(int i = 0; i < n; i++){
            p_new = getPositionOfNext_L(p_new, s);
            addHexagon(randomTiles, p_new, s, randomTile());
        }
        return p_new;
    }

    public static Position getPositionOfNext_R(Position p, int s) {
        int xl = p.x + s - hexRowOffset(s, s);
        int yl = p.y + s;
        Position p_new = new Position(xl, yl);
        return p_new;
    }

    public static Position DrawNext_R_N(TETile[][] world, Position p, int s, int n) {
        TETile[][] randomTiles = world;
        Position p_new = p;
        for(int i = 0; i < n; i++){
            p_new = getPositionOfNext_R(p_new, s);
            addHexagon(randomTiles, p_new, s, randomTile());
        }
        return p_new;
    }

    public static Position getPositionOfNext_UP(Position p, int s) {
        int xl = p.x;
        int yl = p.y + (2 * s);
        Position p_new = new Position(xl, yl);
        return p_new;
    }

    public static Position DrawNext_UP_N(TETile[][] world, Position p, int s, int n) {
        TETile[][] randomTiles = world;
        Position p_new = p;
        for(int i = 0; i < n; i++){
            p_new = getPositionOfNext_UP(p_new, s);
            addHexagon(randomTiles, p_new, s, randomTile());
        }
        return p_new;
    }

    public static Position getPositionOfNext_DOWN(Position p, int s) {
        int xl = p.x;
        int yl = p.y - (2 * s);
        Position p_new = new Position(xl, yl);
        return p_new;
    }

    public static Position DrawNext_DOWN_N(TETile[][] world, Position p, int s, int n) {
        TETile[][] randomTiles = world;
        Position p_new = p;
        for(int i = 0; i < n; i++){
            p_new = getPositionOfNext_DOWN(p_new, s);
            addHexagon(randomTiles, p_new, s, randomTile());
        }
        return p_new;
    }

    public static TETile randomTile() {
        int tileNum = StdRandom.uniform(4);
        switch (tileNum) {
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            default: return Tileset.TREE;
        }
    }

    public static void addTesselationHexagon(TETile[][] world, Position p, int s) {
        Position p_temp = p;
        addHexagon(world, p_temp, s, randomTile());
        DrawNext_UP_N(world, p_temp, s, 4);

        Position p_temp_1 = DrawNext_R_N(world, p_temp, s, 2);
        Position p_temp_2 = DrawNext_UP_N(world, p_temp_1, s, 2);
        Position p_temp_3 = DrawNext_L_N(world, p_temp_2, s, 1);
        DrawNext_DOWN_N(world, p_temp_3, s, 2);

        Position p_temp_4 = DrawNext_L_N(world, p_temp, s, 2);
        Position p_temp_5 = DrawNext_UP_N(world, p_temp_4, s, 2);
        Position p_temp_6 = DrawNext_R_N(world, p_temp_5, s, 1);
        DrawNext_DOWN_N(world, p_temp_6, s, 2);

    }

    public static void main(String[] args) {
        int s = 3;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];

        //character: the pattern on the hexagon
        //color(1) character's color
        //color(2) hexagon background color
        //description some description words   <--
        //like that:                              |
        //public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");

        //if I write: Position p = new Position(1, 10), that will cause an error called "ArrayIndexOutOfBoundsException"
        //because the hexagon size is 3, so the widest row have two tiles more than the zero row, if Position(1, 10)
        //the tile out of bound, that's why it led to an error.
        Position p = new Position(20, 10);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                randomTiles[x][y] = Tileset.NOTHING;
            }
        }
        addTesselationHexagon(randomTiles, p, s);
        ter.renderFrame(randomTiles);
    }
}


