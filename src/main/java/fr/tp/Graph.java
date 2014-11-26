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

        List<Edge> edges = vertexFrom.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            if(edges.get(i).getTarget().getName() == to) {
                //Adjacent vertices, nothing to do
                return edges.get(i).getDistance();
            }
        }
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
        for (int i = 0; i < vertexFrom.getEdges().size(); i++){
            //Convert each child into a list containing it
            List<Vertex> child = new ArrayList<Vertex>();
            child.add(vertexFrom.getEdges().get(i).getTarget());

            //if the child is equal to the target, add it to the good path, else to the paths to browse
            if(vertexFrom.getEdges().get(i).getTarget() == vertexTo)
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
                    //duplicate the list and add a child
                    List<Vertex> pathPlusChild = new ArrayList<Vertex>(toBrowse.get(0));
                    pathPlusChild.add(children.get(i));
                    //add the new path to toBrowse object
                    toBrowse.add(pathPlusChild);
                }
                //we remove the current path from the toBrowse list
                toBrowse.remove(0);
            }
        }
        return goodPaths;
    }
}
