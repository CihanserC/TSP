import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Cihanser Çalışkan - 20500002010
 */

public class GA {

    private static  int populationSize;
    private static  double crossOverRate;
    private static  double mutationRate;

    private static  int generationCount;
    private static  int targetFitness; // Best Fitness value is 7542 For Berlin52.tsp
    private static int cityNumber;

    Random rnd;

    private Solution_TSP[] population;
    private Random rand;

    public GA( int cityNumber, int populationSize, int generationCount, double crossOverRate, double mutationRate ) {
        population = new Solution_TSP[populationSize];
        this.populationSize = populationSize;
        this.mutationRate = mutationRate ;
        this.crossOverRate = crossOverRate ;
        this.cityNumber = cityNumber;
        this.generationCount = generationCount;
        rnd = new Random();
    }

    public void Run() throws IOException {
        Solution_TSP[] matingPool = new Solution_TSP[population.length];
        Solution_TSP[] children = new Solution_TSP[population.length];
        String dataPath = "TSP.txt";
        FileWriter fileWriter = new FileWriter(dataPath,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for ( int i = 0; i < population.length; i++ ) {
            population[ i ] = new Solution_TSP( cityNumber );
            population[ i ].randomize();
        }

        for ( int i = 0; i < generationCount; i++ ) {
            //parent selection
            for ( int j = 0; j < population.length; j++ ) {
                matingPool[ j ] = parentSelectionByTournament();
            }

            //crossover
            for ( int j = 0; j < population.length; j += 2 ) {
                if ( rnd.nextDouble() < crossOverRate ) {
                    Solution_TSP[] offspring = parentSelectionByTournament( matingPool[ j ], matingPool[ j + 1 ] );
                    children[ j ] = offspring[ 0 ];
                    children[ j + 1 ] = offspring[ 1 ];
                }
                else {
                    children[ j ] = matingPool[ j ];
                    children[ j + 1 ] = matingPool[ j + 1 ];
                }
            }
            //mutation
            for ( int j = 0; j < children.length; j++ ) {
                for ( int k = 0; k < cityNumber; k++ ) {
                    for ( int p = 0; p < cityNumber; p++ ){
                        if ( rnd.nextDouble()<mutationRate) {
                            children[j].setCity(k,p, !children[j].getCity (k,p) );
                        }
                    }

                }
            }
            //survivor selection
            population=children;

            int best = 0;
            int worst = cityNumber * cityNumber;
            int total = 0;
            for ( int j = 0; j < population.length; j++ ) {
                if ( population[j].getFitness() > best ){
                    best = population[j].getFitness();
                }
                if ( population[j].getFitness() < worst ){
                    worst = population[j].getFitness();
                }
                total+=population[j].getFitness();
            }
            System.out.println( "Best: " + best + " Average: " + (double)total / population.length + " Worst: " + worst );



            String resultString= "";
            resultString += (i+1) + "-" + best + "-" + worst + "-" + ((double)total / population.length);
            bufferedWriter.write(resultString);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();


    }

    private Solution_TSP parentSelectionByTournament() {
        int p1 = rnd.nextInt( population.length );
        int p2 = rnd.nextInt( population.length );
        if ( population[ p1 ].getFitness() > population[ p2 ].getFitness() ) {
            return population[ p1 ];
        }
        else {
            return population[ p2 ];
        }
    }

    private Solution_TSP parentSelectionByRouletteWheel() {
        double totalFitness = 0;
        for (Solution_TSP tour : population) {
            totalFitness += tour.getFitness();
        }

        double[] prob = new double[population.length];
        prob[0] = population[0].getFitness()/totalFitness;
        for (int j = 1; j < population.length; j++) {
            prob[j] = population[j].getFitness()/totalFitness + prob[j-1];
        }

        double randomNumber = rnd.nextDouble();
        for (int a = 0; a < population.length; a++) {
            if(randomNumber<prob[a]){
                return population[a];
            }
        }
        return population[0];
    }


}
