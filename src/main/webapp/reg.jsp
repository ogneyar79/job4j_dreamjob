<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 21.10.2020
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Работа мечты</title>
    <script>
        function validate() {
            alert('function validate')
            let name = $('#names').val();
            let password = $('#pass').val();

            let letters = /^[A-Za-z]+$/;
            if (!name.match(letters)) {
                console.log(name)
                alert('Username must have alphabetcharactersonly');
                // name.focus();
                return false
            }
            alert("Password" + password)
            if (password.length === 0 || password.length >= my || password.length < mx) {
                alert("Password should not be empty / length be between " + mx + " to " + my);
                //    password.focus();
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg.do" method="post">
                    <div class="form-group">
                        <label>ИМЯ ПОЛЬЗОВАТЕЛЯ</label>
                        <input type="text" class="form-control" name="name" id="names">
                    </div>
                    <div class="form-group">
                        <label>Почта</label>
                        <input type="email" class="form-control" name="email" id="emails">
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="password" class="form-control" name="password" id="pass">
                    </div>
                    <button onclick="validate()" type="submit" class="btn btn-default">ЗАРЕГИСТРИРОВАТЬ</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
