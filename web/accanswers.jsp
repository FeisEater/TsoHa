<%-- 
    Document   : accanswers
    Created on : Mar 21, 2014, 1:16:26 AM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="User's answers">
    <t:accbase site="1">
        <t:list curUrl="accanswers?" size="${loggedIn.anscount}">
            <thead>
                <tr>
                    <th>Answer</th>
                    <th>Question</th>
                    <th>Answered</th>
                    <th>Rating</th>
                    <th>Show question</th>
                    <th>Edit answer</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="unit" items="${list}">
                    <tr>
                        <td width="40%">${unit.shortBody}</td>
                        <td width="40%">${unit.question.title}</td>
                        <td width="10%">${unit.answered}</td>
                        <td width="10%">${unit.rating}</td>
                        <td width="5%">
                            <a href="question?id=${unit.question.ID}" type="button" class="btn btn-xs btn-default">
                                <span class="glyphicon glyphicon-arrow-left"></span>
                            </a>
                        </td>
                        <td width="5%">
                            <a href="append?id=${unit.ID}" type="button" class="btn btn-xs btn-default">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </t:list>
    </t:accbase>
</t:base>