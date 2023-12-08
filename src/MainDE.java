import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainDE {
    public static void main(String[] args) {
         try {
            // Đọc dữ liệu từ file sensor.input
            List<Sensor> sensors = readSensors("C:\\Users\\Admin\\Documents\\GitHub\\DE_Sensor\\data\\sensor.input");

            // Đọc dữ liệu từ file target.input
            List<Target> targets = readTargets("C:\\Users\\Admin\\Documents\\GitHub\\DE_Sensor\\data\\sentarget.input");

            // Xác định sensor cảm biến được những target nào
            ArrayList<ArrayList<Integer>> listTargets = new ArrayList<>();
            for (Sensor sensor : sensors) {
                ArrayList<Integer> coveredTargets = new ArrayList<>();
                for (int i = 0; i < targets.size(); i++) {
                    Target target = targets.get(i);
                    double distance = calculateDistance(sensor, target);
                    if (distance <= sensor.getRadius()) {
                        coveredTargets.add(i);
                    }
                }
                listTargets.add(coveredTargets);
            }

            // Chuyển đổi danh sách sensors thành List<Integer>
            List<Integer> listSensors = new ArrayList<>();
            for (int i = 0; i < sensors.size(); i++) {
                listSensors.add(i);
            }
            

            // Thực hiện Differential Evolution
            double F = 0.8;
            double mutationRate = 0.2;
            int populationSize = 50;
            int generation_size = 10000;
            int numTargets = targets.size();  // Số lượng target
            int numOfSensor = sensors.size();
            DifferentialEvolution de = new DifferentialEvolution(F, populationSize, listSensors, listTargets,numTargets);
            Population population = de.initPopulation();



            
            writeStartGen("C:\\Users\\Admin\\Documents\\GitHub\\DE_Sensor\\result\\genDE.out");

            for (int generation = 0; generation < generation_size; generation++) {
                System.out.println(generation);
                // Tiến hành lặp DE cho từng thế hệ
                for (int i = 0; i < populationSize; i++) {
                    Individual target = population.getIndividual(i);
                    Individual crossed = population.getIndividual(i);
                    if (Math.random() < mutationRate) {
                        crossed = de.mutated(target);
                    }
                    else{
            
                    Individual mutated = de.mutate(population, target);
                     crossed = de.crossover(target, mutated);
                    }

                    population = de.replaceBetter(population, target, crossed, i);
                }

                // Lấy cá thể có độ thích nghi tốt nhất trong thế hệ hiện tại
                Individual bestIndividual = de.getFittest(population);

                // Ghi thông tin về fitness của cá thể tốt nhất trong thế hệ vào file
                writeGeneration("C:\\Users\\Admin\\Documents\\GitHub\\DE_Sensor\\result\\genDE.out", generation, bestIndividual.getFitness());
            }

            // Lưu kết quả vào file result.out
            Individual maxFitness = de.getFittest(population);
            writeResult("C:\\Users\\Admin\\Documents\\GitHub\\DE_Sensor\\result\\result.out", maxFitness);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayListTargets(ArrayList<ArrayList<Integer>> listTargets) {
        for (int i = 0; i < listTargets.size(); i++) {
            System.out.print("Sensor " + i + " covers targets: ");
            ArrayList<Integer> coveredTargets = listTargets.get(i);
            for (int j = 0; j < coveredTargets.size(); j++) {
                System.out.print(coveredTargets.get(j) + " ");
            }
            System.out.println();
        }
    }

    private static List<Sensor> readSensors(String fileName) throws IOException {
        List<Sensor> sensors = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(" ");
            double x = Double.parseDouble(values[0]);
            double y = Double.parseDouble(values[1]);
            double z = Double.parseDouble(values[2]);
            double radius = Double.parseDouble(values[3]);
            sensors.add(new Sensor(x, y,z, radius));
        }
        br.close();
        return sensors;
    }

    private static List<Target> readTargets(String fileName) throws IOException {
        List<Target> targets = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(" ");
            double x = Double.parseDouble(values[0]);
            double y = Double.parseDouble(values[1]);
            double z = Double.parseDouble(values[2]);
            targets.add(new Target(x, y,z));
        }
        br.close();
        return targets;
    }

    private static double calculateDistance(Sensor sensor, Target target) {
        double x1 = sensor.getX();
        double y1 = sensor.getY();
        double z1 = sensor.getZ();
        double x2 = target.getX();
        double y2 = target.getY();
        double z2 = target.getZ();
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1-z2) * (z1 - z2));
    }

    private static void writeResult(String fileName, Individual maxFitness) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        List<Integer> displayResult = maxFitness.display();
        
        writer.write(String.join(" ", displayResult.stream().map(Object::toString).toArray(String[]::new)));
        writer.close();
    }
    private static void writeGeneration(String fileName, int index, int maxFitness) throws IOException {
        FileWriter writer = new FileWriter(fileName, true);
        writer.write("Best fit for generation " + index + ": " + maxFitness + "\n");
        writer.close();
    }  
    private static void writeStartGen(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write("Maximum fit for each generation: " + "\n");
        writer.close();
    }

}

