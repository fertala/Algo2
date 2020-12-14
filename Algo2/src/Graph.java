import java.util.ArrayList;
import java.util.List;

public class Graph {
    public enum Color {
        RED, BLUE;
    }
    List<Edge> edges = new ArrayList<>();
    List<Edge> redSequence = new ArrayList<>();


    public boolean deleteEdge(Edge edge){
        if(edges.contains(edge)){
            if(edge.isRed()){
                edge.transformNeighbors();
                edges.remove(edge);
                redSequence.add(edge);
                return true;
            }
            System.out.println("Cannot remove blue node");
        }
        System.out.println("Unknown edge");
        return false;
    }

}
