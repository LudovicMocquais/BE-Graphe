package org.insa.graphs.model;

public class LabelStar extends Label {
	private double estimatedCost;
	
	public LabelStar(Node currentPeak, Arc father,double cost,boolean marked,Node destination) {
		super(currentPeak, father,cost,marked);
		this.estimatedCost = destination.getPoint().distanceTo(currentPeak.getPoint());
	}
	
	public LabelStar(Node currentPeak, Arc father,double cost,boolean marked, Node destination, int estimatedSpeed) {
		super(currentPeak, father,cost ,marked);
		this.estimatedCost = destination.getPoint().distanceTo(currentPeak.getPoint())/(double)estimatedSpeed;
	}
	
	public double getCoutTotal() {
		return this.estimatedCost + this.getCost();
	}
}