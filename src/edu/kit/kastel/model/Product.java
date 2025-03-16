package edu.kit.kastel.model;

public class Product extends Node {
    private final int id;

    public Product(String name, int productId) {
        super(name);
        this.id = productId;
    }

    public int getId() {
        return id;
    }

    @Override
    public  boolean isProduct() {
        return true;
    }

    @Override
    public String toString() {
        return name + ":" + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null || getClass() != obj.getClass())) {
            return false;
        }
        Product other = (Product) obj;
        return name.equalsIgnoreCase(other.name) && id == other.getId();
    }
}
