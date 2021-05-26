package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	protected final ShortestPathData data;

    protected BinaryHeap<Label> heap;
    protected Label[] labels;
    protected List<Node> nodes;

    protected Node origin;
    protected Node destination;

    protected Label label_origin;
    protected Label label_destination;
		
    public DijkstraAlgorithm(ShortestPathData data) {
    	super(data);
    	this.data = data;
        this.labels = new Label[data.getGraph().size()];
        this.nodes = data.getGraph().getNodes();

        this.heap = new BinaryHeap<Label>();
        this.origin = this.data.getOrigin();
        this.destination = this.data.getDestination();
    }
    //Initialization function useful for A* (All peak written as false, infinite cost, no father)
	
    protected void SetLabels(ShortestPathData data) {
    	

        this.label_origin = new Label(origin, null, 0);

        if (this.origin.getId() != this.destination.getId())
            this.label_destination = new Label(destination);
        else
            this.label_destination = this.label_origin;

        for (Node node : this.nodes) {
            if (node.getId() == this.origin.getId()) {
                this.labels[node.getId()] = this.label_origin;
            } else if (node.getId() == this.destination.getId()) {
                this.labels[node.getId()] = this.label_destination;
            } else {
                this.labels[node.getId()] = new Label(node);
            }
        }

    }

    @Override
    protected ShortestPathSolution doRun() {
    
    	//Initialization of data and heap
        SetLabels(this.data);
        ShortestPathSolution solution;
        this.heap.insert(this.label_origin);
        
        while(!label_destination.isMarked() && !heap.isEmpty()) { 
        	
        	//Extract minHeap from heap and marked it
        	Label minHeap = this.heap.deleteMin();
        	minHeap.setMarked();
        	notifyNodeMarked(minHeap.currentPeak);
        	
        	if(minHeap.currentPeak.getId() == data.getDestination().getId()) { 
        		break; 
        	}
        	
        	

        	// Update all successors of minHeap
            for (Arc successor : minHeap.currentPeak.getSuccessors()) {
                Label label = this.labels[successor.getDestination().getId()];
                if (!label.isMarked() && this.data.isAllowed(successor) && label.getCost() > (minHeap.getCost() + this.data.getCost(successor))) {
                    if (label.getFather() == null) {
                        if (label.currentPeak.getId() == this.destination.getId()) {
                            notifyDestinationReached(this.destination);
                        } else {
                            notifyNodeReached(label.currentPeak);
                        }
                    } else {
                        this.heap.remove(label);
                    }
                 // New cost
                    double cost = minHeap.getCost() + this.data.getCost(successor);
                    label.setFather(successor);
                    label.setCost(cost);
                    
                    this.heap.insert(label);
                }
            }
        }
        
     // Verify if path is feasible and create solution
        if (label_destination.isMarked()) {
            ArrayList<Arc> arcs = new ArrayList<>();
            Label label = label_destination;
            while (label.getFather() != null) {
                arcs.add(0, label.getFather());
                label = labels[label.getFather().getOrigin().getId()];
            }

            Path path = new Path(data.getGraph(), arcs);
            solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, path);
        } else {
            solution = new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        }
        return solution;
    }

}