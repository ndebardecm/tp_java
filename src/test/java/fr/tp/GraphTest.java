package fr.tp;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class GraphTest {
    private Vertex lille = new Vertex("Lille");
    private Vertex paris = new Vertex("Paris");
    private Vertex reims = new Vertex("Reims");
    private Vertex nancy = new Vertex("Nancy");
    private Vertex lyon = new Vertex("Lyon");
    private Vertex marseille = new Vertex("Marseille");
    private Vertex lemans = new Vertex("Le Mans");
    private Vertex nantes = new Vertex("Nantes");
    private Vertex bordeaux = new Vertex("Bordeaux");
    private Vertex toulouse = new Vertex("Toulouse");
    private Vertex clermont = new Vertex("Clermont Ferrant");
    private Vertex montpellier = new Vertex("Montpellier");

    @Before
    public void setup() {
        lille.connectTo(reims, 206);
        lille.connectTo(paris, 222);
        lille.connectTo(nancy, 418);

        reims.connectTo(paris, 144);
        reims.connectTo(nancy, 245);
        reims.connectTo(lyon, 489);

        paris.connectTo(lyon, 465);
        paris.connectTo(lemans, 208);
        paris.connectTo(clermont, 423);

        lyon.connectTo(clermont, 166);
        lyon.connectTo(marseille, 313);
        lyon.connectTo(montpellier, 304);

        lemans.connectTo(nantes, 189);
        lemans.connectTo(bordeaux, 443);

        nantes.connectTo(bordeaux, 347);

        bordeaux.connectTo(toulouse, 243);

        toulouse.connectTo(montpellier, 245);

        montpellier.connectTo(marseille, 169);
        montpellier.connectTo(toulouse, 245);

        marseille.connectTo(montpellier, 169);

        clermont.connectTo(lyon, 166);
        clermont.connectTo(montpellier, 333);
        clermont.connectTo(marseille, 474);
    }

    @Test
    public void getVerticeInGraphFromName() {
        Graph graph = new Graph(paris, lyon);

        Vertex Paris = graph.getVertexFromName("Paris");
        Vertex notInGraph = graph.getVertexFromName("toto");

        assertEquals(Paris.getName(),"Paris");
        assertNull(notInGraph);
    }


    @Test
    public void getDistanceForTwoAdjacentVertices() {
        Graph graph = new Graph(paris, lyon);

        assertEquals(graph.getDistance("Paris", "Lyon"), 465);
    }

    @Test
    public void getAllChildrenFromOneVertex() {
        Graph graph = new Graph(paris, lyon, clermont, marseille);

        List<Vertex> children = graph.getAllChildrenFromVertex(paris);

        //Only considerate the children in the graph
        assertFalse(children.contains(lemans));

        assertEquals(children.size(), 2);
        assertTrue(children.contains(lyon));
        assertTrue(children.contains(clermont));
        assertFalse(children.contains(marseille));
        assertFalse(children.contains(montpellier));
    }

    @Test
    public void getPathWithOneStep() {
        Graph graph = new Graph(paris, lyon, marseille);

        List<List<Vertex>> paths = graph.getAllPaths("Paris", "Marseille");

        assertNotNull(paths);
        assertEquals(paths.size(), 2);
        assertTrue(paths.get(0).contains(lyon));
        assertTrue(paths.get(0).contains(marseille));
        assertFalse(paths.get(0).contains(paris));
    }

    @Test
    public void getAllPathsWithOneStepNoCycle() {
        Graph graph = new Graph(lemans, nantes, bordeaux);

        List<List<Vertex>> paths = graph.getAllPaths("Le Mans", "Bordeaux");
        assertNotNull(paths);
        assertEquals(paths.size(), 2);
        if(paths.get(0).contains(nantes)){
            // Path 0 going through nantes so the other must not
            assertFalse(paths.get(1).contains(nantes));
            assertTrue(paths.get(0).size() == 2);
            assertTrue(paths.get(1).size() == 1);
            assertTrue(paths.get(0).get(1) == bordeaux);
            assertTrue(paths.get(1).get(0) == bordeaux);
        } else {
            // Path 0 not going through nantes
            assertFalse(paths.get(0).contains(nantes));
            assertTrue(paths.get(1).size() == 2);
            assertTrue(paths.get(0).size() == 1);
            assertTrue(paths.get(0).get(0) == bordeaux);
            assertTrue(paths.get(1).get(1) == bordeaux);
        }

        assertFalse(paths.get(0).contains(lemans));
    }

    @Test
    public void getPathWhenDoNotExists() {
        Graph graph = new Graph(lemans, marseille);
        assertEquals(graph.getAllPaths("Le Mans", "Marseille").size(), 0);
        assertEquals(graph.getAllPaths(null, "Marseille").size(), 0);
        assertEquals(graph.getAllPaths("Le Mans", null).size(), 0);
        assertEquals(graph.getAllPaths(null, null).size(), 0);
        assertEquals(graph.getAllPaths("toto", "titi").size(), 0);
    }

    @Test
    public void getPathWhenFromEqualsTo() {
        Graph graph = new Graph(lemans, marseille);
        assertEquals(graph.getAllPaths("Marseille", "Marseille").size(), 0);
    }
}
