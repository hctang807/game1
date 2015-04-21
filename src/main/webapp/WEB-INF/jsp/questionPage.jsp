<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<style type="text/css">
	a {text-decoration: none}
        body {
                background-image: url(/{image_file});
                background-size: 100%;
                background-origin: content;
                background-repeat: no-repeat;
                background-position:center top;
        }
</style>
</head>
<body bgcolor="black">
<form method="post" action="/game/login">
<table align=center width="80%">
<tr><td height="200" colspan=2><font size="8" color="white">問題：${question}</font></td></tr>
<tr >
<td height="100" align=center><a href="/game/verifyAnswer?answer=1&username=${username}"><font size="7" color="blue">${answer1}</font></a></td>
<td align=center><a href="/game/verifyAnswer?answer=2&username=${username}"><font size="7" color="green">${answer2}</font></a></td>
</tr>
<tr>
<td height="100" align=center><a href="/game/verifyAnswer?answer=3&username=${username}"><font size="7" color="red">${answer3}</font></a></td>
<td align=center><a href="/game/verifyAnswer?answer=4&username=${username}"><font size="7" color="yellow">${answer4}</font></a></td>
</tr>
</table>
</form>
</body>
</html>
