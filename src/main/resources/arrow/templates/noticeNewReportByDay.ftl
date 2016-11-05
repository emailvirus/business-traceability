<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
${datetime} レポート
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
【以下のメンバーの日報が入力されました】
<#list user as employee>
<br/>
${employee.emp_name}(${employee.addresspoint_mst.adp_name})
<br/>
${url[employee_index]}
<br/>
</#list>
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
このメールは通知設定に基づいて配信しています。
</body>
</html>