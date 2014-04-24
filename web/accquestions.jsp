<%-- 
    Document   : account
    Created on : Mar 19, 2014, 6:45:10 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="User's questions">
    <t:accbase page="0">
        <t:list>
            <thead>
                <tr>
                    <th>Question</th>
                    <th>Asked</th>
                    <th>Amount of answers</th>
                    <th>Show question</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="unit" items="${list}">
                <tr>
                    <td width="70%">${unit.title}</td>
                    <td width="10%">${unit.asked}</td>
                    <td width="15%">${unit.answerCount}</td>
                    <td width="5%"><a href="question?id=${unit.ID}" type="button" class="btn btn-xs btn-default center-block"><span class="glyphicon glyphicon-arrow-left"></span></a></td>
                </tr>
                </c:forEach>
            </tbody>
        </t:list>
    </t:accbase>
</t:base>