var r = /<(div|p|h|a|table|td|tr)\s+id=("|')([a-zA-Z0-9]+)("|')/gi;
var text = target;

var ids = [];
var regexList = text.match(r);
if (regexList) {
    for (var i = 0; i < regexList.length; i++) {
        var res = regexList[i].match(/id=('|")([a-zA-Z0-9]+)('|")/);
        if (res) {
            ids.push(res[2]);
        }
    }

    for (var i = 0; i < ids.length; i++) {
        var id = ids[i];
        r = new RegExp('<script[^>]*>(?:[^<]+|<(?!/script>))+', 'gi');
        while (res = r.exec(text)) {
            var begin = res.index;
            var end = begin + res[0].length;

            var pre = text.substring(0, begin);
            var inText = text.substring(begin, end);
            var post = text.substring(end);
            inText = inText.replace(new RegExp("(\\(?)(" + id + ")((\\.|\\s*,|\\s*\\)))", 'g'), "$1document.getElementById('" + id + "')$3");

            text = pre + inText + post;

        }

    }
}

return text;