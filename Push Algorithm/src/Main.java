import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: alexgru
 */
public class Main {

    public static Node[] nodes;
    public static final int LINE_LENGTH_GRAPH = 100;
    public static final int NUMBER_NODES = 10000;
    public static final int NUMBER_SIMULATIONS = 100;
    protected static Infector infector;
    private static FileWriter runTimeWriter;
    private static FileWriter infectionRunsWriter;

    public static void main(String[] args) throws InterruptedException, IOException {
//        runTimeWriter = new FileWriter(new File("runtime.csv"));
//        runTimeWriter.write("RANDOM PHONE CALL MODEL - PUSH ALGORITHM");
//        runTimeWriter.append("\nSimulation on a complete graph with " + NUMBER_NODES + " nodes. #Simulations: "
//                + NUMBER_SIMULATIONS + "\nRuntime:\n");

        infectionRunsWriter = new FileWriter(new File("infectionRuns.csv"));
        infectionRunsWriter.write("RANDOM PHONE CALL MODEL - PUSH ALGORITHM");
        infectionRunsWriter.append("\nSimulation on a complete graph with " + NUMBER_NODES + " nodes. #Simulations: "
                + NUMBER_SIMULATIONS + "\nNumber of Infection steps:\n");

        for (int i = 0; i < NUMBER_SIMULATIONS; i++) {
            infector = new Infector();
            nodes = new Node[NUMBER_NODES];
            generateNodes(nodes);
            infectRandomStartNode();
            long start = System.currentTimeMillis();
            spreadInfection();
//        printNumberOfInfectionsPerNode();
            double runtime = ((double) (System.currentTimeMillis() - start)) / 1000;
//            runTimeWriter.append(runtime + "\n");
//            runTimeWriter.flush();

            System.out.println("Runtime: " + runtime + " seconds");
        }

//        runTimeWriter.close();
        infectionRunsWriter.close();
    }

    private static void printNumberOfInfectionsPerNode() {
        for (int i = 0; i < infector.infectedNodes.size(); i++) {
            Node infectedNode = infector.infectedNodes.get(i);
            System.out.println("Node: " + infectedNode.index + "\t\t #infections: " + infectedNode.numberOfInfections);
        }
    }

    private static void spreadInfection() throws InterruptedException, IOException {
        int numRuns = 0;
        while (infector.infectedNodes.size() < Main.nodes.length) {
            numRuns++;
            infector.spread();
//            System.out.println("#Infected: " + Infector.numberOfInfectedNodes);
//            displayInfectedNodesIndexes();
//            graphNodeStates();
            Thread.sleep(100);
        }
//        System.out.println("ALL NODES INFECTED! Number of runs needed: " + numRuns);
        infectionRunsWriter.append(numRuns + "\n");
        infectionRunsWriter.flush();
    }

    private static void displayInfectedNodesIndexes() {
        System.out.println("-------------");
        for (int i = 0; i < infector.infectedNodes.size(); i++) {
            System.out.println(infector.infectedNodes.get(i).index);
        }
    }

    private static void graphNodeStates() {
        int i = 0;

        while (i < nodes.length) {
            if (i % LINE_LENGTH_GRAPH == 0 && i != 0) {
                System.out.println();
            }

            Node current = nodes[i];
            if (current.infected) {
                System.out.print("X ");
            } else {
                System.out.print("  ");
            }
            i++;
        }
        System.out.println();
        System.out.print("-------------------------------------------------------------------");
        System.out.print("-------------------------------------------------------------------");
        System.out.print("-------------------------------------------------------------------");
        System.out.println();
    }

    private static void infectRandomStartNode() {
        int randomNodeIndex = (int) (Math.random() * nodes.length);
        nodes[randomNodeIndex].infect();
    }

    private static void generateNodes(Node[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i);
        }
    }
}
