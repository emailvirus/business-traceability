(function(){var t,e=[].indexOf||function(t){for(var e=0,n=this.length;e<n;e++)if(e in this&&this[e]===t)return e
return-1},n=[].slice,r=function(t,e){return function(){return t.apply(e,arguments)}},a={}.hasOwnProperty;(t=function(t){return"object"==typeof exports&&"object"==typeof module?t(require("jquery")):"function"==typeof define&&define.amd?define(["jquery"],t):t(jQuery)})(function(t){var o,i,s,u,l,c,p,h,f,d,g,m,v,b,A,C,y,w
return i=function(t,e,n){var r,a,o,i
for(t+="",a=t.split("."),o=a[0],i=a.length>1?n+a[1]:"",r=/(\d+)(\d{3})/;r.test(o);)o=o.replace(r,"$1"+e+"$2")
return o+i},g=function(e){var n
return n={digitsAfterDecimal:2,scaler:1,thousandsSep:",",decimalSep:".",prefix:"",suffix:"",showZero:!1},e=t.extend(n,e),function(t){var n
return isNaN(t)||!isFinite(t)?"":0!==t||e.showZero?(n=i((e.scaler*t).toFixed(e.digitsAfterDecimal),e.thousandsSep,e.decimalSep),""+e.prefix+n+e.suffix):""}},A=g(),C=g({digitsAfterDecimal:0}),y=g({digitsAfterDecimal:1,scaler:100,suffix:"%"}),s={count:function(t){return null==t&&(t=C),function(){return function(e,n,r){return{count:0,push:function(){return this.count++},value:function(){return this.count},format:t}}}},countUnique:function(t){return null==t&&(t=C),function(n){var r
return r=n[0],function(n,a,o){return{uniq:[],push:function(t){var n
if(n=t[r],e.call(this.uniq,n)<0)return this.uniq.push(t[r])},value:function(){return this.uniq.length},format:t,numInputs:null!=r?0:1}}}},listUnique:function(t){return function(n){var r
return r=n[0],function(n,a,o){return{uniq:[],push:function(t){var n
if(n=t[r],e.call(this.uniq,n)<0)return this.uniq.push(t[r])},value:function(){return this.uniq.join(t)},format:function(t){return t},numInputs:null!=r?0:1}}}},sum:function(t){return null==t&&(t=A),function(e){var n
return n=e[0],function(e,r,a){return{sum:0,push:function(t){if(!isNaN(parseFloat(t[n])))return this.sum+=parseFloat(t[n])},value:function(){return this.sum},format:t,numInputs:null!=n?0:1}}}},min:function(t){return null==t&&(t=A),function(e){var n
return n=e[0],function(e,r,a){return{val:null,push:function(t){var e,r
if(r=parseFloat(t[n]),!isNaN(r))return this.val=Math.min(r,null!=(e=this.val)?e:r)},value:function(){return this.val},format:t,numInputs:null!=n?0:1}}}},max:function(t){return null==t&&(t=A),function(e){var n
return n=e[0],function(e,r,a){return{val:null,push:function(t){var e,r
if(r=parseFloat(t[n]),!isNaN(r))return this.val=Math.max(r,null!=(e=this.val)?e:r)},value:function(){return this.val},format:t,numInputs:null!=n?0:1}}}},average:function(t){return null==t&&(t=A),function(e){var n
return n=e[0],function(e,r,a){return{sum:0,len:0,push:function(t){if(!isNaN(parseFloat(t[n])))return this.sum+=parseFloat(t[n]),this.len++},value:function(){return this.sum/this.len},format:t,numInputs:null!=n?0:1}}}},sumOverSum:function(t){return null==t&&(t=A),function(e){var n,r
return r=e[0],n=e[1],function(e,a,o){return{sumNum:0,sumDenom:0,push:function(t){if(isNaN(parseFloat(t[r]))||(this.sumNum+=parseFloat(t[r])),!isNaN(parseFloat(t[n])))return this.sumDenom+=parseFloat(t[n])},value:function(){return this.sumNum/this.sumDenom},format:t,numInputs:null!=r&&null!=n?0:2}}}},sumOverSumBound80:function(t,e){return null==t&&(t=!0),null==e&&(e=A),function(n){var r,a
return a=n[0],r=n[1],function(n,o,i){return{sumNum:0,sumDenom:0,push:function(t){if(isNaN(parseFloat(t[a]))||(this.sumNum+=parseFloat(t[a])),!isNaN(parseFloat(t[r])))return this.sumDenom+=parseFloat(t[r])},value:function(){var e
return e=t?1:-1,(.821187207574908/this.sumDenom+this.sumNum/this.sumDenom+1.2815515655446004*e*Math.sqrt(.410593603787454/(this.sumDenom*this.sumDenom)+this.sumNum*(1-this.sumNum/this.sumDenom)/(this.sumDenom*this.sumDenom)))/(1+1.642374415149816/this.sumDenom)},format:e,numInputs:null!=a&&null!=r?0:2}}}},fractionOf:function(t,e,r){return null==e&&(e="total"),null==r&&(r=y),function(){var a
return a=1<=arguments.length?n.call(arguments,0):[],function(n,o,i){return{selector:{total:[[],[]],row:[o,[]],col:[[],i]}[e],inner:t.apply(null,a)(n,o,i),push:function(t){return this.inner.push(t)},format:r,value:function(){return this.inner.value()/n.getAggregator.apply(n,this.selector).inner.value()},numInputs:t.apply(null,a)().numInputs}}}}},u=function(t){return{Count:t.count(C),"Count Unique Values":t.countUnique(C),"List Unique Values":t.listUnique(", "),Sum:t.sum(A),"Integer Sum":t.sum(C),Average:t.average(A),Minimum:t.min(A),Maximum:t.max(A),"Sum over Sum":t.sumOverSum(A),"80% Upper Bound":t.sumOverSumBound80(!0,A),"80% Lower Bound":t.sumOverSumBound80(!1,A),"Sum as Fraction of Total":t.fractionOf(t.sum(),"total",y),"Sum as Fraction of Rows":t.fractionOf(t.sum(),"row",y),"Sum as Fraction of Columns":t.fractionOf(t.sum(),"col",y),"Count as Fraction of Total":t.fractionOf(t.count(),"total",y),"Count as Fraction of Rows":t.fractionOf(t.count(),"row",y),"Count as Fraction of Columns":t.fractionOf(t.count(),"col",y)}}(s),v={Table:function(t,e){return m(t,e)},"Table Barchart":function(e,n){return t(m(e,n)).barchart()},Heatmap:function(e,n){return t(m(e,n)).heatmap()},"Row Heatmap":function(e,n){return t(m(e,n)).heatmap("rowheatmap")},"Col Heatmap":function(e,n){return t(m(e,n)).heatmap("colheatmap")}},h={en:{aggregators:u,renderers:v,localeStrings:{renderError:"An error occurred rendering the PivotTable results.",computeError:"An error occurred computing the PivotTable results.",uiRenderError:"An error occurred rendering the PivotTable UI.",selectAll:"Select All",selectNone:"Select None",tooMany:"(too many to list)",filterResults:"Filter results",totals:"Totals",vs:"vs",by:"by"}}},f=["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],l=["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],w=function(t){return("0"+t).substr(-2,2)},c={bin:function(t,e){return function(n){return n[t]-n[t]%e}},dateFormat:function(t,e,n,r,a){var o
return null==n&&(n=!1),null==r&&(r=f),null==a&&(a=l),o=n?"UTC":"",function(n){var i
return i=new Date(Date.parse(n[t])),isNaN(i)?"":e.replace(/%(.)/g,function(t,e){switch(e){case"y":return i["get"+o+"FullYear"]()
case"m":return w(i["get"+o+"Month"]()+1)
case"n":return r[i["get"+o+"Month"]()]
case"d":return w(i["get"+o+"Date"]())
case"w":return a[i["get"+o+"Day"]()]
case"x":return i["get"+o+"Day"]()
case"H":return w(i["get"+o+"Hours"]())
case"M":return w(i["get"+o+"Minutes"]())
case"S":return w(i["get"+o+"Seconds"]())
default:return"%"+e}})}}},d=function(t){return function(t,e){var n,r,a,o,i,s,u
if(s=/(\d+)|(\D+)/g,i=/\d/,u=/^0/,"number"==typeof t||"number"==typeof e)return isNaN(t)?1:isNaN(e)?-1:t-e
if(n=String(t).toLowerCase(),a=String(e).toLowerCase(),n===a)return 0
if(!i.test(n)||!i.test(a))return n>a?1:-1
for(n=n.match(s),a=a.match(s);n.length&&a.length;)if(r=n.shift(),o=a.shift(),r!==o)return i.test(r)&&i.test(o)?r.replace(u,".0")-o.replace(u,".0"):r>o?1:-1
return n.length-a.length}}(this),b=function(t){var e,n,r
n={}
for(e in t)r=t[e],n[r]=e
return function(t,e){return null!=n[t]&&null!=n[e]?n[t]-n[e]:null!=n[t]?-1:null!=n[e]?1:d(t,e)}},p=function(e,n){var r
return r=e(n),t.isFunction(r)?r:d},o=function(){function e(t,n){this.getAggregator=r(this.getAggregator,this),this.getRowKeys=r(this.getRowKeys,this),this.getColKeys=r(this.getColKeys,this),this.sortKeys=r(this.sortKeys,this),this.arrSort=r(this.arrSort,this),this.aggregator=n.aggregator,this.aggregatorName=n.aggregatorName,this.colAttrs=n.cols,this.rowAttrs=n.rows,this.valAttrs=n.vals,this.sorters=n.sorters,this.tree={},this.rowKeys=[],this.colKeys=[],this.rowTotals={},this.colTotals={},this.allTotal=this.aggregator(this,[],[]),this.sorted=!1,e.forEachRecord(t,n.derivedAttributes,function(t){return function(e){if(n.filter(e))return t.processRecord(e)}}(this))}return e.forEachRecord=function(e,n,r){var o,i,s,u,l,c,p,h,f,d,g,m
if(o=t.isEmptyObject(n)?r:function(t){var e,a,o
for(e in n)o=n[e],t[e]=null!=(a=o(t))?a:t[e]
return r(t)},t.isFunction(e))return e(o)
if(t.isArray(e)){if(t.isArray(e[0])){d=[]
for(s in e)if(a.call(e,s)&&(i=e[s],s>0)){h={},f=e[0]
for(u in f)a.call(f,u)&&(l=f[u],h[l]=i[u])
d.push(o(h))}return d}for(g=[],c=0,p=e.length;c<p;c++)h=e[c],g.push(o(h))
return g}if(e instanceof jQuery)return m=[],t("thead > tr > th",e).each(function(e){return m.push(t(this).text())}),t("tbody > tr",e).each(function(e){return h={},t("td",this).each(function(e){return h[m[e]]=t(this).text()}),o(h)})
throw new Error("unknown input format")},e.convertToArray=function(t){var n
return n=[],e.forEachRecord(t,{},function(t){return n.push(t)}),n},e.prototype.arrSort=function(t){var e,n
return n=function(){var n,r,a
for(a=[],n=0,r=t.length;n<r;n++)e=t[n],a.push(p(this.sorters,e))
return a}.call(this),function(t,e){var r,o,i
for(o in n)if(a.call(n,o)&&(i=n[o],r=i(t[o],e[o]),0!==r))return r
return 0}},e.prototype.sortKeys=function(){if(!this.sorted)return this.sorted=!0,this.rowKeys.sort(this.arrSort(this.rowAttrs)),this.colKeys.sort(this.arrSort(this.colAttrs))},e.prototype.getColKeys=function(){return this.sortKeys(),this.colKeys},e.prototype.getRowKeys=function(){return this.sortKeys(),this.rowKeys},e.prototype.processRecord=function(t){var e,n,r,a,o,i,s,u,l,c,p,h,f
for(e=[],h=[],u=this.colAttrs,a=0,o=u.length;a<o;a++)f=u[a],e.push(null!=(l=t[f])?l:"null")
for(c=this.rowAttrs,s=0,i=c.length;s<i;s++)f=c[s],h.push(null!=(p=t[f])?p:"null")
if(r=h.join(String.fromCharCode(0)),n=e.join(String.fromCharCode(0)),this.allTotal.push(t),0!==h.length&&(this.rowTotals[r]||(this.rowKeys.push(h),this.rowTotals[r]=this.aggregator(this,h,[])),this.rowTotals[r].push(t)),0!==e.length&&(this.colTotals[n]||(this.colKeys.push(e),this.colTotals[n]=this.aggregator(this,[],e)),this.colTotals[n].push(t)),0!==e.length&&0!==h.length)return this.tree[r]||(this.tree[r]={}),this.tree[r][n]||(this.tree[r][n]=this.aggregator(this,h,e)),this.tree[r][n].push(t)},e.prototype.getAggregator=function(t,e){var n,r,a
return a=t.join(String.fromCharCode(0)),r=e.join(String.fromCharCode(0)),n=0===t.length&&0===e.length?this.allTotal:0===t.length?this.colTotals[r]:0===e.length?this.rowTotals[a]:this.tree[a][r],null!=n?n:{value:function(){return null},format:function(){return""}}},e}(),t.pivotUtilities={aggregatorTemplates:s,aggregators:u,renderers:v,derivers:c,locales:h,naturalSort:d,numberFormat:g,sortAs:b,PivotData:o},m=function(e,n){var r,o,i,s,u,l,c,p,h,f,d,g,m,v,b,A,C,y,w,x,N
l={localeStrings:{totals:"Totals"}},n=t.extend(l,n),i=e.colAttrs,d=e.rowAttrs,m=e.getRowKeys(),u=e.getColKeys(),f=document.createElement("table"),f.className="pvtTable",v=function(t,e,n){var r,a,o,i,s,u,l,c
if(0!==e){for(i=!0,c=r=0,s=n;0<=s?r<=s:r>=s;c=0<=s?++r:--r)t[e-1][c]!==t[e][c]&&(i=!1)
if(i)return-1}for(a=0;e+a<t.length;){for(l=!1,c=o=0,u=n;0<=u?o<=u:o>=u;c=0<=u?++o:--o)t[e][c]!==t[e+a][c]&&(l=!0)
if(l)break
a++}return a}
for(p in i)if(a.call(i,p)){o=i[p],y=document.createElement("tr"),0===parseInt(p)&&0!==d.length&&(A=document.createElement("th"),A.setAttribute("colspan",d.length),A.setAttribute("rowspan",i.length),y.appendChild(A)),A=document.createElement("th"),A.className="pvtAxisLabel",A.textContent=o,y.appendChild(A)
for(c in u)a.call(u,c)&&(s=u[c],N=v(u,parseInt(c),parseInt(p)),N!==-1&&(A=document.createElement("th"),A.className="pvtColLabel",A.textContent=s[p],A.setAttribute("colspan",N),parseInt(p)===i.length-1&&0!==d.length&&A.setAttribute("rowspan",2),y.appendChild(A)))
0===parseInt(p)&&(A=document.createElement("th"),A.className="pvtTotalLabel",A.innerHTML=n.localeStrings.totals,A.setAttribute("rowspan",i.length+(0===d.length?0:1)),y.appendChild(A)),f.appendChild(y)}if(0!==d.length){y=document.createElement("tr")
for(c in d)a.call(d,c)&&(h=d[c],A=document.createElement("th"),A.className="pvtAxisLabel",A.textContent=h,y.appendChild(A))
A=document.createElement("th"),0===i.length&&(A.className="pvtTotalLabel",A.innerHTML=n.localeStrings.totals),y.appendChild(A),f.appendChild(y)}for(c in m)if(a.call(m,c)){g=m[c],y=document.createElement("tr")
for(p in g)a.call(g,p)&&(w=g[p],N=v(m,parseInt(c),parseInt(p)),N!==-1&&(A=document.createElement("th"),A.className="pvtRowLabel",A.textContent=w,A.setAttribute("rowspan",N),parseInt(p)===d.length-1&&0!==i.length&&A.setAttribute("colspan",2),y.appendChild(A)))
for(p in u)a.call(u,p)&&(s=u[p],r=e.getAggregator(g,s),x=r.value(),b=document.createElement("td"),b.className="pvtVal row"+c+" col"+p,b.textContent=r.format(x),b.setAttribute("data-value",x),y.appendChild(b))
C=e.getAggregator(g,[]),x=C.value(),b=document.createElement("td"),b.className="pvtTotal rowTotal",b.textContent=C.format(x),b.setAttribute("data-value",x),b.setAttribute("data-for","row"+c),y.appendChild(b),f.appendChild(y)}y=document.createElement("tr"),A=document.createElement("th"),A.className="pvtTotalLabel",A.innerHTML=n.localeStrings.totals,A.setAttribute("colspan",d.length+(0===i.length?0:1)),y.appendChild(A)
for(p in u)a.call(u,p)&&(s=u[p],C=e.getAggregator([],s),x=C.value(),b=document.createElement("td"),b.className="pvtTotal colTotal",b.textContent=C.format(x),b.setAttribute("data-value",x),b.setAttribute("data-for","col"+p),y.appendChild(b))
return C=e.getAggregator([],[]),x=C.value(),b=document.createElement("td"),b.className="pvtGrandTotal",b.textContent=C.format(x),b.setAttribute("data-value",x),y.appendChild(b),f.appendChild(y),f.setAttribute("data-numrows",m.length),f.setAttribute("data-numcols",u.length),f},t.fn.pivot=function(e,n){var r,a,i,u,l
r={cols:[],rows:[],vals:[],filter:function(){return!0},aggregator:s.count()(),aggregatorName:"Count",sorters:function(){},derivedAttributes:{},renderer:m,rendererOptions:null,localeStrings:h.en.localeStrings},n=t.extend(r,n),u=null
try{i=new o(e,n)
try{u=n.renderer(i,n.rendererOptions)}catch(e){a=e,"undefined"!=typeof console&&null!==console&&console.error(a.stack),u=t("<span>").html(n.localeStrings.renderError)}}catch(e){a=e,"undefined"!=typeof console&&null!==console&&console.error(a.stack),u=t("<span>").html(n.localeStrings.computeError)}for(l=this[0];l.hasChildNodes();)l.removeChild(l.lastChild)
return this.append(u)},t.fn.pivotUI=function(n,r,i,s){var u,l,c,f,g,m,v,b,A,C,y,w,x,N,T,S,F,E,D,k,R,I,O,M,K,L,q,U,V,j,H,B,P,J,_,z,Q,W,Z,$
null==i&&(i=!1),null==s&&(s="en"),null==h[s]&&(s="en"),v={derivedAttributes:{},aggregators:h[s].aggregators,renderers:h[s].renderers,hiddenAttributes:[],menuLimit:200,cols:[],rows:[],vals:[],exclusions:{},inclusions:{},unusedAttrsVertical:85,autoSortUnusedAttrs:!1,rendererOptions:{localeStrings:h[s].localeStrings},onRefresh:null,filter:function(){return!0},sorters:function(){},localeStrings:h[s].localeStrings},A=this.data("pivotUIOptions"),R=null==A||i?t.extend(v,r):A
try{n=o.convertToArray(n),J=function(){var t,e
t=n[0],e=[]
for(x in t)a.call(t,x)&&e.push(x)
return e}(),M=R.derivedAttributes
for(g in M)a.call(M,g)&&e.call(J,g)<0&&J.push(g)
for(f={},N=0,T=J.length;N<T;N++)$=J[N],f[$]={}
o.forEachRecord(n,R.derivedAttributes,function(t){var e,n,r
n=[]
for(x in t)a.call(t,x)&&(r=t[x],R.filter(t)&&(null==r&&(r="null"),null==(e=f[x])[r]&&(e[r]=0),n.push(f[x][r]++)))
return n}),Q=t("<table>",{class:"pvtUi"}).attr("cellpadding",5),B=t("<td>"),H=t("<select>").addClass("pvtRenderer").appendTo(B).bind("change",function(){return V()}),K=R.renderers
for($ in K)a.call(K,$)&&t("<option>").val($).html($).appendTo(H)
if(m=t("<td>").addClass("pvtAxisContainer pvtUnused"),P=function(){var t,n,r
for(r=[],n=0,t=J.length;n<t;n++)g=J[n],e.call(R.hiddenAttributes,g)<0&&r.push(g)
return r}(),Z=!1,W="auto"===R.unusedAttrsVertical?120:parseInt(R.unusedAttrsVertical),!isNaN(W)){for(c=0,D=0,S=P.length;D<S;D++)u=P[D],c+=u.length
Z=c>W}R.unusedAttrsVertical===!0||Z?m.addClass("pvtVertList"):m.addClass("pvtHorizList"),C=function(n){var r,a,o,i,s,u,l,c,h,d,g,v,b,A,C
if(l=function(){var t
t=[]
for(x in f[n])t.push(x)
return t}(),u=!1,C=t("<div>").addClass("pvtFilterBox").hide(),C.append(t("<h4>").text(n+" ("+l.length+")")),l.length>R.menuLimit)C.append(t("<p>").html(R.localeStrings.tooMany))
else for(a=t("<p>").appendTo(C),a.append(t("<button>",{type:"button"}).html(R.localeStrings.selectAll).bind("click",function(){return C.find("input:visible").prop("checked",!0)})),a.append(t("<button>",{type:"button"}).html(R.localeStrings.selectNone).bind("click",function(){return C.find("input:visible").prop("checked",!1)})),a.append(t("<br>")),a.append(t("<input>",{type:"text",placeholder:R.localeStrings.filterResults,class:"pvtSearch"}).bind("keyup",function(){var e
return e=t(this).val().toLowerCase(),C.find(".pvtCheckContainer p").each(function(){var n
return n=t(this).text().toLowerCase().indexOf(e),n!==-1?t(this).show():t(this).hide()})})),o=t("<div>").addClass("pvtCheckContainer").appendTo(C),d=l.sort(p(R.sorters,n)),h=0,c=d.length;h<c;h++)x=d[h],A=f[n][x],i=t("<label>"),s=!1,R.inclusions[n]?s=e.call(R.inclusions[n],x)<0:R.exclusions[n]&&(s=e.call(R.exclusions[n],x)>=0),u||(u=s),t("<input>").attr("type","checkbox").addClass("pvtFilter").attr("checked",!s).data("filter",[n,x]).appendTo(i),i.append(t("<span>").text(x)),i.append(t("<span>").text(" ("+A+")")),o.append(t("<p>").append(i))
return b=function(){var t
return t=C.find("[type='checkbox']").length-C.find("[type='checkbox']:checked").length,t>0?r.addClass("pvtFilteredAttribute"):r.removeClass("pvtFilteredAttribute"),l.length>R.menuLimit?C.toggle():C.toggle(0,V)},t("<p>").appendTo(C).append(t("<button>",{type:"button"}).text("OK").bind("click",b)),g=function(e){var n,r,a
return a=t(e.currentTarget).position(),n=a.left,r=a.top,C.css({left:n+10,top:r+10}).toggle(),C.find(".pvtSearch").val(""),C.find(".pvtCheckContainer p").show()},v=t("<span>").addClass("pvtTriangle").html(" &#x25BE;").bind("click",g),r=t("<li>").addClass("axis_"+y).append(t("<span>").addClass("pvtAttr").text(n).data("attrName",n).append(v)),u&&r.addClass("pvtFilteredAttribute"),m.append(r).append(C),r.bind("dblclick",g)}
for(y in P)a.call(P,y)&&(g=P[y],C(g))
_=t("<tr>").appendTo(Q),l=t("<select>").addClass("pvtAggregator").bind("change",function(){return V()}),L=R.aggregators
for($ in L)a.call(L,$)&&l.append(t("<option>").val($).html($))
for(t("<td>").addClass("pvtVals").appendTo(_).append(l).append(t("<br>")),t("<td>").addClass("pvtAxisContainer pvtHorizList pvtCols").appendTo(_),z=t("<tr>").appendTo(Q),z.append(t("<td>").addClass("pvtAxisContainer pvtRows").attr("valign","top")),I=t("<td>").attr("valign","top").addClass("pvtRendererArea").appendTo(z),R.unusedAttrsVertical===!0||Z?(Q.find("tr:nth-child(1)").prepend(B),Q.find("tr:nth-child(2)").prepend(m)):Q.prepend(t("<tr>").append(B).append(m)),this.html(Q),q=R.cols,k=0,F=q.length;k<F;k++)$=q[k],this.find(".pvtCols").append(this.find(".axis_"+t.inArray($,P)))
for(U=R.rows,O=0,E=U.length;O<E;O++)$=U[O],this.find(".pvtRows").append(this.find(".axis_"+t.inArray($,P)))
null!=R.aggregatorName&&this.find(".pvtAggregator").val(R.aggregatorName),null!=R.rendererName&&this.find(".pvtRenderer").val(R.rendererName),w=!0,j=function(r){return function(){var a,o,i,s,u,c,p,h,f,g,m,v,b,A,C
if(v={derivedAttributes:R.derivedAttributes,localeStrings:R.localeStrings,rendererOptions:R.rendererOptions,sorters:R.sorters,cols:[],rows:[]},c=null!=(f=R.aggregators[l.val()]([])().numInputs)?f:0,C=[],r.find(".pvtRows li span.pvtAttr").each(function(){return v.rows.push(t(this).data("attrName"))}),r.find(".pvtCols li span.pvtAttr").each(function(){return v.cols.push(t(this).data("attrName"))}),r.find(".pvtVals select.pvtAttrDropdown").each(function(){return 0===c?t(this).remove():(c--,""!==t(this).val()?C.push(t(this).val()):void 0)}),0!==c)for(h=r.find(".pvtVals"),$=m=0,g=c;0<=g?m<g:m>g;$=0<=g?++m:--m){for(u=t("<select>").addClass("pvtAttrDropdown").append(t("<option>")).bind("change",function(){return V()}),b=0,s=P.length;b<s;b++)a=P[b],u.append(t("<option>").val(a).text(a))
h.append(u)}if(w&&(C=R.vals,y=0,r.find(".pvtVals select.pvtAttrDropdown").each(function(){return t(this).val(C[y]),y++}),w=!1),v.aggregatorName=l.val(),v.vals=C,v.aggregator=R.aggregators[l.val()](C),v.renderer=R.renderers[H.val()],o={},r.find("input.pvtFilter").not(":checked").each(function(){var e
return e=t(this).data("filter"),null!=o[e[0]]?o[e[0]].push(e[1]):o[e[0]]=[e[1]]}),i={},r.find("input.pvtFilter:checked").each(function(){var e
if(e=t(this).data("filter"),null!=o[e[0]])return null!=i[e[0]]?i[e[0]].push(e[1]):i[e[0]]=[e[1]]}),v.filter=function(t){var n,r
if(!R.filter(t))return!1
for(x in o)if(n=o[x],r=""+t[x],e.call(n,r)>=0)return!1
return!0},I.pivot(n,v),p=t.extend(R,{cols:v.cols,rows:v.rows,vals:C,exclusions:o,inclusions:i,inclusionsInfo:i,aggregatorName:l.val(),rendererName:H.val()}),r.data("pivotUIOptions",p),R.autoSortUnusedAttrs&&(A=r.find("td.pvtUnused.pvtAxisContainer"),t(A).children("li").sort(function(e,n){return d(t(e).text(),t(n).text())}).appendTo(A)),I.css("opacity",1),null!=R.onRefresh)return R.onRefresh(p)}}(this),V=function(t){return function(){return I.css("opacity",.5),setTimeout(j,10)}}(this),V(),this.find(".pvtAxisContainer").sortable({update:function(t,e){if(null==e.sender)return V()},connectWith:this.find(".pvtAxisContainer"),items:"li",placeholder:"pvtPlaceholder"})}catch(t){b=t,"undefined"!=typeof console&&null!==console&&console.error(b.stack),this.html(R.localeStrings.uiRenderError)}return this},t.fn.heatmap=function(e){var n,r,a,o,i,s,u,l,c,p
switch(null==e&&(e="heatmap"),l=this.data("numrows"),u=this.data("numcols"),n=function(t,e,n){var r
return r=function(){switch(t){case"red":return function(t){return"ff"+t+t}
case"green":return function(t){return t+"ff"+t}
case"blue":return function(t){return""+t+t+"ff"}}}(),function(t){var a,o
return o=255-Math.round(255*(t-e)/(n-e)),a=o.toString(16).split(".")[0],1===a.length&&(a=0+a),r(a)}},r=function(e){return function(r,a){var o,i,s
return i=function(n){return e.find(r).each(function(){var e
if(e=t(this).data("value"),null!=e&&isFinite(e))return n(e,t(this))})},s=[],i(function(t){return s.push(t)}),o=n(a,Math.min.apply(Math,s),Math.max.apply(Math,s)),i(function(t,e){return e.css("background-color","#"+o(t))})}}(this),e){case"heatmap":r(".pvtVal","red")
break
case"rowheatmap":for(a=i=0,c=l;0<=c?i<c:i>c;a=0<=c?++i:--i)r(".pvtVal.row"+a,"red")
break
case"colheatmap":for(o=s=0,p=u;0<=p?s<p:s>p;o=0<=p?++s:--s)r(".pvtVal.col"+o,"red")}return r(".pvtTotal.rowTotal","red"),r(".pvtTotal.colTotal","red"),this},t.fn.barchart=function(){var e,n,r,a,o,i
for(o=this.data("numrows"),a=this.data("numcols"),e=function(e){return function(n){var r,a,o,i
return r=function(r){return e.find(n).each(function(){var e
if(e=t(this).data("value"),null!=e&&isFinite(e))return r(e,t(this))})},i=[],r(function(t){return i.push(t)}),a=Math.max.apply(Math,i),o=function(t){return 100*t/(1.4*a)},r(function(e,n){var r,a
return r=n.text(),a=t("<div>").css({position:"relative",height:"55px"}),a.append(t("<div>").css({position:"absolute",bottom:0,left:0,right:0,height:o(e)+"%","background-color":"gray"})),a.append(t("<div>").text(r).css({position:"relative","padding-left":"5px","padding-right":"5px"})),n.css({padding:0,"padding-top":"5px","text-align":"center"}).html(a)})}}(this),n=r=0,i=o;0<=i?r<i:r>i;n=0<=i?++r:--r)e(".pvtVal.row"+n)
return e(".pvtTotal.colTotal"),this}})}).call(this)
