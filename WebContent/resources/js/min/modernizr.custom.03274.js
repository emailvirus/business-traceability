/* Modernizr 2.8.3 (Custom Build) | MIT & BSD
 * Build: http://modernizr.com/download/#-canvas-canvastext-input-inputtypes-shiv-cssclasses-testprop-testallprops-hasevent-domprefixes-load-cssclassprefix:md!
 */
window.Modernizr=function(e,t,n){function r(e){g.cssText=e}function i(e,t){return typeof e===t}function o(e,t){return!!~(""+e).indexOf(t)}function a(e,t){for(var r in e){var i=e[r]
if(!o(i,"-")&&g[i]!==n)return"pfx"!=t||i}return!1}function c(e,t,r){for(var o in e){var a=t[e[o]]
if(a!==n)return r===!1?e[o]:i(a,"function")?a.bind(r||t):a}return!1}function l(e,t,n){var r=e.charAt(0).toUpperCase()+e.slice(1),o=(e+" "+x.join(r+" ")+r).split(" ")
return i(t,"string")||i(t,"undefined")?a(o,t):(o=(e+" "+S.join(r+" ")+r).split(" "),c(o,t,n))}function u(){d.input=function(n){for(var r=0,i=n.length;r<i;r++)A[n[r]]=n[r]in b
return A.list&&(A.list=!!t.createElement("datalist")&&!!e.HTMLDataListElement),A}("autocomplete autofocus list placeholder max min multiple pattern required step".split(" ")),d.inputtypes=function(e){for(var r,i,o,a=0,c=e.length;a<c;a++)b.setAttribute("type",i=e[a]),r="text"!==b.type,r&&(b.value=E,b.style.cssText="position:absolute;visibility:hidden;",/^range$/.test(i)&&b.style.WebkitAppearance!==n?(h.appendChild(b),o=t.defaultView,r=o.getComputedStyle&&"textfield"!==o.getComputedStyle(b,null).WebkitAppearance&&0!==b.offsetHeight,h.removeChild(b)):/^(search|tel)$/.test(i)||(r=/^(url|email)$/.test(i)?b.checkValidity&&b.checkValidity()===!1:b.value!=E)),w[e[a]]=!!r
return w}("search tel url email datetime date month week time datetime-local number range color".split(" "))}var s,f,p="2.8.3",d={},m=!0,h=t.documentElement,v="modernizr",y=t.createElement(v),g=y.style,b=t.createElement("input"),E=":)",C=({}.toString,"Webkit Moz O ms"),x=C.split(" "),S=C.toLowerCase().split(" "),j={},w={},A={},k=[],F=k.slice,N=function(){function e(e,o){o=o||t.createElement(r[e]||"div"),e="on"+e
var a=e in o
return a||(o.setAttribute||(o=t.createElement("div")),o.setAttribute&&o.removeAttribute&&(o.setAttribute(e,""),a=i(o[e],"function"),i(o[e],"undefined")||(o[e]=n),o.removeAttribute(e))),o=null,a}var r={select:"input",change:"input",submit:"form",reset:"form",error:"img",load:"img",abort:"img"}
return e}(),T={}.hasOwnProperty
f=i(T,"undefined")||i(T.call,"undefined")?function(e,t){return t in e&&i(e.constructor.prototype[t],"undefined")}:function(e,t){return T.call(e,t)},Function.prototype.bind||(Function.prototype.bind=function(e){var t=this
if("function"!=typeof t)throw new TypeError
var n=F.call(arguments,1),r=function(){if(this instanceof r){var i=function(){}
i.prototype=t.prototype
var o=new i,a=t.apply(o,n.concat(F.call(arguments)))
return Object(a)===a?a:o}return t.apply(e,n.concat(F.call(arguments)))}
return r}),j.canvas=function(){var e=t.createElement("canvas")
return!!e.getContext&&!!e.getContext("2d")},j.canvastext=function(){return!!d.canvas&&!!i(t.createElement("canvas").getContext("2d").fillText,"function")}
for(var M in j)f(j,M)&&(s=M.toLowerCase(),d[s]=j[M](),k.push((d[s]?"":"no-")+s))
return d.input||u(),d.addTest=function(e,t){if("object"==typeof e)for(var r in e)f(e,r)&&d.addTest(r,e[r])
else{if(e=e.toLowerCase(),d[e]!==n)return d
t="function"==typeof t?t():t,"undefined"!=typeof m&&m&&(h.className+=" md-"+(t?"":"no-")+e),d[e]=t}return d},r(""),y=b=null,function(e,t){function n(e,t){var n=e.createElement("p"),r=e.getElementsByTagName("head")[0]||e.documentElement
return n.innerHTML="x<style>"+t+"</style>",r.insertBefore(n.lastChild,r.firstChild)}function r(){var e=g.elements
return"string"==typeof e?e.split(" "):e}function i(e){var t=y[e[h]]
return t||(t={},v++,e[h]=v,y[v]=t),t}function o(e,n,r){if(n||(n=t),s)return n.createElement(e)
r||(r=i(n))
var o
return o=r.cache[e]?r.cache[e].cloneNode():m.test(e)?(r.cache[e]=r.createElem(e)).cloneNode():r.createElem(e),!o.canHaveChildren||d.test(e)||o.tagUrn?o:r.frag.appendChild(o)}function a(e,n){if(e||(e=t),s)return e.createDocumentFragment()
n=n||i(e)
for(var o=n.frag.cloneNode(),a=0,c=r(),l=c.length;a<l;a++)o.createElement(c[a])
return o}function c(e,t){t.cache||(t.cache={},t.createElem=e.createElement,t.createFrag=e.createDocumentFragment,t.frag=t.createFrag()),e.createElement=function(n){return g.shivMethods?o(n,e,t):t.createElem(n)},e.createDocumentFragment=Function("h,f","return function(){var n=f.cloneNode(),c=n.createElement;h.shivMethods&&("+r().join().replace(/[\w\-]+/g,function(e){return t.createElem(e),t.frag.createElement(e),'c("'+e+'")'})+");return n}")(g,t.frag)}function l(e){e||(e=t)
var r=i(e)
return g.shivCSS&&!u&&!r.hasCSS&&(r.hasCSS=!!n(e,"article,aside,dialog,figcaption,figure,footer,header,hgroup,main,nav,section{display:block}mark{background:#FF0;color:#000}template{display:none}")),s||c(e,r),e}var u,s,f="3.7.0",p=e.html5||{},d=/^<|^(?:button|map|select|textarea|object|iframe|option|optgroup)$/i,m=/^(?:a|b|code|div|fieldset|h1|h2|h3|h4|h5|h6|i|label|li|ol|p|q|span|strong|style|table|tbody|td|th|tr|ul)$/i,h="_html5shiv",v=0,y={}
!function(){try{var e=t.createElement("a")
e.innerHTML="<xyz></xyz>",u="hidden"in e,s=1==e.childNodes.length||function(){t.createElement("a")
var e=t.createDocumentFragment()
return"undefined"==typeof e.cloneNode||"undefined"==typeof e.createDocumentFragment||"undefined"==typeof e.createElement}()}catch(e){u=!0,s=!0}}()
var g={elements:p.elements||"abbr article aside audio bdi canvas data datalist details dialog figcaption figure footer header hgroup main mark meter nav output progress section summary template time video",version:f,shivCSS:p.shivCSS!==!1,supportsUnknownElements:s,shivMethods:p.shivMethods!==!1,type:"default",shivDocument:l,createElement:o,createDocumentFragment:a}
e.html5=g,l(t)}(this,t),d._version=p,d._domPrefixes=S,d._cssomPrefixes=x,d.hasEvent=N,d.testProp=function(e){return a([e])},d.testAllProps=l,h.className=h.className.replace(/(^|\s)no-js(\s|$)/,"$1$2")+(m?" md-js md-"+k.join(" md-"):""),d}(this,this.document),function(e,t,n){function r(e){return"[object Function]"==v.call(e)}function i(e){return"string"==typeof e}function o(){}function a(e){return!e||"loaded"==e||"complete"==e||"uninitialized"==e}function c(){var e=y.shift()
g=1,e?e.t?m(function(){("c"==e.t?p.injectCss:p.injectJs)(e.s,0,e.a,e.x,e.e,1)},0):(e(),c()):g=0}function l(e,n,r,i,o,l,u){function s(t){if(!d&&a(f.readyState)&&(b.r=d=1,!g&&c(),f.onload=f.onreadystatechange=null,t)){"img"!=e&&m(function(){C.removeChild(f)},50)
for(var r in A[n])A[n].hasOwnProperty(r)&&A[n][r].onload()}}var u=u||p.errorTimeout,f=t.createElement(e),d=0,v=0,b={t:r,s:n,e:o,a:l,x:u}
1===A[n]&&(v=1,A[n]=[]),"object"==e?f.data=n:(f.src=n,f.type=e),f.width=f.height="0",f.onerror=f.onload=f.onreadystatechange=function(){s.call(this,v)},y.splice(i,0,b),"img"!=e&&(v||2===A[n]?(C.insertBefore(f,E?null:h),m(s,u)):A[n].push(f))}function u(e,t,n,r,o){return g=0,t=t||"j",i(e)?l("c"==t?S:x,e,t,this.i++,n,r,o):(y.splice(this.i++,0,e),1==y.length&&c()),this}function s(){var e=p
return e.loader={load:u,i:0},e}var f,p,d=t.documentElement,m=e.setTimeout,h=t.getElementsByTagName("script")[0],v={}.toString,y=[],g=0,b="MozAppearance"in d.style,E=b&&!!t.createRange().compareNode,C=E?d:h.parentNode,d=e.opera&&"[object Opera]"==v.call(e.opera),d=!!t.attachEvent&&!d,x=b?"object":d?"script":"img",S=d?"script":x,j=Array.isArray||function(e){return"[object Array]"==v.call(e)},w=[],A={},k={timeout:function(e,t){return t.length&&(e.timeout=t[0]),e}}
p=function(e){function t(e){var t,n,r,e=e.split("!"),i=w.length,o=e.pop(),a=e.length,o={url:o,origUrl:o,prefixes:e}
for(n=0;n<a;n++)r=e[n].split("="),(t=k[r.shift()])&&(o=t(o,r))
for(n=0;n<i;n++)o=w[n](o)
return o}function a(e,i,o,a,c){var l=t(e),u=l.autoCallback
l.url.split(".").pop().split("?").shift(),l.bypass||(i&&(i=r(i)?i:i[e]||i[a]||i[e.split("/").pop().split("?")[0]]),l.instead?l.instead(e,i,o,a,c):(A[l.url]?l.noexec=!0:A[l.url]=1,o.load(l.url,l.forceCSS||!l.forceJS&&"css"==l.url.split(".").pop().split("?").shift()?"c":n,l.noexec,l.attrs,l.timeout),(r(i)||r(u))&&o.load(function(){s(),i&&i(l.origUrl,c,a),u&&u(l.origUrl,c,a),A[l.url]=2})))}function c(e,t){function n(e,n){if(e){if(i(e))n||(f=function(){var e=[].slice.call(arguments)
p.apply(this,e),d()}),a(e,f,t,0,u)
else if(Object(e)===e)for(l in c=function(){var t,n=0
for(t in e)e.hasOwnProperty(t)&&n++
return n}(),e)e.hasOwnProperty(l)&&(!n&&!--c&&(r(f)?f=function(){var e=[].slice.call(arguments)
p.apply(this,e),d()}:f[l]=function(e){return function(){var t=[].slice.call(arguments)
e&&e.apply(this,t),d()}}(p[l])),a(e[l],f,t,l,u))}else!n&&d()}var c,l,u=!!e.test,s=e.load||e.both,f=e.callback||o,p=f,d=e.complete||o
n(u?e.yep:e.nope,!!s),s&&n(s)}var l,u,f=this.yepnope.loader
if(i(e))a(e,0,f,0)
else if(j(e))for(l=0;l<e.length;l++)u=e[l],i(u)?a(u,0,f,0):j(u)?p(u):Object(u)===u&&c(u,f)
else Object(e)===e&&c(e,f)},p.addPrefix=function(e,t){k[e]=t},p.addFilter=function(e){w.push(e)},p.errorTimeout=1e4,null==t.readyState&&t.addEventListener&&(t.readyState="loading",t.addEventListener("DOMContentLoaded",f=function(){t.removeEventListener("DOMContentLoaded",f,0),t.readyState="complete"},0)),e.yepnope=s(),e.yepnope.executeStack=c,e.yepnope.injectJs=function(e,n,r,i,l,u){var s,f,d=t.createElement("script"),i=i||p.errorTimeout
d.src=e
for(f in r)d.setAttribute(f,r[f])
n=u?c:n||o,d.onreadystatechange=d.onload=function(){!s&&a(d.readyState)&&(s=1,n(),d.onload=d.onreadystatechange=null)},m(function(){s||(s=1,n(1))},i),l?d.onload():h.parentNode.insertBefore(d,h)},e.yepnope.injectCss=function(e,n,r,i,a,l){var u,i=t.createElement("link"),n=l?c:n||o
i.href=e,i.rel="stylesheet",i.type="text/css"
for(u in r)i.setAttribute(u,r[u])
a||(h.parentNode.insertBefore(i,h),m(n,0))}}(this,document),Modernizr.load=function(){yepnope.apply(window,[].slice.call(arguments,0))}
