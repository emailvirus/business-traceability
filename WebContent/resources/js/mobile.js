personInChargeSuggestion = function (empStr, deptStr) {
    $("#psnAutocomplete").on("listviewbeforefilter", function (e, data) {
        console.debug(e);
        var $ul = $(this);
            $input = $ul.prev().find('input');
            var value = $input.val(),
            html = "";
        $ul.html("");
        if (value.length > 0) {
            $ul.listview("refresh");
            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("comPointCode", $input.attr("comPointCode"));
                    request.setRequestHeader("companyCode", $input.attr("companyCode"));
                },
                url: location.origin + "/business_traceability/rest/dailyreport/suggest/employee/" + value,
                dataType: "json",
                crossDomain: true,
                success: function (data) {
                    if (data && data.length > 0) {
                        $.each(data, function (i, val) {
                            //var li = $('<li employee-name="' + val.dai_compemp_name + '" department-name="' + val.dai_employee_post + '"></li>');
                            var li = $('<li class="ui-li-static"></li>');
                            li.data(val);
                            var div = $('<div></div>');
                            var table = $('<table width="100%"></table>');

                            var trEmployee = $('<tr></tr>');
                            var tdEmployeeTitle = $('<td class="arrow-mobile-suggestion-item-title">' + empStr + ':</td>').width(200);
                            var tdEmployeeName = $('<td class="arrow-mobile-suggestion-item-content">' + val.dai_compemp_name + '</td>');
                            trEmployee.append(tdEmployeeTitle);
                            trEmployee.append(tdEmployeeName);
                            table.append(trEmployee);

                            var trDepartment = $('<tr></tr>');
                            var tdDepartmentTitle = $('<td class="arrow-mobile-suggestion-item-title">' + deptStr + ':</td>').width(200);
                            var tdDepartmentName = $('<td class="arrow-mobile-suggestion-item-content">' + val.dai_employee_post + '</td>');
                            trDepartment.append(tdDepartmentTitle);
                            trDepartment.append(tdDepartmentName);
                            table.append(trDepartment);

                            div.append(table);
                            li.append(div[0].innerHTML);

                            $ul.append(li);

                        });
                        console.debug($ul.children());
                        //$ul.listview("refresh");
                        //$ul.trigger("updatelayout");

                        $ul.children('li').bind('touchstart mousedown', function (e) {
                            $input.val($(this).data('dai_compemp_name'));
                            $(this).siblings().addBack().addClass("ui-screen-hidden");
                            $input.data('item-data', $(this).data());
                            setDepartmentName([{name: 'data',value: JSON.stringify($(this).data())}]);
                        });
                    }
                }
            });
        }
    });
};

departmentSuggestion = function (deptStr, empStr) {
    console.debug('init department');
    console.debug($("#departmentAutocomplete"));
    $("#departmentAutocomplete").on("listviewbeforefilter", function (e, data) {
        console.debug("On Change");
        var $ul = $(this);
        $input = $ul.prev().find('input');
        var value = $input.val(),
            html = "";
        $ul.html("");
        $ul.listview("refresh");
        if (value.length > 0) {

            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("comPointCode", $input.attr("comPointCode"));
                    request.setRequestHeader("companyCode", $input.attr("companyCode"));
                },
                url: location.origin + "/business_traceability/rest/dailyreport/suggest/department/" + $input.val(),
                dataType: "json",
                crossDomain: true,
                success: function (data) {
                    console.debug(data);
                    if (data && data.length > 0) {
                        $.each(data, function (i, val) {
                            var li = $('<li class="ui-li-static"></li>');
                            li.data(val);
                            var div = $('<div></div>');
                            var table = $('<table width="100%"></table>');

                            var trDepartment = $('<tr></tr>');
                            var tdDepartmentTitle = $('<td class="arrow-mobile-suggestion-item-title">' + deptStr + ':</td>').width(200);
                            var tdDepartmentName = $('<td class="arrow-mobile-suggestion-item-content">' + val.dai_employee_post + '</td>');
                            trDepartment.append(tdDepartmentTitle);
                            trDepartment.append(tdDepartmentName);
                            table.append(trDepartment);

                            var trEmployee = $('<tr></tr>');
                            var tdEmployeeTitle = $('<td class="arrow-mobile-suggestion-item-title">' + empStr + ':</td>').width(200);
                            var tdEmployeeName = $('<td class="arrow-mobile-suggestion-item-content">' + val.dai_compemp_name + '</td>');
                            trEmployee.append(tdEmployeeTitle);
                            trEmployee.append(tdEmployeeName);
                            table.append(trEmployee);

                            div.append(table);
                            li.append(div[0].innerHTML);
                            $ul.append(li);
                        });
                        //$ul.listview("refresh");
                        //$ul.trigger("updatelayout");

                        $ul.children('li').bind('touchstart mousedown', function (e) {
                            $input.val($(this).data('dai_employee_post'));
                            $(this).siblings().addBack().addClass("ui-screen-hidden");
                            $input.data('item-data', $(this).data());
                            setEmployeeName([{name: 'data',value: JSON.stringify($(this).data())}]);
                        });
                    }
                }
            });
        }
    });
};

