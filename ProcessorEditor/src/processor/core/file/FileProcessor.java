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
import processor.core.graph.FailNode;
import processor.core.graph.StartNode;
import processor.core.graph.actions.Action;
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

    public ProcessingResult processFile(File f) {
        ProcessingResult processingResult = new ProcessingResult();
        DefaultDirectedGraph<ComplexNode, DecisionEdge> graph = project.getGraph();
        ComplexNode start = graph.vertexSet().stream().filter(n -> n instanceof StartNode).findFirst().get();
        Optional<DecisionEdge> findFirst = graph.outgoingEdgesOf(start).stream().filter(e -> e.getSign()).findFirst();
        if (!findFirst.isPresent()) {
            return processingResult;
        }
        DecisionEdge trueEdge = findFirst.get();
        ComplexNode node = graph.getEdgeTarget(trueEdge);
        
        Object content = f;
        while (!((node instanceof EndNode) || (node instanceof FailNode))){            
            boolean condRes = true;
            boolean actionRes = true;
            if (node.getCondition() != null) {
                Condition condition = node.getCondition();
                try {
                    Object translation = TypeTranslator.translateFor(condition, content);
                    condRes = condition.test(translation);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    condRes = false;
                }
            }
            if(node.getAction() != null && condRes){
                Action action  = node.getAction();
                try {
                    Object original = TypeTranslator.translateFor(action, content);
                    content = action.process(original);
                    if(!original.equals(content)){
                        processingResult.actions.add(action);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    actionRes = false;
                }
            }
            
            boolean finalRes = condRes && actionRes;
            DecisionEdge directionEdge = null;
            if(graph.outgoingEdgesOf(node).size() > 1){
                directionEdge = graph.outgoingEdgesOf(node).stream().filter(e -> e.getSign() == finalRes).findFirst().get();
            }else{
                directionEdge = (DecisionEdge) graph.outgoingEdgesOf(node).toArray()[0];
            }
            node = graph.getEdgeTarget(directionEdge);
            
        }

        if(node instanceof FailNode){
             processingResult.setPassed(false);
        }else{
            processingResult.setPassed(true);
        }
           
        return processingResult;
        
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
