
public class GATest {

    public static void main(String[] args) {

        // Load dataset
        ProblemInstance_TSP problemTSP = new ProblemInstance_TSP("C:/Users/User/IdeaProjects/TSP-with-GA/src/berlin52.tsp");

        // Begin local search
        Heuristics h = new Heuristics();
        System.out.println("Simulated Annealing Started...");
        //h.SimulatedAnnealing();
        System.out.println("Tabu Search Started...");
        h.TabuSearch();

    }

}