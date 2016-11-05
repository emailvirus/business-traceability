var isModified = false;
var customerCodeMask = '____0000';
var customerCodeRegex = /^[a-zA-Z](\d){3}$/;

function start() {
    if (PF('globalBlockUI_js')) {
        PF('globalBlockUI_js').block();
    }
}

function stop() {
    if (PF('globalBlockUI_js')) {
        PF('globalBlockUI_js').unblock();
    }
}

// http://stackoverflow.com/questions/14112708/start-calling-js-function-when-pc-wakeup-from-sleep-mode
var lastTimeCheck = Date.now(); // save last time check and PC not stand by
var TIME_RATE = 1000000; // every 10s, system check PC was sleep
function checkPCSleep() {
    if (Date.now() - lastTimeCheck >= TIME_RATE * 2) {
        $('#hiddenLogout').click();
        location.reload(true);
        lastTimeCheck = Date.now();
        return;
    }
    lastTimeCheck = Date.now();
    setTimeout(checkPCSleep, TIME_RATE);
}
lastTimeCheck = Date.now();
checkPCSleep();

function split(val) {
    return val.split(/\s/g);
}

function extractLast(term) {
    return split(term).pop();
}

var SetCaretAtEnd = function(elem) {
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
    } else if (elem.selectionStart || elem.selectionStart == '0') {
        // Firefox/Chrome
        elem.selectionStart = elemLen;
        elem.selectionEnd = elemLen;
    }
};

// $.widget('custom.mcautocomplete', $.ui.autocomplete, {
// _create: function () {
// this._super();
// this.widget().menu("option", "items", "> :not(.ui-widget-header)");
// },
// _renderMenu: function (ul, items) {
// var that = this;
// ul.addClass('arrow-suggestion-panel');
//
// // Render header
// if (this.options.showHeader) {
// var li = $('<li class="ui-widget-header suggestion-header"></li>');
// //var container = $('<div class="ui-widget-header
// suggestion-header"></div>');
// var div = $('<div class="ui-autocomplete-panel ui-widget-content
// ui-helper-hidden arrow-suggestion-header-item"></div>');
// var table = $('<table class="ui-autocomplete-items ui-autocomplete-table
// ui-widget-content ui-widget ui-corner-all ui-helper-reset">');
// var header = '<thead><tr>';
// $.each(this.options.columns, function (index, column) {
// var td = '<td class="ui-state-default width-fifty" style="width:' +
// column.width + 'px;">' + column.name + '</td>';
// header += td;
// });
// header += '</tr></thead>';
// table.append(header);
// table.append('</table>');
// div.append(table);
// div.appendTo(li);
// li.appendTo(ul);
// }
//
// // Render body
// $.each(items, function (index, item) {
// that._renderItem(ul, item);
// });
//
// },
// _renderItem: function (ul, item) {
// var width = 0;
// var heightValue = 25;
// var container = $('<div"></div>');
// var div = $('<div class="ui-autocomplete-panel ui-widget-content
// ui-helper-hidden arrow-suggestion-render-items"></div>');
// var table = $('<table class="ui-autocomplete-items ui-autocomplete-table
// ui-widget-content ui-widget ui-corner-all ui-helper-reset" style="width:
// 100%;"></table>');
// var tr = $('<tr id="' + item.id + '" class="ui-autocomplete-item
// ui-autocomplete-row ui-widget-content ui-corner-all" ></tr>');
// var that = this;
// $.each(this.options.columns, function(index, column){
// var tdValue = (item[column.valueField] != "") ? item[column.valueField] :
// "&nbsp;";
// var numberRow = Math.ceil(item[column.valueField].length / 14);
// var height = 25;
// if(numberRow > 1){
// height = height + 15*(numberRow - 1);
// }
// if(height >= heightValue){
// heightValue = height;
// }
// var widthValue = ((index % 2 == 0) == that.options.resizeEvenColumn) ?
// (column.width - 2) : column.width;
// var td = $('<td width="' + widthValue + 'px">' + tdValue + '</td>');
// tr.append(td);
// width += column.width;
// });
// table.append(tr);
// div.append(table);
// container.append(div);
//
// var li = $('<li class="arrow-suggestion-select-items" style="border:none;
// width: ' + width + 'px; height: ' + heightValue +'px; "></li>');
//
// li.data('ui-autocomplete-item', item)
// .append('<a>' + container[0].innerHTML + '<div style="clear:
// both;"></div></a>')
// .appendTo(ul);
// },
// _resizeMenu: function () {
// var ul = this.menu.element;
// var maxWidth = this.options.columns.reduce(function(previous, current){
// return previous + current.width;
// }, 0);
// ul.outerWidth(maxWidth + 10);
// }
// });

