import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {

    public static void main(String[] args) {
        generateSensorData("sensor.input", 100, 1, 50, 10, 20);
        generateTargetData("sentarget.input", 20, 10, 45);
    }

    private static void generateSensorData(String fileName, int numSensors, int minX, int maxX, int minRadius, int maxRadius) {
        try {
            FileWriter writer = new FileWriter(fileName);

            Random random = new Random();

            for (int i = 0; i < numSensors; i++) {
                int x = getRandomInt(minX, maxX, random);
                int y = getRandomInt(minX, maxX, random);
                int z = getRandomInt(minX, maxX, random);
                int radius = getRandomInt(minRadius, maxRadius, random);
                writer.write(x + " " + y + " " +z+ " "+ radius + "\n");
            }

            writer.close();
            System.out.println("Generated sensor data and saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateTargetData(String fileName, int numTargets, int minX, int maxX) {
        try {
            FileWriter writer = new FileWriter(fileName);

            Random random = new Random();

            for (int i = 0; i < numTargets; i++) {
                int x = getRandomInt(minX, maxX, random);
                int y = getRandomInt(minX, maxX, random);
                int z = getRandomInt(minX, maxX, random);
                writer.write(x + " " + y +" " + z+ "\n");
            }

            writer.close();
            System.out.println("Generated target data and saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomInt(int min, int max, Random random) {
        return random.nextInt(max - min + 1) + min;
    }
}
