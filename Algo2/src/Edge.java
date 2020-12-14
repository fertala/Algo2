import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Edge {
    Graph.Color color;
    List<Vertex> vertices = new ArrayList<>();

    /* No neighbors */
    Edge(Graph.Color color){
        this.color = color;
    }

    Edge(Graph.Color color, Edge neighbor1, Graph.Color vertexColor){
        this(color);
        vertices.add(new Vertex(neighbor1, vertexColor));
    }

    Edge(Graph.Color color, Edge neighbor1, Graph.Color vertexColor, Edge neighbor2, Graph.Color vertexColor2){
        this(color, neighbor1, vertexColor);
        vertices.add(new Vertex(neighbor2, vertexColor2));
    }
}