// $.widget('custom.mobileAutocomplete', $.ui.autocomplete, {
// _create: function () {
// this._super();
// this.widget().menu("option", "items", "> :not(.ui-widget-header)");
// },
// _renderMenu: function (ul, items) {
// var that = this;
// ul.addClass('arrow-mobile-suggestion-panel');
// //debugger;
// //$(ul).width($('.mobile-suggestion')[0].offsetWidth);
// $(ul).css('margin-left', '-2px');
//
// // Render body
// $.each(items, function (index, item) {
// that._renderItem(ul, item);
// });
//
// $(ul).find('a').last().css('border-bottom-width', '1px');
// },
// _renderItem: function (ul, item) {
// var biggestDiv = $('<div></div>');
// var container = $('<div></div>');
// var panel = $('<div class="ui-controlgroup ui-controlgroup-vertical
// ui-corner-all ui-themes arrow-mobile-suggestion-panel"></div>');
// var div = $('<div class="ui-controlgroup-controls"></div>')
// var a = $('<a id="' + item.id + '" href="#" class="ui-autocomplete-item
// ui-btn ui-corner-all ui-shadow ui-page-theme-a"></a>')
// var table = $('<table></table>');
//
// $.each(this.options.columns, function(index, column){
// var tr = $('<tr></tr>');
// var tdTitle = $('<td class="arrow-mobile-suggestion-item-title">' +
// column.name + ':</td>').width(column.width);
// var tdValue = (item[column.valueField] != "") ? item[column.valueField] :
// "&nbsp;";
// var tdContent = $('<td class="arrow-mobile-suggestion-item-content">' +
// tdValue + '</td>').width($('.mobile-suggestion')[0].offsetWidth -
// column.width);
// tr.append(tdTitle);
// tr.append(tdContent);
// table.append(tr);
// });
//
// a.append(table);
// div.append(a);
// panel.append(div);
// container.append(panel);
// biggestDiv.append(container);
//
// var li = $('<li style="border:none; float: left; width: ' +
// $('.mobile-suggestion')[0].offsetWidth + 'px;"></li>');
//
// li.data('ui-autocomplete-item', item)
// .append( biggestDiv[0].innerHTML )
// .appendTo(ul);
// },
// _resizeMenu: function () {
// var ul = this.menu.element;
// ul.outerWidth($('.mobile-suggestion')[0].offsetWidth);
// }
// });

var disableMenuFunction = function(event) {
    if (event.keyCode === $.ui.keyCode.TAB &&
        $(this).autocomplete("instance").menu.active) {
        event.preventDefault();
    }
};

var triggerRowEdit = function() {
    var editors = $('.ui-row-editor span.ui-icon-pencil');
    if (editors.length > 0) {
        editors[0].click();
    }
};

var triggerCancelEditRow = function() {
    var editors = $('.ui-row-editor span.ui-icon-close');
    if (editors.length > 0) {
        editors[0].click();
    }
};

