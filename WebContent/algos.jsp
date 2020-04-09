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
		<script type="text/javascript">
			function jsFunction(value){
				var x = document.getElementById("algos");
				if(!x.options[x.selectedIndex].value.localeCompare("classification")){
					document.getElementById("classi").style.display = "block";
					document.getElementById("reg").style.display = "none";
					console.log(x.options[x.selectedIndex].text);
				}
				else if(!x.options[x.selectedIndex].value.localeCompare("regression")){
					document.getElementById("reg").style.display = "block";
					document.getElementById("classi").style.display = "none";
					console.log(x.options[x.selectedIndex].text);
				}
				else if(!x.options[x.selectedIndex].value.localeCompare("default")){
					document.getElementById("reg").style.display = "none";
					document.getElementById("classi").style.display = "none";
					console.log(x.options[x.selectedIndex].text);
				}
			}
		</script>
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
		<div align='center'>
			<p>Uploaded data:</p>
        	${requestScope["values"]}
        	<br><br>
        	${requestScope["message"]}
        </div>
        <br><br>
        <div align='center'>
        	<form action='AlgoServlet' method='post'>
        		<p>Does the data contain header:</p>
        		<input type="radio" name="header" value="true"/>
        		<label>Yes</label>
        		<input type="radio" name="header" value="false"/>
        		<label>No</label><br><br>
        		<label for="algorithm">Choose a type:</label>
				<select id="algos" name="dropdown" onchange="jsFunction(this.value)">
					<option value="default">Default</option>
					<option value="classification">Classification</option>
					<option value="regression">Regression</option>
				</select>
				<br><br>
				<div id="classi" style = "display:none">
					<label>Choose Ordinal Data coloumn:</label>
					<select name="colc">
						${requestScope["columns"]}
					</select>
				</div>
				<div id="reg" style = "display:none">
					<label>Choose Dependant coloumn:</label>
					<select name="colr">
						${requestScope["columns"]}
					</select>
				</div>
				<br><br>
				<input type='submit' value='Submit' />
        	</form>
        </div>
	</body>
</html>