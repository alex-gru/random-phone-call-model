import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: alexgru
 */
public class Infector {
    protected List<Node> infectedNodes = new CopyOnWriteArrayList<Node>();
    protected int numberOfInfectedNodes = 0;

    public void registerInfectedNode(Node infectedNode) {
        int randomIndex = (int) (Math.random() * infectedNodes.size());
        infectedNodes.add(randomIndex, infectedNode);
        numberOfInfectedNodes++;
    }

    public void spread() {
        for (Node node : infectedNodes) {
            node.infectRandomNode();
        }
    }
}
