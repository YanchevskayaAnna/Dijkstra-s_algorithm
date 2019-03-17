public class PathApp {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');

        graph.addEdge(0, 1, 50); //AB 50
        graph.addEdge(0, 3, 80); //AD 80
        graph.addEdge(1, 2, 60); //BC 60
        graph.addEdge(1, 3, 90); //BD 90
        graph.addEdge(2, 4, 40); //CE 40
        graph.addEdge(3, 2, 20); //DC 20
        graph.addEdge(3, 4, 70); //DE 70
        graph.addEdge(4, 1, 50); //EB 50
        System.out.println("Shortest paths");
        graph.path(); //Кратчайшие пути
        System.out.println();
    }
}
