package org.insa.graphs.model;

public class LabelStar extends Label {
	private double estimatedCost;
	
	public LabelStar(Node currentPeak, Arc father,double cost) {
		super(currentPeak, father,cost);
		this.estimatedCost = 0;
	}

	
	public LabelStar(Node currentPeak) {
		super(currentPeak);
		this.estimatedCost = 0;
	}
	
	public void setEstimatedCost(Node destination, int maxSpeed) {
		
		if(maxSpeed != 0) {this.estimatedCost = destination.getPoint().distanceTo(currentPeak.getPoint())/(double)maxSpeed;}
		else {this.estimatedCost = destination.getPoint().distanceTo(currentPeak.getPoint());}
		
	}
	
	public double getCoutTotal() {
		return this.estimatedCost + this.getCost();
	}
}