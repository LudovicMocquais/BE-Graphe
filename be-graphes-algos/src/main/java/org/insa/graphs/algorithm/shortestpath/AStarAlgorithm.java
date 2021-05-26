package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.*;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        this.labels = new LabelStar[data.getGraph().size()];
    }
   
    @Override
    protected void SetLabels(ShortestPathData data) {
    	int maxSpeed;
    	if(data.getMode()==Mode.LENGTH) {
    		System.out.println("Length Mode");
    		maxSpeed = 0;
    	} else {
    		System.out.println("Time Mode");
    		maxSpeed = data.getGraph().getGraphInformation().getMaximumSpeed();
    	}

		this.label_origin = new LabelStar(origin,null,0);

        if (this.origin.getId() != this.destination.getId())
            this.label_destination = new LabelStar(destination);
        else
            this.label_destination = this.label_origin;

        for (Node node : this.nodes) {
            if (node.getId() == this.origin.getId()) {
                this.labels[node.getId()] = this.label_origin;
            } else if (node.getId() == this.destination.getId()) {
                this.labels[node.getId()] = this.label_destination;
            } else {
                this.labels[node.getId()] = new LabelStar(node);
            }

            this.labels[node.getId()].setEstimatedCost(data.getDestination(), maxSpeed);
        }
    }
}