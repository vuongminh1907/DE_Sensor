import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticEvolution {
    private double crossoverRate;
    private double mutationRate;
    private int populationSize;
    private List<Integer> listSensors;
    private ArrayList<ArrayList<Integer>> listTargets;
    private int numTargets;

    public GeneticEvolution(double crossoverRate, double mutationRate, int populationSize, List<Integer> listSensors, ArrayList<ArrayList<Integer>> listTargets, int numTargets) {
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.listSensors = listSensors;
        this.listTargets = listTargets;
        this.numTargets = numTargets;
    }

    public Population initPopulation() {
        return new Population(populationSize, listSensors.size(), listSensors, listTargets, numTargets);
    }

    public Individual getFittest(Population population) {
        Individual[] individuals = population.getPopulation();
        int fittestIndex = 0;
        int maxFitness = individuals[0].getFitness();

        for (int i = 1; i < populationSize; i++) {
            int fitness = individuals[i].getFitness();
            if (fitness > maxFitness) {
                maxFitness = fitness;
                fittestIndex = i;
            }
        }

        return individuals[fittestIndex];
    }

    public Individual crossover(Individual parent1, Individual parent2) {
        // crossover điểm 
        int crossoverPoint = new Random().nextInt(parent1.getLength());

        List<Integer> newGenes = new ArrayList<>();
        for (int i = 0; i < crossoverPoint; i++) {
            newGenes.add(parent1.getSensor(i));
        }

        for (int i = crossoverPoint; i < parent2.getLength(); i++) {
            newGenes.add(parent2.getSensor(i));
        }

        return new Individual(newGenes, listTargets, numTargets);
    }

    public Individual mutate(Individual individual) {
        // Đột biến bằng cách tráo đổi 2 gen
        int pos1 = new Random().nextInt(individual.getLength());
        int pos2 = new Random().nextInt(individual.getLength());
    
        List<Integer> mutatedGenes = new ArrayList<>(individual.getIndividual());
        Collections.swap(mutatedGenes, pos1, pos2);
    
        return new Individual(mutatedGenes, listTargets, numTargets);
    }
    

    public Population replaceWorst(Population population, Individual newIndividual) {
        int worstIndex = findWorstIndex(population);
        int worstFitness = population.getIndividual(worstIndex).getFitness();
        int newFitness = newIndividual.getFitness();

        if (newFitness > worstFitness) {
            population.setIndividual(worstIndex, newIndividual);
        }

        return population;
    }

    private int findWorstIndex(Population population) {
        int worstIndex = 0;
        int minFitness = population.getIndividual(0).getFitness();

        for (int i = 1; i < populationSize; i++) {
            int fitness = population.getIndividual(i).getFitness();
            if (fitness < minFitness) {
                minFitness = fitness;
                worstIndex = i;
            }
        }

        return worstIndex;
    }
}
