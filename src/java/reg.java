import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class reg extends HttpServlet{
    public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,
            ServletException{
        res.setContentType("text/html");
        PrintWriter pw1=res.getWriter();
        String nm1=req.getParameter("n1");
        String nm2=req.getParameter("n2");
        String nm3=req.getParameter("n3");        
        String nm4=req.getParameter("n4");
        String nm5=req.getParameter("n5");
        
         try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection
                    ("jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            Statement stmt=con.createStatement();
            String q1="insert into reg2 values('"+nm1+"','"+nm2+"','"+nm3+"','"+nm4+"','"+nm5+"')";
        int x = stmt.executeUpdate(q1);
            if(x>0){
                res.sendRedirect("login.html");
            }
            else{
                pw1.println("Registration unsuccess");
            }
            con.close();
        }
        catch(Exception e){
            pw1.println(e);
        }
        
    }
}