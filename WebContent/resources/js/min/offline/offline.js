(function(){var n,t,e,o,r,i,u
o=function(n,t){var e,o,r,i
i=[]
for(o in t.prototype)try{r=t.prototype[o],null==n[o]&&"function"!=typeof r?i.push(n[o]=r):i.push(void 0)}catch(n){e=n}return i},n={},null==n.options&&(n.options={}),e={checks:{xhr:{url:function(){return"/favicon.ico?_="+Math.floor(1e9*Math.random())},timeout:5e3},image:{url:function(){return"/favicon.ico?_="+Math.floor(1e9*Math.random())}},active:"xhr"},checkOnLoad:!1,interceptRequests:!0,reconnect:!0},r=function(n,t){var e,o,r,i,u,c
for(e=n,i=t.split("."),o=u=0,c=i.length;u<c&&(r=i[o],e=e[r],"object"==typeof e);o=++u);return o===i.length-1?e:void 0},n.getOption=function(t){var o,i
return o=null!=(i=r(n.options,t))?i:r(e,t),"function"==typeof o?o():o},"function"==typeof window.addEventListener&&window.addEventListener("online",function(){return setTimeout(n.confirmUp,100)},!1),"function"==typeof window.addEventListener&&window.addEventListener("offline",function(){return n.confirmDown()},!1),n.state="up",n.markUp=function(){if(n.trigger("confirmed-up"),"up"!==n.state)return n.state="up",n.trigger("up")},n.markDown=function(){if(n.trigger("confirmed-down"),"down"!==n.state)return n.state="down",n.trigger("down")},i={},n.on=function(t,e,o){var r,u,c,a,f
if(u=t.split(" "),u.length>1){for(f=[],c=0,a=u.length;c<a;c++)r=u[c],f.push(n.on(r,e,o))
return f}return null==i[t]&&(i[t]=[]),i[t].push([o,e])},n.off=function(n,t){var e,o,r,u,c
if(null!=i[n]){if(t){for(o=0,c=[];o<i[n].length;)u=i[n][o],e=u[0],r=u[1],r===t?c.push(i[n].splice(o,1)):c.push(o++)
return c}return i[n]=[]}},n.trigger=function(n){var t,e,o,r,u,c,a
if(null!=i[n]){for(u=i[n],a=[],o=0,r=u.length;o<r;o++)c=u[o],t=c[0],e=c[1],a.push(e.call(t))
return a}},t=function(n,t,e){var o,r,i,u,c
return o=function(){return n.status&&n.status<12e3?t():e()},null===n.onprogress?(r=n.onerror,n.onerror=function(){return e(),"function"==typeof r?r.apply(null,arguments):void 0},c=n.ontimeout,n.ontimeout=function(){return e(),"function"==typeof c?c.apply(null,arguments):void 0},i=n.onload,n.onload=function(){return o(),"function"==typeof i?i.apply(null,arguments):void 0}):(u=n.onreadystatechange,n.onreadystatechange=function(){return 4===n.readyState?o():0===n.readyState&&e(),"function"==typeof u?u.apply(null,arguments):void 0})},n.checks={},n.checks.xhr=function(){var e,o
o=new XMLHttpRequest,o.offline=!1,o.open("HEAD",n.getOption("checks.xhr.url"),!0),null!=o.timeout&&(o.timeout=n.getOption("checks.xhr.timeout")),t(o,n.markUp,n.markDown)
try{o.send()}catch(t){e=t,n.markDown()}return o},n.checks.image=function(){var t
t=document.createElement("img"),t.onerror=n.markDown,t.onload=n.markUp,t.src=n.getOption("checks.image.url")},n.checks.down=n.markDown,n.checks.up=n.markUp,n.check=function(){return n.trigger("checking"),n.checks[n.getOption("checks.active")]()},n.confirmUp=n.confirmDown=n.check,n.onXHR=function(n){var t,e,r
if(t=function(t,e){var o
return o=t.open,t.open=function(r,i,u,c,a){return n({type:r,url:i,async:u,flags:e,user:c,password:a,xhr:t}),o.apply(t,arguments)}},r=window.XMLHttpRequest,window.XMLHttpRequest=function(n){var e,o,i
return e=new r(n),t(e,n),i=e.setRequestHeader,e.headers={},e.setRequestHeader=function(n,t){return e.headers[n]=t,i.call(e,n,t)},o=e.overrideMimeType,e.overrideMimeType=function(n){return e.mimeType=n,o.call(e,n)},e},o(window.XMLHttpRequest,r),null!=window.XDomainRequest)return e=window.XDomainRequest,window.XDomainRequest=function(){var n
return n=new e,t(n),n},o(window.XDomainRequest,e)},u=function(){if(n.getOption("interceptRequests")&&n.onXHR(function(e){var o
if(o=e.xhr,o.offline!==!1)return t(o,n.confirmUp,n.confirmDown)}),n.getOption("checkOnLoad"))return n.check()},setTimeout(u,0),window.Offline=n}).call(this)
