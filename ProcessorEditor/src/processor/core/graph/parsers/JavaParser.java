/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.parsers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cbaez
 */
public class JavaParser {

    protected String code;
    protected List<CodeScope> scopes;

    public JavaParser(String code) {
        this.code = code;
        this.scopes = new LinkedList<>();
        
        loadScopes();
    }

    public final void loadScopes() {
        String regex = "(protected|private|public)\\s+(static\\s+)?[a-zA-Z0-9_]+\\s+[a-zA-Z0-9_]+\\((.|\\s)*?\\{";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);

        while (matcher.find()) {
            int start = matcher.start();
            
            int bracketStart = code.indexOf("{", start) + 1;
            int level = 0;
            int end = bracketStart;
            for (; end < code.length(); end++) {
                if (code.charAt(end) == '{') {
                    level += 1;
                }
                if (code.charAt(end) == '}') {
                    if (level == 0) {
                        break;
                    }
                    level -= 1;
                }
            }
            
            CodeScope scope = new CodeScope(code, start, end);
            scopes.add(scope);
        }
    }

    public CodeScope getMethodScopeOf(int index) {
        CodeScope scope = null;
        for(CodeScope s: scopes){
            if((s.getStart() <= index) && (index <= s.getEnd()))
                return s;
        }
        
        return scope;
    }
    
    

    public String getCode() {
        return code;
    }

    public List<CodeScope> getScopes() {
        return scopes;
    }

    

}
