package org.insa.graphs.algorithm.algo;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.*;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.junit.Before;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;

import static org.junit.Assert.*;

public class AlgoTest {
    // Small graphs
    protected static Graph graph, hgaronne, bordeaux;

    // List of nodes
    protected static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    protected static Arc a2b, a2d, b2c, b2d, c2e, d2b, d2c, d2e, e2a, e2c;

    // Shortest path from A to another node
    protected static Path short2a, short2b, short2c, short2d, short2e;

    /**
     * Init all attributes with custom graph and files from the maps folder
     * @throws IOException if files are not founds or invalids
     */
    @Before
    public void initAll() throws IOException {
        // Restrict to access mode FOOT only
        EnumMap<AccessRestrictions.AccessMode, AccessRestrictions.AccessRestriction> restrictions = new EnumMap<>(AccessRestrictions.AccessMode.class);
        for (AccessRestrictions.AccessMode mode: AccessRestrictions.AccessMode.values())
            restrictions.put(mode, AccessRestrictions.AccessRestriction.FORBIDDEN);
        restrictions.put(AccessRestrictions.AccessMode.FOOT, AccessRestrictions.AccessRestriction.ALLOWED);

        // Init pedestrian information
        RoadInformation info = new RoadInformation(RoadInformation.RoadType.PEDESTRIAN, new AccessRestrictions(restrictions), true, 1, "");

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = new Node(i, new Point(0,0));

        // Create arcs
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, info, null);
        a2d = Node.linkNodes(nodes[0], nodes[3], 5, info, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 1, info, null);
        b2d = Node.linkNodes(nodes[1], nodes[3], 2, info, null);
        c2e = Node.linkNodes(nodes[2], nodes[4], 4, info, null);
        d2b = Node.linkNodes(nodes[3], nodes[1], 3, info, null);
        d2c = Node.linkNodes(nodes[3], nodes[2], 9, info, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 2, info, null);
        e2a = Node.linkNodes(nodes[4], nodes[0], 3, info, null);
        e2c = Node.linkNodes(nodes[4], nodes[2], 5, info, null);

        // Create graphs
        graph = new Graph("test", "Basic Test Map", Arrays.asList(nodes), null);
        hgaronne = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../maps/haute-garonne.mapgr")))).read();
        bordeaux = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../maps/bordeaux.mapgr")))).read();

