<html>
	<head>
		<style>
		header{
			margin: -9px;
			padding: 5px;
			background-color: #3b404f;
			color: #ffffff
		}
		body{
		}
		#table-scroll {
  			height:400px;
  			overflow:auto;  
  			margin-top:20px;
  		}
  		#form{
  			margin-left: auto;
  			margin-right: auto;
  			margin-top:20px;
  		}
		</style>
	</head>
	<body>
		<header>
			<table style="width:100%; color: #ffffff; padding: 20px;">
				<tr>
					<th width=20%></th>
					<th width=20%></th>
					<th width=20% align="center"><pre>Welcome</pre></th>
					<th width="28%" align="right" onClick="document.location.href='algos.jsp';"><pre>See Algorithms</pre></th>
					<th width="2%" align="center"><pre>|</pre></th>
					<th width="10%" align="left" onClick="document.location.href='index.jsp';"><pre>Choose Dataset</pre></th>
				</tr>
			</table>
		</header>
		<br><br>
		<div align="center">
			<label for="myfile">Select a file:</label>
			<form action="DemoServlet" method="post" enctype="multipart/form-data" >
				<input type="hidden" value="upload" name="type"/>
				<input type="file" name="Browse" multiple="multiple" />
				<input type="submit" value="upload" />
			</form>
			<form action="DemoServlet" method="post">
				<input type="hidden" value="delete" name="type"/>
				Remove uploaded files:
				<input type="submit" value="Delete" />
			</form>
		</div><br><br>
		<div align='center'>
			<div id="result">
            	<h3>${requestScope["message"]}</h3><br>
            	<form action="DemoServlet" method="get">
            		<input type="submit" value="Get items" />
            	</form>
            	${requestScope["files"]}
        	</div>
		</div>
		<div align='center' id="details">
			${requestScope["button"]}
		</div>
	</body>
</html>