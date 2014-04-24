<%-- 
    Document   : index
    Created on : 14.3.2014, 1:49:19
    Author     : FeisEater
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="QuesAns">
    <div class="container-fluid" style="background-color:#EEFFDD">
        <br>
        <div class="row">
            <div class="col-md-offset-1 col-md-5">
                <p>Ask a question! Answer a question!</p>
            </div>
            <div class="col-md-6">
                <a href="ask" type="button" class="btn btn-primary btn-lg">Ask a question!</a>
            </div>
            <div class="col-md-12"><br></div>
            <div class="col-md-12"><br></div>
            <div class="col-md-offset-6 col-md-5">
                <form class="form-horizontal" role="form" action="index" method="POST">
                    <div class="input-group">
                        <input type="text" class="form-control" id="search" name="tags" placeholder="Search...">
                        <span class="input-group-btn">
                            <button type="submit" class="btn btn-default pull-left"><span class="glyphicon glyphicon-search"></span></button>
                        </span>
                    </div>
                </form>
            </div>
        </div>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <t:list>
                <thead>
                    <tr>
                        <th>Question</th>
                        <th>Amount of answers</th>
                        <th>Show question</th>
                        <c:if test="${loggedIn.moderator == true}">
                            <th>Remove question</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="unit" items="${list}">
                        <tr>
                            <td width="80%">${unit.title}</td>
                            <td width="10%">${unit.answerCount}</td>
                            <td width="5%">
                                <a href="question?id=${unit.ID}" type="button" class="btn btn-xs btn-default">
                                    <span class="glyphicon glyphicon-arrow-left"></span>
                                </a>
                            </td>
                            <c:if test="${loggedIn.moderator == true}">
                                <td width="5%">
                                    <a href="modremove?type=ques&id=${unit.ID}" type="button" class="btn btn-xs btn-default">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </t:list>
        </div>
    </div>
</t:base>
