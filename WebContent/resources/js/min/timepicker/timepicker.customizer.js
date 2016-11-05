function tpStartOnHourShowCallback(e){if(!PrimeFaces.widgets.endTimeWidget)return!1
var t=parseInt(PF("endTimeWidget").getHours())
return t==-1||parseInt(e)<=t}function tpStartOnMinuteShowCallback(e,t){if(!PrimeFaces.widgets.endTimeWidget)return!1
var r=parseInt(PF("endTimeWidget").getHours()),i=parseInt(PF("endTimeWidget").getMinutes())
return r==-1||(parseInt(e)<r||parseInt(e)==r&&parseInt(t)<i)}function tpEndOnHourShowCallback(e){if(!PrimeFaces.widgets.startTimeWidget)return!1
var t=parseInt(PF("startTimeWidget").getHours())
return parseInt(e)>=t}function tpEndOnMinuteShowCallback(e,t){if(!PrimeFaces.widgets.startTimeWidget)return!1
var r=parseInt(PF("startTimeWidget").getHours()),i=parseInt(PF("startTimeWidget").getMinutes())
return parseInt(e)>r||parseInt(e)==r&&parseInt(t)>i}function tpCloseStartTime(){if(!PrimeFaces.widgets.startTimeWidget||!PrimeFaces.widgets.endTimeWidget)return!1
var e=parseInt(PF("startTimeWidget").getHours()),t=parseInt(PF("startTimeWidget").getMinutes())
PF("endTimeWidget").setTime(e+1+":"+t)}$(".startTime").on("keypress",function(e){return!1}),$(".endTime").on("keypress",function(e){return!1}),PrimeFacesExt.locales.TimePicker.ja={hourText:"時間",minuteText:"分",amPmText:["午前","午後"],closeButtonText:"閉じる",nowButtonText:"現時",deselectButtonText:"選択解除"}
