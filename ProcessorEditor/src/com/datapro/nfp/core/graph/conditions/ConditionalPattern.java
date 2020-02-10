/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.conditions;

import java.util.regex.Pattern;

/**
 *
 * @author cbaez
 */
public class ConditionalPattern {

    public static final String INCLUDE = "+", EXCLUDE = "-";

    protected Pattern pattern;
    protected String condition;
    protected transient boolean found = false;
    protected transient int state = 0;

    public ConditionalPattern(Pattern pattern, String condition) {
        this.pattern = pattern;
        this.condition = condition;
    }

    public static ConditionalPattern compile(String pattern, int flags, String condition) {
        Pattern p = Pattern.compile(pattern, flags);
        return new ConditionalPattern(p, condition);

    }
    
    /**
     * Returns an stateful response depending on the condition and on the fact
     * that whether the pattern has been found or not.
     *
     * @param str
     * @return Values {-1, 0, 1} => {fail, continue, success}
     */
    public int matches(String str) {
        found |= pattern.matcher(str).find();
        return getState();

    }

    public int getState() {
        if (state == 0) {
            if (found && condition.equals(INCLUDE)) {
                state = 1;
            }

            if (found && condition.equals(EXCLUDE)) {
                state = -1;
            }
        }

        return state;
    }

    public int finalState() {
        if (getState() == 0 && condition.equals(INCLUDE)) {
            return -1;
        }
        if (getState() == 0 && condition.equals(EXCLUDE)) {
            return 1;
        }
        return getState();

    }

    public void restart() {
        found = false;
        state = 0;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String condition){
        this.condition = condition;
    }
    
    public String toString(){
        return "<" + condition + ">";
        
    }

}
