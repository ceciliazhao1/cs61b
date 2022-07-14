import java.util.*;
import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
public class SeamCarver {
    public Picture picture;
    private int width;
    private int height;


    public SeamCarver(Picture picture){
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
    }
    public Picture picture(){
        return new Picture(picture);
    }// current picture
    public int width(){return picture.width();}//picture是变化的，所以返回picturelibrary里的width函数
    public int height(){return picture.height();}

    public  double energy(int x, int y) {
        if (x < 0 || x >= width()  || y < 0 || y >= height() ) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int xl, xr, yh, yl;
        if (x == 0) {xl = width() - 1;} else {xl = x - 1;}
        if (x == width() - 1) {xr = 0;} else {xr = x + 1;}
        if (y == 0) {yh = height() - 1;} else {yh = y - 1;}
        if (y == height() - 1) {yl = 0;} else {yl = y + 1;}
        int energyHor = pow(getR(xl, y) - getR(xr, y)) +
                pow(getG(xl, y) - getG(xr, y)) + pow(getB(xl, y) - getB(xr, y));
        int energyVer = pow(getR(x, yh) - getR(x, yl)) +
                pow(getG(x, yh) - getG(x, yl)) + pow(getB(x, yh) - getB(x, yl));
        return energyHor + energyVer;

    }
    int getR(int x, int y){return(picture.getRGB(x,y) >> 16) & 0xFF;}
    int getG(int x, int y){return (picture.getRGB(x,y) >> 8) & 0xFF;}
    int getB(int x, int y){return picture.getRGB(x,y) & 0xFF;}
    private int pow(int x) { return x * x; }
    public   int[] findVerticalSeam(){
        double [][] energyMat= new double[width()][height()];
        for(int i=0;i<width();i++){
            for(int j=0;j<height();j++){
                energyMat[i][j]=energy(i,j);
            }
        }
        double dp[][]= new double [width()][height()];
        int[][] prePixelMat = new int[width()][height()];
        //Arrays.fill(dp,Double.MAX_VALUE);
        for (int i = 0; i < width(); i++) {
            dp[i][0] = energyMat[i][0];
        }

        for (int j = 1; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                double minCost=Double.MAX_VALUE;
                for (int n = -1; n <=1; n++) {
                    if (i + n < 0 || i + n >= width())  { continue; }
                    if (dp[i + n][j - 1] < minCost) {
                        minCost = dp[i + n][j - 1];//取上一层的最小值
                        dp[i][j] = minCost + energyMat[i][j];//路上累加的最小值
                        prePixelMat[i][j] = i + n;//记录上一层的x坐标i-1，i，i+1

                    }
                }
            }
        }
        // 找到x坐标，find bottom row
        double minCostTotal = dp[0][height() - 1];
        int minEndIndex = 0;
        for (int i = 1; i < width(); i++) {
            if (dp[i][height() - 1] < minCostTotal) {
                minCostTotal = dp[i][height() - 1];
                minEndIndex = i;
            }
        }
        int [] res= new int[height()];
        res[height()-1]=minEndIndex;
        for (int j = height() - 1; j >= 1; j--) {
            minEndIndex = prePixelMat[minEndIndex][j];//每一个minEndIndex记录上一层的x坐标i-1，i，i+1
            res[j - 1] = minEndIndex;
        }
        return res;
    }
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        Picture blank = new Picture(height(), width());
        Picture pictureCopy = new Picture(width(), height());
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                blank.setRGB(j,i,picture.getRGB(i,j));
                pictureCopy.setRGB(i,j,picture.getRGB(i,j));
            }
        }
        this.picture = new Picture(blank);
        int[] res = findVerticalSeam();
        this.picture = new Picture(pictureCopy);
        return res;
    }
    // sequence of indices for vertical seam
    public void removeHorizontalSeam(int[] seam){
        if(seam.length!=width())
            throw new IllegalArgumentException("wrong width of seam!");
        for(int i=1;i<width();i++){
            if(Math.abs(seam[i]-seam[i-1])>1){
                throw new IllegalArgumentException("more than 1 of seam!");
            }
        }
        picture=SeamRemover.removeHorizontalSeam(picture,seam);
    }// remove horizontal seam from picture
    public void removeVerticalSeam(int[] seam) {
        if(seam.length!=height())
            throw new IllegalArgumentException("wrong height of seam!");
        for(int i=1;i<height();i++){
            if(Math.abs(seam[i]-seam[i-1])>1){
                throw new IllegalArgumentException("more than 1 of seam!");
            }
        }
        picture=SeamRemover.removeVerticalSeam(picture,seam);
    }// remove vertical seam from picture
}
