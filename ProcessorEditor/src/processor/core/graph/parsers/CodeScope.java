/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.parsers;

/**
 *
 * @author cbaez
 */
public class CodeScope {
    public static String METHOD = "method", TRY_CATCH = "try-catch";
    protected int start;
    protected int end;
    protected String code;

    public CodeScope(String code, int start, int end) {
        this.start = start;
        this.end = end;
        
        this.code = code.substring(start, end + 1);
    }

    
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    
    
}
