<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
</head>
<body>
<tiles:insertAttribute name="header" />
<tiles:insertAttribute name="left" />
  <tiles:insertAttribute name="leftchat" />
<tiles:insertAttribute name="body" />
</body>
</html>