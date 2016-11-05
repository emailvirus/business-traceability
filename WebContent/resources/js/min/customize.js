/**
 * Created by arrow on 04/02/2016.
 */
function split(e){return e.split(/\s/g)}function extractLast(e){return split(e).pop()}var SetCaretAtEnd=function(e){var t=e.value.length
if(document.selection){var i=document.selection.createRange()
i.moveStart("character",-t),i.moveStart("character",t),i.moveEnd("character",0),i.select()}else(e.selectionStart||"0"==e.selectionStart)&&(e.selectionStart=t,e.selectionEnd=t)}
$.widget("custom.mcautocomplete",$.ui.autocomplete,{_create:function(){this._super(),this.widget().menu("option","items","> :not(.ui-widget-header)")},_renderMenu:function(e,t){var i=this
if(e.addClass("arrow-suggestion-panel"),this.options.showHeader){var n=$('<li class="ui-widget-header suggestion-header"></li>'),a=$('<div class="ui-autocomplete-panel ui-widget-content ui-helper-hidden arrow-suggestion-header-item"></div>'),o=$('<table class="ui-autocomplete-items ui-autocomplete-table ui-widget-content ui-widget ui-corner-all ui-helper-reset">'),r="<thead><tr>"
$.each(this.options.columns,function(e,t){var i='<td class="ui-state-default width-fifty" style="width:'+t.width+'px;">'+t.name+"</td>"
r+=i}),r+="</tr></thead>",o.append(r),o.append("</table>"),a.append(o),a.appendTo(n),n.appendTo(e)}$.each(t,function(t,n){i._renderItem(e,n)})},_renderItem:function(e,t){var i=0,n=25,a=$('<div"></div>'),o=$('<div class="ui-autocomplete-panel ui-widget-content ui-helper-hidden arrow-suggestion-render-items"></div>'),r=$('<table class="ui-autocomplete-items ui-autocomplete-table ui-widget-content ui-widget ui-corner-all ui-helper-reset" style="width: 100%;"></table>'),s=$('<tr id="'+t.id+'" class="ui-autocomplete-item ui-autocomplete-row ui-widget-content ui-corner-all" ></tr>'),d=this
$.each(this.options.columns,function(e,a){var o=""!=t[a.valueField]?t[a.valueField]:"&nbsp;",r=Math.ceil(t[a.valueField].length/14),l=25
r>1&&(l+=15*(r-1)),l>=n&&(n=l)
var u=e%2==0==d.options.resizeEvenColumn?a.width-2:a.width,c=$('<td width="'+u+'px">'+o+"</td>")
s.append(c),i+=a.width}),r.append(s),o.append(r),a.append(o)
var l=$('<li class="arrow-suggestion-select-items" style="border:none; width: '+i+"px; height: "+n+'px; "></li>')
l.data("ui-autocomplete-item",t).append("<a>"+a[0].innerHTML+'<div style="clear: both;"></div></a>').appendTo(e)},_resizeMenu:function(){var e=this.menu.element,t=this.options.columns.reduce(function(e,t){return e+t.width},0)
e.outerWidth(t+10)}})
