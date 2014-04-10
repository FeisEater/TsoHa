<%-- 
    Document   : question
    Created on : Mar 21, 2014, 8:57:18 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
    <div class="container">
        <div class="row">
            <div class="col-md-12"><br></div>
            <div class="col-md-10">
                <div class="col-md-1">
                    <img src="defavatar.png" alt="avatar" height="80">
                    <div class="caption">
                        <a href="accquestions" type="button" class="btn btn-link text-left">${objectFromID.asker.name}</a>
                    </div>
                </div>
                <div class="col-md-offset-1 col-md-8">
                    <h1>${objectFromID.title}</h1>
                </div>
                <div class="col-md-2">
                    <a href="answer" type="button" class="btn btn-primary btn-lg">Give an answer</a>
                    <div class="col-md-12"><br></div>
                    <c:if test="${userName != null}">
                        <a href="flag?id=${objectFromID.ID}" type="button" class="btn btn-default pull-left btn-xs">
                            Flag as inappropriate <span class="glyphicon glyphicon-flag"></span>
                        </a>
                    </c:if>
                </div>
                <div class="col-md-12">
                    <div class="well">${objectFromID.body}</div>
                </div>
                <t:list>
                    <thead>
                        <tr>
                            <th></th>
                            <th>Answer</th>
                            <th>Rating</th>
                            <th>Mark as useful</th>
                            <th>Flag answer as inappropriate</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="unit" items="${list}">
                            <tr>
                                <td width="10%">
                                    <img src="defavatar.png" alt="avatar" height="64">
                                    <div class="caption">
                                        <a href="accquestions" type="button" class="btn btn-link text-left"><c:out value="${unit.answerer.name}"></c:out></a>
                                    </div>
                                </td>
                                <td width="70%">${unit.body}</td>
                                <td width="10%">${unit.rating}</td>
                                <td width="5%"><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-up"></span></button></td>
                                <td width="5%"><button type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-flag"></span></button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </t:list>
            </div>
            <div class="col-md-2">
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <label class="col-md-12 control-label">Tags:</label>
                <t:taglist>
                    <c:forEach var="tag" items="${taglist}">
                        <a href="index" type="button" class="btn btn-xs btn-info">${tag.text}</a>
                    </c:forEach>
                </t:taglist>
            </div>
        </div>
    </div>
</t:base>