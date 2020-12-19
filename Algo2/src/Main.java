
import java.util.Scanner;

public class Main {
    static Graph graph = new Graph();
        public static void MainCaller(){
            graph = new Graph();
            main(null);
        }
        public static void launche10times(){
            int val = 0 ;
            for(int i = 0 ; i <10 ;i++ ){
                graph = new Graph();
                graph.generateGraph(0.1,0.1);
                graph.getSolution1();
                val += graph.redSequence.size();
            }
            System.out.println("au bout de 10 execution on a : "+(val/10));
        }

        public static void main(String... args) {
            Scanner sc= new Scanner(System.in);/*
            System.out.println("number of red edges : " +graph.edges.stream().filter(Edge::isRed).count());
            System.out.println("number of blue edges : " +graph.edges.stream().filter(Edge::isBlue).count());
            System.out.println("number of red vertex out  for first edge :" +graph.edges.get(0).outVertices.stream().filter(vertex -> vertex.isRed()).count());
            System.out.println("number of blue vertex out for first edge k:" +graph.edges.get(0).outVertices.stream().filter(vertex -> vertex.isBlue()).count());*/
            while(true) {
                System.out.println("1 - Add edge");
                System.out.println("2 - Delete edge");
                System.out.println("3 - Add neighbor");
                System.out.println("4 - Print Graph & Red sequence");
                System.out.println("5 - solution avec les differences");
                switch (sc.nextInt()) {
                    case 1:
                        while (true) {
                            System.out.println("label (type ! to stop)");
                            String label = sc.next();
                            if (label.equals("!")) break;
                            System.out.println("color");
                            Graph.Color color = sc.next().equals("r") ? Graph.Color.RED : Graph.Color.BLUE;
                            graph.addEdge(label, color);
                        }

                        break;


                        case 2:
                                while (true) {
                                    System.out.println("delete ? (type ! to stop)");
                                    String edgeToDelete = sc.next();
                                    if (edgeToDelete.equals("!")) break;
                                    graph.deleteEdge(edgeToDelete);
                                    System.out.println(graph);
                                }
                                break;


                                case 3:
                                while (true) {
                                    System.out.println("from: (type ! to stop)");
                                    String edge = sc.next();
                                    if (edge.equals("!")) break;
                                    System.out.println("to: ");
                                    String neighbor = sc.next();
                                    System.out.println("vertex color: ");
                                    Graph.Color vertexColor = sc.next().equals("r") ? Graph.Color.RED : Graph.Color.BLUE;
                                    graph.addNeighbor(edge, neighbor, vertexColor);
                                }
                                break;

                            case 4:
                                System.out.println(graph);
                                break;

                            case 5 :
                                System.out.println("Choose a solution to exec: ");
                                System.out.println(graph.getAverageOnXExec(100, 100, 0.1, 0.1, sc.nextInt()));
                                break;

                        }
                    }
                }

            }


