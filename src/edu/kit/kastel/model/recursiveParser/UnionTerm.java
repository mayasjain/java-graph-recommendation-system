package edu.kit.kastel.model.recursiveParser;

import edu.kit.kastel.model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a UNION operation in the recommendation grammar.
 */
public class UnionTerm implements Term {
    private final Term leftTerm;
    private final Term rightTerm;

    /**
     * Creates a new union term.
     *
     * @param leftTerm  the left term of the union
     * @param rightTerm the right term of the union
     */
    public UnionTerm(Term leftTerm, Term rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

    @Override
    public List<Product> evaluate() {
        List<Product> leftResults = leftTerm.evaluate();
        List<Product> rightResults = rightTerm.evaluate();

        // Create a set to ensure uniqueness in the union
        Set<Product> resultSet = new HashSet<>(leftResults);
        resultSet.addAll(rightResults);

        // Convert back to a sorted list
        List<Product> results = new ArrayList<>(resultSet);
        results.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));

        return results;
    }

    @Override
    public String toString() {
        return "UNION(" + leftTerm + ", " + rightTerm + ")";
    }
}