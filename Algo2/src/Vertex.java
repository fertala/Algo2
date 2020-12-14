public class Vertex {

    Edge edge;
    Graph.Color color;

    Vertex(Edge e, Graph.Color color){
        this.color = color;
        this.edge = e;
    }

    public Graph.Color getColor() {
        return color;
    }

    public Edge getEdge() {
        return edge;
    }


}
