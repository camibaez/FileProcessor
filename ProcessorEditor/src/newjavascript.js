function a(target) {
    function extractVarName(target) {
        var pattern = /\sSocket\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\s*(=|;)/;
        var found = pattern.exec(target);
        if (Array.isArray(found) && found.length > 1) {
            return [found[1], found.index];
        }
        return null;
    }
   
    var extractResult = extractVarName(target);
    if (extractResult == null) {
        print("\n ==> Error 1");
        return target;
    }

    var varName = extractResult[0];
    var varIndex = extractResult[1];
    
    var JavaParser = Java.type('processor.core.graph.jslinker.JavaParser');
    var scope = JavaParser.getMethodScopeOf(target, varIndex);
    if (!scope.length) {
        var pattern  = new RegExp(varName + "\\s*=\\s*new\\s*Socket\\(");
        var found = pattern.exec(target);
        if (found != null) {
            varIndex = found.index;
            scope = JavaParser.getMethodScopeOf(target, varIndex);
        }else{
            return target;
        } 
    }
    varIndex = scope[0];
    var endScopeIndex = scope[1];
    print("\n" + varIndex + "," + endScopeIndex);
    var stringPreSpace = target.substring(0, varIndex);
    var stringSpace = target.substring(varIndex, endScopeIndex);
    var stringPostSpace = target.substring(endScopeIndex);
    var regsLists = [
        [new RegExp("Socket\\s+" + varName + "\\s*=\\s*null\\s*;", 'g'), ""],
        [new RegExp("(Socket)?.*" + varName + "\\s*=\\s*new\\s*Socket\\(.*?;", 'g'), ""],
        [new RegExp("[^a-zA-Z0-9_]" + varName + "\\..*?;", 'g'), ""]
    ]

    for (var i = 0; i < regsLists.length; i++) {
        var pattern = regsLists[i][0];
        var replace = regsLists[i][1];
        stringSpace = stringSpace.replace(pattern, replace);
    }

    return target = stringPreSpace + stringSpace + stringPostSpace;


}










/*
 varPatterns = [
 /\sSocket\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\s*(=|;)/;/,
 //
 ];
 
 
 function extractVarName(target) {
 var pattern = /\sSocket\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\s*(=|;)/;
 
 var found = pattern.exec(target);
 if (Array.isArray(found) && found.length > 1) {
 return [found[1], found.index];
 }
 return null;
 }
 
 
 
 var extractResult = extractVarName(target);
 
 if (extractResult == null){
 print("\n ==> Error 1");
 return target;
 }
 
 var varName = extractResult[0];
 var varIndex = extractResult[1];
 
 var JavaParser = Java.type('processor.core.graph.jslinker.JavaParser');
 var scope = JavaParser.getMethodScopeOf(target, varIndex);
 
 
 if (!scope.length) {
 print("\nError 2");
 return target;
 }
 varIndex = scope[0];
 var endScopeIndex = scope[1];
 print("\n" + varIndex + "," + endScopeIndex);	
 var stringPreSpace = target.substring(0, varIndex);
 var stringSpace = target.substring(varIndex, endScopeIndex);
 var stringPostSpace = target.substring(endScopeIndex);
 var regsLists = [
 
 [new RegExp("Socket\\s+" + varName + "\\s*=\\s*null\\s*;", 'g'), ""],
 [new RegExp("(Socket)?.*" + varName + "\\s*=\\s*new\\s*Socket\\(.*?;", 'g'), ""],
 [new RegExp("[^a-zA-Z0-9_]" + varName + "\\..*?;", 'g'), ""]
 ]
 
 for (var i = 0; i < regsLists.length; i++) {
 var pattern = regsLists[i][0];
 var replace = regsLists[i][1];
 stringSpace = stringSpace.replace(pattern, replace);
 }
 
 return target = stringPreSpace + stringSpace + stringPostSpace;
 */