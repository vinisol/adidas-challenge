package com.app.planitinerary.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class City implements Comparable<City> {

    private String name;

    private List<Edge> adjacenciesList;

    private boolean visited;

    private City previous;

    private long distance = Long.MAX_VALUE;

    public City(String name) {
        this.name = name;
        this.adjacenciesList = new ArrayList<>();
    }

    public void addNeighbour(Edge edge) {
        adjacenciesList.add(edge);
    }

    public void computeShortestPaths() {
        setDistance(0);
        PriorityQueue<City> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(this);
        setVisited(true);

        while (!priorityQueue.isEmpty()) {
            City actualVertex = priorityQueue.poll();
            for (Edge edge : actualVertex.getAdjacenciesList()) {
                City v = edge.getDestination();
                if (!v.isVisited()) {
                    long newDistance = actualVertex.getDistance() + edge.getWeight();
                    if (newDistance < v.getDistance()) {
                        priorityQueue.remove(v);
                        v.setDistance(newDistance);
                        v.setPrevious(actualVertex);
                        priorityQueue.add(v);
                    }
                }
            }
            actualVertex.setVisited(true);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        City other = (City) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(City otherVertex) {
        return Double.compare(this.distance, otherVertex.getDistance());
    }
}
