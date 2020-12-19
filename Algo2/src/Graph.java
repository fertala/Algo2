import java.util.ArrayList;
import java.util.Comparator;
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

    public int getAverageOnXExec(int nbEdges, int t, double pRedVertex  , double pRedEdge , int solution){
        int i = 0;
        int avg = 0;
        NUM_EDGE = nbEdges;
        while(i < t) {
            generateGraph(pRedVertex,pRedEdge);
            if( solution == 1){
                getSolution1();
            }
            if(solution == 3) {
                solution3();
            }
            if(solution == 4) {
                solution4();
            }

            if(solution == 5) {
                solution5();
            }
            avg += getRedSequence();
            i++;
        }
        return avg / t;
    }
    public void getSolution1(){
        Vertex vertex;
        vertex = bestEdge();
        while (vertex != null){
            this.deleteEdge(vertex);
            vertex = bestEdge();
        }
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
                if(this.vertices.isEmpty()){
                    System.out.println("Graph empty");
                    System.out.println(this);
                    System.exit(1);
                }
            }
            else
                System.out.println("Cannot remove blue node");
        }
        else
            System.out.println("Unknown edges");
    }
    /**
     *@param pRedEdge la probabilité pour un sommet d’être rouge,
     *@param pRedVertex la probabilité pour un arc d’être bleu.
     * graphe complet est un graphe simple dont tous les sommets sont adjacents deux à deux vertexs = (n *(n-1))/2
     */

    public void generateGraph(double pRedVertex  , double pRedEdge){
        vertices.clear();
        redSequence.clear();
        String name = "";
        Color color ;
        for(int i = 0; i<NUM_EDGE ; i++ ){
            color = getRandomColor(pRedVertex);
            Vertex vertex = new Vertex(name+i,color);
            this.vertices.add(vertex);
        }
        for(Vertex vertex1 : vertices){
            for(Vertex vertex2 : vertices){
                if(vertex1 != vertex2) {
                    color = getRandomColor(pRedEdge);
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

    public void deleteSequence(List<Vertex> edges){
        for(Vertex e : edges){
            deleteEdge(e);
        }
    }


    public void solution4(){
        Vertex vertex;
        vertex = candidateEdge();
        while (vertex != null){
            this.deleteEdge(vertex);
            vertex = candidateEdge();
        }
    }

    public Vertex candidateEdge(){

        Vertex vertex = this.vertices.stream().filter(Vertex::isRed).sorted(Vertex::comparing).findFirst().orElse(null);
        return vertex;
    }


    public Vertex bestEdge(){
        //Edge edges  = findZeroOut();
       // if(edges == null)
        Vertex vertex = this.vertices.stream().filter(Vertex::isRed).sorted(Vertex::compareTo).findFirst().orElse(null);
        return vertex;
    }


    public Vertex findZeroOut(){
        return this.vertices.stream().filter(Vertex::isRed).filter(Vertex::zeroOut).findFirst().orElse(null);
    }

    public void solution3(){
        while(true){
           Vertex candidate =  vertices.stream().filter(Vertex::isRed).findFirst().orElse(null);
           if (candidate == null) break;
            deleteEdge(candidate);
        }
    }

    public void solution5(){
        while(true){
            Vertex candidate =  vertices.stream().filter(Vertex::isRed).max(Comparator.comparingInt(Vertex::getCountRedOutDegree)).orElse(null);
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
