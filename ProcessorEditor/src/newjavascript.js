function a() {
    var pattern = /([A-Z][A-Z0-9]{6})(.*Message)/;
    var found = target.match(pattern);
    if (found.length > 0) {
        var pName = found[1];
        target = target.replace("<cleaner#pname>", pName);
    }

    return target;
}