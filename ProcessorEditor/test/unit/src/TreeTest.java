/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import processor.core.GraphBuilder;
import processor.core.file.Profile;
import processor.profile.ProfileAdministration;

/**
 *
 * @author cbaez
 */
public class TreeTest {
    Profile profile;

    public TreeTest() {
    }
    
    
    @Before
    public void setUp() {
        String projPath = "C:\\Users\\cbaez\\Documents\\NetBeansProjects\\HTMLFixer\\conf\\test-files\\BigChangeTest\\proj1.json";
        String filesPath = "C:\\Users\\cbaez\\Documents\\NetBeansProjects\\HTMLFixer\\conf\\test-files\\BigChangeTest\\files";

        profile = ProfileAdministration.loadProject(projPath);
        profile.setWorkingDirectory(filesPath);

    }
    
    @After
    public void tearDown() {
        
        
    }

   @Test
   public void graphTree(){
       GraphBuilder gb = new GraphBuilder();
       //profile.setGraph(gb.build(profile));
       System.out.println(gb.export(profile.getGraph()));
   }
    
    @Test
    public void saveGraph(){
        //profile.setGraph(new GraphBuilder().build(profile));
        System.out.println(ProfileAdministration.generateProfileJSON(profile));
    }

}
