var p1 = /Socket\s*s\s*=\s*null\s*/;
var r1 = "//#ssl_mig# Socket s = null";

var p2 = /s\s*=\s*new\s*Socket\(/;
var r2 = "//#ssl_mig# s = new Socket(";

var p3 = /s\.setSoTimeout/;
var r3 = "//#ssl_mig# s.setSoTimeout";

var p4 = /s.close\(\)/;
var r4 = "//#ssl_mig# s.close()";

var regsLits = [[p1, r1], [p2, r2], [p3, r3], [p4, r4]];

for (var i = 0; i < regsLists.length; i++) {
    var pattern = regsLists[i][0];
    var replace = regsLists[i][1];
    target = target.replace(pattern, replace);
}

return target;
