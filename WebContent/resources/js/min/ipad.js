function hiddenDate(e){var n=e.attr("name"),t=e.val(),r=$("<input></input>")
r.prop("type","hidden"),r.prop("name",n),r.prop("id","hid"+n),r.val(e.val()),e.after(r),e.removeAttr("name")
var a=t.match(/^(\d{4})-(\d{1,2})-(\d{1,2})$/)
return a&&e.val(a[3]+"/"+a[2]+"/"+a[1]),r}$(document).ready(function(){$("#growl-popup").click(function(){$("#growl-popup").hide()})}),$(document).keypress(function(e){if(13==e.which&&"TEXTAREA"!=e.target.nodeName)return!1})
