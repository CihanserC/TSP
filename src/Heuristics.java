import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Heuristics {

    public static Solution_TSP twoOpt(Solution_TSP s) {
        boolean modified = true;
        while (modified) {
            modified = false;
            for (int i = 0; i < s.tour.size() - 2; i++) {
                for (int j = i + 2; j < s.tour.size() - 1; j++) {
                    double d1 = ProblemInstance_TSP.getDistance(s.tour.get(i), s.tour.get(i + 1))
                            + ProblemInstance_TSP.getDistance(s.tour.get(j), s.tour.get(j + 1));
                    double d2 = ProblemInstance_TSP.getDistance(s.tour.get(i), s.tour.get(j))
                            + ProblemInstance_TSP.getDistance(s.tour.get(i + 1), s.tour.get(j + 1));

                    // if distance can be shortened, adjust the tour
                    if (d2 < d1) {
                        // reverse
                        int begin = i + 1, end = j;
                        while (begin < end) {
                            Collections.swap(s.tour, begin, end);
                            begin++;
                            end--;
                        }
                        s.evaluate();
                        System.out.println("TwoOpt: "+s.toString()); // Print tour
                        modified = true;
                    }
                }
            }
        }
        s.evaluate();
        System.out.println("TwoOpt: "+s.toString()); // Print tour
        return s;
    }

    public static Solution_TSP SimulatedAnnealing(){
        Random rnd = new Random();

        double T = 100;
        double H = 0.999;
        double tMin = 0.001;

        Solution_TSP current = new Solution_TSP(0);
        System.out.println("SA: "+current.toString()); // Print tour
        current = twoOpt(current);
        System.out.println("SA: "+current.toString()); // Print tour

        Solution_TSP best = new Solution_TSP(current);
        Solution_TSP neighbor = null;

        do
        {
            neighbor = new Solution_TSP(current);
            Solution_TSP bestNeigbor = new Solution_TSP(current);

            for (int i = 0; i < ProblemInstance_TSP.getNbOfCities(); i++) {

                for (int j = 0; j < ProblemInstance_TSP.getNbOfCities(); j++) {
                    if (i == j) { continue; }
                    neighbor.removeAndInsert(i, j);
                    neighbor.evaluate();
                    if (neighbor.getFitness() < bestNeigbor.getFitness())
                    {
                        bestNeigbor = new Solution_TSP(neighbor);
                        System.out.println("SA: "+best.toString()); // Print tour

                    }
                }
            }

            if (bestNeigbor.getFitness() < current.getFitness())
            {
                current = new Solution_TSP(bestNeigbor);
                if (bestNeigbor.getFitness() < best.getFitness())
                {
                    best = new Solution_TSP(bestNeigbor);
                    System.out.println("SA: "+best.toString()); // Print tour
                }
            }
            else
            {
                double probability = (double)(1 / (1 + (Math.pow(Math.E, (current.getFitness() - bestNeigbor.getFitness()) / T))));
                double chance = rnd.nextDouble();
                if (chance <= probability)
                {
                    current = new Solution_TSP(bestNeigbor);
                }
            }

            T *= H;
        }while (T > tMin);
        return best;
    }

    public static Solution_TSP TabuSearch(){

        List<Solution_TSP> tabuList = new ArrayList<Solution_TSP>();;
        int Tabu_MaxIteration = 10000;
        Solution_TSP best = null;

        for (int i = 0; i < Tabu_MaxIteration; i++)
        {
            Solution_TSP current = new Solution_TSP(0);
            current.evaluate();
            Solution_TSP bestNeighbor = new Solution_TSP(current.tour);
            best = new Solution_TSP(current.tour);

            for (Solution_TSP neighbor : FindNeighbors(current))
            {
                neighbor.evaluate();
                bestNeighbor.evaluate();
                if(neighbor.getFitness() < bestNeighbor.getFitness()){
                    best = neighbor;
                    continue;
                }
                if (tabuList.contains(neighbor)){
                    continue;
                }

                neighbor.evaluate();
                bestNeighbor.evaluate();
                if(neighbor.getFitness() < bestNeighbor.getFitness()){
                    bestNeighbor = neighbor;
                }
            }
            bestNeighbor.evaluate();
            best.evaluate();
            if(bestNeighbor.getFitness() < best.getFitness()){
                best = bestNeighbor;
                System.out.println("Tabu search: "+best.toString()); // Print tour
            }
            // Update tabu list: FIFO
            if(tabuList.size() >= 100){
                tabuList.remove(99);
            }
            tabuList.add(0, best);
        }
        return best;
    }

    public static List<Solution_TSP> FindNeighbors(Solution_TSP current){

        List<Solution_TSP> NeighborList = new ArrayList<Solution_TSP>();
        Solution_TSP neighbor = null;

        for (int i = 0; i < current.tour.size(); i++) {
            neighbor = new Solution_TSP(current.tour);
            for (int j = i+1; j < current.tour.size()-1; j++) {
                if(i==j){
                    continue;
                }
                neighbor.swap(i, j);
                //NeighborList.add(new Solution_TSP( ));
                if(NeighborList.size() == 100){
                    return NeighborList;
                }
            }
        }
        return NeighborList;
    }
}
