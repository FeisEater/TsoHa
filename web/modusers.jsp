<%-- 
    Document   : modusers
    Created on : 5.4.2014, 16:44:09
    Author     : Pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Users starting from newly joined" modpage="0">
    <div class="container">
        <div class="row">
            <t:list curUrl="modusers?">
                <thead>
                    <tr>
                        <th></th>
                        <th>Name</th>
                        <th>Joined</th>
                        <th>Questions</th>
                        <th>Answers</th>
                        <th>Ban</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="unit" items="${list}">
                        <tr>
                            <td class="unit" width="10%">
                                <c:set var="avatar" value="${unit.avatar}"/>
                                <c:if test="${avatar == null}">
                                    <img src="defavatar.png" alt="avatar" class="avatar" height="128" width="128">
                                </c:if>
                                <c:if test="${avatar != null}">
                                    <img src="data:image/jpg;base64,${avatar}" alt="avatar" class="avatar" height="128" width="128">
                                </c:if>
                            </td>
                            <td width="55%">${unit.name}</td>
                            <td class="unit" width="10%">${unit.joined}</td>
                            <td class="unit" width="10%">${unit.quescount}</td>
                            <td class="unit" width="10%">${unit.anscount}</td>
                            <td class="unit" width="5%">
                                <a href="modremove?type=user&id=${unit.ID}" type="button" class="btn btn-xs btn-default">
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
