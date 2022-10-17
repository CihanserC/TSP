import java.util.ArrayList;
import java.util.Collections;

public class Solution_TSP {
    public ArrayList<Integer> tour;
    private double distance;

    public Solution_TSP(int type){
        constructTour(type);
        evaluate();
    }

    public Solution_TSP(ArrayList<Integer> tour){
        this.tour = (ArrayList<Integer>) tour.clone();
        evaluate();
    }

    public Solution_TSP(Solution_TSP solution){
        this.distance = solution.distance;
        this.tour = (ArrayList<Integer>) solution.tour.clone();
    }

    private void constructTour(int type){
        // type: 0 - Random, 1 - Ascending Order
        tour = new ArrayList<>();
        for (int i = 0; i < ProblemInstance_TSP.getNbOfCities(); i++) {
            tour.add(i);
        }

        // Controlling the initial seed
        //Random rnd = new Random(0);
        if(type == 0){
            Collections.shuffle(tour);
            //Collections.shuffle(tour, rnd);
        }
    }

    public void evaluate(){
        distance = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += ProblemInstance_TSP.getDistance(tour.get(i), tour.get(i+1));
        }
        distance += ProblemInstance_TSP.getDistance(tour.get(tour.size()-1), tour.get(0));
    }

    public double getFitness(){
        return distance;
    }

    public void setCity(int position, int value){
        this.tour.set(position, value);
        evaluate();
    }

    public int getCity(int position){
        return tour.get(position);
    }

    public void swap(int index1, int index2){
        Collections.swap(tour, index1, index2);
    }

    // public void insert(int index, int city){ tour.add(index, city); }

    public void removeAndInsert(int fromIndex, int toIndex){
        int city = tour.get(fromIndex);
        tour.remove(fromIndex);
        if (fromIndex < toIndex){
            toIndex--;
        }
        tour.add(toIndex, city);
    }

    public boolean isValid(){
        if (tour.size() != ProblemInstance_TSP.getNbOfCities())
            return false;
        for (int i = 0; i < tour.size(); i++) {
            if (!tour.contains(i))
                return false;
        }

        return true;
    }

    @Override
    public String toString(){
        String s = "Tour = ";
        for (int i = 0; i < tour.size(); i++) {
            s += Integer.toString(tour.get(i)) + "-";
        }
        s += Integer.toString(tour.get(0)) + "; Fitness = " + distance;
        s += "; isValid = " + isValid();
        return s;
    }
}
