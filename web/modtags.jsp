<%-- 
    Document   : modtags
    Created on : 5.4.2014, 16:44:09
    Author     : Pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base pageTitle="Tags" modpage="3">
    <div class="container">
        <div class="row">
            <div class="col-md-2">
                <ul class="list-group">
                    <t:taglist>
                        <c:forEach var="tag" items="${taglist}">
                            <li class="list-group-item">
                                <div class="btn-group btn-group-xs">
                                    <a href="index?tags=${tag.text}" type="button" class="btn btn-info">${tag.text}</a>
                                    <a href="modremove?type=tags&id=${tag.ID}" type="button" class="btn btn-info">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </div>
                            </li>
                        </c:forEach>
                    </t:taglist>
                </ul>
            </div>
            <div class="col-md-2">
                <ul class="list-group">
                    <t:taglist>
                        <c:forEach var="tag" items="${taglist2}">
                            <li class="list-group-item">
                                <div class="btn-group btn-group-xs">
                                    <a href="index?tags=${tag.text}" type="button" class="btn btn-info">${tag.text}</a>
                                    <a href="modremove?type=tags&id=${tag.ID}" type="button" class="btn btn-info">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </div>
                            </li>
                        </c:forEach>
                    </t:taglist>
                </ul>
            </div>
            <div class="col-md-2">
                <ul class="list-group">
                    <t:taglist>
                        <c:forEach var="tag" items="${taglist3}">
                            <li class="list-group-item">
                                <div class="btn-group btn-group-xs">
                                    <a href="index?tags=${tag.text}" type="button" class="btn btn-info">${tag.text}</a>
                                    <a href="modremove?type=tags&id=${tag.ID}" type="button" class="btn btn-info">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </div>
                            </li>
                        </c:forEach>
                    </t:taglist>
                </ul>
            </div>
            <div class="col-md-2">
                <ul class="list-group">
                    <t:taglist>
                        <c:forEach var="tag" items="${taglist4}">
                            <li class="list-group-item">
                                <div class="btn-group btn-group-xs">
                                    <a href="index?tags=${tag.text}" type="button" class="btn btn-info">${tag.text}</a>
                                    <a href="modremove?type=tags&id=${tag.ID}" type="button" class="btn btn-info">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </div>
                            </li>
                        </c:forEach>
                    </t:taglist>
                </ul>
            </div>
            <div class="col-md-2">
                <ul class="list-group">
                    <t:taglist>
                        <c:forEach var="tag" items="${taglist5}">
                            <li class="list-group-item">
                                <div class="btn-group btn-group-xs">
                                    <a href="index?tags=${tag.text}" type="button" class="btn btn-info">${tag.text}</a>
                                    <a href="modremove?type=tags&id=${tag.ID}" type="button" class="btn btn-info">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </div>
                            </li>
                        </c:forEach>
                    </t:taglist>
                </ul>
            </div>
            <div class="col-md-2">
                <ul class="list-group">
                    <t:taglist>
                        <c:forEach var="tag" items="${taglist6}">
                            <li class="list-group-item">
                                <div class="btn-group btn-group-xs">
                                    <a href="index?tags=${tag.text}" type="button" class="btn btn-info">${tag.text}</a>
                                    <a href="modremove?type=tags&id=${tag.ID}" type="button" class="btn btn-info">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </div>
                            </li>
                        </c:forEach>
                    </t:taglist>
                </ul>
            </div>

        </div>
    </div>
</t:base>
