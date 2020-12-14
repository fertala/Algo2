import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Edge {
    Graph.Color color;
    List<Vertex> vertices = new ArrayList<>();
    String label;

    /* No neighbors */
    Edge(String label, Graph.Color color){
        this.label = label;
        this.color = color;
    }

    Edge(String label, Graph.Color color, Edge neighbor1, Graph.Color vertexColor){
        this(label, color);
        vertices.add(new Vertex(neighbor1, vertexColor));
    }

    Edge(String label, Graph.Color color, Edge neighbor1, Graph.Color vertexColor, Edge neighbor2, Graph.Color vertexColor2){
        this(label, color, neighbor1, vertexColor);
        vertices.add(new Vertex(neighbor2, vertexColor2));
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
        for(Vertex v : vertices){
            v.transformNeighbor();
        }
    }

    public void changeColor(Graph.Color color) {
        this.color = color;
    }
}
