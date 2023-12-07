import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Individual {
    private List<Integer> individual;
    private ArrayList<ArrayList<Integer>> listTargets;  // Liệt kê các target đã phát hiện
    private int numTarget;
    //private int numOfSensor;
    
    // Khởi tạo một cách đảo lộn theo số sensor truyền vào 
    public Individual(int numOfSensor,ArrayList<ArrayList<Integer>> listTargets, int numTarget ) {
        this.individual = new ArrayList<Integer>();
        this.listTargets = listTargets;
        this.numTarget = numTarget;
        for (int i = 0; i < numOfSensor; i++) {
            individual.add(i);
        }
        Collections.shuffle(individual);
        updateListTargets(); 
    }

    public Individual(List<Integer> individual, ArrayList<ArrayList<Integer>> listTargets, int numTarget) {
        this.listTargets = listTargets;
        this.numTarget = numTarget;
        this.individual = individual;
        updateListTargets(); 
    }
    public List<Integer> getIndividual() {
        return individual;
    }
    public void setIndividual(List<Integer> individual) {
        this.individual = individual;
    }
    public int getSensor(int pos) {
        return individual.get(pos);
    }
    public void setSensor(int pos, int s) {
        individual.set(pos, s);
    }
    public void deleteSensor(int pos) {
        individual.remove(pos);
    }
    public int getLength() {
        return individual.size();
    }
    public int getNumtarget(){
        return numTarget;
    }

    private void updateListTargets() {
        ArrayList<ArrayList<Integer>> newListTargets = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < individual.size(); i++) {
            newListTargets.add(listTargets.get(individual.get(i)));
        }
        this.listTargets = newListTargets;
    }
    public ArrayList<ArrayList<Integer>> getListTarget(){
        return listTargets;
    }
     
    public int getFitness() {
        Set<Integer> setTarget = new HashSet<>();
        int k = 0;
        for (int i = 0; i < listTargets.size(); i++) {
            for (int j = 0; j < listTargets.get(i).size(); j++) {
                setTarget.add(listTargets.get(i).get(j));
            }
            if (setTarget.size() == numTarget) {
                k++;
                setTarget.clear();
            }
        }
        return (k * numTarget + setTarget.size());
    }
    
    public int getCovers() {
        Set<Integer> setTarget = new HashSet<>();
        int k = 0;
        for (ArrayList<Integer> sensorTargets : listTargets) {
            setTarget.addAll(sensorTargets);
            if (setTarget.size() == numTarget) {
                k++;
                setTarget.clear();
            }
        }
        return k;
    }

    // public ArrayList<ArrayList<Integer>> getCovers() {
    //     Set<Integer> setTarget = new HashSet<>();
    //     ArrayList<ArrayList<Integer>> newCovers = new ArrayList<>();
    //     int k = 0;
    
    //     for (int i = 0; i < listTargets.size(); i++) {
    //         for (int j = 0; j < listTargets.get(i).size(); j++) {
    //             setTarget.add(listTargets.get(i).get(j));
    //         }
    
    //         if (setTarget.size() == numTarget) {
    //             ArrayList<Integer> covers = new ArrayList<>();
    //             for (int j = k; j <= i; j++) {
    //                 covers.add(individual.getSensor(j));
    //             }
    //             k = i + 1;
    //             newCovers.add(covers);
    //             setTarget.clear();
    //         }
    //     }
    
    //     return newCovers;
    // }
    public List<Integer> display() {
        return individual;
    }
}
