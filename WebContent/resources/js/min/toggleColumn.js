// Only use for table with columns have filter , sort
// If Table have column that is not filter or sort then not use this
//				debugger;
function renderTableWithFilter(){PrimeFaces.widget.ColumnToggler.prototype.render=function(){this.columns=this.thead.find("> tr > th"),this.panel=$("<div></div>").attr("id",this.cfg.id).addClass("ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all").append('<ul class="ui-columntoggler-items"></ul').appendTo(document.body).hide(),this.itemContainer=this.panel.children("ul")
for(var i=0;i<this.columns.length;i++){var t=this.columns.eq(i)
null!=t.children(".ui-column-title").prevObject[0].childNodes[0]&&null!=t.children(".ui-column-title").prevObject[0].childNodes[0].childNodes[0]&&null!=t.children(".ui-column-title").prevObject[0].childNodes[0].childNodes[0].text&&(0==t.children(".ui-column-title").find(":visible:not(.ui-static-column)").length?$('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default"><span class="ui-chkbox-icon ui-icon ui-icon-blank"></span></div></div><label>'+t.children(".ui-column-title").prevObject[0].childNodes[0].childNodes[0].text+"</label></li>").data("column",t.attr("id")).appendTo(this.itemContainer):$('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div></div><label>'+t.children(".ui-column-title").prevObject[0].childNodes[0].childNodes[0].text+"</label></li>").data("column",t.attr("id")).appendTo(this.itemContainer))}this.hide()}}function renderTableWithNoFilter(){PrimeFaces.widget.ColumnToggler.prototype.render=function(){if("formList:historyScreenId_reportTable_head"==jQuery(this.thead.get(0)).attr("id")){if(jQuery(this.tbody).find("tr > td").length<=1){this.columns=jQuery(this.thead.get(0)).find("> tr > th"),this.panel=$("<div></div>").attr("id",this.cfg.id).addClass("ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all").append('<ul class="ui-columntoggler-items"></ul').appendTo(document.body).hide(),this.itemContainer=this.panel.children("ul"),this.itemContainer.empty()
for(var i=0;i<this.columns.length;i++){var t=this.columns.eq(i)
t.hasClass("ui-static-column")||$('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div></div><label>'+t.children(".ui-column-title").prevObject[0].textContent+"</label></li>").data("column",t.attr("id")).appendTo(this.itemContainer)}}else{this.columns=jQuery(this.thead.get(0)).find("> tr > th"),this.panel=$("<div></div>").attr("id",this.cfg.id).addClass("ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all").append('<ul class="ui-columntoggler-items"></ul').appendTo(document.body).hide(),this.itemContainer=this.panel.children("ul"),this.itemContainer.empty()
for(var i=0;i<this.columns.length;i++){var t=this.columns.eq(i)
t.hasClass("ui-static-column")||(t.hasClass("ui-helper-hidden")?$('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default"><span class="ui-chkbox-icon ui-icon ui-icon-blank"></span></div></div><label>'+t.children(".ui-column-title").prevObject[0].textContent+"</label></li>").data("column",t.attr("id")).appendTo(this.itemContainer):$('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div></div><label>'+t.children(".ui-column-title").prevObject[0].textContent+"</label></li>").data("column",t.attr("id")).appendTo(this.itemContainer))}}return void this.hide()}this.columns=jQuery(this.thead.get(0)).find("> tr > th"),this.panel=$("<div></div>").attr("id",this.cfg.id).addClass("ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all").append('<ul class="ui-columntoggler-items"></ul').appendTo(document.body).hide(),this.itemContainer=this.panel.children("ul"),this.itemContainer.empty()
for(var i=0;i<this.columns.length;i++){var t=this.columns.eq(i)
null!=t.children(".ui-column-title").prevObject[0].textContent&&""!=t.children(".ui-column-title").prevObject[0].textContent&&(0==t.children(".ui-column-title").find(":visible:not(.ui-static-column)").length?$('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default"><span class="ui-chkbox-icon ui-icon ui-icon-blank"></span></div></div><label>'+t.children(".ui-column-title").prevObject[0].textContent+"</label></li>").data("column",t.attr("id")).appendTo(this.itemContainer):$('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div></div><label>'+t.children(".ui-column-title").prevObject[0].textContent+"</label></li>").data("column",t.attr("id")).appendTo(this.itemContainer))}this.hide()}}