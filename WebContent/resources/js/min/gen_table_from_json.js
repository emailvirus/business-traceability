function genTable(t,e,d){var e=JSON.parse(e)
e=e.sort()
var r=JSON.parse(t.replace(/\n/g,"")),a="<table id='myTable'><thead><tr>"
for(j=0;j<e.length;j++)a+='<td style="padding-right:30px"><h4>'+e[j]+"</h4></td>"
for(a+="</tr></thead><tbody>",i=0;i<r.length;i++){for(a+="<tr>",j=0;j<e.length;j++)a+="undefined"!==jQuery.type(r[i][e[j]])?'<td style="padding-right:30px">'+r[i][e[j]]+"</td>":"<td></td>"
a+="</tr>"}a+="</tbody></table>",$(d).append(a)}