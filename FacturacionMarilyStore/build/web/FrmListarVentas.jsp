<%-- 
    Document   : FrmListarVentas
    Created on : 27/10/2023, 04:47:23 PM
    Author     : Usuario
--%>

<%@page import="Entidades.Facturas"%>
<%@page import="java.util.List"%>
<%@page import="LogicaNegocio.BL_Factura"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link href="lib/bootstrap-datepicker/css/bootstrap-datepicker3.standalone.min.css" rel="stylesheet" type="text/css"/>
        <link href="lib/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="lib/fontawesome-free-5.14.0-web/css/all.min.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="	https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>
        <title>Listado de Ventas</title>
         <style>
            body{
                background-image: url('img/Fondo1.jpg');
                background-size: cover;
                background-repeat: no-repeat;
            }
        </style>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <div class="container-fluid">
                  <a class="navbar-brand" href="FrmListarFacturas.jsp">Facturacion</a>
                  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                  </button>
                  <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                      <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="index.html">Inicio</a>
                      </li>                   
                      <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                          Mantenimientos
                        </a>
                        <ul class="dropdown-menu">
                          <li><a class="dropdown-item" href="frmClientes.jsp">Clientes</a></li>
                          <li><a class="dropdown-item" href="frmListarProductos.jsp">Productos</a></li>
                          <li><a class="dropdown-item" href="#">Empleados</a></li>
                          <li><a class="dropdown-item" href="#">Historial de empleado</a></li>                     
                        </ul>
                      </li>                     
                    </ul>
                  </div>
                </div>
            </nav>
        </header>

        <div class="container">
            <div class="card-header">
                <h1>Lista de Facturas Canceladas</h1>
            </div>
            <br/>
            <form action="FrmListarVentas.jsp" method="post">
                <div class="form-group">
                    <div class="input-group">
                        <input type="text" id="txtfecha" name="txtfecha" value="" 
                               placeholder="Seleccione la fecha" class="datepicker"/> &nbsp; &nbsp;
                        <input type="submit" id="btnbuscar" name="btnBuscar" value="Buscar" 
                               class="btn-success"/> <br><br>
                    </div>
                </div>
            </form>
            </hr>
            <table class="table">
                <thead>
                    <tr>
                        <th>Num. Factura</th>
                        <th>Cliente</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                        <th>SubTotal</th>
                    </tr>
                </thead>

                <tbody>
                    <%
                        String fecha = "";
                        String condicion = ""; //Estado='Pendiente'";
                        if (request.getParameter("txtfecha") != null
                                && !request.getParameter("txtfecha").equals("")) {
                            fecha = request.getParameter("txtfecha");
                            //condicion = condicion + " AND FECHA = '" + fecha + "'";
                            condicion = condicion + "FECHA = '" + fecha + "'";
                        }

                        BL_Factura logica = new BL_Factura();
                        List<Facturas> datos;
                        datos = logica.ListarRegistros(condicion);
                        for (Facturas registro : datos) {
                    %>
                    <tr>
                        <%int num = registro.getIdFactura();%>
                        <td><%= num%></td>
                        <td><%= registro.getNombreCliente()%></td>
                        <td><%= registro.getFecha()%></td>
                        <td><%= registro.getEstado()%></td>
                        
                        <td>
                            <a href="Frm_Facturar.jsp?txtnumFactura=<%= num%>">
                                <i class="fas fa-cart-plus"></i></a>
                        </td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
            <br/>
            <a href="Frm_Facturar.jsp?txtnumFactura=-1" class="btn btn-primary">Descargar en PDF</a>
        </div>
        <script src="lib/jquery/dist/jquery.min.js" type="text/javascript"></script>
        <script src="lib/bootstrap/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>
        <script src="lib/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="lib/bootstrap-datepicker/locales/bootstrap-datepicker.es.min.js" type="text/javascript"></script>
    </body>

    <script>
        $('.datepicker').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        });
    </script>
    </body>
</html>
