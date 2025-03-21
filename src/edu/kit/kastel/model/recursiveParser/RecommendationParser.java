package edu.kit.kastel.model.recursiveParser;

import edu.kit.kastel.model.Graph;
import edu.kit.kastel.model.Strategy;

/**
 * Parser for recommendation commands.
 */
public class RecommendationParser {
    private final Graph graph;
    private String input;
    private int position;

    /**
     * Creates a new recommendation parser.
     *
     * @param graph the product graph
     */
    public RecommendationParser(Graph graph) {
        this.graph = graph;
    }

    /**
     * Parses a recommendation command.
     *
     * @param command the command string
     * @return the parsed term
     * @throws IllegalArgumentException if the command is invalid
     */
    public Term parse(String command) {
        if (command == null || !command.trim().startsWith("recommend")) {
            throw new IllegalArgumentException("Error: Invalid recommendation command");
        }

        // Extract the term part after "recommend"
        String termString = command.trim().substring("recommend".length()).trim();
        this.input = termString;
        this.position = 0;

        return parseTerm();
    }

    /**
     * Parses a term according to the grammar.
     * term ::= final | INTERSECTION(term, term) | UNION(term, term)
     */
    private Term parseTerm() {
        skipWhitespace();

        // Check for INTERSECTION
        if (remainingInput().startsWith("INTERSECTION(")) {
            position += "INTERSECTION(".length();
            Term leftTerm = parseTerm();
            skipWhitespace();

            // Ensure we have a comma
            if (position < input.length() && input.charAt(position) == ',') {
                position++; // Skip comma
                Term rightTerm = parseTerm();
                skipWhitespace();

                // Ensure closing parenthesis
                if (position < input.length() && input.charAt(position) == ')') {
                    position++; // Skip closing parenthesis
                    return new IntersectionTerm(leftTerm, rightTerm);
                }
            }
            throw new IllegalArgumentException("Error: Invalid INTERSECTION syntax");
        }

        // Check for UNION
        if (remainingInput().startsWith("UNION(")) {
            position += "UNION(".length();
            Term leftTerm = parseTerm();
            skipWhitespace();

            // Ensure we have a comma
            if (position < input.length() && input.charAt(position) == ',') {
                position++;
                Term rightTerm = parseTerm();
                skipWhitespace();

                // Ensure closing parenthesis
                if (position < input.length() && input.charAt(position) == ')') {
                    position++; // Skip closing parenthesis
                    return new UnionTerm(leftTerm, rightTerm);
                }
            }
            throw new IllegalArgumentException("Error: Invalid UNION syntax");
        }

        // If not a composite term, it must be a final term
        return parseFinalTerm();
    }

    /**
     * Parses a final term according to the grammar.
     * final ::= strategy productid
     */
    private Term parseFinalTerm() {
        skipWhitespace();

        // Parse strategy
        Strategy strategy;
        if (remainingInput().startsWith("S1")) {
            strategy = Strategy.S1;
            position += 2;
        } else if (remainingInput().startsWith("S2")) {
            strategy = Strategy.S2;
            position += 2;
        } else if (remainingInput().startsWith("S3")) {
            strategy = Strategy.S3;
            position += 2;
        } else {
            throw new IllegalArgumentException("Error: Expected strategy (S1, S2, or S3)");
        }

        skipWhitespace();

        // Parse product ID
        StringBuilder productIdStr = new StringBuilder();
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            productIdStr.append(input.charAt(position));
            position++;
        }

        if (productIdStr.length() == 0) {
            throw new IllegalArgumentException("Error: Expected product ID after strategy");
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Invalid product ID");
        }

        return new FinalTerm(strategy, productId, graph);
    }

    /**
     * Returns the remaining input from the current position.
     */
    private String remainingInput() {
        return input.substring(position);
    }

    /**
     * Skips whitespace characters.
     */
    private void skipWhitespace() {
        while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }
}