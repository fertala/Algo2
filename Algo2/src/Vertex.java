import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex implements Comparable {


    Graph.Color color;
    List<Edge> outEdges = new ArrayList<>();
    List<Vertex> inEdges = new ArrayList<>();
    String label;

    Vertex(String label, Graph.Color color){
        this.label = label;
        this.color = color;
    }



    public boolean isRed() {
        return color == Graph.Color.RED;
    }

    public boolean isBlue() {
        return color == Graph.Color.BLUE;
    }

    public void transformNeighbors() {
        for(Edge v : outEdges){
            v.transformNeighbor();
        }
        for(Vertex e : inEdges){
            e.deleteNeighbor(this);
        }
    }

    private void deleteNeighbor(Vertex vertex) {
        if(isNeighbor(vertex)){
            outEdges.removeIf(v->v.getNeighbor().equals(vertex));
        }
    }

    public void changeColor(Graph.Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void addNeighbor(Vertex vertex, Graph.Color color) {
        if (!isNeighbor(vertex)) {
            Edge v = new Edge(vertex, color);
            outEdges.add(v);
            vertex.addInNeighbor(this);
        }
        else
            System.out.println(vertex.getLabel() + " already neighbor");
        }

    private void addInNeighbor(Vertex e) {
        inEdges.add(e);
    }

    private boolean isNeighbor(Vertex vertex) {
        return outEdges.stream().anyMatch(v->v.getNeighbor().equals(vertex));
    }

    public int differenceOutEdges(){
        return (int) (outEdges.stream().filter(Edge::isRed).count()  -  outEdges.stream().filter(Edge::isBlue).count());
    }

    public int impact(){
        int goodImpact = 0;
        for(Edge e : outEdges){
            if(e.isRed() && e.isBlueNeighbor()){
                goodImpact += 50;
            }
            if(e.isBlue() && e.isRedNeighbor()){
                goodImpact += 0;
            }
            if(e.isBlue() && e.isBlueNeighbor()){
                goodImpact += 100;
            }
            if(e.isRed() && e.isRedNeighbor()){
                goodImpact += -50;
            }
        }
        if(outEdges.isEmpty()){
            return 1000000000;
        }
       return goodImpact / outEdges.size();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Edge v : outEdges){
            if (isRed())
                sb.append(Graph.ANSI_RED);
            else
                sb.append(Graph.ANSI_BLUE);
            sb.append(label);
            sb.append(Graph.ANSI_RESET);
            sb.append(v);
            sb.append("\n");
        }
        if(outEdges.isEmpty()) {
            if (isRed())
                sb.append(Graph.ANSI_RED);
            else
                sb.append(Graph.ANSI_BLUE);
            sb.append(label);
            sb.append(Graph.ANSI_RESET);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        Vertex compareToVertex = (Vertex) o;
        return Integer.compare(this.differenceOutEdges(), compareToVertex.differenceOutEdges());
    }

    public int getCountBlueOutDegree() {
        int count = 0;
        for(Edge v : outEdges){
            if (v.isBlue()) count++;
        }
        return  count;
    }


}
