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
		<div align='center'>
			<h3>Dataset Properties:</h3>
			<p>${requestScope["dataset"]}</p>
			<br><br>
			<h3>Evaluation Properties (on trained set):</h3>
			<p>${requestScope["evaluation"]}</p>
		</div>
	</body>
</html>