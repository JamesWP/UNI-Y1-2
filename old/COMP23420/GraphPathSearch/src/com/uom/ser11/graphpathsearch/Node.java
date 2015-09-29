package com.uom.ser11.graphpathsearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by james on 19/03/15.
 */
public class Node<T> {

    private final T value;
    public Node(T value){
        this.value = value;
    }

    public Map<Node<T>,Float> adjacencyMap = new HashMap<Node<T>,Float>();
    public Set<Node<T>> adjacencySet = new HashSet<Node<T>>();
    public void addAdjacentNode(Node<T> other, float i) {
        adjacencyMap.put(other,i);
        adjacencySet.add(other);
    }

    public Set<Node<T>> getAdjacentNodes() {
        return adjacencySet;
    }

    public Float getEdgeWeight(Node neighbor) {
        assert(adjacencySet.contains(neighbor));
        return adjacencyMap.get(neighbor);
    }

    @Override
    public String toString() {
        return "Node("+ value +")";
    }

    public Float estimateDistanceTo(Node<T> next) {
        //TODO: find disance estimate
        return 10.0f;
    }
}
