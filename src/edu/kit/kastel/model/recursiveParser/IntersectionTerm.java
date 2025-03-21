package edu.kit.kastel.model.recursiveParser;
import edu.kit.kastel.model.Product;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents an INTERSECTION operation in the recommendation grammar.
 */
public class IntersectionTerm implements Term {
    private final Term leftTerm;
    private final Term rightTerm;

    /**
     * Creates a new intersection term.
     *
     * @param leftTerm  the left term of the intersection
     * @param rightTerm the right term of the intersection
     */
    public IntersectionTerm(Term leftTerm, Term rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

    @Override
    public List<Product> evaluate() {
        List<Product> leftResults = leftTerm.evaluate();
        List<Product> rightResults = rightTerm.evaluate();

        List<Product> results = new ArrayList<>();

        for (Product product : rightResults) {
            if (leftResults.contains(product)) {
                results.add(product);
            }
        }

        results.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));

        return results;
    }

    @Override
    public String toString() {
        return "INTERSECTION(" + leftTerm + ", " + rightTerm + ")";
    }
}