
public class GATest {

    public static void main(String[] args) {

        // Load dataset
        ProblemInstance_TSP problemTSP = new ProblemInstance_TSP("C:/Users/User/IdeaProjects/TSP/src/berlin52.tsp");
        
        // Begin local search
        Heuristics h = new Heuristics();
        h.SimulatedAnnealing();
        h.TabuSearch();

    }

}