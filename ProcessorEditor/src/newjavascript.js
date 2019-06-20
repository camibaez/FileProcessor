function f(target) {
    var progPattern = /public class JS([A-Za-z0-9])\s+/;
    var found = target.match(progPattern);
    if (found.length > 0) {
        var pName = found[1];
        var pHolder = /<cleaner#pname>/g;
        target = target.replace(pHolder, pName);
    }
    
    return target;
}