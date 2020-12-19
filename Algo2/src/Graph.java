import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    public enum Color {
        RED, BLUE;
    }
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";


                            /** PART 1 : OPERATIONS ON THE GRAPH*/

    List<Vertex> vertices = new ArrayList<>();
    List<Vertex> redSequence = new ArrayList<>();

    public void addEdge(String label, Color color) {
        if(getEdgeByLabel(label) == null) {
            vertices.add(new Vertex(label, color));
            System.out.println("Vertex " + label + " added.");
        }
        else
            System.out.println(label + " exist already");
    }
    public void deleteEdge(String edgeToDelete) {
        Vertex vertex = getEdgeByLabel(edgeToDelete);
        deleteEdge(vertex);
    }

    private Vertex getEdgeByLabel(String label) {
        return vertices.stream().filter(e->e.getLabel().equals(label)).findFirst().orElse(null);
    }

    public double runHeuristicXTimes(int nbVertices, int t, double pRedVertex  , double pBlueEdge , int heuristic){
        int i = 0;
        double avg = 0;
        while(i < t) {
            generateGraph(pRedVertex,pBlueEdge, nbVertices);
            avg += runHeuristic(heuristic);
            i++;
        }
        return avg / t;
    }

    public int runHeuristic(int heuristicNo) {
        if(heuristicNo==1){
            heuristic1();
        }
        if(heuristicNo==2){
            heuristic2();
        }
        int sizeRedSequence = redSequence.size();
        redSequence.clear();
        vertices.clear();
        return sizeRedSequence;
    }

    public void addNeighbor(String edges, String neighbor, Graph.Color vertexColor) {
        Vertex first = getEdgeByLabel(edges);
        Vertex second = getEdgeByLabel(neighbor);
        first.addNeighbor(second, vertexColor);
    }

    public void deleteEdge(Vertex vertex){
        if(this.vertices.contains(vertex)){
            if(vertex.isRed()){
                vertex.transformNeighbors();
                this.vertices.remove(vertex);
                redSequence.add(vertex);
            }
            else
                System.out.println("Cannot remove blue node");
        }
        else
            System.out.println("Unknown edges");
    }

    /**
     *@param pRedVertex la probabilité pour un sommet d’être rouge,
     *@param pBlueEdge la probabilité pour un arc d’être bleu.
     * graphe complet est un graphe simple dont tous les sommets sont adjacents deux à deux vertexs = (n *(n-1))/2
     */

    public void generateGraph(double pRedVertex  , double pBlueEdge, int nbVertices){
        vertices.clear();
        redSequence.clear();
        String name = "";
        Color color ;
        for(int i = 0; i < nbVertices; i++ ){
            color = getRandomColor(pRedVertex);
            Vertex vertex = new Vertex(name+i,color);
            this.vertices.add(vertex);
        }
        for(Vertex vertex1 : vertices){
            for(Vertex vertex2 : vertices){
                if(vertex1 != vertex2) {
                    color = getRandomColor(1 - pBlueEdge);
                    vertex1.addNeighbor(vertex2,color);
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

                                /** PART 2 : Heuristics */

    public void heuristic1(){
        Vertex vertex;
        vertex = bestEdge();
        while (vertex != null){
            this.deleteEdge(vertex);
            vertex = bestEdge();
        }
    }

    public Vertex bestEdge(){
        return this.vertices.stream().filter(Vertex::isRed).min(Vertex::compareTo).orElse(null);
    }

    public void heuristic2(){
        while(true){
            Vertex candidate =  vertices.stream().filter(Vertex::isRed).max(Comparator.comparingInt(Vertex::getCountBlueOutDegree)).orElse(null);
           if (candidate == null) break;
           deleteEdge(candidate);
        }
    }

    public void heuristic3(){
        while(true){
            Vertex candidate =  vertices.stream().filter(Vertex::isRed).max(Comparator.comparingInt(Vertex::impact)).orElse(null);
            if (candidate == null) break;
            deleteEdge(candidate);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Graph: ").append("\n");
        for(Vertex e : vertices){
            sb.append(e);
            sb.append("\n");
        }
        sb.append(ANSI_RED);
        sb.append("Red Sequence de taille ");
        sb.append(redSequence.size());
        sb.append(": [ ");
        for(Vertex e : redSequence){
            sb.append(e.getLabel()).append(" ");
        }
        sb.append(" ]");
        sb.append(ANSI_RESET);
        return sb.toString();
    }
}
