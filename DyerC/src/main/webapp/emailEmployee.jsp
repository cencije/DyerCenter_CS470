<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Email Dyer Center Employee</title>
</head>
<body>
	<h1>Contact Us!!</h1>
	
	<form method="POST" actions="MailDispatcherServlet"> 
		<table>
			<tr>
				<td> align="right"<b>To:</b></td>
				<td> 
					<input type = "text" name="email" size="75">
				</td>
			</tr>
			<tr>
				<td> align="right"<b>Subject:</b></td>
				<td> 
					<input type = "text" name="subject" size="75">
				</td>
			</tr>
			<tr>
				<td> align="right"<b>Message:</b></td>
				<td> 
					<textarea name="message" cols="75" rows="6"></textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td> 
					<input type = "submit" value="send">
				</td>
			</tr>
		</table>
	</form>
	
	
</body>
</html>