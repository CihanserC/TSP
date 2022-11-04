import java.util.ArrayList;
import java.util.Collections;

public class Solution_TSP {
    public ArrayList<Integer> tour;
    private double distance, time;

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
        this.time = solution.time;
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
        calculateDistanceFitness();
        calculateTotalTime();
    }

    public void calculateDistanceFitness(){
        distance = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += ProblemInstance_TSP.getDistance(tour.get(i), tour.get(i+1));
        }
        distance += ProblemInstance_TSP.getDistance(tour.get(tour.size()-1), tour.get(0));
    }

    public void calculateTotalTime(){
        time = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            time += ProblemInstance_TSP.getTime(tour.get(i)
                    , tour.get(i+1));
        }
        time += ProblemInstance_TSP.getTime(tour.get(tour.size()-1)
                , tour.get(0));
    }

    public double getFitness(int type){
        if (type == 1)
            return distance;
        if (type == 2)
            return time;
        return -1.0;
    }

    public void setTour(ArrayList<Integer> tour){
        this.tour = (ArrayList<Integer>) tour.clone();
        evaluate();
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

    public void invert(int beginIndex, int endIndex){
        while(beginIndex < endIndex){
            Collections.swap(tour, beginIndex, endIndex);
            beginIndex++;
            endIndex--;
        }
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
            s += (tour.get(i)) + "-";
        }
        s += (tour.get(0)) + "; Fitness = " + distance;
        s += "; isValid = " + isValid();
        return s;
    }
}
