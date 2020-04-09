import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "uploads";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String hiddenParam = request.getParameter("type");
		request.setAttribute("hide", "document.getElementById(\"details\").style.display = \"none\";");
				
		if(hiddenParam == null) {
			if(ServletFileUpload.isMultipartContent(request)){
	            try {
	                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	               
	                for(FileItem item : multiparts){
	                    if(!item.isFormField()){
	                    	File file = new File(UPLOAD_DIRECTORY);
	                    	if(!file.isDirectory())file.mkdir();
	                        file = new File(item.getName());
	                        File file1 = new File(UPLOAD_DIRECTORY + File.separator + file.getName());
	                        item.write(file1);
	                    }
	                }
	            
	               //File uploaded successfully
	               request.setAttribute("message", "File Uploaded Successfully");
	            } catch (Exception ex) {
	               request.setAttribute("message", "File Upload Failed due to " + ex);
	            }          
	          
	        }else{
	            request.setAttribute("message",
	                                 "Sorry this Servlet only handles file upload request");
	        }
			//request.getRequestDispatcher("/index.jsp").forward(request, response);
			doGet(request,response);
		}else if(hiddenParam.equals("delete")) {
			
			File file = new File(UPLOAD_DIRECTORY);
			try {
				String[] fileNames = file.list();
				int len = fileNames.length;
				if(len>0) {
					for(int i=0; i<len; i++) {
						File toDelete = new File(UPLOAD_DIRECTORY + File.separator + fileNames[i]);
						System.out.println(UPLOAD_DIRECTORY + File.separator + fileNames[i]);
						toDelete.delete();
					}
				}
			}
			catch(Exception e) {
				
			}
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			//doGet(request,response);
		}
		else if(hiddenParam.equals("check")) {
			request.getRequestDispatcher("/algos.jsp").forward(request, response);
		}
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("uploaded");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String button = "";
		String files = "";
		File file = new File(UPLOAD_DIRECTORY);
		if(file.isDirectory()) {
			String[] fileNames = file.list();
			for(String name: fileNames) {
				files += "<p>" + name + "</p>";
			}
		}
		
		request.setAttribute("files", files);
		
		if(!files.equals("")) {
			button += "<form action=\"AlgoServlet\" method=\"get\">\r\n" + 
				"<input type=\"submit\" value=\"View and select Algorithms\"/>\r\n" + 
				"</form>";
		}
		
		request.setAttribute("button", button);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		//response.sendRedirect("/Servlet/index.jsp");
	}

}
