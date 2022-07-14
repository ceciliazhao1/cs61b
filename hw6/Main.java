import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String boardPath = "smallBoard.txt";
    // "exampleBoard2.txt";
    //"smallBoard.txt";
    public static void main (String []args){
        StringBuffer board = null;
        List<String> result= new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(boardPath));//构造一个BufferedReader类来读取文件
            board = new StringBuffer();
            String line = null;
            while((line = br.readLine())!=null){//使用readLine方法，一次读一行
                board.append(line+",");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        String databoard = new String(board);
        Boggle b= new Boggle();
        result=b.solve(8,databoard);

        int kth=8;
        if(result.size()<8)
            kth= result.size();

        for(int i=0;i<kth;i++)
            System.out.println(result.get(i));
    }


}
