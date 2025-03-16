package edu.kit.kastel.model;

import java.util.Objects;

public abstract class Node {
    private static final String NAME_PATTERN = "[a-zA-Z0-9]+";
    protected String name;


    public Node(String name) {
        validateName(name);
        this.name = name.toLowerCase();
    }

    public abstract boolean isProduct();

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null || getClass() != obj.getClass())) {
            return false;
        }
        Node other = (Node) obj;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }


    protected static void validateName(String name) {
        if (name == null || !name.matches(NAME_PATTERN)) {
            throw new IllegalArgumentException("Error, Node name must match pattern " + NAME_PATTERN);
        }
    }
}

