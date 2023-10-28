/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Acceso_Datos.ClaseConexion;
import Entidades.DetalleFactura;
import Entidades.Facturas;
import LogicaNegocio.BL_Factura;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
public class CrearPDF extends HttpServlet {

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
        response.setContentType("application/pdf");
        Connection _Conexion = null; 
        BL_Factura LogicaFactura = new BL_Factura();
        Facturas document = new Facturas();
        DetalleFactura EntidadDetalle = new DetalleFactura();
        List<Facturas> ListaF = new ArrayList<>();
        int resultado;
    try {
            // Inicializa el documento y el escritor PDF
            PdfWriter.getInstance(document, response.getOutputStream());


            // Conectarse a la base de datos
            _Conexion = ClaseConexion.getConnection();

            // Consulta SQL para obtener los datos de la tabla
            String sql = "SELECT * FROM Facturas";
            PreparedStatement statement = _Conexion.prepareStatement(sql);
            ResultSet RS = statement.executeQuery();

            // Crear una tabla en el PDF
            PdfPTable table = new PdfPTable(4);

            // Agregar encabezados
            table.addCell("Num. Factura");
            table.addCell("Id Cliente");
            table.addCell("Fecha");
            table.addCell("Estado");

            // Agregar datos de la tabla a la tabla en el PDF
            while (RS.next()) {
                table.addCell(String.valueOf(RS.getInt("NUM_FACTURA")));
                table.addCell(String.valueOf(RS.getInt("Id_Cliente")));
                table.addCell(RS.getString("Fecha"));
                table.addCell(RS.getString("Estado"));
            }

            // Agregar la tabla al documento
            document.add(new Phrase("Información de la Tabla\n\n"));
            document.add(table);


        } catch (Exception e) {
            e.printStackTrace();
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
