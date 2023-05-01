import java.io.FileWriter;
import java.io.IOException;

public class NQueensTest {
    public static void testRate(String filename){
        NQueensSolver solver;
        Sol sol;
        Integer[] sizes = { 4, 8, 10, 12, 15, 20, 30,50,70,100};
        FileWriter fw;
        for(int N: sizes){
        solver = new NQueensSolver(N);
        sol = solver.solve("GA");
        try {
            // true for append mode
            fw = new FileWriter(filename, true);
            fw.write(N + sol.logmsg);
            fw.write("\n"); // add newline
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    }
    public static void main(String []args){
        for(int i=0;i<15;i++){
        testRate("ga_sr1.log");}
    }
}
