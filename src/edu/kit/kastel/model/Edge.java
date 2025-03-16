package edu.kit.kastel.model;

public class Edge {
    private final Node source;
    private final Node target;
    private final RelationType relation;

    public Edge(Node source, Node target, RelationType relation) {
        if (source.equals(target)) {
            throw new IllegalArgumentException("Error, A node cannot relate to itself.");
        }
        this.source = source;
        this.target = target;
        this.relation = relation;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public RelationType getRelation() {
        return relation;
    }

    public boolean isConnectingFrom(Node source, RelationType relation) {
        return this.source.equals(source) && this.relation == relation;
    }

    @Override
    public String toString() {
        return source + " -[" + relation.name().toLowerCase() + "]-> " + target;
    }
}
