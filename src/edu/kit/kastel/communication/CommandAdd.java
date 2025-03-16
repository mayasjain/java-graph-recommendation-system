/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.kastel.communication;

import edu.kit.kastel.model.*;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the {@code quit} command to terminate the application as specified.
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

        System.out.println("IN ADD COMMAND");
        System.out.println(command);
        System.out.println(Arrays.toString(arguments));



        String subject = arguments[0];
        String predicate = arguments[1];
        String object = arguments[2];

        Node sourceNode = createNode(subject);
        Node targetNode = createNode(object);

        RelationType relationType = RelationType.valueOf(predicate.toUpperCase().replace("-", "_"));

        Edge edge = new Edge(sourceNode, targetNode, relationType);

        //TODO: check whether edge is allowed based on relationship type
        System.out.println(edge);
        this.graph.addEdge(edge);
        return true;
    }

    private Node createNode(String arg) {
        // Create a matcher with our input
        Matcher productMatcher = PRODUCT_PATTERN.matcher(arg);
        Matcher categoryMatcher = CATEGORY_PATTERN.matcher(arg);

        // Check if the pattern matches
        if (productMatcher.matches()) {
            // Extract the captured groups
            String name = productMatcher.group(1);
            String idString = productMatcher.group(2);
            int id = Integer.parseInt(idString);

            System.out.println("Name: " + name);
            System.out.println("ID: " + id);

            Product product = new Product(name, id);
            return product;
        }

        if (categoryMatcher.matches()) {
            Category category = new Category(arg);
            return category;
        }

        return null;
    }
}
