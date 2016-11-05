// Only use for table with columns have filter , sort
// If Table have column that is not filter or sort then not use this

function renderTableWithFilter() {
	PrimeFaces.widget.ColumnToggler.prototype.render = function() {
		// variable for creating id referance for each checkbox in
		// ColumnToggler
		// this.columns = this.thead.find('> tr >
		// th:visible:not(.ui-static-column)');
		this.columns = this.thead.find('> tr > th');
		this.panel = $('<div></div>')
				.attr('id', this.cfg.id)
				.addClass(
						'ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all')
				.append('<ul class="ui-columntoggler-items"></ul').appendTo(
						document.body).hide();
		this.itemContainer = this.panel.children('ul');
		for (var i = 0; i < this.columns.length; i++) {
			var column = this.columns.eq(i);
			if (column.children('.ui-column-title').prevObject[0].childNodes[0] != null
					&& column.children('.ui-column-title').prevObject[0].childNodes[0].childNodes[0] != null
					&& column.children('.ui-column-title').prevObject[0].childNodes[0].childNodes[0].text != null) {
				if (column.children('.ui-column-title').find(
						':visible:not(.ui-static-column)').length == 0) {
					$(
							'<li class="ui-columntoggler-item">'
									+ '<div class="ui-chkbox ui-widget">'
									+ '<div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default"><span class="ui-chkbox-icon ui-icon ui-icon-blank"></span></div>'
									+ '</div>'
									+ '<label>'
									+ column.children('.ui-column-title').prevObject[0].childNodes[0].childNodes[0].text
									+ '</label></li>').data('column',
							column.attr('id')).appendTo(this.itemContainer);
				} else {
					$(
							'<li class="ui-columntoggler-item">'
									+ '<div class="ui-chkbox ui-widget">'
									+ '<div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div>'
									+ '</div>'
									+ '<label>'
									+ column.children('.ui-column-title').prevObject[0].childNodes[0].childNodes[0].text
									+ '</label></li>').data('column',
							column.attr('id')).appendTo(this.itemContainer);
				}
				// For only Chrome :
				// column.children('.ui-column-title')[0].firstChild.innerText
				// column.children('.ui-column-title').prevObject.text()
				// column.children('.ui-column-title').prevObject[0].children[0].childNodes[0].text
			}
		}
		this.hide();

	}
};

function renderTableWithNoFilter() {
	PrimeFaces.widget.ColumnToggler.prototype.render = function() {
		if (jQuery(this.thead.get(0)).attr('id') == "formList:historyScreenId_reportTable_head") {
			if (jQuery(this.tbody).find("tr > td").length <= 1) {
				this.columns = jQuery(this.thead.get(0)).find('> tr > th');
				this.panel = $('<div></div>')
						.attr('id', this.cfg.id)
						.addClass(
								'ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all')
						.append('<ul class="ui-columntoggler-items"></ul')
						.appendTo(document.body).hide();
				this.itemContainer = this.panel.children('ul');
				this.itemContainer.empty();
				for (var i = 0; i < this.columns.length; i++) {
					var column = this.columns.eq(i);
					if (!column.hasClass("ui-static-column")) {
						$(
								'<li class="ui-columntoggler-item">'
										+ '<div class="ui-chkbox ui-widget">'
										+ '<div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div>'
										+ '</div>'
										+ '<label>'
										+ column.children('.ui-column-title').prevObject[0].textContent
										+ '</label></li>').data('column',
								column.attr('id')).appendTo(this.itemContainer);
					}

				}
			}
			else{
				this.columns = jQuery(this.thead.get(0)).find('> tr > th');
//				debugger;
				this.panel = $('<div></div>')
						.attr('id', this.cfg.id)
						.addClass(
								'ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all')
						.append('<ul class="ui-columntoggler-items"></ul')
						.appendTo(document.body).hide();
				this.itemContainer = this.panel.children('ul');
				this.itemContainer.empty();
				for (var i = 0; i < this.columns.length; i++) {
					var column = this.columns.eq(i);
					if(column.hasClass("ui-static-column")){
						continue;
					}
					if (column.hasClass("ui-helper-hidden")) {
						$(
								'<li class="ui-columntoggler-item">'
										+ '<div class="ui-chkbox ui-widget">'
										+ '<div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default"><span class="ui-chkbox-icon ui-icon ui-icon-blank"></span></div>'
										+ '</div>'
										+ '<label>'
										+ column.children('.ui-column-title').prevObject[0].textContent
										+ '</label></li>').data('column',
								column.attr('id')).appendTo(this.itemContainer);
					}
					else{
						$(
								'<li class="ui-columntoggler-item">'
										+ '<div class="ui-chkbox ui-widget">'
										+ '<div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div>'
										+ '</div>'
										+ '<label>'
										+ column.children('.ui-column-title').prevObject[0].textContent
										+ '</label></li>').data('column',
								column.attr('id')).appendTo(this.itemContainer);
					}

				}
			}
			this.hide();
			return;
		}
		this.columns = jQuery(this.thead.get(0)).find('> tr > th');
		this.panel = $('<div></div>')
				.attr('id', this.cfg.id)
				.addClass(
						'ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all')
				.append('<ul class="ui-columntoggler-items"></ul').appendTo(
						document.body).hide();
		this.itemContainer = this.panel.children('ul');
		this.itemContainer.empty();
		for (var i = 0; i < this.columns.length; i++) {
			var column = this.columns.eq(i);
			if (column.children('.ui-column-title').prevObject[0].textContent != null
					&& column.children('.ui-column-title').prevObject[0].textContent != '') {
				if (column.children('.ui-column-title').find(
						':visible:not(.ui-static-column)').length == 0) {
					$(
							'<li class="ui-columntoggler-item">'
									+ '<div class="ui-chkbox ui-widget">'
									+ '<div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default"><span class="ui-chkbox-icon ui-icon ui-icon-blank"></span></div>'
									+ '</div>'
									+ '<label>'
									+ column.children('.ui-column-title').prevObject[0].textContent
									+ '</label></li>').data('column',
							column.attr('id')).appendTo(this.itemContainer);
				} else {
					$(
							'<li class="ui-columntoggler-item">'
									+ '<div class="ui-chkbox ui-widget">'
									+ '<div class="ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active"><span class="ui-chkbox-icon ui-icon ui-icon-check"></span></div>'
									+ '</div>'
									+ '<label>'
									+ column.children('.ui-column-title').prevObject[0].textContent
									+ '</label></li>').data('column',
							column.attr('id')).appendTo(this.itemContainer);
				}
			}
		}
		this.hide();
	}
}

