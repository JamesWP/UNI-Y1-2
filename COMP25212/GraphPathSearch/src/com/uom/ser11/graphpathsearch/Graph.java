package com.uom.ser11.graphpathsearch;

import java.util.*;

/**
 * Created by james on 19/03/15.
 */
public class Graph extends HashSet<Node> {
    public Graph(){
        super();
    }

    /**
     * calculateShortestPath
     *
     * standard implementation of the a* algorithm in java
     *
     * see (wikipedia)[http://en.wikipedia.org/wiki/A*_search_algorithm]
     * for more info
     *
     * */
    public Path calculateShortestPath(Node start, Node end) throws NoPathFoundException {
        Set<Node> closedSet = new HashSet<Node>();
        Set<Node> openSet = new HashSet<Node>();
        Map<Node,Node> cameFrom = new HashMap<Node,Node>();
        Map<Node,Float> costToNode = new HashMap<Node,Float>();
        Map<Node,Float> estimatedCostToEnd = new HashMap<Node,Float>();
        openSet.add(start);
        costToNode.put(start,0.0f);
        estimatedCostToEnd.put(start,costToNode.get(start) + calculateHeuristic(start,end));

        while(!openSet.isEmpty()){
            Node current = openSet.iterator().next();
            if(current==end){
                return reconstructPath(cameFrom, current);
            }else{
                openSet.remove(current);
                closedSet.add(current);
                Set<Node> neighbors = current.getAdjacentNodes();
                for(Node neighbor : neighbors){
                    if(closedSet.contains(neighbor)) continue;
                    Float estimateOfNewPossibleBest = costToNode.get(current) + current.getEdgeWeight(neighbor);
                    if(!openSet.contains(neighbor)
                        || (costToNode.containsKey(neighbor)
                            && estimateOfNewPossibleBest < costToNode.get(neighbor))){
                        cameFrom.put(neighbor,current);
                        costToNode.put(neighbor,estimateOfNewPossibleBest);
                        estimatedCostToEnd.put(neighbor,costToNode.get(neighbor) + calculateHeuristic(neighbor,end));
                        if(!openSet.contains(neighbor))openSet.add(neighbor);
                    }
                }
            }
        }

        throw new NoPathFoundException(start,end);
    }

    private Path reconstructPath(Map<Node, Node> cameFrom, Node current) {
        Path path = new Path();
        path.add(current);
        while (cameFrom.containsKey(current)){
            current = cameFrom.get(current);
            path.addStep(current);
        }
        return path;
    }

    private Float calculateHeuristic(Node curent, Node next) {
        return curent.estimateDistanceTo(next);
    }
}
