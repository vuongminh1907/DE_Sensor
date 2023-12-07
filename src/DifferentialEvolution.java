import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DifferentialEvolution {
    private double F;
    private int populationSize;
    private List<Integer> listSensors;
    private ArrayList<ArrayList<Integer>> listTargets;
    private int numTargets;

    public DifferentialEvolution(double F, int populationSize, List<Integer> listSensors, ArrayList<ArrayList<Integer>> listTargets, int numTargets) {
        this.F = F;
        this.populationSize = populationSize;
        this.listSensors = listSensors;
        this.listTargets = listTargets;
        this.numTargets = numTargets;
    }

    public Population initPopulation() {
        Population population = new Population(populationSize, listSensors.size(), listSensors, listTargets, numTargets);
        return population;
    }

    public Individual getFittest(Population population) {
        Individual[] individuals = population.getPopulation();

        Arrays.sort(individuals, Comparator.comparingInt(individual -> individual.getFitness()));
        return individuals[populationSize-1];
    }

    public Individual mutate(Population population, Individual target) {
        Individual donor1 = population.getIndividual(new Random().nextInt(populationSize));
        Individual donor2 = population.getIndividual(new Random().nextInt(populationSize));
        Individual donor3 = population.getIndividual(new Random().nextInt(populationSize));

        List<Integer> mutatedGenes = new ArrayList<>();
        for (int i = 0; i < target.getLength(); i++) {
            int donor1Sensor = donor1.getSensor(i);
            int donor2Sensor = donor2.getSensor(i);
            int donor3Sensor = donor3.getSensor(i);
            double mutatedGene = donor1Sensor + F * (donor2Sensor - donor3Sensor);
            mutatedGenes.add((int) mutatedGene);
        }

        List<Integer> sortedGenes = new ArrayList<>(mutatedGenes.size());
        for (int i = 0; i < mutatedGenes.size(); i++) {
            sortedGenes.add(i);
        }
        sortedGenes.sort(Comparator.comparingDouble(mutatedGenes::get));

        Individual mutateIndividual = new Individual(sortedGenes, listTargets, numTargets);

        return mutateIndividual;
    }

    public Individual crossover(Individual target, Individual mutated) {
        int k = new Random().nextInt(target.getLength());

        List<Integer> newGenes = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            newGenes.add(target.getSensor(i));
        }

        for (int i = 0; i < mutated.getLength(); i++) {
            int sensor = mutated.getSensor(i);
            if (!newGenes.contains(sensor)) {
                newGenes.add(sensor);
            }
        }

        Individual newIndividual = new Individual(newGenes, listTargets, numTargets);

        return newIndividual;
    }

    public Population replaceBetter(Population population, Individual target, Individual crossed, int index) {
        int targetFitness = target.getFitness();
        int crossedFitness = crossed.getFitness();

        if (targetFitness < crossedFitness) {
            population.setIndividual(index, crossed);
        }
        return population;
    }
}
