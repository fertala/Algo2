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
}
