<%-- 
    Document   : frmProductos
    Created on : 24/10/2023, 08:28:28 PM
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Entidades.Producto" %>
<%@page import="LogicaNegocio.BL_Producto" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Agregar productos</title>
        <link href="lib/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="lib/fontawesome-free-5.14.0-web/css/all.min.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="	https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>
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
            <div class="row">
                <div class="col-md-8 mx-auto">
                    <div class="card-header">
                        <h1>Lista de Productos</h1>
                    </div>

                    <%
                        Producto entidad;
                        BL_Producto logica = new BL_Producto();
                        int idproducto;
                        if (request.getParameter("txtIdproducto") != null
                                && !request.getParameter("txtIdproducto").equals("")) {
                            idproducto = Integer.parseInt(request.getParameter("txtIdproducto"));
                            entidad = logica.ObtenerRegistro("Id_producto=" + idproducto);
                        } else {
                            entidad = new Producto();
                            entidad.setIdProducto(-1);
                        }
                    %>
                    <br>
                    <form action="AgregarProducto" method="post">

                        <div class="form-group">
                            <input type="hidden" name="txtIdproducto" id="txtIdproducto" class="form-control"
                                   value="<%= entidad.getIdProducto()%>" readonly/>

                            <label for="txtdescripcion">Descripción</label>
                            <input type="text" name="txtdescripcion" id="txtdescripcion" 
                                   value="<%= entidad.getDescripcion()%>" required class="form-control"/>
                        </div>  
                        <div class="form-group">
                            <label for="txtprecio">Precio</label>
                            <input type="text" name="txtprecio" id="txtprecio" 
                                   value="<%= entidad.getPrecio()%>" required class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="txtexistencia">Existencia</label>
                            <input type="text" name="txtexistencia" id="txtexistencia" 
                                   value="<%= entidad.getExistencia()%>" required class="form-control"/>

                        </div>  
                        <div class="form-group">
                            <input type="submit" value="Guardar" class="btn btn-primary">
                            <input type="button" id="btnRegresar" value="Regresar" onclick="location.href = 'frmListarProductos.jsp'" class="btn btn-secondary"/>
                        </div>  
                    </form>
                </div> <!-- clase que crea las 6 columnas -->

            </div> <!-- class row, div de la fila -->
        </div> <!-- class container -->

        <script src="lib/jquery/dist/jquery.min.js" type="text/javascript"></script>
        <script src="lib/bootstrap/dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>
    </body>
</html>
