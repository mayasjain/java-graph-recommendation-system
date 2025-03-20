package edu.kit.kastel.command;
import edu.kit.kastel.model.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles validation of user input for relationship commands.
 *
 * @author Programmieren-Team
 */
public class ValidateUserInput {
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("[a-zA-Z0-9]+");
    private static final Pattern PRODUCT_PATTERN = Pattern.compile("([a-zA-Z0-9]+)\\s*\\(\\s*id\\s*=\\s*([0-9]+)\\s*\\)");

    private final Graph graph;

    /**
     * Creates a new instance.
     *
     * @param graph the graph to validate against
     */
    public ValidateUserInput(Graph graph) {
        this.graph = graph;
    }

    /**
     * Validates and processes a relationship specification.
     *
     * @param subject The subject string
     * @param predicate The predicate string
     * @param object The object string
     * @return An Edge if valid, null otherwise
     */
    public Edge validateRelationship(String subject, String predicate, String object) {
        RelationType relationType = getRelationType(predicate);
        if (relationType == null) {
            return null;
        }

        Node sourceNode = createOrGetNode(subject);
        Node targetNode = createOrGetNode(object);

        if (sourceNode == null || targetNode == null) {
            return null;
        }

        if (!isValidRelationship(sourceNode, targetNode, relationType)) {
            return null;
        }

        if (sourceNode.equals(targetNode)) {
            return null;
        }

        return new Edge(sourceNode, targetNode, relationType);
    }

    /**
     * Converts a predicate string to the corresponding RelationType.
     *
     * @param predicate the predicate string
     * @return the corresponding RelationType or null if invalid
     */
    public RelationType getRelationType(String predicate) {
        String enumName = predicate.toUpperCase().replace("-", "_");
        for (RelationType type : RelationType.values()) {
            if (type.name().equals(enumName)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Creates a new node or returns an existing node from the graph.
     *
     * @param nodeString the string representation of the node
     * @return the created or retrieved node, or null if invalid
     */
    public Node createOrGetNode(String nodeString) {
        Matcher productMatcher = PRODUCT_PATTERN.matcher(nodeString);
        Matcher categoryMatcher = CATEGORY_PATTERN.matcher(nodeString);

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

        if (categoryMatcher.matches()) {
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

    /**
     * Validates product parameters.
     *
     * @param name the product name
     * @param id the product id
     * @return true if parameters are valid, false otherwise
     */
    private boolean isValidProductParameters(String name, int id) {
        return name != null && name.matches("[a-zA-Z0-9]+") && id >= 0;
    }

    /**
     * Validates category name.
     *
     * @param name the category name
     * @return true if name is valid, false otherwise
     */
    private boolean isValidCategoryName(String name) {
        return name != null && name.matches("[a-zA-Z0-9]+");
    }

    /**
     * Checks if the relationship between nodes is valid.
     *
     * @param source the source node
     * @param target the target node
     * @param relationType the relationship type
     * @return true if the relationship is valid, false otherwise
     */
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
            case HAS_PART:
            case SUCCESSOR_OF:
            case PREDECESSOR_OF:
                // These relationships require both nodes to be products
                return sourceIsProduct && targetIsProduct;

            default:
                return false;
        }
    }
}