package fr.tp;



import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class Graph {
    private List<Vertex> vertices = new ArrayList<Vertex>();

    public Graph(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    /**
     * Get the shortest distance between two vertices of the given graph
     * @param from
     * @param to
     * @return int distance. Note that 0 is returned in all the limits cases (vertex not in the graph, no path...)
     */
    public int getDistance(String from, String to) {
        List<List<Vertex>> goodPaths = this.getAllPaths(from, to);

        if(goodPaths.size() == 0)
            return 0;

        int shortest = 0;
        for (int i = 0; i < goodPaths.size(); i++){
            Vertex previous = getVertexFromName(from);
            int tempTotal = 0;

            for (int j = 0; j < goodPaths.get(i).size(); j++){
                //browse the edges so as to get the good one
                tempTotal = tempTotal + this.getLengthBetweenTwoAdjacentVerticies(previous, goodPaths.get(i).get(j));
                previous = goodPaths.get(i).get(j);
            }

            //Update the shortest
            if(shortest == 0)
                shortest = tempTotal;
            if(tempTotal < shortest)
                shortest = tempTotal;
        }

        return shortest;
    }

    /**
     * Get a vertex object in the graph from its name
     * @param name
     * @return Vertex | null if not found in the graph
     */
    public Vertex getVertexFromName(String name) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if(this.vertices.get(i).getName() == name)
                return this.vertices.get(i);
        }
        return null;
    }

    /**
     * Get all the children of a vertex (i.e. vertices targeted by its children, and that belong to the graph)
     * @param vertex
     * @return list of vertices
     */
    public List<Vertex> getAllChildrenFromVertex(Vertex vertex) {
        List<Vertex> children = new ArrayList<Vertex>();
        List<Edge> edges = vertex.getEdges();
        for(int i = 0; i < edges.size(); i++){
            //Check that the vertex is in the graph
            if(this.getVertexFromName(edges.get(i).getTarget().getName()) == null)
                continue;
            children.add(edges.get(i).getTarget());
        }
        return children;
    }

    /**
     * Get all the paths between two verticies, avoiding cycles
     * @param from
     * @param to
     * @return list of paths. A path is a list of verticies, starting from the first child (not the starting vertex) and ending by the destination
     */
    public List<List<Vertex>> getAllPaths(String from, String to) {
        List<List<Vertex>> toBrowse = new ArrayList<List<Vertex>>();
        //toBrowse contains all the paths that are not yet fully explored and not yet excluded

        List<List<Vertex>> goodPaths = new ArrayList<List<Vertex>>();
        //goodPaths contains all the paths that connect the two vertices

        Vertex vertexFrom = this.getVertexFromName(from);
        Vertex vertexTo = this.getVertexFromName(to);

        if (vertexFrom == null || vertexTo == null || vertexFrom == vertexTo) //return an empty list
            return goodPaths;

        //Prepare toBrowse for statement
        List<Vertex> childrenFrom = this.getAllChildrenFromVertex(vertexFrom);
        for (int i = 0; i < childrenFrom.size(); i++){
            //Convert each child into a list containing it
            List<Vertex> child = new ArrayList<Vertex>();
            child.add(childrenFrom.get(i));

            //if the child is equal to the target, add it to the good path, else to the paths to browse
            if(childrenFrom.get(i) == vertexTo)
                goodPaths.add(child);
            else
                toBrowse.add(child);
        }

        //Browse
        while (toBrowse.size() > 0){
            //Get all the children of the last Vertex from the first path to explore
            int lastVertexIndex = toBrowse.get(0).size() -1;
            Vertex lastVertex = toBrowse.get(0).get(lastVertexIndex);
            List<Vertex> children = this.getAllChildrenFromVertex(lastVertex);

            if (children.contains(vertexTo)){
                //we add the vertexTo to the path
                List<Vertex> goodPath = toBrowse.get(0);
                goodPath.add(vertexTo);
                //we add this path to the goodPaths list
                goodPaths.add(goodPath);
                //we remove the current path from the toBrowse list
                toBrowse.remove(0);
            } else {
                //we add all the new paths to browse (1 per child) to toBrowse list
                for (int i = 0; i < children.size(); i++){
                    //duplicate the list and add a child, only if it does not create a cycle
                    if(!(toBrowse.get(0).contains(children.get(i))) && !(children.get(i) == vertexFrom)) {
                        List<Vertex> pathPlusChild = new ArrayList<Vertex>(toBrowse.get(0));
                        pathPlusChild.add(children.get(i));
                        //add the new path to toBrowse object
                        toBrowse.add(pathPlusChild);
                    }
                }
                //we remove the current path from the toBrowse list
                toBrowse.remove(0);
            }
        }
        return goodPaths;
    }

    /**
     * Get the length between two adjacent verticies, based on the edges
     * @param from
     * @param to
     * @return
     */
    public int getLengthBetweenTwoAdjacentVerticies(Vertex from, Vertex to){
        List<Edge> edges = from.getEdges();
        for (int i = 0; i < edges.size(); i++){
            if(edges.get(i).getTarget().equals(to))
                return edges.get(i).getDistance();
        }
        return 0;
    }
}
