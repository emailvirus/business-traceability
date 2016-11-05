<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
${datetime} レポート
<br/>
<br/>
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
<br/>
<br/>
【以下のメンバーの日報が入力されました】
<br/>
<br/>
<#list user as employee>
<br/>
${employee.emp_name}(${employee.addresspoint_mst.adp_name})
<br/>
${url[employee_index]}
<br/>
<br/>
</#list>
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
<br/>

－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
<br/>
このメールは通知設定に基づいて配信しています。
<br/>
</body>
</html>