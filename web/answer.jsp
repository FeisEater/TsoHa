<%-- 
    Document   : answer
    Created on : Mar 22, 2014, 1:36:00 AM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Answer to ${objectFromID.title}">
    <div class="container">
        <div class="row" style="background-color:#FFDDFF">
            <div class="col-md-12"><br></div>
            <div class="col-md-1">
                <c:set var="avatar" value="${objectFromID.asker.avatar}"/>
                <c:if test="${avatar == null}">
                    <img src="defavatar.png" alt="avatar" class="avatar" height="128" width="128" align="right">
                </c:if>
                <c:if test="${avatar != null}">
                    <img src="data:image/jpg;base64,${avatar}" alt="avatar" class="avatar" height="128" width="128" align="right">
                </c:if>
                <div class="caption">
                    <a href="accquestions" type="button" class="btn btn-link text-left">${objectFromID.asker.name}</a>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-10">
                <h1>${objectFromID.title}</h1>
            </div>
            <div class="col-md-12">
                <div class="well">${objectFromID.body}</div>
            </div>
            <form class="form-horizontal" role="form" action="answer?id=${objectFromID.ID}" method="POST">
                <div class="col-md-12" style="background-color:#FFFFFF">
                    <div class="col-md-12"><br></div>
                    <div class="col-md-offset-10 col-md-2">
                        <button type="submit" class="btn btn-primary btn-lg">Post answer</button>
                    </div>
                    <label for="answer" class="control-label">Write your answer here.</label>
                    <textarea class="form-control" id="answer" name="answer" rows="20" spellcheck="false" placeholder="Your answer"></textarea>
                </div>
            </form>
        </div>
    </div>
</t:base>