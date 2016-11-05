(function(){var e,n,r,t,u,f
if(!window.Offline)throw new Error("Requests module brought in without offline.js")
r=[],f=!1,t=function(e){return Offline.trigger("requests:capture"),"down"!==Offline.state&&(f=!0),r.push(e)},u=function(e){var n,r,t,u,f,i,o,s,a
s=e.xhr,f=e.url,u=e.type,i=e.user,t=e.password,n=e.body,s.abort(),s.open(u,f,!0,i,t),a=s.headers
for(r in a)o=a[r],s.setRequestHeader(r,o)
return s.mimeType&&s.overrideMimeType(s.mimeType),s.send(n)},e=function(){return r=[]},n=function(){var n,t,f,i,o,s
for(Offline.trigger("requests:flush"),f={},o=0,s=r.length;o<s;o++)t=r[o],i=t.url.replace(/(\?|&)_=[0-9]+/,function(e,n){return"?"===n?n:""}),f[""+t.type.toUpperCase()+" - "+i]=t
for(n in f)t=f[n],u(t)
return e()},setTimeout(function(){if(Offline.getOption("requests")!==!1)return Offline.on("confirmed-up",function(){if(f)return f=!1,e()}),Offline.on("up",n),Offline.on("down",function(){return f=!1}),Offline.onXHR(function(e){var n,r,u,f,i
if(u=e.xhr,n=e.async,u.offline!==!1&&(r=function(){return t(e)},i=u.send,u.send=function(n){return e.body=n,i.apply(u,arguments)},n))return null===u.onprogress?(u.addEventListener("error",r,!1),u.addEventListener("timeout",r,!1)):(f=u.onreadystatechange,u.onreadystatechange=function(){return 0===u.readyState?r():4===u.readyState&&(0===u.status||u.status>=12e3)&&r(),"function"==typeof f?f.apply(null,arguments):void 0})}),Offline.requests={flush:n,clear:e}},0)}).call(this)
