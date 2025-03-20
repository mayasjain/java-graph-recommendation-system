package edu.kit.kastel.communication;

import edu.kit.kastel.model.*;
import java.io.IOException;
import java.util.List;

/**
 * Represents the {@code load database} command to load a database file.
 */
public class CommandLoadDatabase extends Command {
    private static final int EXPECTED_ARGUMENTS = 1;

    private final Graph graph;
    private final DatabaseParser parser;

    /**
     * Creates a new instance.
     *
     * @param graph the graph to load data into
     */
    public CommandLoadDatabase(Graph graph) {
        super(EXPECTED_ARGUMENTS);
        this.graph = graph;
        this.parser = new DatabaseParser(graph);
    }

    @Override
    public boolean execute(String command, String[] arguments) {
        if (arguments.length != EXPECTED_ARGUMENTS) {
            return false;
        }

        String filePath = arguments[0].trim();

        try {
            List<String> lines = parser.parseFile(filePath);
            for (String line : lines) {
                System.out.println(line);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}