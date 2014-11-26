package fr.tp;



import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Graph {
    private List<Vertex> vertices = new ArrayList<Vertex>();

    public Graph(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    public int getDistance(String from, String to) {

        Vertex vertexFrom = getVertexFromName(from);
        Vertex vertexTo = getVertexFromName(to);
        return 0;
    }

    /**
     * Get a Vertex in the graph from its name
     * @param name
     * @return Vertex | null
     */
    public Vertex getVertexFromName(String name) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if(this.vertices.get(i).getName() == name)
                return this.vertices.get(i);
        }
        return null;
    }

    public boolean areVerticesAdjacents(Vertex from, Vertex to){
        List<Edge> edges = from.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            if(edges.get(i).getTarget().getName() == to.getName())
                return true;
        }
        return false;
    }
}
