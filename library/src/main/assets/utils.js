function getSelectedText(var t) {
    var txt;
    var title = t;
    if (window.getSelection) {
        txt = window.getSelection().toString();
    } else if (window.document.getSelection) {
        txt = window.document.getSelection().toString();
    } else if (window.document.selection) {
        txt = window.document.selection.createRange().text;
    }

    JSInterface.callback(txt,title);
}()