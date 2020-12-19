public class Solution {
    Graph graph ;

    public Solution(float p , float q){
        this.graph = new Graph();
        graph.generateGraph(p,q);
    }

    public Graph getGraph() {
        return graph;
    }

    public void getSolution1(){
        Vertex vertex = graph.vertices.stream().filter(Vertex::isRed).sorted(Vertex::compareTo).findFirst().orElse(null);
        while (vertex != null){
            this.graph.deleteEdge(vertex);
            System.out.println(graph);
            vertex = graph.vertices.stream().filter(Vertex::isRed).sorted(Vertex::compareTo).findFirst().orElse(null);
        }
    }

    public void getSolution(){
        boolean redFind;
        Vertex best = graph.vertices.get(0);
        do {
            redFind = false ;
            for(Vertex vertex : graph.vertices){
                if(vertex.isRed()){
                   redFind =true ;

                }
            }
        }
        while(redFind);


    }
}
