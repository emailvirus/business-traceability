<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
${datetime} レポート
<br/>
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
<br/>
【以下のメンバーの日報が未入力です】
<br/>
<#list user?keys as key>
${key} (${user[key]})
<br/>
</#list>
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
<br/>
<br/>
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
<br/>
このメールは通知設定に基づいて配信しています。
<br/>
</body>
</html>