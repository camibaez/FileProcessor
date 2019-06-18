/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void saveGraph(){
        System.out.println(ProfileAdministration.generateProfileJSON(profile));
    }

}
