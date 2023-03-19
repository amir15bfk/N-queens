import java.util.*;

public class NQueensSolver {
    
    private int N = 0;
    private int[] board = null;
    private int nodes_cpt = 0;
    private int checks_cpt = 0;
    public Sol solve(String algo) {
        int r = 1;
        nodes_cpt = 0;
        checks_cpt = 0;
        if (algo == "DFS"){
            r= dfs(0);
        }
        if (algo == "BFS"){
            r = bfs();
        }
        if (algo == "A* h1"){
            r = aStar(1);
        }
        if (algo == "A* h2") {
            r=aStar(2);
        }
        return new Sol(r, nodes_cpt,checks_cpt,board);
    }
    public NQueensSolver(int N){
        this.N = N;
        this.board = new int[N];
    }
    
    private int dfs(int row) {
        nodes_cpt++;
        if (row == N) {
            return 0;
        }
        
        for (int col = 0; col < N; col++) {
            checks_cpt ++;
            if (isValid(row, col)) {
                board[row] = col;
                if (dfs(row + 1)!=1){
                    return 0;
                }
            }
        }
        return 1;
    }
    private int bfs() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(0, -1));
        
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int row = node.getRow();
            int col = node.getCol();
            nodes_cpt++;
            if (row == N) {
                updateBoard(node);
                return 0;
            } else {
                for (int j = 0; j < N; j++) {
                    checks_cpt ++;
                    if (col == -1 || isValid(row, j,node)) {
                        Node newNode = new Node(row + 1, j, node);
                        queue.add(newNode);
                        board[row] = j;
                    }
                }
            }
        }
        return 1;
    }


    private int aStar(int h_chois) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        
        queue.add(new Node(0, -1, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int row = node.getRow();
            int col = node.getCol();
            nodes_cpt++;
            if (row == N) {
                updateBoard( node );
                return 0;
            }
            
            for (int j = 0; j < N; j++) {
                checks_cpt ++;
                if (col == -1 || isValid(row, j,node)) {
                    int g = node.getG() + 1;
                    int h;
                    if (h_chois == 1){
                        h = heuristic1(g);
                    }else{
                        h = heuristic2(j,node,g);
                    }
                    int f = g + h;
                    Node newNode = new Node(row + 1, j, f, g, node);
                    queue.add(newNode);
                    board[row] = j;
                }
            }
        }
        return 1;
    }
    private int heuristic1(int g) {
        return -2*g;
    }
    private int heuristic2(int col,Node parant ,int g) {
        return Math.abs(col-parant.col)-2*g;
    }
    private boolean isValid(int row, int col,Node sol) {
        Node node = sol;
        for (int i=sol.row-1;i>=0;i--){
            board[i] =node.col;
            node = node.parent;
        }
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == row - i) {
                return false;
            }
        }
        return true;
    }
    private boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == row - i) {
                return false;
            }
        }
        return true;
    }
    private void updateBoard(Node sol) {
        Node node = sol;
        for (int i=sol.row-1;i>=0;i--){
            board[i] =node.col;
            node = node.parent;
        }
    }
    private void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

private static class Node {
    private int row;
    private int col;
    private int f;
    private int g;
    private Node parent;
    
    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.parent = null;
    }

    public Node(int row, int col, int f) {
        this.row = row;
        this.col = col;
        this.f = f;
        this.g = 0;
        this.parent = null;
    }
    public Node(int row, int col, Node parent) {
        this.row = row;
        this.col = col;
        this.parent = parent;
    }
    public Node(int row, int col, int f, int g, Node parent) {
        this.row = row;
        this.col = col;
        this.f = f;
        this.g = g;
        this.parent = parent;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public int getF() {
        return f;
    }
    
    public int getG() {
        return g;
    }

}
}
class Sol {
    public int flag;
    public int nodes_cpt;
    public int checks_cpt;
    public int [] board;

    
    public Sol(int flag, int nodes_cpt,int checks_cpt, int [] board) {
        this.flag = flag;
        this.nodes_cpt = nodes_cpt;
        this.checks_cpt = checks_cpt;
        this.board = board;
    }
    

}
