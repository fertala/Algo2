import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex implements Comparable {


    Graph.Color color;
    List<Edge> outVertices = new ArrayList<>();
    List<Vertex> inEdges = new ArrayList<>();
    String label;
    boolean printed = false;

    /* No neighbors */
    Vertex(String label, Graph.Color color){
        this.label = label;
        this.color = color;
    }

    Vertex(String label, Graph.Color color , List<Edge> outVertices , List<Vertex> inEdges){
        this(label, color);
        this.outVertices = outVertices ;
        this.inEdges = inEdges;
    }

    Vertex(String label, Graph.Color color, Vertex neighbor1, Graph.Color vertexColor){
        this(label, color);
        outVertices.add(new Edge(neighbor1, vertexColor));
    }

    Vertex(String label, Graph.Color color, Vertex neighbor1, Graph.Color vertexColor, Vertex neighbor2, Graph.Color vertexColor2){
        this(label, color, neighbor1, vertexColor);
        outVertices.add(new Edge(neighbor2, vertexColor2));
    }
    public void setOutVertices(List<Edge> outVertices){
        this.outVertices = outVertices ;
    }
    public void setInEdges(List<Vertex> inEdges){
        this.inEdges = inEdges ;
    }

    public Graph.Color getColor() {
        return color;
    }

    public boolean isRed() {
        return color == Graph.Color.RED;
    }

    public boolean isBlue() {
        return color == Graph.Color.BLUE;
    }

    public void transformNeighbors() {
        for(Edge v : outVertices){
            v.transformNeighbor();
        }
        for(Vertex e : inEdges){
            e.deleteNeighbor(this);
        }
    }

    private void deleteNeighbor(Vertex vertex) {
        if(isNeighbor(vertex)){
            outVertices.removeIf(v->v.getNeighbor().equals(vertex));
        }
    }

    public void changeColor(Graph.Color color) {
        //System.out.println(label + " old color : "+ this.color.name() + " new color " + color.name());
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void addNeighbor(Vertex vertex, Graph.Color color) {
        if (!isNeighbor(vertex)) {
            Edge v = new Edge(vertex, color);
            outVertices.add(v);
            vertex.addInNeighbor(this);
        }
        else
            System.out.println(vertex.getLabel() + " already neighbor");
        }

    public boolean zeroOut(){
        return this.outVertices.size() == 0 ;
    }
    private void addInNeighbor(Vertex e) {
        inEdges.add(e);
    }

    private boolean isNeighbor(Vertex vertex) {
        return outVertices.stream().anyMatch(v->v.getNeighbor().equals(vertex));
    }

    private boolean isNotFull() {
        return outVertices.size() < 2;
    }

    public int differenceOutVertices(){
        return (int) (outVertices.stream().filter(Edge::isRed).count()  -  outVertices.stream().filter(Edge::isBlue).count());
    }

    public boolean outVertices(){
        if (outVertices.stream().filter(Edge::isRed).count()>0)
            return true;
        return false;

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
        for(Edge v : outVertices){
            if (isRed())
                sb.append(Graph.ANSI_RED);
            else
                sb.append(Graph.ANSI_BLUE);
            sb.append(label);
            sb.append(Graph.ANSI_RESET);
            sb.append(v);
            sb.append("\n");
        }
        if(outVertices.isEmpty()) {
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
        if(this.differenceOutVertices() == compareToVertex.differenceOutVertices()) return 0 ;
        if(this.differenceOutVertices() > compareToVertex.differenceOutVertices()) return 1;
        return -1;
    }

    public int comparing(Object o) {
        Vertex compareToVertex = (Vertex) o;
        if(!(this.outVertices() == compareToVertex.outVertices())) return 0 ;
        if(this.outVertices() && !compareToVertex.outVertices()) return 1;
        if(this.outVertices() == compareToVertex.outVertices()) return 1 ;
        return -1;
    }

    public int getCountBlueOutDegree() {
        int count = 0;
        for(Edge v : outVertices){
            if (v.isBlue() && v.getNeighbor().isRed()) count++;
        }
        return  count;
    }

    public int getCountRedOutDegree() {
        int count = 0;
        for(Edge v : outVertices){
            if (v.isRed()) count++;
        }
        return  count;
    }

}
