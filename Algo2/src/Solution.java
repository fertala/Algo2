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
        Edge edge = graph.edges.stream().filter(Edge::isRed).sorted(Edge::compareTo).findFirst().orElse(null);
        while (edge != null){
            this.graph.deleteEdge(edge);
            System.out.println(graph);
            edge = graph.edges.stream().filter(Edge::isRed).sorted(Edge::compareTo).findFirst().orElse(null);
        }
    }

    public void getSolution(){
        boolean redFind;
        Edge best = graph.edges.get(0);
        do {
            redFind = false ;
            for(Edge edge : graph.edges){
                if(edge.isRed()){
                   redFind =true ;

                }
            }
        }
        while(redFind);


    }
}
