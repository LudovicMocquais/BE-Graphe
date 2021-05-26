package org.insa.graphs.model;

public class Label implements Comparable<Label>{
	public Node currentPeak;
	
	private boolean marked;
	
	private double cost;
	
	private Arc father;
	
	public double getCost() {
		return this.cost;
	}
	
	public Label (Node currentPeak) {
        this.currentPeak = currentPeak;
        this.marked = false;
        this.cost = Float.POSITIVE_INFINITY;
        this.father = null;
    }
	
	public Label(Node currentPeak, Arc father,double cost) {
		this.cost = cost;
		this.currentPeak = currentPeak;
		this.father = father;
		this.marked = marked;
	}
	
	public void setCost(double newCost) {
		this.cost = newCost;
	}
	
	public void setFather(Arc newFather) {
		this.father = newFather;
	}
	
	public Arc getFather() {
		return this.father;
	}
	
	public void setMarked() {
		this.marked = true;
	}
	
	public boolean isMarked() {
		return this.marked;
	}
	
	public double getCoutTotal() {
		return this.cost;
	}
	
	public void setEstimatedCost(Node destination, int estimatedSpeed) {	}
	
	public int compareTo(Label other) {
        return Double.compare(this.getCoutTotal(), other.getCoutTotal());
    }
}