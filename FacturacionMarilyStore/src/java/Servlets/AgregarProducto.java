package Servlets;

import Entidades.Producto;
import LogicaNegocio.BL_Producto;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
public class AgregarProducto extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
  PrintWriter out = response.getWriter();
        try{
            BL_Producto logica = new BL_Producto();
            int resultado;
            // aqui se deberia verificar que los parametros sean valores validos, no se hizo en este ejemplo
            Producto entidad = new Producto(Integer.parseInt(request.getParameter("txtIdproducto")),
                                                request.getParameter("txtdescripcion"),
                                                Double.parseDouble(request.getParameter("txtprecio")),
                                                Double.parseDouble(request.getParameter("txtexistencia")));
            if (entidad.getIdProducto()> 0) {
                resultado = logica.Modificar(entidad);
            } else {
                resultado = logica.Insertar(entidad);
            }
            response.sendRedirect("frmListarProductos.jsp");
        }catch(Exception ex){
            out.print(ex.getMessage());
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
