
import java.util.Scanner;

public class Main {
    static Graph graph = new Graph();


        public static void main(String... args) {

            Scanner sc= new Scanner(System.in);
            graph.generateGraph(0.30,0.50);
            while(true) {
                System.out.println("1 - Add edge");
                System.out.println("2 - Delete edge");
                System.out.println("3 - Add neighbor");
                System.out.println("4 - Print Graph & Red sequence");
                System.out.println("5 - Exit");
                System.out.println("number of red edges : " +graph.edges.stream().filter(Edge::isRed).count());
                System.out.println("number of blue edges : " +graph.edges.stream().filter(Edge::isBlue).count());
                System.out.println("number of red vertex out  for first edge :" +graph.edges.get(0).outVertices.stream().filter(vertex -> vertex.isRed()).count());
                System.out.println("number of blue vertex out for first edge :" +graph.edges.get(0).outVertices.stream().filter(vertex -> vertex.isBlue()).count());

                switch (sc.nextInt()) {
                    case 1 -> {
                        while(true) {
                            System.out.println("label (type ! to stop)");
                            String label = sc.next();
                            if(label.equals("!")) break;
                            System.out.println("color");
                            Graph.Color color = sc.next().equals("r") ? Graph.Color.RED : Graph.Color.BLUE;
                            graph.addEdge(label, color);
                        }
                    }
                    case 2 -> {
                        while(true) {
                            System.out.println("delete ? (type ! to stop)");
                            String edgeToDelete = sc.next();
                            if(edgeToDelete.equals("!")) break;
                            graph.deleteEdge(edgeToDelete);
                            System.out.println(graph);
                        }
                    }
                    case 3 -> {
                        while(true) {
                            System.out.println("from: (type ! to stop)");
                            String edge = sc.next();
                            if(edge.equals("!")) break;
                            System.out.println("to: ");
                            String neighbor = sc.next();
                            System.out.println("vertex color: ");
                            Graph.Color vertexColor = sc.next().equals("r") ? Graph.Color.RED : Graph.Color.BLUE;
                            graph.addNeighbor(edge, neighbor, vertexColor);
                        }
                    }
                    case 4 -> {System.out.println(graph);}
                    case 5 -> { return ; }
                }
            }
        }

}