public class Vertex {

    Edge neighbor;
    Graph.Color color;

    Vertex(Edge e, Graph.Color color){
        this.color = color;
        this.neighbor = e;
    }

    public Graph.Color getColor() {
        return color;
    }

    public Edge getNeighbor() {
        return neighbor;
    }

    public boolean isBlueNeighbor(){
        return neighbor.getColor() == Graph.Color.BLUE;
    }

    public boolean isRedNeighbor(){
        return neighbor.getColor() == Graph.Color.RED;
    }



}
