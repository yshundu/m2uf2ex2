/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2uf2ex2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;

/** Servlet that displays a list of items being ordered.
 *  Accumulates them in an ArrayList with no attempt at
 *  detecting repeated items. Used to demonstrate basic
 *  session tracking. Updated to use generics.
 *  <p>
 *  From <a href="http://courses.coreservlets.com/Course-Materials/">the
 *  coreservlets.com tutorials on servlets, JSP, Struts, JSF, Ajax, GWT, 
 *  Spring, Hibernate, JPA, and Java</a>.
 */

@WebServlet("/show-items")
public class ShowItems extends HttpServlet {
  @Override
  public void doPost (HttpServletRequest request,
                      HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    synchronized(session) {
      @SuppressWarnings("unchecked")
              
      List<String> previousItems =
        (List<String>)session.getAttribute("previousItems");
         
      List<Integer> previousInteger =
        (List<Integer>)session.getAttribute("previousInteger");
      
      boolean changedOld=false;
      
      if (previousItems == null) {
        previousItems = new ArrayList<String>();
        previousInteger = new ArrayList<Integer>();
      }
      
      String newItem = request.getParameter("newItem");
      if ((newItem != null) &&
          (!newItem.trim().equals(""))) {
        for(int i=0; i<previousItems.size();i++) {
            if (previousItems.get(i).equalsIgnoreCase(newItem) && changedOld == false) {
                previousInteger.set(i, previousInteger.get(i)+1);
                changedOld=true;
            }
            
        }
        if (changedOld==false) {
                previousItems.add(newItem);
                previousInteger.add(1);
            }
      }
      session.setAttribute("previousItems", previousItems);
      session.setAttribute("previousInteger", previousInteger);
      
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Items Purchased";
      String docType =
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
        "Transitional//EN\">\n";
      out.println(docType +
                  "<HTML>\n" +
                  "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                  "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                  "<H1>" + title + "</H1>");
      if (previousItems.size() == 0) {
        out.println("<I>No items</I>");
      } else {
        out.println("<UL>");
        for(int i = 0; i<previousItems.size();i++) {
          out.println("  <LI>" + previousItems.get(i)+" ("+ previousInteger.get(i)+")");
          
        }
        out.println("</UL>");
      }
      out.println("</BODY></HTML>");
    }
  }
}
