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
            Infector.registerInfectedNode(this);
            infected = true;
        }
        numberOfInfections++;
    }

    public void infectRandomNode() {
        Node[] nodes = Main.nodes;
        int randomNodeIndex = (int) (Math.random() * nodes.length);
        nodes[randomNodeIndex].infect();
    }
}
