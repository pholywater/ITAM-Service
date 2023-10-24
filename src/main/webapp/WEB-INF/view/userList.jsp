<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User List</title>
</head>
<body>
    <h2>User List</h2>
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>username</th>
        </tr>
        <c:forEach items="${list}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.username}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>