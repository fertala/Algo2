import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Edge {


    Graph.Color color;
    List<Vertex> outVertices = new ArrayList<>();
    List<Edge> inEdges = new ArrayList<>();
    String label;
    boolean printed = false;

    /* No neighbors */
    Edge(String label, Graph.Color color){
        this.label = label;
        this.color = color;
    }

    Edge(String label, Graph.Color color ,List<Vertex> outVertices , List<Edge> inEdges){
        this(label, color);
        this.outVertices = outVertices ;
        this.inEdges = inEdges;
    }

    Edge(String label, Graph.Color color, Edge neighbor1, Graph.Color vertexColor){
        this(label, color);
        outVertices.add(new Vertex(neighbor1, vertexColor));
    }

    Edge(String label, Graph.Color color, Edge neighbor1, Graph.Color vertexColor, Edge neighbor2, Graph.Color vertexColor2){
        this(label, color, neighbor1, vertexColor);
        outVertices.add(new Vertex(neighbor2, vertexColor2));
    }
    public void setOutVertices(List<Vertex> outVertices){
        this.outVertices = outVertices ;
    }
    public void setInEdges(List<Edge> inEdges){
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
        for(Vertex v : outVertices){
            v.transformNeighbor();
        }
        for(Edge e : inEdges){
            e.deleteNeighbor(this);
        }
    }

    private void deleteNeighbor(Edge edge) {
        if(isNeighbor(edge)){
            outVertices.removeIf(v->v.getNeighbor().equals(edge));
        }
    }

    public void changeColor(Graph.Color color) {
        System.out.println(label + " old color : "+ this.color.name() + " new color " + color.name());
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void addNeighbor(Edge edge, Graph.Color color) {
        if (!isNeighbor(edge)) {
            Vertex v = new Vertex(edge, color);
            outVertices.add(v);
            edge.addInNeighbor(this);
        }
        else
            System.out.println(edge.getLabel() + " already neighbor");
        }


    private void addInNeighbor(Edge e) {
        inEdges.add(e);
    }

    private boolean isNeighbor(Edge edge) {
        return outVertices.stream().anyMatch(v->v.getNeighbor().equals(edge));
    }

    private boolean isNotFull() {
        return outVertices.size() < 2;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(label, edge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Vertex v : outVertices){
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

    public int getCountBlueOutDegree() {
        int count = 0;
        for(Vertex v : outVertices){
            if (v.isBlue() && v.getNeighbor().isRed()) count++;
        }
        return  count;
    }

    public int getCountRedOutDegree() {
        int count = 0;
        for(Vertex v : outVertices){
            if (v.isRed() && v.getNeighbor().isRed()) count++;
        }
        return  count;
    }

}
