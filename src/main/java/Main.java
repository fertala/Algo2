import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;


public class Main {
    static Graph graph = new Graph();

    public static void main(String... args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Random Graph options");
            System.out.println("1 - Create a random Graph & choose a heuristic");
            System.out.println("2 - Create a random Graph & write the probability about a heuristic into an Excel file");
            System.out.println("3 - Run the heuristic 1 100x on a random graph of 100 edges (p=0.9 & q=0.9)");
            System.out.println("4 - Run the heuristic 2 100x on a random graph of 100 edges (p=0.9 & q=0.9)");
            System.out.println();
            System.out.println("Custom Graph options");
            System.out.println("5 - Create a custom Graph");
            System.out.println("6 - Print the Graph & Red sequence");
            System.out.println("7 - Choose heuristic to run it on the customized Graph");
            System.out.println();
            System.out.println("8 - Exit");
            System.out.print("Your choice: ");
            switch (sc.nextInt()) {
                case 1:
                    System.out.println("Which heuristic to exec on the random graph(1 or 2): ");
                    int solution = sc.nextInt();
                    System.out.println("How many vertices");
                    int vertices = sc.nextInt();
                    System.out.println("How many times it should loop");
                    int loop = sc.nextInt();
                    System.out.println("Probability to create a red vertex (between 0 & 1)");
                    double pRVertex = sc.nextDouble();
                    System.out.println("Probability to create a blue edge (between 0 & 1)");
                    double pBEdge = sc.nextDouble();
                    System.out.println("Average size of the red sequence: " + graph.runHeuristicXTimes(vertices, loop, pRVertex, pBEdge, solution));
                    break;

                case 2:
                    System.out.println("Which heuristic  (1 or 2): ");
                    int sol = sc.nextInt();
                    System.out.println("How many vertices");
                    int vert = sc.nextInt();
                    System.out.println("How many times the algorithm should loop");
                    int lp = sc.nextInt();
                    System.out.println();
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("Heuristic " + sol);
                    int rowCount = 0;
                    int i = 0;
                    double[] pZeroToOne = {0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1};
                    Row topRow = sheet.createRow(0);
                    Cell topCell = topRow.createCell(0);
                    topCell.setCellValue("p | q ->");
                    for (double p :pZeroToOne) {
                        Cell cell = topRow.createCell(++rowCount);
                        cell.setCellValue(String.format(Locale.ROOT, "%.1f",p));
                    }
                    rowCount = 0;
                    for (double p : pZeroToOne) {
                        System.out.println("STEP "+i+++"/10");
                        Row row = sheet.createRow(++rowCount);
                        int columnCount = 0;
                        Cell head = row.createCell(columnCount);
                        head.setCellValue(String.format(Locale.ROOT, "%.1f",p));
                        for (double q : pZeroToOne) {
                            Cell cell = row.createCell(++columnCount);
                            cell.setCellValue(String.format(Locale.ROOT, "%.2f", graph.runHeuristicXTimes(vert, lp, p, q, sol)));
                        }
                    }
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime now = LocalDateTime.now();
                    String name = "MiniProjet2-Proba_" + dtf.format(now) +".xlsx";
                    try (FileOutputStream outputStream = new FileOutputStream(name)) {
                        workbook.write(outputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + " saved\n");
                    break;
                case 3:
                    System.out.println(graph.runHeuristicXTimes(100,100,0.9,0.9,1));
                    break;

                case 4:
                    System.out.println(graph.runHeuristicXTimes(100,100,0.9,0.9,2));
                    break;

                case 5:
                    boolean back = true;
                    while(back) {
                        System.out.println("1 - Add edge");
                        System.out.println("2 - Delete edge");
                        System.out.println("3 - Add neighbor");
                        System.out.println("4 - Back");
                        switch (sc.nextInt()) {
                                case 1:
                                    while (true) {
                                    System.out.println("label (type ! to stop)");
                                    String label = sc.next();
                                    if (label.equals("!")) break;
                                    System.out.println("color (r for red & everything else for blue)");
                                    Graph.Color color = sc.next().equals("r") ? Graph.Color.RED : Graph.Color.BLUE;
                                    graph.addEdge(label, color);
                                }
                                break;
                                case 2:
                                    while (true) {
                                        System.out.println("delete label ? (type ! to stop)");
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
                                        System.out.println("vertex color (r for red & everything else for blue): ");
                                        Graph.Color vertexColor = sc.next().equals("r") ? Graph.Color.RED : Graph.Color.BLUE;
                                        graph.addNeighbor(edge, neighbor, vertexColor);
                                    }
                                    break;
                            case 4:
                                back = false;
                            }
                    }

                case 6:
                    System.out.println(graph);
                    break;

                case 7:
                    System.out.println("Which heuristic  (1 or 2): ");
                    int heuristicNo = sc.nextInt();
                    System.out.println("Size of the red sequence: " + graph.runHeuristic(heuristicNo));
                    break;


                case 8:
                    return;
            }

        }
    }

}


