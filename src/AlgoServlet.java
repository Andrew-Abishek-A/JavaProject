
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AlgoServlet")
public class AlgoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "uploads";
	//private static final String TEST_DIRECTORY = System.getProperty("user.dir") + File.separator + "tests";
	Classification classi = null;
	Regression reg = null;
	int flag = 0;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String values="";
		
		File file = new File(UPLOAD_DIRECTORY);
		try {
			String[] fileNames = file.list();
			int len = fileNames.length;
			if(len>0) {
				for(int i=0; i<len; i++) {
					String ext = getFileExtension(fileNames[i]);
					if(ext.equals("csv")) {
						//request.setAttribute("message", "Loading the dataset");
						File csvFile = new File(UPLOAD_DIRECTORY + File.separator + fileNames[i]);
						try {
							values += "<div id=\"table-scroll\">";
							values += "<table style=\"width:80%; color: #000000; padding: 20px; margin-left: auto; margin-right: auto;\">\n";
							Scanner sc = new Scanner(csvFile);
							boolean flag = true;
							while(sc.hasNextLine()) {
								String[] str = sc.nextLine().split(",");
								//StringTokenizer str = new StringTokenizer(sc.nextLine(), ",\"");
								int length=str.length;
								if(flag) {
									flag = false;
									values += "<tr>\n";
									String columns = "";
									for(int j=0; j<length; j++) {
										values += "<th align=\"center\">" + "<b>Column " +(j+1)+ "<b>" + "</th>\n";
										columns += "<option value=\""+ (j+1) +"\">Column "+ (j+1) +"</option>";
									}
									request.setAttribute("columns", columns);
									values += "</tr>\n";
								}
								values += "<tr>\n";
								for(int j=0; j<length; j++) {
									values += "<td align=\"center\">" + str[j] +"</td>\n";
								}
								values += "</tr>\n";
							}
							values += "</table>";
							values += "</div>";
							sc.close();
							//request.setAttribute("hide", "document.getElementById(\"details\").style.display = \"block\";");
						}catch(Exception e) {
							request.setAttribute("hide", "document.getElementById(\"details\").style.display = \"none\";");
						}
					}
					else if(ext.equals("xls") || ext.equals("xlxs")) {
						
					}
					else {
						request.setAttribute("hide", "document.getElementById(\"details\").style.display = \"none\";");
						request.setAttribute("message", "Error loading the dataset");
					}
				}
			}
		}catch(Exception e) {
				
		}
		
		request.setAttribute("values", values);
		
		request.getRequestDispatcher("/algos.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		//System.out.println(request.getParameter("dropdown"));
		if(request.getParameter("test").equals("test")) {
			try {
				if(flag == 1) {
					int attr = classi.dataset.numAttributes();
					String data = "";
					for(int i=0;i<attr-1;i++) {
						data += request.getParameter(classi.dataset.attribute(i).name()) + ",";
						//System.out.println(data);
					}
					//File file = new File(UPLOAD_DIRECTORY + File.pathSeparator + "test.csv");
					String output = classi.test(data);
					//System.out.println(output);
					StoreToDataBase.store(data,output);
					request.setAttribute("output", output);
					request.setAttribute("message", "Classified Class is:");
					request.getRequestDispatcher("/output.jsp").forward(request, response);
				}
				else {
					int attr = reg.dataset.numAttributes();
					String data = "";
					for(int i=0;i<attr-1;i++) {
						data += request.getParameter(reg.dataset.attribute(i).name()) + ",";
					}
					String output = reg.test(data);
					System.out.println(output);
					StoreToDataBase.store(data,output);
					request.setAttribute("output", output);
					request.setAttribute("message", "Predicted Value is:");
					request.getRequestDispatcher("/output.jsp").forward(request, response);
				}
			}
			catch(Exception e) {
				System.out.println("This cause varialble is null: " +e);
			}
		}
		else {
			if(request.getParameter("dropdown").equals("classification")) {
				//System.out.println(request.getParameter("colc"));
				String header = request.getParameter("header");
				int column = Integer.parseInt(request.getParameter("colc"));
				Classification obj = new Classification();
				classi = obj;
				flag = 1;
				String a = obj.newDataSet(Boolean.parseBoolean(header));
				obj.loadModel(column);
				String b = obj.evaluate();
				StringTokenizer str = new StringTokenizer(a, "\n");
				String dataset = "";
				while(str.hasMoreTokens()) {
					//System.out.println(str.nextToken());
					dataset += "<pre>"+ str.nextToken() +"</pre>";
				}
				str = new StringTokenizer(b, "\n");
				String eval = "";
				while(str.hasMoreTokens()) {
					eval += "<pre>"+ str.nextToken() +"</pre>";
				}
				String inputs="";
				int attr = obj.dataset.numAttributes();
				for(int i=0;i<attr-1;i++) {
					inputs += "Enter " + obj.dataset.attribute(i).name() + ": ";
					if((i+1)%3 != 0)
						inputs += "<input type=\"text\" name=\""+ obj.dataset.attribute(i).name() +"\"/> ";
					else
						inputs += "<input type=\"text\" name=\""+ obj.dataset.attribute(i).name() +"\"/><br><br>";
				}
				//System.out.println(inputs);
				request.setAttribute("inputs", inputs);
				request.setAttribute("dataset", dataset);
				request.setAttribute("evaluation", eval);
				request.getRequestDispatcher("/result.jsp").forward(request, response);
			}
			else if(request.getParameter("dropdown").equals("regression")) {
				//System.out.println(request.getParameter("colr"));
				String header = request.getParameter("header");
				int column = Integer.parseInt(request.getParameter("colr"));
				Regression obj = new Regression();
				reg = obj;
				String a = obj.newDataSet(Boolean.parseBoolean(header));
				obj.loadModel(column);
				String b = obj.evaluate();
				StringTokenizer str = new StringTokenizer(a, "\n");
				String dataset = "";
				while(str.hasMoreTokens()) {
					//System.out.println(str.nextToken());
					dataset += "<pre>"+ str.nextToken() +"</pre>";
				}
				str = new StringTokenizer(b, "\n");
				String eval = "";
				while(str.hasMoreTokens()) {
					eval += "<pre>"+ str.nextToken() +"</pre>";
				}
				String inputs="";
				int attr = obj.dataset.numAttributes();
				for(int i=0;i<attr-1;i++) {
					inputs += "Enter " + obj.dataset.attribute(i).name() + ": ";
					if((i+1)%3 != 0)
						inputs += "<input type=\"text\" name=\""+ obj.dataset.attribute(i).name() +"\"/> ";
					else
						inputs += "<input type=\"text\" name=\""+ obj.dataset.attribute(i).name() +"\"/><br><br>";
				}
				request.setAttribute("inputs", inputs);
				request.setAttribute("dataset", dataset);
				request.setAttribute("evaluation", eval);
				request.getRequestDispatcher("/result.jsp").forward(request, response);
			}
			else {
				request.setAttribute("message", "Select Classification or Regression");
				doGet(request, response);
			}
		}
		
	}
	
	private static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

}
