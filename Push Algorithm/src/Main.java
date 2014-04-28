/**
 * User: alexgru
 */
public class Main {

    public static Node[] nodes;

    public static void main(String[] args) throws InterruptedException {
        nodes = new Node[10_000];

        generateNodes(nodes);
        infectRandomStartNode();
        spreadInfection();
        printNumberOfInfectionsPerNode();
    }

    private static void printNumberOfInfectionsPerNode() {
        for (int i = 0; i < Infector.infectedNodes.size(); i++) {
            Node infectedNode = Infector.infectedNodes.get(i);
            System.out.println("Node: " + infectedNode.index + "\t #infections: " + infectedNode.numberOfInfections);
        }
    }

    private static void spreadInfection() throws InterruptedException {
        while (Infector.infectedNodes.size() < Main.nodes.length) {
            Infector.spread();
            System.out.println("#Infected: " + Infector.numberOfInfectedNodes);
//            displayInfectedNodesIndexes();
            Thread.sleep(100);
        }
        System.out.println("ALL NODES INFECTED!");
    }

    private static void displayInfectedNodesIndexes() {
        System.out.println("-------------");
        for (int i = 0; i < Infector.infectedNodes.size(); i++) {
            System.out.println(Infector.infectedNodes.get(i).index);
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
