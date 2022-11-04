import java.util.Random;

public class MOOP_TSP {
    
    public static ProblemInstance_TSP p;
    public static Random rnd;
    
    public static void test1(){
       Solution_TSP s1 = new Solution_TSP(0);
       Solution_TSP s2 = new Solution_TSP(0);
       System.out.println("S1: Distance: " + s1.getFitness(1) 
               + "; Time: "+ s1.getFitness(2));
       System.out.println("S2: Distance: " + s2.getFitness(1) 
               + "; Time: "+ s2.getFitness(2));
    }

    public static void main(String[] args) {
        // Filepathleri değiştir!
        p = new ProblemInstance_TSP("./problem_instances_TSP/berlin52.tsp", "./time_matrices_TSP/TSP_Time_Matrix.txt");
        test1();
    }
    
}
