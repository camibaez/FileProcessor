var reg = /getMessageHandler("[A-Za-z0-9]+")/;
var found = reg.exec(target);
if (found != null) {
    varIndex = found.index;
    var JavaParser = Java.type('processor.core.graph.jslinker.JavaParser');
    var scope = JavaParser.getMethodScopeOf(target, varIndex);
    if(scope.lenght){
        var stringPreSpace = target.substring(0, scope[0]);
        var stringSpace = target.substring(scope[0], scope[1]);
        var stringPostSpace = target.substring(scope[1]);
        
        srtingSpace = stringSpace.replace(/catch\s+\(IOException/, "catch (Exception");
        
        target =  stringPreSpace + stringSpace + stringPostSpace;
    }
}

return target;
