<%@ page language="java" contentType="text /html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
</head>
﻿<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
	// document ready..
	$(function() {
		var pathName = location.pathname;
		
		$("#btn").click(function() {
		});	
		
		$('#file').change(function() {
			console.log('file changed');
			
			var selectedFileList = this.files; // this는 #file
			console.log(selectedFileList);

			$.ajax({
				type: 'POST',
				url: pathName + "/file",
				data: {
					a: "1",
					b: "2"
				},
				success: function(result) {
					console.log('success');
					console.log(result);
				},
				error: function(e) {
					console.log("ERROR: " + e);
				}
			});
		});
	});

</script>

<body>
	<P>  test </P>
	<input id="file" type="file" accept="image/*"; capture="camera" multiple/>
	<button id="btn">버튼</button>
</body>
</html>
