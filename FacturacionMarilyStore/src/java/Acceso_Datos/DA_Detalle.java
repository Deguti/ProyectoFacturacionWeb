
package Acceso_Datos;

import Entidades.DetalleFactura;
import java.sql.*;
import java.util.*;

public class DA_Detalle {
    // ATRIBUTOS
    private String _Mensaje;

    // PROPIEDADES
    public String getMensaje() {
        return _Mensaje;
    }

    // CONSTRUCTOR VACÍO
    public DA_Detalle() {
        _Mensaje = "";
    }
    
    // METODOS
    
    public int Eliminar(DetalleFactura Entidad) throws Exception{
        CallableStatement CS = null;
        int resultado = -1;
        Connection _Conexion = null;
        try{
            _Conexion = ClaseConexion.getConnection();
            CS = _Conexion.prepareCall("{call Eliminar_Detalle(?,?,?)}");
            
            CS.setInt(1, Entidad.getIdFactura());
            CS.setInt(2, Entidad.getIdProducto());
            CS.setString(3, _Mensaje);
            resultado = CS.executeUpdate();
        }catch (Exception ex){
            resultado = -1;
            throw ex;
        }finally{
            if (_Conexion != null ) {
                ClaseConexion.close(_Conexion);
            }
        }
        return resultado; // la cantidad de filas afectadas
    } // fin Eliminar
    
    
    public List<DetalleFactura> ListarRegistros(String Condicion) throws Exception{
        ResultSet RS = null;
        /*  Porque la consulta la BD SIEMPRE va a devolver eun ResultSet, no hay
            forma que la consulta nos devuelva una LISTA. 
            Luego convertimos el ResultSet a una Lista para retornarla 
        */
                
        DetalleFactura Entidad;
        List<DetalleFactura> lista = new ArrayList<>();
        Connection _Conexion = null;
        
        try {
            _Conexion = ClaseConexion.getConnection();
            Statement ST = _Conexion.createStatement();
            String Sentencia;
            
            Sentencia = "SELECT NUM_FACTURA, DETALLEFACTURA.ID_PRODUCTO,DESCRIPCION,CANTIDAD,PRECIO_VENTA "
                    + "FROM DETALLEFACTURA "
                    + "INNER JOIN PRODUCTOS ON DETALLEFACTURA.ID_PRODUCTO = PRODUCTOS.ID_PRODUCTO";
            
            /*  La sentencia puede ser tan grande o compleja como se necesite, puede incluir todo lo que sea 
                necesario para obtener la INFORMACIÓN ÚTIL que requiera el USUARIO para que su trabajo sea
                más fácil (no para que el trabajo del programador(a) sea más fácil !)
            
                Con estas instrucciones muy largas una buena estrategia es escribirlas primero en SSMS y verificar que
                funcionan correctamente, luego copiar y pegar la consulta aquí. 
                Existen muchas otras opciones, por ejemplo crear una función en la BD que haga esa consulta y llamar
                a la función, y muchas otras formas más. 
            */
            
            if(!Condicion.equals(""))  {
                Sentencia = String.format("%s WHERE %s", Sentencia, Condicion);
            }
            
            RS = ST.executeQuery(Sentencia);
            
            while(RS.next()){
                Entidad = new DetalleFactura(RS.getInt("NUM_FACTURA"), 
                                                RS.getInt("ID_PRODUCTO"), 
                                                RS.getString("DESCRIPCION"),
                                                RS.getInt("CANTIDAD"),
                                                RS.getInt("PRECIO_VENTA"));
                // los datos que se refieren al RESULTSET tienen que utilizar los mismos nombres que están dentro de la BD
                // y en el ORDEN del constructor 
                
                // Si se pasaron numeros seria asi, tomando en cuenta el ORDEN en que aparecen en la consulta del SELECT de arriba
                // no necesariamente van en orden numérico, dependen de la posicion en que se encuentren en el SELECT
                
//                Entidad = new DetalleFactura(RS.getInt(1), 
//                                                RS.getInt(2), 
//                                                RS.getString(3),
//                                                RS.getInt(4),
//                                                RS.getDouble(5));
                
                lista.add(Entidad);
            }
        }
        catch (Exception ex) {
            throw ex;
        } finally{
            if (_Conexion != null) ClaseConexion.close(_Conexion);
        }
        return lista;
    }
    
    public DetalleFactura ObtenerRegistro(String condicion) throws SQLException,Exception {
        ResultSet RS = null;
        DetalleFactura Entidad = new DetalleFactura();
        String Sentencia;
        Connection _Conexion = null;
        Sentencia = "SELECT NUM_FACTURA, DETALLEFACTURA.ID_PRODUCTO,DESCRIPCION,CANTIDAD,PRECIO_VENTA "
                    + "FROM DETALLEFACTURA INNER JOIN PRODUCTOS "
                    + "ON DETALLEFACTURA.ID_PRODUCTO = PRODUCTOS.ID_PRODUCTO";
        if(!condicion.equals("")){
            Sentencia = String.format("%s WHERE %s", Sentencia, condicion);
        }
        try {
            _Conexion = ClaseConexion.getConnection();
            Statement ST = _Conexion.createStatement();
            RS = ST.executeQuery(Sentencia);
            if(RS.next()){
                Entidad.setIdFactura(RS.getInt(1));
                Entidad.setIdProducto(RS.getInt(2));
                Entidad.setNombreProducto(RS.getString(3));
                Entidad.setCantidad(RS.getInt(4));
                Entidad.setPrecio(RS.getInt(5));
                Entidad.setExisteRegistro(true);
            }
            else{
                Entidad.setExisteRegistro(false);
            }
            
        } catch (Exception ex) {
            throw ex;
        }finally{
            if(_Conexion != null) ClaseConexion.close(_Conexion);
        }
        return Entidad;
    }
    
}
