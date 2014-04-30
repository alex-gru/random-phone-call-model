/**
 * User: alexgru
 */
public class Node {
    protected final int index;
    boolean infected = false;
    int numberOfInfections = 0;

    public Node(int index) {
       this.index = index;
    }

    public void infect() {
        if (!infected) {
            Main.infector.registerInfectedNode(this);
            infected = true;
//            System.out.println("Node " + index + " infected.");
        }
        numberOfInfections++;
    }

    public void infectRandomNode() {
        Node[] nodes = Main.nodes;
        int randomNodeIndex = (int) (Math.random() * nodes.length);
        nodes[randomNodeIndex].infect();
    }
}
