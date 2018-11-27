<!DOCTYPE html>
<html ng-app="sariGaMa">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>sariGaMa</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no" name="viewport">
  <script type="text/javascript">
 var contextPath=window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
 console.log(contextPath);
 </script>
 <!-- <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css"> -->

 
</head>
<body class="hold-transition skin-blue sidebar-mini">

<!-- <ui-view></ui-view> -->

<script src="chatUI/resources/static/bower_components/angular/angular.min.js"></script>
<script src="chatUI/resources/static/app/app.js"></script>
<script src="chatUI/resources/static/bower_components/angular-sanitize/angular-sanitize.min.js"></script>
<script src="chatUI/resources/static/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
<ui-view>
</ui-view>
</body>
</html>

<!-- <html>
    <body>
        <div style="margin-left: 20%;">
            <div style="margin-left: 25%;margin-top: 4%;font-size: 40px;">SariGaMa Chat</div>
            <img src="sarigama/images/livechat-animation.gif" style="padding: 3%;">
        </div>
    </body>
</html> -->



