
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
                System.out.println("6 - solution Armand n fois");
                System.out.println("7 - launch 10 times");
                System.out.println("8 - restart");
                System.out.println("9 - Solution Best");
                System.out.println("10 - Exit");
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

                            case 5:
                                graph.generateGraph(0.1,0.1);
                                graph.getSolution1();
                                break;

                            case 6 :
                                System.out.println(graph.getAverageOnXExec(100, 5, 0.1, 0.1, 1));
                                break;
                            case 9:
                                graph.generateGraph(0.1,0.1);
                                graph.getBestSolution();
                                break;
                            case 10:
                                return;
                            case 8:
                                Main.MainCaller();
                                return;
                            case 7:
                                launche10times();
                                return;
                        }


                    }
                }

            }


