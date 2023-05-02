
import java.util.*;
public class NQueensGA {

    public NQueensGA(int N, int populationSize, double crossoverProbability, double mutationProbability, int maxGenerations) {
        Individual.init(N, crossoverProbability, mutationProbability, maxGenerations);
        Individual.createPopulation(populationSize);
    }

    public int[] run() {
        int iter = 0;
        while (Individual.getBest().getFitness() != Individual.getMaxFitness() && iter < Individual.maxGenerations) {
            Individual.updateAll();
            iter++;
            //System.out.println("generation : "+iter+"  size:"+Individual.population.size());
        }
        return Individual.getBest().getChromosome();
    }

    public int getState() {
        if (Individual.getBest().getFitness() == Individual.getMaxFitness())
            return 0;
        else
            return 2;
    }

    static class Individual {
        private static int N;
        private static double crossoverProbability;
        private static double mutationProbability;
        private static int maxGenerations;
        public static ArrayList<Individual> population;
        private static Individual globalBest;

        private int[] chromosome;
        private int fitness;

        public static void init(int N) {
            Individual.N = N;
            Individual.population = new ArrayList<>();
            Individual.globalBest = new Individual(N);
        }

        public static void init(int N, double crossoverProbability, double mutationProbability, int maxGenerations) {
            init(N);
            Individual.crossoverProbability = crossoverProbability;
            Individual.mutationProbability = mutationProbability;
            Individual.maxGenerations = maxGenerations;
        }

        public static void createPopulation(int populationSize) {
            for (int i = 0; i < populationSize; i++) {
                population.add(new Individual(N));
                
            }
        }

        public Individual(int N) {
            this.chromosome = new int[N];
            for (int j = 0; j < N; j++) {
                this.chromosome[j] = (int) (Math.random() * N);
            }
            this.fitness = calculateFitness();
            updateGlobalBest();
        }

        private int calculateFitness() {
            int fitness = 0;
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (chromosome[i] == chromosome[j] || Math.abs(chromosome[i] - chromosome[j]) == Math.abs(j - i)) {
                        // Two queens are attacking each other
                        fitness--;
                    } else {
                        fitness++;
                    }
                }
            }
            return fitness;
        }

        private static Individual selectParent() {
            int k = 5;
            Individual[] tournament = new Individual[k];
            for (int i = 0; i < k; i++) {
                tournament[i] = population.get((int) (Math.random() * population.size()));
            }
            Arrays.sort(tournament, Comparator.comparingInt(individual -> -individual.fitness));
            return tournament[0];
        }

        private void crossover(Individual parent1, Individual parent2) {
            int[] child = new int[N];
            int crossoverPoint = (int) (Math.random() * (N - 1)) + 1;
            for (int i = 0; i < crossoverPoint; i++) {
                child[i] = parent1.chromosome[i];
            }
            for (int i = crossoverPoint; i < N; i++) {
                child[i] = parent2.chromosome[i];
            }
            this.chromosome = child;
            this.fitness = calculateFitness();
        }

        private void mutate() {
            for (int mutationIndex=0;mutationIndex<N;mutationIndex++){
                if (Math.random() < mutationProbability) {
                    chromosome[mutationIndex] = (int) (Math.random() * N);
                }
            }
            this.fitness = calculateFitness();
        }

        public static void updateAll() {
            ArrayList<Individual> newPopulation = new ArrayList<>();

            while (newPopulation.size() < population.size()) {
                Individual parent1 = selectParent();
                Individual parent2 = selectParent();

                if (Math.random() < crossoverProbability) {
                    Individual child = new Individual(N);
                    child.crossover(parent1, parent2);
                    newPopulation.add(child);
                } else {
                    newPopulation.add(parent1);
                }
            }

            for (Individual ind : newPopulation) {
                ind.mutate();
            }
            population = newPopulation;
            globalBest = Collections.max(population, Comparator.comparingInt(Individual::getFitness));
        }

        public int getFitness() {
            return this.fitness;
        }

        public int[] getChromosome() {
            return this.chromosome;
        }

        public static int getMaxFitness() {
            return N * (N - 1) / 2;
        }

        public static Individual getBest() {
            return globalBest;
        }

        private void updateGlobalBest() {
            if (globalBest == null || this.fitness > globalBest.getFitness()) {
                globalBest = this;
            }
        }
    }
}
