/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
		{
		try {
			processRequest(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        JobOperator jo = BatchRuntime.getJobOperator();
        
        Properties p = new Properties();
       long id = jo.start("GenericJob",p);
         
        out.println("Job submitted: " + id );
      
       // jo.get
        	
        } catch (JobStartException | JobSecurityException ex) {
         out.println("Error submitting Job! " + ex.getMessage() );
         ex.printStackTrace();
        }
        out.flush();
	}
}
