<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<style type="text/css">a {text-decoration: none}</style>
</head>
<body bgcolor="black">
<table align=center width="80%" height="80%">
<tr><td bgcolor="#00ffff" height="20%" align=center><font size="7" color="white">分數：${score}</font></td></tr>
<tr><td bgcolor="#98fb98" height="20%" align=center><a href="/game/selectLevel?level=1&username=${username}"><font size="7" color="white">中文</font></a></td></tr>
<tr><td bgcolor="#ffff00" height="20%" align=center><a href="/game/selectLevel?level=2&username=${username}"><font size="7" color="white">英文</font></a></td></tr>
<tr><td bgcolor="#ff6347" height="20%" align=center><a href="/game/selectLevel?level=3&username=${username}"><font size="7" color="white">數學</font></a></td></tr>
<tr><td bgcolor="#ff00ff" height="20%" align=center><a href="/game/selectLevel?level=4&username=${username}"><font size="7" color="white">常識/冷知識</font></a></td></tr>
</table>
</body>
</html>
