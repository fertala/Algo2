import java.util.Collection;

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

    public boolean isRed() {
        return color == Graph.Color.RED;
    }

    public boolean isBlue() {
        return color == Graph.Color.BLUE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isRed()) sb.append(Graph.ANSI_RED);
        else sb.append(Graph.ANSI_BLUE);
        sb.append(" ----> ");
        sb.append(Graph.ANSI_RESET);
        sb.append(neighbor.label);
        return sb.toString();
    }
}