var setRowDataHandler = function(event) {
	if (!this.attributes['data-rk'])
	{
		return;
	}
	
    var table = event.data[0];
    var buttonWidget = event.data[1];
    
   
    if (sequence > 0 && dataRowKey)
    {
    	if (!!buttonWidget) {
            PrimeFaces.widgets[buttonWidget]
                .enable();
        }
    	selectedRowKey = this.attributes['data-rk'].value;
    }else if (sequence > 0 && !dataRowKey)
    {
    	if (!!buttonWidget) {
            PrimeFaces.widgets[buttonWidget]
                .disable();
        }
    	selectedRowKey = null;
    }else if (sequence == 0　)
    {
    	if(event.ctrlKey||event.shiftKey){
   		 table.unselectAllRows();
   		 table.selectRow($(this));
    	}
    	var findCheckboxChecked = $(table.tbody).find('span.ui-icon-check');
    	if (!!buttonWidget) {
    		if (findCheckboxChecked.length > 0)
	            PrimeFaces.widgets[buttonWidget]
	                .enable();
    		else
    		{
    			PrimeFaces.widgets[buttonWidget]
                .disable();
    		}
        }

    	selectedRowKey = this.attributes['data-rk'].value;
    	if (table.widgetVar == 'accountTable')
    	{
	    	var checkbox = $(table.tbody).find('span.ui-icon-check');
	     	var hiddenInput = checkbox.parents('tr').find('input[type=hidden]');
	     	if (hiddenInput.length > 0)
	     	{
	     		idCompany = hiddenInput[0].value;
	     		selectedIdComEntity = hiddenInput[0].attributes['idComEntity'].value;
	     	}
    	}
    	
    	
    }
    sequence = 0;
    if (PrimeFaces.widgets["sansanCompanyTable"] != null) {
        updateDataCustomerPanel();
    }
};

var clearAfterChangePage = function(tableWidget, buttonWidget) {
			var table = PrimeFaces.widgets[tableWidget];
			if (table)
			{
				table.unselectAllRows();
			}
            var button = PrimeFaces.widgets[buttonWidget];
            if (button)
            {
            	button.disable();
            }
    }

var selectedRowKey = null;
var dataRowKey = null;
var sequence = 0;
var idCompany = null;
var selectedIdComEntity = null;
var initDataTableSelectRow = function(formId, tableId, tableWidget, buttonWidget) {
    $(document).ready(function() {
        var table = PrimeFaces.widgets[tableWidget];

        $(document).on('click', '#' + formId + '\\:' + tableId + ' .ui-chkbox-box',
            function() {
        	sequence ++;
                if ($(this).find('span')[0].classList.contains('ui-icon-blank')) {
                    if (!!buttonWidget) {
                        PrimeFaces.widgets[buttonWidget].disable();
                        dataRowKey = null;
                        idCompany = null;
                        selectedIdComEntity = null;
                    }
                    return;
                }

                table.unselectAllRows();

                var selectedRow = $(this).parents('tr');
                table.selectRow(selectedRow);

                // Case user choose other page from
                // paginator then previous row do
                // not unselect, we need remove it
                if (table.selection.length > 1) {
                    table.selection.splice(0, 1);
                    $(table.selectionHolder)[0].value = table.selection[0];
                }

                if (!!buttonWidget) {
                    PrimeFaces.widgets[buttonWidget].enable();
                }
                dataRowKey = $(this).parents(
                    'tr')[0].attributes['data-rk'].value;
                
                if (tableWidget == 'accountTable')
                {
	                var checkbox = $(PrimeFaces.widgets[tableWidget].tbody).find('span.ui-icon-check');
	            	var hiddenInput = checkbox.parents('tr').find('input[type=hidden]');
	            	if (hiddenInput.length > 0)
	            	{
	            		idCompany = hiddenInput[0].value;
	            		selectedIdComEntity = hiddenInput[0].attributes['idComEntity'].value;
	            	}
                }
            });
        $(document).off(
            'click',
            '#' + formId + '\\:' + tableId +
            ' tr.ui-widget-content', setRowDataHandler
        );
        var data = [table, buttonWidget];
        $(document).on(
            'click',
            '#' + formId + '\\:' + tableId +
            ' tr.ui-widget-content', data, setRowDataHandler
        );
        
       
        
        
    });

}

