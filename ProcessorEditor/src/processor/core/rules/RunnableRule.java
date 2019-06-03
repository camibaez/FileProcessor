/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.rules;

/**
 *
 * @author cbaez
 */
public class RunnableRule {
    protected String rawCode = "";
    
    
    public void execute(){
        System.out.println("This is ur code" + rawCode);
    }
    

    public String getRawCode() {
        return rawCode;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }
}
