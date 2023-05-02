import java.util.*;

public class NQueensPSO {

    public NQueensPSO(int N, int number_workers) {
        Particale.init(N);
        Particale.createParticales(number_workers);
    }

    public NQueensPSO(int N, int number_workers, float inertia, float pi, float si) {
        Particale.init(N, inertia, pi, si);
        Particale.createParticales(number_workers);
    }

    public int[] run(int max_iter) {
        int iter = 0;
        while (Particale.getBest().getScore() != 1 && iter < max_iter) {
            Particale.updateAll();
            iter++;
        }
        return Particale.getBest().getVector();
    }

    public int getState() {
        if (Particale.getBest().getScore() == 1)
            return 0;
        else
            return 2;
    }

}

class Particale {
    private static int N;// problem size
    private static int number_of_particales = 0;// number of particales
    private static ArrayList<Particale> list_of_particales;// number of particales
    private static float w = 0.4f;// Inertia
    private static float c1 = 1.49445f;// Personal Influence
    private static float c2 = 1.49445f;// Social Influence
    private static Point global_best;
    private Point private_best;
    private Point velocities;
    private Point position;

    public static void init(int size) {
        N = size;
        number_of_particales = 0;
        list_of_particales = new ArrayList<Particale>();
        global_best = new Point(N, N);
    }

    public static void init(int size, float inertia, float pi, float si) {
        N = size;
        number_of_particales = 0;
        list_of_particales = new ArrayList<Particale>();
        global_best = new Point(N, N);
        w = inertia;// Inertia
        c1 = pi;// Personal Influence
        c2 = si;// Social Influence

    }

    public static void createParticales(int number_workers) {
        for (int i = 0; i < number_workers; i++) {
            new Particale();
        }
        globalUpdate();
    }

    public Particale() {
        number_of_particales++;
        list_of_particales.add(this);
        this.position = new Point(N, N);
        this.private_best = new Point(this.position.getVectorCopy());
        this.velocities = new Point(N, (int) (N/ 4),(int) (N / 8));
    }

    public static void updateAll() {
        for (Particale p : list_of_particales) {
            p.update();
        }
        globalUpdate();
    }

    public void update() {

        this.velocities = Point.velocitieUpdate(this.position, this.velocities, this.private_best, global_best,
                w, c1, c2);
        this.position = Point.add(this.position, this.velocities);
        this.privateUpdate();
    }

    public void privateUpdate() {
        if (this.private_best.getScore() < this.position.getScore()) {
            this.private_best = new Point(this.position.getVectorCopy());
        }
    }

    public static void globalUpdate() {
        for (Particale p : list_of_particales) {
        if (global_best.getScore() < p.position.getScore()) {
            global_best = new Point(p.position.getVectorCopy());
        }}
    }

    public static int getN() {
        return N;
    }

    public static int getNumber_of_particales() {
        return number_of_particales;
    }

    public static Point getBest() {
        return global_best;
    }

}

class Point {
    private int[] vector;
    private double score;

    public Point(int size, int max) {
        this.vector = new int[size];
        for (int i = 0; i < max; i++) {
            this.vector[i] = (int) (Math.random() * max);
        }
        this.setVector(vector);
    }
    public Point(int size, int max,int norm) {
        this.vector = new int[size];
        for (int i = 0; i < max; i++) {
            this.vector[i] = (int) (Math.random() * max-norm);
        }
        this.setVector(vector);
    }

    public Point(int[] vector) {
        this.setVector(vector);
    }

    private double computeScore() {
        // Count the number of attacking queen pairs
        int numAttackingPairs = 0;
        for (int i = 0; i < this.vector.length; i++) {
            for (int j = i + 1; j < this.vector.length; j++) {
                if (this.vector[i] == this.vector[j] || Math.abs(this.vector[i] - this.vector[j]) == Math.abs(i - j)) {
                    numAttackingPairs++;
                }
            }
        }

        // Return a fitness value that is inversely proportional to the number of
        // attacking queen pairs
        return 1.0 / (numAttackingPairs + 1);
    }

    public void setVector(int[] vector) {
        this.vector = vector;
        this.score = computeScore();
    }

    public int[] getVector() {
        return this.vector;
    }

    public int[] getVectorCopy() {
        return this.vector.clone();
    }

    public int getVector(int i) {
        return this.vector[i];
    }

    public double getScore() {
        return this.score;
    }

    public static Point add(Point point1, Point point2) {
        int N = point1.getVector().length;
        if (N == point2.getVector().length) {
            int[] newVector = new int[N];
            for (int i = 0; i < N; i++) {
                newVector[i] = posMod((point1.getVector(i) + point2.getVector(i)), N);
            }
            return new Point(newVector);
        } else {
            return null;
        }
    }

    public static Point sub(Point point1, Point point2) {
        int N = point1.getVector().length;
        if (N == point2.getVector().length) {
            int[] newVector = new int[N];
            for (int i = 0; i < N; i++) {
                newVector[i] = (point1.getVector(i) - point2.getVector(i)) % N;
            }
            return new Point(newVector);
        } else {
            return null;
        }
    }

    public static Point mul(Point point1, float scaler) {
        int N = point1.getVector().length;
        int[] newVector = new int[N];
        for (int i = 0; i < N; i++) {
            newVector[i] = (int) (point1.getVector(i) * scaler) % N;
        }
        return new Point(newVector);
    }

    public static Point velocitieUpdate(Point position, Point velocities, Point private_best, Point global_best,
            float w, float c1, float c2) {
        int N = velocities.getVector().length;
        int[] newVector = new int[N];
        float u1;
        float u2;
        for (int i = 0; i < N; i++) {
            u1 = (float) Math.random();
            u2 = (float) Math.random();
            newVector[i] = posMod((int) (velocities.getVector(i) * w
                    + (private_best.getVector(i) - position.getVector(i)) * c1 * u1
                    + (global_best.getVector(i) - position.getVector(i)) * c2 * u2), N);

        }
        return new Point(newVector);
    }

    public static int posMod(int a, int b) {
        int r = a % b;
        while (r < 0) {
            r += b;
        }
        return r;
    }

}