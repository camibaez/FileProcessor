/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openide.util.Exceptions;

import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.actions.TypeTranslator;
import processor.core.graph.conditions.Condition;
import processor.profile.log.LogSerializer;

/**
 *
 * @author cbaez
 */
public class FileProcessor {
    public static VariableHolder variableHolder;
    
    protected int processedCount;
    protected Profile project;
    
    
    public FileProcessor(Profile project) {
        this.project = project;

    }

    public ProcessingResult processFile(File f) {
        ProcessingResult processingResult = new ProcessingResult();
        System.out.println("Processing file: " + f.getAbsolutePath());
        DecisionGraph graph = project.getGraph();
        GraphNode node = graph.getInitialNode();
        if (node == null) {
            return null;
        }
        
        variableHolder = new VariableHolder(); 
        variableHolder.put("fileName", f.getName());
        Object content = f;
        while (!((node instanceof processor.core.graph.FailNode) || (node instanceof processor.core.graph.EndNode))) {
            boolean res = true;
            if (node.isActive()) {
                if (node instanceof Condition) {
                    try {
                        content = TypeTranslator.translateFor((Condition) node, content);
                        res = ((Condition) node).test(content);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        res = false;
                    }
                }
                if (node instanceof Action) {
                    try {
                        Object original = TypeTranslator.translateFor(((Action) node), content);
                        content = ((Action) node).process(original);
                        if (!original.equals(content)) {
                            processingResult.actions.add((Action) node);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            node = graph.getDecisionTargetOf(node, res);

        }
        processingResult.setPassed(!(node instanceof processor.core.graph.FailNode));
        System.out.println("Result was: " + processingResult.isPassed());
        processingResult.setResult(content);
        return processingResult;

    }

    public void processAll(boolean saveBackup) {
        System.out.println("Starting processing all");
        Path backupPath = null;
        Path basePath = null;
        if(saveBackup){
            try {
                basePath = Paths.get(project.getWorkingDirectory());
                backupPath = LogSerializer.generateLogFolder(ProjectCentral.instance().getProfileFile());
                LogSerializer.saveLog(backupPath);
            } catch (IOException ex) {
                saveBackup = false;
                ex.printStackTrace();
            }
            
        }
        
        for (Map.Entry<String, ProcessingResult> a : project.getFileCentral().getResultMap().entrySet()) {
            Path f = Paths.get(a.getKey());
            ProcessingResult r = a.getValue();
            if (r.getActions().size() > 0) {
                try {
                    String result = (String) processFile(f.toFile()).getResult();
                    
                    if(saveBackup){
                        LogSerializer.backupFile(basePath, f, backupPath);
                    }
                    
                    saveFile(result, f);
                } catch (IOException ex) {
                    Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
       
        System.out.println("All files processed");
    }

    public void saveFile(String result, Path p) throws FileNotFoundException, IOException {
        File f = p.toFile();
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.ISO_8859_1)
        )) {
            writer.write(result);
            project.getFileCentral().getProcessedFiles().add(p);
        }
    }

    

    public static VariableHolder getVariableHolder() {
        return variableHolder;
    }

   
   
    
    
    

}
