
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<html>
<head>
<title>Company App Test</title>
<link href="${pageContext.request.contextPath}/resources/css/css.css"
	rel="stylesheet" type="text/css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js">
	
</script>
<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js">
	
</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js">
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/angular/angularApp.js"></script>
</head>
<body ng-app="myApp">
	<h1>Company App Test Application</h1>

	<div ng-view></div>
</body>
</html>
