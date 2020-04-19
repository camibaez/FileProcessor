/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.profile.log;

import java.io.File;
import com.datapro.nfp.profile.log.FilesLog;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.datapro.nfp.core.file.FileProcessor;
import com.datapro.nfp.core.file.ProcessingResult;
import com.datapro.nfp.core.file.Profile;
import com.datapro.nfp.core.file.ProjectCentral;
import com.datapro.nfp.core.file.VariableHolder;
import com.datapro.nfp.core.graph.DecisionGraph;
import com.datapro.nfp.core.graph.GraphNode;
import com.datapro.nfp.core.graph.actions.Action;
import com.datapro.nfp.core.graph.serialization.GraphSerializer;
import com.datapro.nfp.profile.DIEmulator;
import com.datapro.nfp.profile.ProfileSerializer;

/**
 *
 * @author cbaez
 */
public class LogSerializer {
    protected static Map variableHolder = DIEmulator.getVariableHolder();
    protected static ProjectCentral projectCentral = DIEmulator.getProjectCentral();
    
    public static List<String> files;
    public static List<String> foundFiles;
    public static void saveLog(Path backupPath) {
        String fileName = backupPath.toString() + ".json";

        Map m = new JSONObject();
        List arr = new JSONArray();
        Profile profile = projectCentral.getProfile();
        profile.getFileCentral().getResultMap().entrySet().forEach((Entry<String, ProcessingResult> e) -> {
            if (e.getValue().getActions().size() < 1) {
                return;
            }
            List record = new JSONArray();
            record.add(e.getKey());
            List<Action> actions = e.getValue().getActions();
            actions.forEach(a -> {
                record.add(a.getId());
            });
            arr.add(record);
        });
        m.put("logs", arr);
        m.put("dataDump", variableHolder);

        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.write(((JSONObject) m).toJSONString());
            pw.flush();
            System.out.println("Savend log " + fileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, Object> readLog(String path) {
        Map<String, Object> logData = new HashMap<>();
        Map<String, Set<String>> filesLog = new FilesLog();
        VariableHolder data = new VariableHolder();

        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(path)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray logs = (JSONArray) jsonObject.get("logs");
            logs.forEach(l -> {
                Set<String> cleaners = new HashSet<>();
                String fileName = (String) ((JSONArray) l).remove(0);
                cleaners.addAll(((JSONArray) l));
                filesLog.put(fileName, cleaners);
            });
            ((Map)jsonObject.get("dataDump")).forEach((k, v) -> {
                data.put((String)k, v);
            });
            logData.put("filesLog", filesLog);
            logData.put("data", data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(ProfileSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return logData;
    }

    
    //TODO DELETE
    public static void loadFilesList(){
        String logPath = "C:\\Users\\cbaez\\Desktop\\LASTIME CHANGE\\04SocketClean\\04SocketClean_log_1563916580644.json";
        Map<String, Object> readLog = readLog(logPath);
        Map<String, Set<String>> get = (Map<String, Set<String>>) readLog.get("filesLog");
        List<String> files = new LinkedList<>();
        
        get.keySet().forEach(f -> {
            int i = f.lastIndexOf("\\");
            files.add(f.substring(i + 1));
        });
        
        LogSerializer.files = files;
        LogSerializer.foundFiles = new LinkedList<>();
    }
    
    public static boolean isContained(String fileName){
        if(files == null){
            loadFilesList();
        }
        boolean res = files.contains(fileName);
        if(res) 
            foundFiles.add(fileName);
        return res;
    }
    
    public static void calculateNone(){
        List<String> noneList = new LinkedList<>();
        files.forEach(f -> {
            
        });
    }
    public static void backupFile(Path basePath, Path originPath, Path backupPath) {
        String o = originPath.toString();
        String base = basePath.toString();
        String backup = backupPath.toString();
        
        Path finalPath = Paths.get(backup, o.replace(base, ""));
        
        try {
            Files.createDirectories(finalPath.getParent());
            Files.copy(originPath, finalPath);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
    
    public static Path generateLogFolder(File profileFile) throws IOException{
        String fileName = profileFile.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileName + "_log_" + System.currentTimeMillis();
        fileName = profileFile.getParent() + "\\" + fileName;
        Path folder = Paths.get(fileName);
        Files.createDirectory(folder);
        return folder;
    }

}
