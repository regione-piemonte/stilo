/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class ForwardingServlet extends HttpServlet
{
    private static final long serialVersionUID = 6494825227879521542L;
    
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/samlsso") || request.getRequestURI().endsWith("/openid") || request.getRequestURI().endsWith("/token")) {
            request.getRequestDispatcher("acs.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
        else if (request.getRequestURI().endsWith("/logout")) {
            request.getRequestDispatcher("loginSchemeSelection.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
    
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}