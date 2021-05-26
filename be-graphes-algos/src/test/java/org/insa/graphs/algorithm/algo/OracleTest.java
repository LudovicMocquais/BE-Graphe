package org.insa.graphs.algorithm.algo;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class OracleTest extends AlgoTest {

    private void solutionCrossing(ShortestPathSolution bellmanFord, ShortestPathSolution dijkstra, ShortestPathSolution aStar) {
        // Compare Dijkstra to Bellman-Ford
        assertEquals(bellmanFord.getStatus(), dijkstra.getStatus());
        assertEquals(bellmanFord.getPath().getOrigin().getId(), dijkstra.getPath().getOrigin().getId());
        assertEquals(bellmanFord.getPath().getDestination().getId(), dijkstra.getPath().getDestination().getId());
        assertEquals(bellmanFord.getPath().getLength(), dijkstra.getPath().getLength(), 0);
        assertEquals(bellmanFord.getPath().getMinimumTravelTime(), dijkstra.getPath().getMinimumTravelTime(), 0);

        // Compare A* to Bellman-Ford
        assertEquals(bellmanFord.getStatus(), aStar.getStatus());
        assertEquals(bellmanFord.getPath().getOrigin().getId(), aStar.getPath().getOrigin().getId());
        assertEquals(bellmanFord.getPath().getDestination().getId(), aStar.getPath().getDestination().getId());
        assertEquals(bellmanFord.getPath().getLength(), aStar.getPath().getLength(), 0);
        assertEquals(bellmanFord.getPath().getMinimumTravelTime(), aStar.getPath().getMinimumTravelTime(), 0);
    }

    /**
     * Test valid path from custom graph with the three algorithm.
     * Compare Dijkstra and A* to Bellman-Ford which serves as an oracle.
     */
    @Test
    public void testValid() {
        // Accessible path from origin to destination
        
        ShortestPathData data = new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(0));
        ShortestPathSolution bFSolution = testValidAtoB(new BellmanFordAlgorithm(data));
        ShortestPathSolution dijSolution = testValidAtoB(new DijkstraAlgorithm(data));
        ShortestPathSolution aStarSolution = testValidAtoB(new AStarAlgorithm(data));
        solutionCrossing(bFSolution, dijSolution, aStarSolution);
        
        data = new ShortestPathData(graph, nodes[0], nodes[2], ArcInspectorFactory.getAllFilters().get(0));
        bFSolution = testValidAtoC(new BellmanFordAlgorithm(data));
        dijSolution = testValidAtoC(new DijkstraAlgorithm(data));
        aStarSolution = testValidAtoC(new AStarAlgorithm(data));
        solutionCrossing(bFSolution, dijSolution, aStarSolution);
        
        data = new ShortestPathData(graph, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(0));
        bFSolution = testValidAtoD(new BellmanFordAlgorithm(data));
        dijSolution = testValidAtoD(new DijkstraAlgorithm(data));
        aStarSolution = testValidAtoD(new AStarAlgorithm(data));
        solutionCrossing(bFSolution, dijSolution, aStarSolution);
        
        data = new ShortestPathData(graph, nodes[0], nodes[4], ArcInspectorFactory.getAllFilters().get(0));
        bFSolution = testValidAtoE(new BellmanFordAlgorithm(data));
        dijSolution = testValidAtoE(new DijkstraAlgorithm(data));
        aStarSolution = testValidAtoE(new AStarAlgorithm(data));
        solutionCrossing(bFSolution, dijSolution, aStarSolution);
    }

    /**
     * Test shortest path on 10 random origin/destination in Bordeaux or Haute Garonne map with the three algorithm.
     * Compare Dijkstra and A* to Bellman-Ford which serves as an oracle.
     */
    @Test
    public void testRandomShortest() {
    	 int i = 0;
    	 List<Graph> maps = new ArrayList<Graph>();
         maps.add(hgaronne);
         maps.add(bordeaux);
         Random rand = new Random();
         while (i < 10) {
        	 System.out.println("Iteration " + i);
             // Shortest path from random origin to random destination by any road, car only or pedestrian only
             Graph map = maps.get(rand.nextInt(maps.size()));
             ShortestPathData data = new ShortestPathData(map, map.get(rand.nextInt(map.size())), map.get(rand.nextInt(map.size())), ArcInspectorFactory.getAllFilters().get(rand.nextInt(2)));
             ShortestPathSolution bFSolution = new BellmanFordAlgorithm(data).run();
             if (bFSolution.isFeasible()) {
                 ShortestPathSolution dijSolution = new DijkstraAlgorithm(data).run();
                 ShortestPathSolution aStarSolution = new AStarAlgorithm(data).run();

                 solutionCrossing(bFSolution, dijSolution, aStarSolution);

                 i++;
             }
        }
    }

    /**
     * Test fastest path on 10 random origin/destination in Bordeaux or Haute Garonne map with the three algorithm.
     * Compare Dijkstra and A* to Bellman-Ford which serves as an oracle.
     */
    @Test
    public void testRandomFastest() {
    	int i = 0;
   	 	List<Graph> maps = new ArrayList<Graph>();
        maps.add(hgaronne);
        maps.add(bordeaux);
        Random rand = new Random();
        while (i < 10) {
        	System.out.println("Iteration " + i);
            // Shortest path from random origin to random destination by any road, car only or pedestrian only
            Graph map = maps.get(rand.nextInt(maps.size()));
            ShortestPathData data = new ShortestPathData(map, map.get(rand.nextInt(map.size())), map.get(rand.nextInt(map.size())), ArcInspectorFactory.getAllFilters().get(rand.nextInt(3)+2));
            ShortestPathSolution bFSolution = new BellmanFordAlgorithm(data).run();
            if (bFSolution.isFeasible()) {
            	
                ShortestPathSolution dijSolution = new DijkstraAlgorithm(data).run();
                ShortestPathSolution aStarSolution = new AStarAlgorithm(data).run();

                solutionCrossing(bFSolution, dijSolution, aStarSolution);

                i++;
            }
        }
    }
}