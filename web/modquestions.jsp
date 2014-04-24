<%-- 
    Document   : modquestions
    Created on : 5.4.2014, 16:44:09
    Author     : Pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Flagged questions">
    <div class="container">
        <div class="row">
            <t:list>
                <thead>
                    <tr>
                        <th>Question</th>
                        <th>Asked</th>
                        <th>Flags</th>
                        <th>Show question</th>
                        <th>Remove question</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="unit" items="${list}">
                        <tr>
                            <td width="70%">${unit.title}</td>
                            <td width="10%">${unit.asked}</td>
                            <td width="10%">${unit.flags}</td>
                            <td width="5%">
                                <a href="question?id=${unit.ID}" type="button" class="btn btn-xs btn-default">
                                    <span class="glyphicon glyphicon-arrow-left"></span>
                                </a>
                            </td>
                            <td width="5%">
                                <a href="modremove?type=ques&id=${unit.ID}" type="button" class="btn btn-xs btn-default">
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
