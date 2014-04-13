<%-- 
    Document   : question
    Created on : Mar 21, 2014, 8:57:18 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="${objectFromID.title}">
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
                    <c:if test="${userName != null}">
                        <a href="answer?id=${objectFromID.ID}" type="button" class="btn btn-primary btn-lg">Give an answer</a>
                        <div class="col-md-12"><br></div>
                        <a href="flag?type=ques&id=${objectFromID.ID}" type="button" class="btn btn-default pull-left btn-xs">
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
                            <th>Rate</th>
                            <c:if test="${loggedIn != null}">
                                <th>Flag</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="unit" items="${list}">
                            <tr>
                                <td width="10%">
                                    <img src="defavatar.png" alt="avatar" height="64">
                                    <div class="caption">
                                        <a href="accquestions" type="button" class="btn btn-link text-left"><a href="accquestions">${unit.answerer.name}</a>
                                        <c:if test="${loggedIn.ID == unit.answerer.ID}">
                                            <a href="append?id=${unit.ID}" type="button" class="btn btn-xs btn-primary">Edit answer</a>
                                        </c:if>
                                    </div>
                                </td>
                                <td width="60%">${unit.body}</td>
                                <td width="10%">${unit.rating}</td>
                                <td width="10%">
                                    <a href="rate?type=t&id=${unit.ID}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-up"></span></a>
                                    <a href="rate?type=f&id=${unit.ID}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-down"></span></a>
                                </td>
                                <c:if test="${loggedIn != null}">
                                    <td width="5%"><a href="flag?type=ans&id=${unit.ID}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-flag"></span></a></td>
                                </c:if>
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
                        <a href="index?tags=${tag.text}" type="button" class="btn btn-xs btn-info">${tag.text}</a>
                    </c:forEach>
                </t:taglist>
            </div>
        </div>
    </div>
</t:base>