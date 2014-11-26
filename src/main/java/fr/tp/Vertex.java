package fr.tp;

import java.util.ArrayList;
import java.util.List;


public class Vertex {
    private String name;

    private List<Edge> edges = new ArrayList<Edge>();

    /**
     * Override the equals method. To verticies are considered as equals when they have the same name.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Vertex)) return false;
        Vertex objVertex = (Vertex)obj;
        if (objVertex.getName() == name) return true;
        return false;
    }

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void connectTo(Vertex target, int distance) {
        edges.add(new Edge(target, distance));
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
