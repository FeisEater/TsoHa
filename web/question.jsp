<%-- 
    Document   : question
    Created on : Mar 21, 2014, 8:57:18 PM
    Author     : FeisEater
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cu" uri="/WEB-INF/custom.tld"%>
<t:base pageTitle="${objectFromID.title}">
    <div class="container">
        <div class="row">
            <div class="col-md-12"><br></div>
            <div class="col-md-10">
                <div class="col-md-1">
                    <c:set var="avatar" value="${objectFromID.asker.avatar}"/>
                    <c:if test="${avatar == null}">
                        <img src="defavatar.png" alt="avatar" class="avatar" height="128" width="128">
                    </c:if>
                    <c:if test="${avatar != null}">
                        <img src="data:image/jpg;base64,${avatar}" alt="avatar" class="avatar" height="128" width="128">
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
                                    <c:set var="avatar" value="${unit.answerer.avatar}"/>
                                    <c:if test="${avatar == null}">
                                        <img src="defavatar.png" alt="avatar" class="avatar" height="80" width="80">
                                    </c:if>
                                    <c:if test="${avatar != null}">
                                        <img src="data:image/jpg;base64,${avatar}" alt="avatar" class="avatar" height="80" width="80">
                                    </c:if>
                                    <div class="caption">
                                        <a href="accquestions" type="button" class="btn btn-link text-left">${unit.answerer.name}</a>
                                        <c:if test="${loggedIn.ID == unit.answerer.ID}">
                                            <a href="append?id=${unit.ID}" type="button" class="btn btn-xs btn-primary">Edit answer</a>
                                        </c:if>
                                    </div>
                                </td>
                                <td width="60%">${unit.body}</td>
                                <td width="10%">${unit.rating}</td>
                                <td width="10%">
                                    <c:if test="${loggedIn != null}">
                                        <cu:Rate loggedIn="${loggedIn}" answer="${unit}" />
                                    </c:if>
                                    <c:if test="${loggedIn == null}">
                                        <cu:Rate rateList="${rated}" answer="${unit}" />
                                    </c:if>
                                    <!--a href="rate?type=t&id=${unit.ID}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-up"></span></a>
                                    <a href="rate?type=f&id=${unit.ID}" type="button" class="btn btn-xs btn-default"><span class="glyphicon glyphicon-thumbs-down"></span></a-->
                                </td>
                                <c:if test="${loggedIn != null}">
                                    <td width="5%"><a href="flag?type=ans&id=${unit.ID}" type="button" class="btn btn-xs btn-default">
                                        <cu:FlagAnswer loggedIn="${loggedIn}" answer="${unit}" />
                                    </a></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </t:list>
            </div>
            <div class="col-md-2">
                <div class="col-md-12">
                    <c:if test="${loggedIn != null}">
                        <a href="answer?id=${objectFromID.ID}" type="button" class="btn btn-primary btn-lg">Give an answer</a>
                        <div class="col-md-12"><br></div>
                        <a href="flag?type=ques&id=${objectFromID.ID}" type="button" class="btn btn-default pull-left btn-xs">
                            <cu:FlagQuestion loggedIn="${loggedIn}" question="${objectFromID}" />
                        </a>
                    </c:if>
                </div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div class="col-md-12"><br></div>
                <div align="center">
                    <label class="col-md-12 control-label">Tags:</label>
                    <t:taglist>
                        <c:forEach var="tag" items="${taglist}">
                            <a href="index?tags=${tag.text}" type="button" class="btn btn-xs btn-info">${tag.text}</a>
                        </c:forEach>
                    </t:taglist>
                </div>
            </div>
        </div>
    </div>
</t:base>