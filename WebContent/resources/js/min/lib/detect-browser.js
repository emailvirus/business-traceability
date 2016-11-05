/**
 * 
 * @author SM@K<smali.kazmi@hotmail.com>
 * @description website: smak.pk
 * @see https://github.com/smali-kazmi/detect-mobile-browser
 */
var SmartPhone={getUserAgent:function(){return navigator.userAgent},isAndroid:function(){return this.getUserAgent().match(/Android/i)},isBlackBerry:function(){return this.getUserAgent().match(/BlackBerry/i)},isIOS:function(){return this.getUserAgent().match(/iPhone|iPad|iPod/i)},isOpera:function(){return this.getUserAgent().match(/Opera Mini/i)},isWindows:function(){return this.isWindowsDesktop()||this.isWindowsMobile()},isWindowsMobile:function(){return this.getUserAgent().match(/IEMobile/i)},isWindowsDesktop:function(){return this.getUserAgent().match(/WPDesktop/i)},isAny:function(){var t=!1,n=Object.getOwnPropertyNames(SmartPhone).filter(function(t){return"function"==typeof SmartPhone[t]})
for(var e in n)if("getUserAgent"!==n[e]&&"isAny"!==n[e]&&"isWindows"!==n[e]&&SmartPhone[n[e]]()){t=!0
break}return t}}