var selectSansanCompanyTableRow = function() {
	var table = PrimeFaces.widgets['sansanCompanyTable'];

	var text = PrimeFaces.widgets['accountTable'].tbody.find('span.ui-icon-check').parents('tr').find('td')[2].innerText;
	if(text.length == 0) {
		table.unselectAllRows();
	}
	if(table.selection.length > 0){
		table.unselectAllRows();
	}	
	var listTr = $(table.tbody).children();
	for (var i = 0; i < listTr.length; i++) {
		var dataRowKey = listTr[i].attributes['data-rk'];
		if (dataRowKey == null) {
			continue;
		}
		if (dataRowKey.value == idCompany) {
			table.selectRow(i);
			selectedRowKey = listTr[i].attributes['data-rk'].value;
			updateDataCustomerPanel();
		} else {
			var idComEntity = $(listTr[i]).find('input[type=hidden]')[0].attributes['idComEntity'];
			if (idComEntity != null && idComEntity.value != selectedIdComEntity) {
				$(listTr[i]).find('div.ui-chkbox-box').css('display', 'none');
				$(listTr[i]).addClass('ui-state-disabled');
				$(listTr[i]).on('click', function() {
					return false;
				});
			}
		}
	}
	
}

var updateDataCustomerPanel = function() {
    var tr = $(PrimeFaces.widgets["sansanCompanyTable"].jqId).find(
        'tr[aria-selected=true]');
    if (tr.length > 0) {
        var idSanCompany = tr.find('input[type="hidden"]')[0].value;
        var idIntSan = tr[0].attributes['data-rk'].value;
        var displaySansanCompanyID = idSanCompany + '(' + idIntSan + ')';
        $('#displaySanCompanyId')[0].innerText = displaySansanCompanyID;
        $('#displayCompanyName')[0].innerText = tr.find('td')[1].innerText;
        $('#maintenaceCompanyForm\\:hiddenSanCompanyId')[0].value=idIntSan;
        
    } else {
        $('#displaySanCompanyId')[0].innerText = 'N/A';
        $('#displayCompanyName')[0].innerText = 'N/A';
        $('#maintenaceCompanyForm\\:hiddenSanCompanyId')[0].value='N/A';
    }
}

function changeStatus() {
    var searchButton = PrimeFaces.widgets["searchButton"];
    var linkStatus = PrimeFaces.widgets["linkStatus"];
    var inputIdCompany = PrimeFaces.widgets["inputIdCompany"];
    if (linkStatus.inputs[0].checked == true) {
        inputIdCompany.jq[0].value = '';
        inputIdCompany.disable();
        searchButton.enable();
    } else {
        inputIdCompany.enable();
        updateSearchButton();
    }
}
var updateSearchButton = function() {
    var linkStatus = PrimeFaces.widgets["linkStatus"];
    if (linkStatus.inputs[1].checked == true) {
        var searchButton = PrimeFaces.widgets["searchButton"];
        var inputIdCompany = PrimeFaces.widgets["inputIdCompany"];
        var inputCompanyName = PrimeFaces.widgets["inputCompanyName"];
        if (inputIdCompany.jq[0].value.length > 0 ||
            inputCompanyName.jq[0].value.length > 0) {
            searchButton.enable();
        } else {
            searchButton.disable();
        }
    }
};

function checkBtnSearch() {
    var inputCustomer = PrimeFaces.widgets["customer_code"];
    if (inputCustomer == null)
        return;
    var searchButtonCustomerCode = PrimeFaces.widgets["searchButtonCustomerCode"];
    if (inputCustomer.jq[0].value.length > 0 && customerCodeRegex.test(inputCustomer.jq[0].value)){
//    	inputCustomer.jq[0].value = inputCustomer.jq[0].value.toUpperCase();
    	searchButtonCustomerCode.enable();
    }
    else
        searchButtonCustomerCode.disable();
}

function disableSelectBtnAfterSearch() {
    PrimeFaces.widgets["buttonSelect"].disable();
    PrimeFaces.widgets['accountTable'].unselectAllRows();
    PrimeFaces.widgets['accountTable'].paginator.setPage(0);
}