companySuggestion = function (fields, mapListed) {
    $("#companyAutocomplete").on("listviewbeforefilter", function (e, data) {
        var $ul = $(this);
        $input = $ul.prev().find('input');
        var value = $input.val();
        $ul.listview("refresh");
        if (value.length > 0) {

            $.ajax({
                url: location.origin + "/business_traceability/rest/dailyreport/suggest/company/" + $input.val(),
                dataType: "json",
                crossDomain: true,
                success: function (data) {
                    console.debug(data);
                    if (data && data.length > 0) {
                        $.each(data, function (i, val) {
                            var titleWidth = 200;
                            var contentWidth = ($(this)[0].offsetWidth - 400)/2;
                            var li = $('<li  class="ui-li-static"></li>');
                            li.data(val);
                            var div = $('<div></div>');
                            var table = $('<table width="100%"></table>');

                            var trFirst = $('<tr></tr>');
                            var tdCustomerCodeTitle = $('<td class="arrow-mobile-suggestion-item-title">' + fields.customerCode + ':</td>').width(titleWidth);
                            var tdCustomerCode = $('<td class="arrow-mobile-suggestion-item-content">' + val.com_customer_code + '</td>').width(contentWidth);
                            var tdCompanyCodeTitle = $('<td class="arrow-mobile-suggestion-item-title">' + fields.companyCode + ':</td>').width(titleWidth);
                            var tdCompanyCode = $('<td class="arrow-mobile-suggestion-item-content">' + val.com_company_code + '</td>').width(contentWidth);
                            trFirst.append(tdCustomerCodeTitle);
                            trFirst.append(tdCustomerCode);
                            trFirst.append(tdCompanyCodeTitle);
                            trFirst.append(tdCompanyCode);
                            table.append(trFirst);

                            var trSecond = $('<tr></tr>');
                            var tdCompanyNameTitle = $('<td class="arrow-mobile-suggestion-item-title">' + fields.companyName + ':</td>').width(titleWidth);
                            var tdCompanyName = $('<td class="arrow-mobile-suggestion-item-content">' + val.com_company_name + '</td>').width(contentWidth);
                            var tdCompanyFuriganaTitle = $('<td class="arrow-mobile-suggestion-item-title">' + fields.companyFurigana + ':</td>').width(titleWidth);
                            var tdCompanyFurigana = $('<td class="arrow-mobile-suggestion-item-content">' + val.com_company_furigana + '</td>').width(contentWidth);
                            trSecond.append(tdCompanyNameTitle);
                            trSecond.append(tdCompanyName);
                            trSecond.append(tdCompanyFuriganaTitle);
                            trSecond.append(tdCompanyFurigana);
                            table.append(trSecond);

                            var trThird = $('<tr></tr>');
                            var tdUrlTitle = $('<td class="arrow-mobile-suggestion-item-title">' + fields.url + ':</td>').width(titleWidth);
                            var tdUrl = $('<td class="arrow-mobile-suggestion-item-content">' + val.com_url + '</td>').width(contentWidth);
                            var tdWorkTypeTitle = $('<td class="arrow-mobile-suggestion-item-title">' + fields.typeOfWork + ':</td>').width(titleWidth);
                            var tdWorkType = $('<td class="arrow-mobile-suggestion-item-content">' + val.worktype + '</td>').width(contentWidth);
                            trThird.append(tdUrlTitle);
                            trThird.append(tdUrl);
                            trThird.append(tdWorkTypeTitle);
                            trThird.append(tdWorkType);
                            table.append(trThird);

                            var trFour = $('<tr></tr>');
                            var tdListedTitle = $('<td class="arrow-mobile-suggestion-item-title">' + fields.listed + ':</td>').width(titleWidth);
                            var tdListed = $('<td class="arrow-mobile-suggestion-item-content">' + mapListed[val.listed] + '</td>');
                            trFour.append(tdListedTitle);
                            trFour.append(tdListed);
                            table.append(trFour);

                            div.append(table);
                            li.append(div[0].innerHTML);
                            $ul.append(li);
                            $ul.show();
                        });
                        //$ul.listview("refresh");
                        //$ul.trigger("updatelayout");

                        $ul.children('li').bind('touchstart mousedown', function (e) {
                            $input.val($(this).data('com_company_name'));
                            $ul.hide();
                            $(this).siblings().addBack().addClass("ui-screen-hidden");
                            $input.data('item-data', $(this).data());
                            setCompany([{name: 'data',value: JSON.stringify($(this).data())}]);
                        });
                    }
                }
            });
        }
    });
};