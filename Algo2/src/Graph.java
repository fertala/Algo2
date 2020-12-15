import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {




    public enum Color {
        RED, BLUE;
    }
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    List<Edge> edges = new ArrayList<>();
    List<Edge> redSequence = new ArrayList<>();

    public void addEdge(String label, Color color) {
        if(getEdgeByLabel(label) == null) {
            edges.add(new Edge(label, color));
            System.out.println("Edge " + label + " added.");
        }
        else
            System.out.println(label + " exist already");
    }
    public void deleteEdge(String edgeToDelete) {
        Edge edge = getEdgeByLabel(edgeToDelete);
        deleteEdge(edge);
    }

    private Edge getEdgeByLabel(String edge) {
        return edges.stream().filter(e->e.getLabel().equals(edge)).findFirst().orElse(null);
    }

    public void addNeighbor(String edge, String neighbor, Graph.Color vertexColor) {
        Edge first = getEdgeByLabel(edge);
        Edge second = getEdgeByLabel(neighbor);
        first.addNeighbor(second, vertexColor);
    }

    public void deleteEdge(Edge edge){
        if(edges.contains(edge)){
            if(edge.isRed()){
                edge.transformNeighbors();
                edges.remove(edge);
                redSequence.add(edge);
                if(edges.isEmpty()){
                    System.out.println("Graph empty");
                    System.out.println(this);
                    System.exit(1);
                }
            }
            else
                System.out.println("Cannot remove blue node");
        }
        else
            System.out.println("Unknown edge");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Graph: ").append("\n");
        for(Edge e : edges){
            sb.append(e);
            sb.append("\n");
        }
        sb.append(ANSI_RED);
        sb.append("Red Sequence: [ ");
        for(Edge e : redSequence){
            sb.append(e.getLabel()).append(" ");
        }
        sb.append(" ]");
        sb.append(ANSI_RESET);
        return sb.toString();
    }
}
