import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProblemInstance_TSP {

    // Coordinate: Coordinates of the cities and DM is distance matrix whih calculates the distance of cities of each other
    private static ArrayList<ArrayList<Double>> Coordinate, DM;
    private static int n = 0;   // Number of the cities

    public ProblemInstance_TSP(String fileName) {
        loadInstance(fileName);
        calculateDistanceMatrix();
    }

    private void loadInstance(String fileName) {
        File file = new File(fileName);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

        String[] data = null;
        while (sc.hasNextLine()) {
            //System.out.println(sc.nextLine());
            data = sc.nextLine().split(" ");
            if (data[0].equals("DIMENSION:")) {
                n = Integer.parseInt(data[1]);
            } else if (data[0].equals("NODE_COORD_SECTION")) {
                break;
            }
        }

        Coordinate = new ArrayList<>();
        while (sc.hasNextLine()) {
            data = sc.nextLine().split(" ");
            if (data[0].equals("EOF")) {
                break;
            }
            Double x = Double.parseDouble(data[1]);
            Double y = Double.parseDouble(data[2]);
            ArrayList<Double> coord = new ArrayList<Double>() {
                {
                    add(x);
                    add(y);
                }
            };
            Coordinate.add(coord);
        }
    }

    private void calculateDistanceMatrix() {
        DM = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            ArrayList<Double> row = new ArrayList<>();

            for (int j = 0; j <= i; j++) {
                row.add(0.0);
            }

            for (int j = i + 1; j < n; j++) {
                Double x1 = Coordinate.get(i).get(0);
                Double y1 = Coordinate.get(i).get(1);
                Double x2 = Coordinate.get(j).get(0);
                Double y2 = Coordinate.get(j).get(1);
                Double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                row.add(distance);
            }
            DM.add(row);
        }

        ArrayList<Double> row = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            row.add(0.0);
        }
        DM.add(row);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                DM.get(i).set(j, DM.get(j).get(i));
            }
        }
    }

    public static Double getDistance(int city1, int city2) {
        return DM.get(city1).get(city2);
    }

    public static int getNbOfCities() {
        return n;
    }
}
