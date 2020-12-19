public class Edge {

    Vertex neighbor;
    Graph.Color color;

    Edge(Vertex e, Graph.Color color){
        this.color = color;
        this.neighbor = e;
    }

    public Graph.Color getColor() {
        return color;
    }

    public Vertex getNeighbor() {
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
        if(neighbor.isRed()) sb.append(Graph.ANSI_RED);
        else sb.append(Graph.ANSI_BLUE);
        sb.append(neighbor.label);
        sb.append(Graph.ANSI_RESET);
        return sb.toString();
    }
}
