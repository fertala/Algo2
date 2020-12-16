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
    public static final int    NUM_EDGE = 5 ;

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
    /**
     *@param p la probabilité pour un sommet d’être rouge,
     *@param q la probabilité pour un arc d’être bleu.
     * graphe complet est un graphe simple dont tous les sommets sont adjacents deux à deux vertexs = (n *(n-1))/2
     */

    public void generateGraph(double p  , double q){
        String name = "";
        Color color ;
        for(int i = 0; i<NUM_EDGE ; i++ ){
            color = getRandomColor(p);
            Edge edge = new Edge(name+i,color);
            this.edges.add(edge);
        }
        for(Edge edge1 : edges){
            for(Edge edge2 : edges){
                if(edge1 != edge2) {
                    color = getRandomColor(q);
                    edge1.addNeighbor(edge2,color);
                }
            }
        }
    }

    public Color getRandomColor(double p){
        double rand = Math.random();
        if(rand < p )
            return Color.RED;
        else
            return Color.BLUE;
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
