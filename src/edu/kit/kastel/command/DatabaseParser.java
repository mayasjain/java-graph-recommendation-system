package edu.kit.kastel.command;

import edu.kit.kastel.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseParser {
    private static final Pattern LINE_PATTERN = Pattern.compile(
            "\\s*(.+?)\\s+(contains|contained-in|part-of|has-part|successor-of|predecessor-of)\\s+(.+?)\\s*");

    private final Graph graph;
    private final ValidateUserInput validator;

    public DatabaseParser(Graph graph) {
        this.graph = graph;
        this.validator = new ValidateUserInput(graph);
    }

    /**
     * Parses a database file and adds valid relationships to the graph.
     *
     * @param filePath the path to the database file
     * @return the lines read from the file
     * @throws IOException if there's an error reading the file
     */
    public List<String> parseFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (!line.trim().isEmpty()) {
                    parseLine(line);
                }
            }
        }

        return lines;
    }

    /**
     * Parses a single line and adds a valid relationship to the graph.
     *
     * @param line the line to parse
     * @return true if the line was parsed successfully, false otherwise
     */
    private boolean parseLine(String line) {
        Matcher lineMatcher = LINE_PATTERN.matcher(line);
        if (!lineMatcher.matches()) {
            return false;
        }

        String subject = lineMatcher.group(1).trim();
        String predicate = lineMatcher.group(2).trim();
        String object = lineMatcher.group(3).trim();

        Edge edge = validator.validateRelationship(subject, predicate, object);

        if (edge == null) {
            return false;
        }

        graph.addEdge(edge);
        return true;
    }
}