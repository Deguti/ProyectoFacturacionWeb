<%-- 
    Document   : frmListarProductos
    Created on : 24/10/2023, 08:07:41 PM
    Author     : Derick Gutierrez
--%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Entidades.Producto"%>
<%@page import="LogicaNegocio.BL_Producto"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Lista de productos</title>
        <link href="lib/bootstrap-datepicker/css/bootstrap-datepicker3.standalone.min.css" rel="stylesheet" type="text/css"/>
        <link href="lib/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="lib/fontawesome-free-5.14.0-web/css/all.min.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="	https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>
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
                          <li><a class="dropdown-item" href="#">Empleados</a></li>
                          <li><a class="dropdown-item" href="#">Historial de empleado</a></li>                     
                        </ul>
                      </li>                     
                    </ul>
                  </div>
                </div>
            </nav>
        </header>
        <br/>
                <div class="container">  <!-- container y card-header son clases de BOOTSTRAP -->
            <div class="row">
                <div class="col-md-8 mx-auto">

                    <div class="card-header">
                        <h1>Listado de Productos</h1>
                    </div>
                    <br/>

                    <form action="frmListarProductos.jsp" method="post">
                        <div class="form-group">
                            <div class="input-group">
                                <label for="txtnombre">Nombre de Producto</label>&nbsp; &nbsp;
                                <!-- el for de este label lo unico que hace asociar esa etiqueta con el input -->
                                <input type="text" id="txtnombre" name="txtnombre" placeholder="Descripción" class="form-control"/>&nbsp; &nbsp;
                                <input type="submit" value="Buscar" name="btnBuscar" class="btn btn-primary"/>
                            </div>
                        </div>
                    </form>
                    <br>

                    <%
                        if (request.getParameter("mensaje") != null
                                && !request.getParameter("mensaje").equals("")) {
                            out.print("<p style='color:red'>" + request.getParameter("mensaje") + "</p>");
                        }
                    %>

                    <table class="table">
                        <tr>
                            <th style="text-align: left">Código</th>
                            <th style="text-align: left">Descripción</th>
                            <th style="text-align: left">Precio</th>
                            <th style="text-align: left">Existencia</th>
                            <th style="text-align: left">Opciones</th>
                        </tr>

                        <%
                            String nombre; // la vamos a llenar con lo que traiga la solicitud
                            String condicion = "";
                            if (request.getParameter("txtnombre") != null
                                    && !request.getParameter("txtnombre").equals("")) {
                                nombre = request.getParameter("txtnombre");
                                condicion = "descripcion like '%" + nombre + "%'";
                            }
                            BL_Producto logica = new BL_Producto();
                            List<Producto> datos = logica.ListarRegistros(condicion);
                            for (Producto registro : datos) {

                        %>

                        <tr>
                            <td><%=registro.getIdProducto()%></td>
                            <td><%= new String(registro.getDescripcion().getBytes("ISO-8859-1"), "UTF-8")%></td>
                           <!--   <td><%=DecimalFormat.getCurrencyInstance().format(registro.getPrecio())%></td> -->
                            <td><%=registro.getPrecio()%></td>
                            <td><%=registro.getExistencia()%></td>
                            <td>
                                <a href="frmProductos.jsp?txtIdproducto=<%= registro.getIdProducto()%>"><i class="fas fa-user-edit"></i></a> |
                                <a href="EliminarProducto?txtIdproducto=<%= registro.getIdProducto()%>"><i class="fas fa-trash-alt"></i></a>
                            </td>
                        </tr>
                        <% } // fin del For %>

                    </table>

                    <br>
                    <a href="frmProductos.jsp" class="btn btn-primary" >Agregar Nuevo Producto</a>
                    <a href="frmListarProductos.jsp" class="btn btn-primary" >Actualizar Lista</a>

                </div> <!-- clase que crea las 6 columnas -->

            </div> <!-- class row, div de la fila -->
        </div> <!-- class container -->

        <script src="lib/jquery/dist/jquery.min.js" type="text/javascript"></script>
        <script src="lib/bootstrap/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>
    </body>
</html>
