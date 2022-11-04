
public class GATest {

    public static void main(String[] args) {

        // Load dataset
        ProblemInstance_TSP problemTSP = new ProblemInstance_TSP("C:/Users/cihanser.caliskan/IdeaProjects/TSPTest/src/berlin52.tsp", "C:/Users/cihanser.caliskan/IdeaProjects/TSPTest/src/TSP_Time_Matrix.txt");

        // Begin local search
        Heuristics h = new Heuristics();
        System.out.println("Simulated Annealing Started...");
        h.SimulatedAnnealing();
        System.out.println("Tabu Search Started...");
        //h.TabuSearch();

    }

}