        // Create paths (shortest paths)
        short2a = new Path(graph, nodes[0]);
        short2b = new Path(graph, Arrays.asList(a2d, d2b));
        short2c = new Path(graph, Arrays.asList(a2d, d2b, b2c));
        short2d = new Path(graph, Collections.singletonList(a2d));
        short2e = new Path(graph, Arrays.asList(a2d, d2e));

    }
    //Make a comparison between a valid path, for example short2d and the solution we want to test the validity of.
    private void testValidSolution(Path validPath, ShortestPathSolution solution) {
    	
        assertTrue(solution.getPath().isValid());
        assertEquals(validPath.getOrigin().getId(), solution.getPath().getOrigin().getId());
        assertEquals(validPath.getDestination().getId(), solution.getPath().getDestination().getId());
        assertEquals(AbstractSolution.Status.OPTIMAL, solution.getStatus());
        assertEquals(validPath.getLength(), solution.getPath().getLength(), 0);
        assertEquals(validPath.getMinimumTravelTime(), solution.getPath().getMinimumTravelTime(), 0);
        
    }
    
  //Make a comparison between a valid path going to itself, for example short2a and the solution we want to test the validity of.
    private void testValidSolutionSingleton(Path validPath, ShortestPathSolution solution) {
        assertTrue(solution.getPath().isValid());
        assertEquals(AbstractSolution.Status.OPTIMAL, solution.getStatus());
        assertEquals(validPath.getLength(), solution.getPath().getLength(), 0);
        assertEquals(validPath.getMinimumTravelTime(), solution.getPath().getMinimumTravelTime(), 0);
    }

  //Make a comparison between an abstract infeasible solution and the solution we want to test the invalidity of.
    private void testInvalidSolution(ShortestPathSolution solution) {
        assertEquals(AbstractSolution.Status.INFEASIBLE, solution.getStatus());
        assertNull(solution.getPath());
    }

  //The following functions run the algorithm we want to test, creating a solution, before testing its validity
    public ShortestPathSolution testValidAtoA(ShortestPathAlgorithm algo) {
        // a to a
        ShortestPathSolution solution = algo.run();
        testValidSolutionSingleton(short2a, solution);

        return solution;
    }

    public ShortestPathSolution testValidAtoB(ShortestPathAlgorithm algo) {
        // a to b
        ShortestPathSolution solution = algo.run();
        testValidSolution(short2b, solution);

        return solution;
    }

    public ShortestPathSolution testValidAtoC(ShortestPathAlgorithm algo) {
        // a to c
        ShortestPathSolution solution = algo.run();
        testValidSolution(short2c, solution);

        return solution;
    }

    public ShortestPathSolution testValidAtoD(ShortestPathAlgorithm algo) {
        // a to d
        ShortestPathSolution solution = algo.run();
        testValidSolution(short2d, solution);

        return solution;
    }

    public ShortestPathSolution testValidAtoE(ShortestPathAlgorithm algo) {
        // a to e
        ShortestPathSolution solution = algo.run();
        testValidSolution(short2e, solution);

        return solution;
    }

  //The following functions run the algorithm we want to test, creating a solution, before testing its invalidity
    public ShortestPathSolution testInvalidAtoB(ShortestPathAlgorithm algo) {
        // invalid a to b
        ShortestPathSolution solution = algo.run();
        testInvalidSolution(solution);

        return solution;
    }

    public ShortestPathSolution testInvalidAtoC(ShortestPathAlgorithm algo) {
        // invalid a to c
        ShortestPathSolution solution = algo.run();
        testInvalidSolution(solution);

        return solution;
    }

    public ShortestPathSolution testInvalidAtoD(ShortestPathAlgorithm algo) {
        // invalid a to d
        ShortestPathSolution solution = algo.run();
        testInvalidSolution(solution);

        return solution;
    }

    public ShortestPathSolution testInvalidAtoE(ShortestPathAlgorithm algo) {
        // invalid a to e
        ShortestPathSolution solution = algo.run();
        testInvalidSolution(solution);

        return solution;
    }
/*
    public ShortestPathSolution testINSAAirportTime(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_fr31_insa_aeroport_time.path"))))
                .readPath(hautegaronne);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }

    public ShortestPathSolution testINSAAirportLength(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_fr31_insa_aeroport_length.path"))))
                .readPath(hautegaronne);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }

    public ShortestPathSolution testINSABikiniCanal(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_fr31_insa_bikini_canal.path"))))
                .readPath(hautegaronne);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }

    public ShortestPathSolution testINSABikiniTimeCar(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_fr31_insa_bikini_time_car.path"))))
                .readPath(hautegaronne);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }

    public ShortestPathSolution testRangueilINSA(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_fr31insa_rangueil_insa.path"))))
                .readPath(insa);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }

    public ShortestPathSolution testRangueilR2(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_fr31insa_rangueil_r2.path"))))
                .readPath(insa);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }

    public ShortestPathSolution testPapeetePihau(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_pf_papeete_pihau_shortest.path"))))
                .readPath(frenchpolynesia);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }

    public ShortestPathSolution testPapeeteFare(ShortestPathAlgorithm algorithm) {
        ShortestPathSolution solution = algorithm.run();
        testInvalidSolution(solution);

        return solution;
    }

    public ShortestPathSolution testINSATonton(ShortestPathAlgorithm algorithm) throws IOException {
        Path path = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/../paths/path_fr31tls_insa_tonton.path"))))
                .readPath(toulouse);

        ShortestPathSolution solution = algorithm.run();
        testValidSolution(path, solution);

        return solution;
    }
    */
}