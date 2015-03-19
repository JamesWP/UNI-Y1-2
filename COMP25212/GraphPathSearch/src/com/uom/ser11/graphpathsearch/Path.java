package com.uom.ser11.graphpathsearch;


import java.util.ArrayList;

/**
 * Created by james on 19/03/15.
 */
public class Path extends ArrayList<Node> {
    public Path(){
        super();
    }

    public void addStep(Node nextStep){
        this.add(0,nextStep);
    }
}