function updateFreezeReason() {
    if (PrimeFaces.widgets['client_code_status'] == null)
        return;
    if (PrimeFaces.widgets['client_code_status'].input[0].value == '00001' ||
        PrimeFaces.widgets['client_code_status'].input[0].value == '09999') {
        PrimeFaces.widgets["freezeReason"].selectValue('');
        document.getElementById("freezeReason").style.visibility = 'visible';
        document.getElementById("freezeReasonLabel").style.visibility = 'visible';
    } else {
        document.getElementById("freezeReason").style.visibility = 'hidden';
        document.getElementById("freezeReasonLabel").style.visibility = 'hidden';
    }
}
function loadFreezeReason() {
    if (PrimeFaces.widgets['client_code_status'] == null)
        return;
    if (PrimeFaces.widgets['client_code_status'].input[0].value == '00001' ||
        PrimeFaces.widgets['client_code_status'].input[0].value == '09999') {
        document.getElementById("freezeReason").style.visibility = 'visible';
        document.getElementById("freezeReasonLabel").style.visibility = 'visible';
    } else {
        document.getElementById("freezeReason").style.visibility = 'hidden';
        document.getElementById("freezeReasonLabel").style.visibility = 'hidden';
    }
}

function enableBtnSelectAfterCancel() {
    PrimeFaces.widgets['buttonSelect'].enable();
}

function disableSelectBtnAfterSelect() {
    PrimeFaces.widgets['buttonSelect'].disable();
}



function editSavingPathTable(){
	document.getElementById('savingPathForm:saveSavingButton').style.display='inline';
	document.getElementById('savingPathForm:cancelButton').style.display='inline';
	document.getElementById('savingPathForm:editSavingButton').style.display='none';
		$('.inputSavingPath')[0].attributes.removeNamedItem('readOnly');
	$('#savingPathForm\\:inputSavingPath').removeClass('ui-state-disabled');
	currentSavingPath=document.getElementById('savingPathForm:inputSavingPath').value;
	}
function saveSavingPathTable(){
	document.getElementById('savingPathForm:saveSavingButton').style.display='none';
	document.getElementById('savingPathForm:cancelButton').style.display='none';
	document.getElementById('savingPathForm:editSavingButton').style.display='inline';
	$('.inputSavingPath')[0].setAttribute('readOnly','readOnly');
	$('#savingPathForm\\:inputSavingPath').addClass('ui-state-disabled');
	}
function cancelSavingPathTable(){
	document.getElementById('savingPathForm:saveSavingButton').style.display='none';
	document.getElementById('savingPathForm:cancelButton').style.display='none';
	document.getElementById('savingPathForm:editSavingButton').style.display='inline';
	$('.inputSavingPath')[0].setAttribute('readOnly','readOnly');
	$('#savingPathForm\\:inputSavingPath').addClass('ui-state-disabled');
	document.getElementById('savingPathForm:inputSavingPath').value=currentSavingPath;
	}
function addReadOnlyInputText(){
	$('.inputSavingPath')[0].setAttribute('readOnly','readOnly');
	}
function copyClipboard(text){
	 var copyTextarea = document.querySelector(text);
	 copyTextarea.select();
	 document.execCommand('copy');
	}
function downloadAllOnlyMe(){    
    var nameProject = $('.param-project-hidden')[0].innerHTML;
    var folderPathEncode =$('.param-project-hidden')[1].innerHTML;
    var linkDownload =$('.register-link');
    for( i =0;i < linkDownload.length;i++)
     {
	 var downloadBassCsv = $('<a />').attr('href', location.origin + nameProject+"/rest/bass/download/"+folderPathEncode+"/"+linkDownload[i].innerHTML).attr('download', linkDownload[i].innerHTML).appendTo('body');
        downloadBassCsv[0].click();   
        downloadBassCsv.remove();   
     }
}
function onHover(button){
	button.classList.add("ui-state-hover");
	}
function normalState(button){
	button.classList.remove("ui-state-hover");
    }

function upperCaseInputByWidgetVar(widgetVar){
	var inputCustomer = PrimeFaces.widgets[widgetVar];
	if(inputCustomer.jq[0].value.length > 0){
		inputCustomer.jq[0].value = inputCustomer.jq[0].value.toUpperCase();
	}
}

