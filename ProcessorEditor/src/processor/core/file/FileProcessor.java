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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.actions.TypeTranslator;
import processor.core.graph.conditions.Condition;

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
                        Object translation = TypeTranslator.translateFor((Condition) node, content);
                        res = ((Condition) node).test(translation);

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
            
            GraphNode nextNode;
            nextNode = graph.getDecisionTargetOf(node, res);

            node = nextNode;

        }
        processingResult.setPassed(!(node instanceof processor.core.graph.FailNode));
        System.out.println("Result was: " + processingResult.isPassed());
        processingResult.setResult(content);
        return processingResult;

    }

    public void processAll() {
        System.out.println("Starting processing all");
        for (Map.Entry<Path, ProcessingResult> a : project.getFileCentral().getResultMap().entrySet()) {
            Path f = a.getKey();
            ProcessingResult r = a.getValue();
            if (r.getActions().size() > 0) {
                try {
                    String result = (String) processFile(f.toFile()).getResult();
                    saveFile(result, f);
                } catch (IOException ex) {
                    Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        saveLog();
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

    public void saveLog() {
        String fileName = ProjectCentral.instance().getProfileFile().getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileName + "_log_" + System.currentTimeMillis() + ".json";
        fileName = ProjectCentral.instance().getProfileFile().getParent() + "\\" + fileName;

        Map m = new JSONObject();
        List arr = new JSONArray();
        project.getFileCentral().getProcessedFiles().forEach((Path f) -> {
            List record = new JSONArray();
            record.add(f.toString());
            List<Action> actions = project.getFileCentral().getResultMap().get(f).getActions();
            actions.forEach(a -> {
                record.add(a.getId());
            });
            arr.add(record);
        });
        m.put("logs", arr);

        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.write(((JSONObject) m).toJSONString());
            pw.flush();
            System.out.println("Savend log " + fileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static VariableHolder getVariableHolder() {
        return variableHolder;
    }

   
    
    
    

}
