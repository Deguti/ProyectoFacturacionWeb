/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Entidades.Cliente;
import LogicaNegocio.LNClientes;
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
public class CrearModifClientes extends HttpServlet {

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
        // No la incluimos dentro del Try, porque necesitamos usarla en el catch

        try {
            LNClientes Logica = new LNClientes();
            Cliente cliente = new Cliente();
            int resultado;

            cliente.setId_cliente(Integer.parseInt(request.getParameter("txtCodigo")));
            // txtCodigo sabemos que tiene un int, por ello lo parseamos a Intenger

            cliente.setNombre(new String(request.getParameter("txtNombre").getBytes("ISO-8859-1"), "UTF-8"));
            // Los campos de texto les aplicamos una CODIFICACIÓN DE CARACTERES con un new String y con el 
            // método getBytes. Esto por si viene con caracteres que no sean reconocidos por la BD por ejemplo. 
            // De no hacer esta conversión, por ejemplo en lugar de las tildes o las ñ pueden aparecer símbolos "extraños" para el usuario
            cliente.setApellido1(new String(request.getParameter("txtP_Apellido").getBytes("ISO-8859-1"), "UTF-8"));
            cliente.setApellido2(new String(request.getParameter("txtS_Apellido").getBytes("ISO-8859-1"), "UTF-8"));
            cliente.setTelefono(request.getParameter("txtTelefono"));
            // El teléfono en teoría sólo tiene números y guiones. Pero si quisiéramos prevenir que no incluya 
            // caracteres especiales no reconocidos, también podríamos aplicarle la codificación de caracteres. 

            cliente.setDireccion(new String(request.getParameter("txtDireccion").getBytes("ISO-8859-1"), "UTF-8"));

            // Si tuviéramos que trabajar un dato del tipo fecha:
            // Por ejemplo cuando se haga la facturación, que necesita llevar una fecha
            /*
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaString = request.getParameter("txtFecha"); // en este ejemplo ese campo no existe
            Date fecha = formato.parse(fechaString);
            java.sql.Date fechasql = new java.sql.Date(fecha.getTime());
            EntidadFactura.setFecha(fechasql); // en este ejemplo no existe esa entidad
            */
            
            if (cliente.getId_cliente() > 0) {
                resultado = Logica.Modificar(cliente);
            } else {
                resultado = Logica.Insertar(cliente);
            }
            response.sendRedirect("frmClientes.jsp");

        } catch (Exception ex) {
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
