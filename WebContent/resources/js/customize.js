/**
 * Created by arrow on 04/02/2016.
 */
function split(val) {
	return val.split(/\s/g);
}

function extractLast(term) {
	return split(term).pop();
}

var SetCaretAtEnd = function (elem) {
	var elemLen = elem.value.length;
	// For IE Only
	if (document.selection) {
		// Use IE Ranges
		var oSel = document.selection.createRange();
		// Reset position to 0 & then set at end
		oSel.moveStart('character', -elemLen);
		oSel.moveStart('character', elemLen);
		oSel.moveEnd('character', 0);
		oSel.select();
	}
	else if (elem.selectionStart || elem.selectionStart == '0') {
		// Firefox/Chrome
		elem.selectionStart = elemLen;
		elem.selectionEnd = elemLen;
	}
};

$.widget('custom.mcautocomplete', $.ui.autocomplete, {
    _create: function () {
        this._super();
        this.widget().menu("option", "items", "> :not(.ui-widget-header)");
    },
    _renderMenu: function (ul, items) {
        var that = this;
        ul.addClass('arrow-suggestion-panel');

        // Render header
        if (this.options.showHeader) {
            var li = $('<li class="ui-widget-header suggestion-header"></li>');
            //var container = $('<div class="ui-widget-header suggestion-header"></div>');
            var div = $('<div class="ui-autocomplete-panel ui-widget-content ui-helper-hidden arrow-suggestion-header-item"></div>');
            var table = $('<table class="ui-autocomplete-items ui-autocomplete-table ui-widget-content ui-widget ui-corner-all ui-helper-reset">');
            var header = '<thead><tr>';
            $.each(this.options.columns, function (index, column) {
                var td = '<td class="ui-state-default width-fifty" style="width:' + column.width + 'px;">' + column.name + '</td>';
                header += td;
            });
            header += '</tr></thead>';
            table.append(header);
            table.append('</table>');
            div.append(table);
            div.appendTo(li);
            li.appendTo(ul);
        }

        // Render body
        $.each(items, function (index, item) {
            that._renderItem(ul, item);
        });

    },
    _renderItem: function (ul, item) {
        var width = 0;
        var heightValue = 25;
        var container = $('<div"></div>');
        var div = $('<div class="ui-autocomplete-panel ui-widget-content ui-helper-hidden arrow-suggestion-render-items"></div>');
        var table = $('<table class="ui-autocomplete-items ui-autocomplete-table ui-widget-content ui-widget ui-corner-all ui-helper-reset" style="width: 100%;"></table>');
        var tr = $('<tr id="' + item.id + '" class="ui-autocomplete-item ui-autocomplete-row ui-widget-content ui-corner-all" ></tr>');
        var that = this;
        $.each(this.options.columns, function(index, column){
            var tdValue = (item[column.valueField] != "") ? item[column.valueField] : "&nbsp;";
            var numberRow = Math.ceil(item[column.valueField].length / 14);
            var height = 25;
            if(numberRow > 1){
                height = height + 15*(numberRow - 1);
            }
            if(height >= heightValue){
                heightValue = height;
            }
            var widthValue = ((index % 2 == 0) == that.options.resizeEvenColumn) ? (column.width - 2) : column.width;
            var td = $('<td width="' + widthValue + 'px">' + tdValue + '</td>');
            tr.append(td);
            width += column.width;
        });
        table.append(tr);
        div.append(table);
        container.append(div);

        var li = $('<li class="arrow-suggestion-select-items" style="border:none; width: ' + width + 'px; height: ' + heightValue +'px; "></li>');

        li.data('ui-autocomplete-item', item)
            .append('<a>' + container[0].innerHTML + '<div style="clear: both;"></div></a>')
            .appendTo(ul);
    },
    _resizeMenu: function () {
        var ul = this.menu.element;
        var maxWidth = this.options.columns.reduce(function(previous, current){
            return previous + current.width;
        }, 0);
        ul.outerWidth(maxWidth + 10);
    }
});

