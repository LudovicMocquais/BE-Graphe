package org.insa.graphs.algorithm.algo;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.junit.Test;

import java.io.IOException;

public class AStarAlgoTest extends AlgoTest {

	 /**
     * Test valid set of path from custom graph
     */
    @Test
    public void testValid() {
        // Accessible path from origin to destination
        ShortestPathData data;
        
        data = new ShortestPathData(graph, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(0));
        testValidAtoA(new AStarAlgorithm(data));

        data = new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(0));
        testValidAtoB(new AStarAlgorithm(data));

        data = new ShortestPathData(graph, nodes[0], nodes[2], ArcInspectorFactory.getAllFilters().get(0));
        testValidAtoC(new AStarAlgorithm(data));

        data = new ShortestPathData(graph, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(0));
        testValidAtoD(new AStarAlgorithm(data));

        data = new ShortestPathData(graph, nodes[0], nodes[4], ArcInspectorFactory.getAllFilters().get(0));
        testValidAtoE(new AStarAlgorithm(data));
    }

    /**
     * Test invalid set of path from custom graph
     */
    @Test
    public void testInvalid() {
        // Unaccessible path from origin to destination (no pedestrian roads)
        ShortestPathData data = new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(1));
        testInvalidAtoB(new AStarAlgorithm(data));

        data = new ShortestPathData(graph, nodes[0], nodes[2], ArcInspectorFactory.getAllFilters().get(1));
        testInvalidAtoC(new AStarAlgorithm(data));

        data = new ShortestPathData(graph, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(1));
        testInvalidAtoD(new AStarAlgorithm(data));

        data = new ShortestPathData(graph, nodes[0], nodes[4], ArcInspectorFactory.getAllFilters().get(1));
        testInvalidAtoE(new AStarAlgorithm(data));
    }

    
//    /**
//     * Test set of paths from paths folder in Haute Garonne map
//     * @throws IOException if paths are not founds or invalids
//     */
// 
//    @Test
//    public void testHauteGaronne() throws IOException {
//        int insa = 10991;
//        int airport = 89149;
//        int bikini = 63104;
//        // Shortest path from INSA to Airport restricted to roads open for cars
//        ShortestPathData data = new ShortestPathData(hautegaronne, hautegaronne.get(insa), hautegaronne.get(airport), ArcInspectorFactory.getAllFilters().get(1));
//        testINSAAirportLength(new DijkstraAlgorithm(data));
//
//        // Fastest path INSA to Airport restricted to roads open for cars
//        data = new ShortestPathData(hautegaronne, hautegaronne.get(insa), hautegaronne.get(airport), ArcInspectorFactory.getAllFilters().get(3));
//        testINSAAirportTime(new DijkstraAlgorithm(data));
//
//        // Shortest path from INSA to Bikini on any road
//        data = new ShortestPathData(hautegaronne, hautegaronne.get(insa), hautegaronne.get(bikini), ArcInspectorFactory.getAllFilters().get(0));
//        testINSABikiniCanal(new DijkstraAlgorithm(data));
//
//        // Fastest path from INSA to Bikini restricted to roads open for cars
//        data = new ShortestPathData(hautegaronne, hautegaronne.get(insa), hautegaronne.get(bikini), ArcInspectorFactory.getAllFilters().get(3));
//        testINSABikiniTimeCar(new DijkstraAlgorithm(data));
//    }
//
//    /**
//     * Test set of paths from paths folder in INSA map
//     * @throws IOException if paths are not founds or invalids
//     */
//    @Test
//    public void testINSA() throws IOException {
//        int rangueil = 552;
//        int entree = 254;
//        int r2 = 526;
//        // Shortest path from Rangueil to INSA restricted to roads open for cars
//        ShortestPathData data = new ShortestPathData(insa, insa.get(rangueil), insa.get(entree), ArcInspectorFactory.getAllFilters().get(3));
//        testRangueilINSA(new DijkstraAlgorithm(data));
//
//        // Shortest path from Rangueil to R2 open to any roads
//        data = new ShortestPathData(insa, insa.get(rangueil), insa.get(r2), ArcInspectorFactory.getAllFilters().get(0));
//        testRangueilR2(new DijkstraAlgorithm(data));
//    }
//
//    /**
//     * Test set of paths from paths folder in French Polynesia map
//     * @throws IOException if paths are not founds or invalids
//     */
//    @Test
//    public void testFrenchPolynesia() throws IOException {
//        int papeete = 3382;
//        int fare = 3642;
//        int pihau = 13979;
//        // Shortest path from Papeete to Pihau on any road
//        ShortestPathData data = new ShortestPathData(frenchpolynesia, frenchpolynesia.get(papeete), frenchpolynesia.get(pihau), ArcInspectorFactory.getAllFilters().get(0));
//        testPapeetePihau(new DijkstraAlgorithm(data));
//
//        // Invalid path from Papeete to Fare
//        data = new ShortestPathData(frenchpolynesia, frenchpolynesia.get(papeete), frenchpolynesia.get(fare), ArcInspectorFactory.getAllFilters().get(0));
//        testPapeeteFare(new DijkstraAlgorithm(data));
//    }
//
//    /**
//     * Test path from paths folder in Toulouse map
//     * @throws IOException if paths are not founds or invalids
//     */
//    @Test
//    public void testToulouse() throws IOException {
//        int insa = 11325;
//        int tonton = 18935; // perhaps the most important path
//        // Fastest path from INSA to Chez Tonton restricted to roads open for cars
//        ShortestPathData data = new ShortestPathData(toulouse, toulouse.get(insa), toulouse.get(tonton), ArcInspectorFactory.getAllFilters().get(3));
//        testINSATonton(new DijkstraAlgorithm(data));
//    }

}