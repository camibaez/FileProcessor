/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import processor.core.graph.actions.TypeTranslator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.openide.util.Exceptions;
import processor.core.graph.DecisionEdge;
import processor.core.graph.EndNode;
import processor.core.graph.StartNode;
import processor.core.graph.conditions.Condition;
import processor.core.lineal.ComplexNode;

/**
 *
 * @author cbaez
 */
public class FileProcessor {

    protected int processedCount;
    protected Profile project;

    public FileProcessor(Profile project) {
        this.project = project;

    }

    public void processFile(File f) {
        ProcessingResult processingResult = new ProcessingResult();
        DefaultDirectedGraph<ComplexNode, DecisionEdge> graph = project.getGraph();
        ComplexNode start = graph.vertexSet().stream().filter(n -> n instanceof StartNode).findFirst().get();
        Optional<DecisionEdge> findFirst = graph.outgoingEdgesOf(start).stream().filter(e -> e.getSign()).findFirst();
        if (!findFirst.isPresent()) {
            return;
        }
        DecisionEdge trueEdge = findFirst.get();
        ComplexNode node = graph.getEdgeTarget(trueEdge);

        while (!(node instanceof EndNode)){
            if (node.getCondition() != null) {
                Condition condition = node.getCondition();
                try {
                    Object translation = TypeTranslator.translateFor(condition, f);
                    boolean res = condition.test(translation);
                    DecisionEdge nodeLink = graph.outgoingEdgesOf(node).stream().filter(e -> e.getSign() ==  res).findFirst().get();
                    node = graph.getEdgeTarget(nodeLink);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
                

            }
        }

    }

    /*
    public boolean isAssigned(ActionCluster cleaner, Path file) {
        return cleaner.getPrototype() == null || project.getFileCentral().belongsTo(cleaner.getPrototype(), file);
    }

    public boolean isAffectig(ActionCluster cleaner, Path file) throws IOException{
        String original = new String(Files.readAllBytes(file));
        String processed = TypeTransformer.transformForType(String.class, cleaner.process(original));
        
        return !original.equals(processed);
    }
    
    public List<ActionCluster> getAssignedCleaners(Path file) {
        List<ActionCluster> list = new LinkedList<>();
        for (ActionCluster cleaner : cleaners) {
            if (isAssigned(cleaner, file)) {
                list.add(cleaner);
            }
        }
        return list;
    }
    
    public List<ActionCluster> getAffectingCleaners(Path file){
        List<ActionCluster> list = new LinkedList<>();
        for (ActionCluster cleaner : cleaners) {
            try {
                if (isAffectig(cleaner, file)) {
                    list.add(cleaner);
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return list;
    }

    public String processFile(Path file) throws IOException {
        Object result = null;
        for (ActionCluster cleaner : cleaners) {
            if(!isAssigned(cleaner, file))
                continue;
            if (result == null) {
                result = new String(Files.readAllBytes(file));
            }
            result = cleaner.process(result);
        }
        if (result == null) {
            return "";
        }
        return TypeTransformer.transformForType(String.class, result);
    }

    public void saveFile(String result, Path p) throws FileNotFoundException, IOException {
        File f = p.toFile();
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)
        )) {
            writer.write(result);
            project.getFileCentral().getProcessedFiles().add(p);
        }
    }

    public void processAll() {
        for (Path f : project.getFileCentral().getMatchedFiles()) {
            try {
                String result = processFile(f);

                saveFile(result, f);

            } catch (IOException ex) {
                Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<ActionCluster> getCleaners() {
        return cleaners;
    }

    public void setCleaners(List<ActionCluster> cleaners) {
        this.cleaners = cleaners;
    }

    public int getProcessed() {
        return processedCount;
    }
     */
}
