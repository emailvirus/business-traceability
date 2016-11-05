/**
 * Created by michael on 03/06/2016.
 */
Offline.options={checks:{xhr:{url:function(){return"/business-traceability/resources/images/triarrow-logo.png?_="+Math.floor(1e9*Math.random())},timeout:5e3},image:{url:function(){return"/business-traceability/resources/images/triarrow-logo.png?_="+Math.floor(1e9*Math.random())}},active:"xhr"},checkOnLoad:!0,interceptRequests:!0,reconnect:{initialDelay:3,delay:1.5},requests:!0,game:!1}
