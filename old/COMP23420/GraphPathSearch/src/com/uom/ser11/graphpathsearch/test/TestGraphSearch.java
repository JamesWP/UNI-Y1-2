package com.uom.ser11.graphpathsearch.test;

import com.uom.ser11.graphpathsearch.Graph;
import com.uom.ser11.graphpathsearch.NoPathFoundException;
import com.uom.ser11.graphpathsearch.Node;
import com.uom.ser11.graphpathsearch.Path;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by james on 19/03/15.
 */
public class TestGraphSearch {
    @Test
    @SuppressWarnings("unused")
    public void testNewEmptyGraph(){
        Graph g = new Graph();
    }
    @Test
    public void testNewGraph(){
        Graph g = new Graph();
        Node node1 = new Node<Integer>(1);
        Node node2 = new Node<Integer>(2);
        node1.addAdjacentNode(node2,1);
        node2.addAdjacentNode(node1,1);
        g.add(node1);
        g.add(node2);
    }
    @Test
    public void testNewPath(){
        Node node1 = new Node<Integer>(1);
        Node node2 = new Node<Integer>(2);
        Path testPath = new Path();
        testPath.add(node1);
        testPath.add(node2);
    }
    @Test
    public void testGetShortestSimplePath(){
        Graph g = new Graph();
        Node node1 = new Node<Integer>(1);
        Node node2 = new Node<Integer>(2);
        node1.addAdjacentNode(node2,1);
        node2.addAdjacentNode(node1,1);
        g.add(node1);
        g.add(node2);
        try {
            Path shortestPath = g.calculateShortestPath(node1, node2);
            assertTrue(shortestPath!=null);
            assertTrue(shortestPath.size()==2);
            assertTrue(shortestPath.get(0)==node1);
            assertTrue(shortestPath.get(1)==node2);
        }catch(NoPathFoundException e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    /**
     *
     * 1 ---- 2 ---- 3 ---- 4
     * .      |      |      |
     * .   -  5 -    6    - 7
     *
     * 1 is start
     * 7 is end
     */
    @Test
    public void testGetShortestComplexPath(){
        Graph g = new Graph();
        Node node1 = new Node<Integer>(1);
        Node node2 = new Node<Integer>(2);
        Node node3 = new Node<Integer>(3);
        Node node4 = new Node<Integer>(4);
        Node node5 = new Node<Integer>(5);
        Node node6 = new Node<Integer>(6);
        Node node7 = new Node<Integer>(7);
        node1.addAdjacentNode(node2,4);
        node2.addAdjacentNode(node3,4);
        node3.addAdjacentNode(node4,4);
        node4.addAdjacentNode(node7,1);
        node1.addAdjacentNode(node5,3);
        node5.addAdjacentNode(node6,1);
        node6.addAdjacentNode(node7,1);
        g.add(node1);
        g.add(node2);
        g.add(node3);
        g.add(node4);
        g.add(node5);
        g.add(node6);
        g.add(node7);
        try {
            Path shortestPath = g.calculateShortestPath(node1, node7);
            assertTrue(shortestPath!=null);
        }catch(NoPathFoundException e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
