/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.parsers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cbaez
 */
public class JavaParser {

    protected String code;

    protected Map<String, List<CodeScope>> scopes;

    public JavaParser(String code) {
        this.code = code;
        this.scopes = new HashMap<>();

        loadMethodScopes();
    }

    public final int closingBracketOf(int index) {
        int bracketStart = index;
        int level = 0;
        int i = bracketStart + 1;
        for (; i < code.length(); i++) {
            if (code.charAt(i) == '{') {
                level += 1;
            }
            if (code.charAt(i) == '}') {
                if (level == 0) {
                    break;
                }
                level -= 1;
            }
        }
        return i;
    }

    public final void loadMethodScopes() {
        String regex = "(protected|private|public)\\s+(static\\s+)?[a-zA-Z0-9_]+\\s+[a-zA-Z0-9_]+\\((.|\\s)*?\\{";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        List<CodeScope> scopesList = new LinkedList<>();
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
            scopesList.add(scope);
        }

        scopes.put(CodeScope.METHOD, scopesList);
    }

    public CodeScope getMethodScopeOf(int index) {
        CodeScope scope = null;
        for (CodeScope s : scopes.get(CodeScope.METHOD)) {
            if ((s.getStart() <= index) && (index <= s.getEnd())) {
                return s;
            }
        }

        return scope;
    }

    public CodeScope getTryScopeOf(int index) {
        CodeScope methodScopeOf = getMethodScopeOf(index);
        if (methodScopeOf == null) {
            return null;
        }
        int start = methodScopeOf.getStart();
        Matcher matcher = Pattern.compile("try\\s+\\{").matcher(code);
        boolean foundFirst = false;
        while (matcher.find(start) && matcher.start() < index) {
            foundFirst = true;
            start = matcher.start();
        }
        if (foundFirst) {
            start = code.indexOf("{", start);
            int end = closingBracketOf(start);
            
            
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public List<CodeScope> getScopes(String scopeType) {
        return scopes.get(scopeType);
    }

}
