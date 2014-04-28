import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: alexgru
 */
public class Infector {
    protected static List<Node> infectedNodes = new CopyOnWriteArrayList<Node>();
    protected static int numberOfInfectedNodes = 0;

    public static void registerInfectedNode(Node infectedNode) {
        int randomIndex = (int) (Math.random() * infectedNodes.size());
        infectedNodes.add(randomIndex, infectedNode);
        numberOfInfectedNodes++;
    }

    public static void spread() {
        for (Node node : infectedNodes) {
            node.infectRandomNode();
        }
    }
}
