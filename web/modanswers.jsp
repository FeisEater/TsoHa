<%-- 
    Document   : modanswers
    Created on : 5.4.2014, 16:44:09
    Author     : Pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Flagged answers">
    <div class="container">
        <div class="row">
            <t:list>
                <thead>
                    <tr>
                        <th>Answer</th>
                        <th>Question</th>
                        <th>Answered</th>
                        <th>Rating</th>
                        <th>Flags</th>
                        <th>Show question</th>
                        <th>Remove answer</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="unit" items="${list}">
                        <tr>
                            <td width="30%">${unit.shortBody}</td>
                            <td width="30%">${unit.question.title}</td>
                            <td width="10%">${unit.answered}</td>
                            <td width="10%">${unit.rating}</td>
                            <td width="10%">${unit.flags}</td>
                            <td width="5%">
                                <a href="question?id=${unit.question.ID}" type="button" class="btn btn-xs btn-default">
                                    <span class="glyphicon glyphicon-arrow-left"></span>
                                </a>
                            </td>
                            <td width="5%">
                                <a href="modremove?type=ans&id=${unit.ID}" type="button" class="btn btn-xs btn-default">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </t:list>
        </div>
    </div>
</t:base>
