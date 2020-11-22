<%--
  Created by IntelliJ IDEA.
  User: maksimsirotkin
  Date: 02.08.2020
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Candidate" %>
<%@ page import="store.PsqlStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
    </script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="jquery.js"></script>
    <script type="text/javascript" src="jquery.autocomplete.js"></script>


    <title>Работа мечты</title>
</head>
<body>
<script>
    $(document).ready(function () {
        $.ajax({
            url: 'http://localhost:8080/job4j_dreamjob_war_exploded/city',
            dataType: 'json',
            type: "POST"
        }).done(function (data) {
            console.log(data)
            let ar = [];
            let jsFrom = JSON.parse(data);
            jsFrom.forEach(el => ar.push(el));
            $("#autocomplete").autocomplete(
                ar,
                {
                    delay: 0,
                    minChars: 1,
                    matchSubset: 1,
                    autoFill: true,
                    maxItemsToShow: 10
                });

        }).fail(function (err) {
            alert(err);
        });
    })
</script>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", 0, 0);
    if (id != null) {
        candidate = PsqlStore.instOf().findCandidate(Integer.valueOf(id));
    }
%>

<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <c:out value="User Name"/>
            <c:out value="${name}"/>
            <div class="card-header">
                <% if (id == null) { %>
                НОВЫЙ КАНДИДАТ.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>&photoId=<%=candidate.getPhotoId()%>&cityId=<%=candidate.getCityId()%>"
                      method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name">
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>

            <div class="container">
                <form>
                    <div class="form-group" id="auto">
                        <label for="exampleInputEmail1">SELECT CITY</label>
                        <input type="text" name="city" id="autocomplete" autocomplete="off"/>
                    </div>
                    <button type="button">Submit</button>
                </form>
            </div>


        </div>
    </div>
</div>
</body>
</html>
