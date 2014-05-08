import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
Team: Alexander Gruschina | Mario Kotoy
This short program shows a demo of the Random Phone Call Model - Push Algorithm.
 */
public class Main {

    private static final boolean LOG_RUNTIME = false;
    private static final boolean LOG_INFECTION_RUNS = false;
    private static final boolean LOG_GRAPH = false;

    public static Node[] nodes;
    public static final int LINE_LENGTH_GRAPH = 100;
    public static final int NUMBER_NODES = 10000;
    public static final int NUMBER_SIMULATIONS = 1;
    protected static Infector infector;
    private static FileWriter runTimeWriter;
    private static FileWriter infectionRunsWriter;
    private static FileWriter infectionGraphWriter;

    public static void main(String[] args) throws InterruptedException, IOException {

        if (LOG_RUNTIME) {
            runTimeWriter = new FileWriter(new File("runtime.csv"));
            runTimeWriter.write("RANDOM PHONE CALL MODEL - PUSH ALGORITHM");
            runTimeWriter.append("\nSimulation on a complete graph with " + NUMBER_NODES + " nodes. #Simulations: "
                    + NUMBER_SIMULATIONS + "\nRuntime:\n");
        }

        if (LOG_INFECTION_RUNS) {
            infectionRunsWriter = new FileWriter(new File("infectionRuns.csv"));
            infectionRunsWriter.write("RANDOM PHONE CALL MODEL - PUSH ALGORITHM");
            infectionRunsWriter.append("\nSimulation on a complete graph with " + NUMBER_NODES + " nodes. #Simulations: "
                    + NUMBER_SIMULATIONS + "\nNumber of Infection steps:\n");
        }

        if (LOG_GRAPH) {
            infectionGraphWriter = new FileWriter(new File("infectionGraph.csv"));
            infectionGraphWriter.write("RANDOM PHONE CALL MODEL - PUSH ALGORITHM");
            infectionGraphWriter.append("\nSimulation on a complete graph with " + NUMBER_NODES + " nodes. #Simulations: "
                    + NUMBER_SIMULATIONS + "\nInfection graphs:\n");
        }

        for (int i = 0; i < NUMBER_SIMULATIONS; i++) {
            infector = new Infector();
            nodes = new Node[NUMBER_NODES];
            generateNodes(nodes);
            infectRandomStartNode();
            long start = System.currentTimeMillis();
            spreadInfection();
//            printNumberOfInfectionsPerNode();
            double runtime = ((double) (System.currentTimeMillis() - start)) / 1000;

            if (LOG_RUNTIME) {
                runTimeWriter.append(runtime + "\n");
                runTimeWriter.flush();
            }

            System.out.println("Runtime: " + runtime + " seconds");
        }

        if (LOG_RUNTIME) {
            runTimeWriter.close();
        }
        if (LOG_INFECTION_RUNS) {
            infectionRunsWriter.close();
        }
        if (LOG_GRAPH) {
            infectionGraphWriter.close();
        }
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
            graphNodeStates();
            Thread.sleep(100);
        }
        System.out.println("ALL NODES INFECTED! Number of runs needed: " + numRuns);
        if (LOG_INFECTION_RUNS) {
            infectionRunsWriter.append(numRuns + "\n");
            infectionRunsWriter.flush();
        }
    }

    private static void displayInfectedNodesIndexes() {
        System.out.println("-------------");
        for (int i = 0; i < infector.infectedNodes.size(); i++) {
            System.out.println(infector.infectedNodes.get(i).index);
        }
    }

    private static void graphNodeStates() throws IOException {
        int i = 0;

        while (i < nodes.length) {
            if (i % LINE_LENGTH_GRAPH == 0 && i != 0) {
                System.out.println();
                if (LOG_GRAPH) {
                    infectionGraphWriter.append("\n");
                }
            }

            Node current = nodes[i];
            if (current.infected) {
                System.out.print("X ");
                if (LOG_GRAPH) {
                    infectionGraphWriter.append("X ");
                }
            } else {
                System.out.print("  ");
                if (LOG_GRAPH) {
                    infectionGraphWriter.append("  ");
                }
            }
            i++;
        }
        System.out.println();
        System.out.print("-------------------------------------------------------------------");
        System.out.print("-------------------------------------------------------------------");
        System.out.print("-------------------------------------------------------------------");
        System.out.println();

        if (LOG_GRAPH) {
            infectionGraphWriter.append("\n");
            infectionGraphWriter.append("-------------------------------------------------------------------");
            infectionGraphWriter.append("-------------------------------------------------------------------");
            infectionGraphWriter.append("-------------------------------------------------------------------");
            infectionGraphWriter.append("\n");
        }
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
