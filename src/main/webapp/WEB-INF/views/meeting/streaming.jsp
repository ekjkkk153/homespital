<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sample Streaming</title>
    <!-- Latest minified Bootstrap & JQuery-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <!-- Custom styles for this template -->
    <%--    <link rel="stylesheet" type="text/css" href="/css/main.css"/>--%>
</head>
<body class="text-center">

<!-- Begin page content -->
<main role="main" class="container-fluid">
    <h1>Simple WebRTC Signalling Server</h1>
    <div class="d-flex justify-content-around mb-3">
        <div id="buttons" class="">
            <button type="button" class="btn btn-primary" id="qvga">QVGA</button>
            <button type="button" class="btn btn-primary" id="vga">VGA</button>
            <button type="button" class="btn btn-primary" id="hd">HD</button>
        </div>
    </div>
    <div id="videoblock" class="col-lg-12 mb-3">
        <video autoplay playsinline></video>
    </div>
</main>

<script src="/resources/js/meeting/streaming.js"></script>
</body>
</html>