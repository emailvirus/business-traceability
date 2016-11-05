function genTable(jsonData, fields, idPanel) {
	var fields = JSON.parse(fields);
	fields= fields.sort();
	debugger;
	var data = JSON.parse(jsonData.replace(/\n/g, ""))
	var out = "<table id='myTable'><thead><tr>";
	for (j = 0; j < fields.length; j++) {
		out += "<td style=\"padding-right:30px\"><h4>" + fields[j] + "</h4></td>";
	}
	out += "</tr></thead><tbody>";
	for (i = 0; i < data.length; i++) {
		out += "<tr>";
		for (j = 0; j < fields.length; j++) {
			if(jQuery.type(data[i][fields[j]]) === "undefined"){
			   out+= "<td></td>";
			   continue;
			}
			out += "<td style=\"padding-right:30px\">" + data[i][fields[j]]+ "</td>";
		}
		out += "</tr>";
	}
	out += "</tbody></table>";
	
	$(idPanel).append(out);
}