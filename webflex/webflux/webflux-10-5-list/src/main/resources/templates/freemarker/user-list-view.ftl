<!DOCTYPE html>
<html>
<body>
<head>
  <meta charset="UTF-8"/>
  <title>用户列表-freemarker</title>
</head>
<table border="1">
  <thead>
  </thead>
  <tbody>
  <#list userList as e>
  <tr>
    <td>${e.name}</td>
    <td>${e.age}</td>
    <td>${e.address}</td>
  </tr>
  </#list>
  </tbody>
</table>
</body>
</html>