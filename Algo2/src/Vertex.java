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
        return neighbor.isBlue();
    }

    public boolean isRedNeighbor(){
        return neighbor.isRed();
    }

    public void transformNeighbor() {
        neighbor.changeColor(color);
    }
}
