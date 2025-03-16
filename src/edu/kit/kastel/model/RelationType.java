package edu.kit.kastel.model;

public enum RelationType {
    CONTAINS("contained-in"),
    CONTAINED_IN("contains"),
    PART_OF("has-part"),
    HAS_PART("part-of"),
    SUCCESSOR_OF("predecessor-of"),
    PREDECESSOR_OF("successor-of");

    private final String inverseRelation;

    RelationType(String inverseRelation) {
        this.inverseRelation = inverseRelation;
    }

    public RelationType getInverse() {
        return RelationType.valueOf(inverseRelation.toUpperCase().replace("-", "_"));
    }

    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_", "-");
    }
}
