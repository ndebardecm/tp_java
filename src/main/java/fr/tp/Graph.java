package fr.tp;



import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Created by nico on 25/11/2014.
 */
public class Graph {
    private List<Vertex> vertices = new ArrayList<Vertex>();

    public Graph(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    public int getDistance(String from, String to) {
        return 0;
    }
}
