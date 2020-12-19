import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph {


    public int getRedSequence() {
       return redSequence.size();
    }

    public enum Color {
        RED, BLUE;
    }
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static int    NUM_EDGE = 100 ;

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

    public int getAverageOnXExec(int nbEdges, int t, double pRedEdge  , double pRedVertex, int solution){
        int i = 0;
        int avg = 0;
        NUM_EDGE = nbEdges;
        while(i < t) {
            generateGraph(pRedEdge, pRedVertex);
            if(solution == 1) {
                getHillClimbingRedSequence();
            }
            avg += getRedSequence();
            i++;
        }
        return avg / t;
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
     *@param pRedEdge la probabilité pour un sommet d’être rouge,
     *@param pRedVertex la probabilité pour un arc d’être bleu.
     * graphe complet est un graphe simple dont tous les sommets sont adjacents deux à deux vertexs = (n *(n-1))/2
     */

    public void generateGraph(double pRedEdge  , double pRedVertex){
        edges.clear();
        redSequence.clear();
        String name = "";
        Color color ;
        for(int i = 0; i<NUM_EDGE ; i++ ){
            color = getRandomColor(pRedEdge);
            Edge edge = new Edge(name+i,color);
            this.edges.add(edge);
        }
        for(Edge edge1 : edges){
            for(Edge edge2 : edges){
                if(edge1 != edge2) {
                    color = getRandomColor(pRedVertex);
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

    public void deleteSequence(List<Edge> edges){
        for(Edge e : edges){
            deleteEdge(e);
        }
    }

    public List<Edge> getHillClimbingRedSequence(){
        List<Edge> edgesRes = new ArrayList<>();
        List<Edge> bestRed = new ArrayList<>();
        List<Edge> bestOnes;
        do  {
            bestRed = getEdgesHighestOutRedDegree();
            bestOnes = new ArrayList<>(bestRed);
            getEdgesLessOutBlueDegree(bestOnes);
            if(bestRed.size() + bestOnes.size() > 0)
                deleteEdge(bestOnes.isEmpty() ? bestRed.isEmpty() ? null : bestRed.get(0) : bestOnes.get(0));
        } while (bestRed.size() > 0);
        while (!bestOnes.isEmpty()) {
            bestOnes.clear();
            bestOnes = getEdgesLessOutBlueDegree(bestRed);
            if(bestOnes.isEmpty())
                break;
            deleteEdge(bestOnes.get(0));
        }
        Iterator<Edge> it = edges.iterator();
        while(it.hasNext()) {
            Edge e = it.next();
            if (e.isRed()) {
                e.transformNeighbors();
                redSequence.add(e);
                it.remove();
            }
        }

        return edgesRes;
    }

    private List<Edge> getEdgesLessOutBlueDegree(List<Edge> bestRed) {
        int minBlueOut = 0;
        Iterator<Edge> it = bestRed.iterator();
        while(it.hasNext()){
            Edge e = it.next();
            if(e.isRed()) {
                int count = e.getCountBlueOutDegree();
                if (count < minBlueOut) {
                    minBlueOut = count;
                } else {
                    it.remove();
                }
            } else it.remove();
        }
        it = bestRed.iterator();
        while(it.hasNext()){
            Edge e = it.next();
            if(e.getCountBlueOutDegree() > minBlueOut)
                it.remove();
        }
        return bestRed;
    }

    private List<Edge> getEdgesHighestOutRedDegree() {
        List<Edge> candidates = new ArrayList<>();
        int maxRedOut = 0;
        for(Edge e : edges) {
            if (e.isRed()) {
                int count = e.getCountRedOutDegree();
                if (count > maxRedOut) {
                    maxRedOut = count;
                    candidates.add(e);
                }
            }
        }
        Iterator<Edge> it = candidates.iterator();
        while(it.hasNext()){
            Edge e = it.next();
            if(e.getCountRedOutDegree() < maxRedOut)
                it.remove();
        }
        return candidates;
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
        sb.append("Red Sequence: (size = ").append(redSequence.size()).append(" [ ");
        for(Edge e : redSequence){
            sb.append(e.getLabel()).append(" ");
        }
        sb.append(" ]");
        sb.append(ANSI_RESET);
        return sb.toString();
    }
}
