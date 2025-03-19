/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.kastel.communication;
import edu.kit.kastel.model.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the {@code add} command to add node to graph.
 * 
 * @author Programmieren-Team
 */
public class CommandAdd extends Command {
    private static final int EXPECTED_ARGUMENTS = 3;
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("[a-zA-Z0-9]+");

    private static final Pattern PRODUCT_PATTERN = Pattern.compile("([a-zA-Z0-9]+)\\(id=([0-9]+)\\)");

    private Graph graph;

    /**
     * Creates a new instance.
     */
    public CommandAdd(Graph graph) {
        super(EXPECTED_ARGUMENTS);
        this.graph = graph;
    }


    @Override
    public boolean execute(String command, String[] arguments) {

        //print error statement maybe
        if (arguments.length != EXPECTED_ARGUMENTS) {
            return false;
        }

        String subject = arguments[0].trim();
        String predicate = arguments[1].trim();
        String object = arguments[2].trim();


        RelationType relationType = getRelationType(predicate);
        if (relationType == null) {
            return false;
        }


        Node sourceNode = createOrGetNode(subject);
        Node targetNode = createOrGetNode(object);


        if (sourceNode == null || targetNode == null) {
            return false;
        }


        if (!isValidRelationship(sourceNode, targetNode, relationType)) {
            return false;
        }

        if (sourceNode.equals(targetNode)) {
            return false;
        }


        Edge edge = new Edge(sourceNode, targetNode, relationType);
        this.graph.addEdge(edge);
        return true;
    }



    private RelationType getRelationType(String predicate) {
        String enumName = predicate.toUpperCase().replace("-", "_");
        for (RelationType type : RelationType.values()) {
            if (type.name().equals(enumName)) {
                return type;
            }
        }
        return null;
    }

    private Node createOrGetNode(String nodeString) {
        // Check if this is a product
        Matcher productMatcher = PRODUCT_PATTERN.matcher(nodeString);
        Matcher catagoryMatcher = CATEGORY_PATTERN.matcher(nodeString);


        if (productMatcher.matches()) {
            String name = productMatcher.group(1);
            int id;

            // Parse the ID safely
            try {
                id = Integer.parseInt(productMatcher.group(2));
            } catch (NumberFormatException e) {
                return null;
            }

            // Check if this product already exists
            Product existingProduct = graph.getProductByNameAndId(name, id);
            if (existingProduct != null) {
                return existingProduct;
            }

            if (isValidProductParameters(name, id)) {
                return new Product(name, id);
            }
            return null;
        }

        if (catagoryMatcher.matches()) {
            Node existingCategory = graph.getNodeByName(nodeString);
            if (existingCategory != null && !existingCategory.isProduct()) {
                return existingCategory;
            }

            if (isValidCategoryName(nodeString)) {
                return new Category(nodeString);
            }
        }

        return null;
    }


    private boolean isValidProductParameters(String name, int id) {
        return name != null && name.matches("[a-zA-Z0-9]+") && id >= 0;
    }

    private boolean isValidCategoryName(String name) {
        return name != null && name.matches("[a-zA-Z0-9]+");
    }

    private boolean isValidRelationship(Node source, Node target, RelationType relationType) {
        // Check relationship rules according to specification
        boolean sourceIsProduct = source.isProduct();
        boolean targetIsProduct = target.isProduct();

        switch (relationType) {
            case CONTAINS:
                // "contains" - source must be a category, target can be product or category
                return !sourceIsProduct;

            case CONTAINED_IN:
                // "contained-in" - target must be a category, source can be product or category
                return !targetIsProduct;

            case PART_OF:
                // "part-of" - both source and target must be products
                return sourceIsProduct && targetIsProduct;

            case HAS_PART:
                // "has-part" - both source and target must be products
                return sourceIsProduct && targetIsProduct;

            case SUCCESSOR_OF:
                // "successor-of" - both source and target must be products
                return sourceIsProduct && targetIsProduct;

            case PREDECESSOR_OF:
                // "predecessor-of" - both source and target must be products
                return sourceIsProduct && targetIsProduct;

            default:
                return false;
        }
    }
}





