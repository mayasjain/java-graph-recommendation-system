package edu.kit.kastel.model;

public class Category extends Node {
    public Category(String name) {
        super(name);
    }

    @Override
    public  boolean isProduct() {
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
