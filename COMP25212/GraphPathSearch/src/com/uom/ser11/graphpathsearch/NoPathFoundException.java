package com.uom.ser11.graphpathsearch;

/**
 * Created by james on 19/03/15.
 */
public class NoPathFoundException extends Exception {
    public NoPathFoundException(Node start, Node end) {
        super("There was no path found between: " + start + "and " + end);
    }
}
