import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class NQueensTest {
    public static void testRate(String filename,String algo){
        NQueensSolver solver;
        Sol sol;
        //Integer[] sizes = { 4, 8, 10, 12, 15, 20, 30,50,70,100};
        Integer[] sizes = { 4, 8, 10, 12, 15, 20, 30};
        FileWriter fw;
        for(int N: sizes){
        solver = new NQueensSolver(N);
        sol = solver.solve(algo);
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
    public static void testGAparm(String filename){
        NQueensGA GA_solver;
        int N = 30;
        int[] board = null;
        Sol sol;
        Integer[] values1 = { 100,200,500,1000,1500,3000};
        //Integer[] values2 = { 40,400,1000,2000,4000,10000};
        Integer[] values2 = { 4000};
        FileWriter fw;
        
        for(int var1: values1){
            for(int var2: values2){
            GA_solver = new NQueensGA(N, var1,0.5,0.03,var2);
            long start = System.currentTimeMillis();
            board = GA_solver.run();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(Arrays.toString(board) + GA_solver.getState());
            sol =  new Sol(GA_solver.getState(), board);
        try {
            // true for append mode
            fw = new FileWriter(filename, true);
            fw.write(N +","+var1+","+var2+","+timeElapsed+ sol.logmsg);
            fw.write("\n"); // add newline
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}}
    }
    public static void testGAparm2(String filename){
        NQueensGA GA_solver;
        int N = 30;
        int[] board = null;
        Sol sol;
        double[] values = { 0,0.1,0.2,0.3,0.5,0.6,0.7,0.8,0.9,1};
        FileWriter fw;
    
        for(double var1: values){
            
            GA_solver = new NQueensGA(N, 1000,var1,0.03,2000);
            long start = System.currentTimeMillis();
            board = GA_solver.run();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(Arrays.toString(board) + GA_solver.getState());
            sol =  new Sol(GA_solver.getState(), board);
        try {
            // true for append mode
            fw = new FileWriter(filename, true);
            fw.write(N +","+var1+","+timeElapsed+ sol.logmsg);
            fw.write("\n"); // add newline
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    }
    public static void testGAparm3(String filename){
        NQueensGA GA_solver;
        int N = 30;
        int[] board = null;
        Sol sol;
        double[] values = { 0,0.005,0.01,0.03,0.05,0.1,0.2,0.4,0.6,0.8,0.9,1};
        FileWriter fw;
    
        for(double var1: values){
            
            GA_solver = new NQueensGA(N, 1000,0.5,var1,2000);
            long start = System.currentTimeMillis();
            board = GA_solver.run();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(Arrays.toString(board) + GA_solver.getState());
            sol =  new Sol(GA_solver.getState(), board);
        try {
            // true for append mode
            fw = new FileWriter(filename, true);
            fw.write(N +","+var1+","+timeElapsed+ sol.logmsg);
            fw.write("\n"); // add newline
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    }
    public static void testPSOparm1(String filename){
        NQueensPSO pso_solver;
        int N = 30;
        int[] board = null;
        Sol sol;
        Integer[] values1 = { 100,200,500,1000,1500};
        //Integer[] values2 = { 40,400,1000,2000,4000};
        Integer[] values2 = { 1500};
        FileWriter fw;
    
        for(int var1: values1){
            for(int var2: values2){
            pso_solver = new NQueensPSO(N,var1, 0.4f, 1.49445f, 1.49445f);
            long start = System.currentTimeMillis();
            board = pso_solver.run(var2);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(Arrays.toString(board) + pso_solver.getState());
            sol =  new Sol(pso_solver.getState(), board);
        try {
            // true for append mode
            fw = new FileWriter(filename, true);
            fw.write(N +","+var1+","+var2+","+timeElapsed+ sol.logmsg);
            fw.write("\n"); // add newline
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}}
    }
    public static void testPSOparm2(String filename){
        NQueensPSO pso_solver;
        int N = 30;
        int[] board = null;
        Sol sol;
        //float[] values = { 0.0f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};
        float[] values = { 0.0f,0.1f};
        FileWriter fw;
    
        for(float var1: values){
            
            pso_solver = new NQueensPSO(N,1000, var1, 1.49445f, 1.49445f);
            long start = System.currentTimeMillis();
            board = pso_solver.run(500);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(Arrays.toString(board) + pso_solver.getState());
            sol =  new Sol(pso_solver.getState(), board);
        try {
            // true for append mode
            fw = new FileWriter(filename, true);
            fw.write(N +","+var1+","+timeElapsed+ sol.logmsg);
            fw.write("\n"); // add newline
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    }
    public static void testPSOparm3(String filename){
        NQueensPSO pso_solver;
        int N = 30;
        int[] board = null;
        Sol sol;
        float[] values1 = { 0.2f,0.4f,0.6f,0.9f,1.2f,1.49445f,1.6f,1.9f};
        float[] values2 = { 0.2f,0.4f,0.6f,0.9f,1.2f,1.49445f,1.6f,1.9f};
        FileWriter fw;
    
        for(float var1: values1){
            for(float var2: values2){
            pso_solver = new NQueensPSO(N,1000, 0.4f, var1, var2);
            long start = System.currentTimeMillis();
            board = pso_solver.run(500);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(Arrays.toString(board) + pso_solver.getState());
            sol =  new Sol(pso_solver.getState(), board);
        try {
            // true for append mode
            fw = new FileWriter(filename, true);
            fw.write(N +","+var1+","+var2+","+timeElapsed+ sol.logmsg);
            fw.write("\n"); // add newline
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}}
    }
    public static void main(String []args){
        for(int i=0;i<10;i++){
            testRate("ga_sr2.log","GA");
            //testGAparm("ga_doublepram1.log");
            //testGAparm2("ga_crossoverrate2.log");
            //testGAparm3("ga_mutation3.log");
            //testPSOparm1("PSO_doublepram1.log");
            //testPSOparm2("PSO_w1.log");
            //testPSOparm3("PSO_cs2.log");
        }
    }
}
