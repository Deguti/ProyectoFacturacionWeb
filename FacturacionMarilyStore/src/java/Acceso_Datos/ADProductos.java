/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Acceso_Datos;

import java.sql.*; // Las clases de SQL de JDBC
import java.util.*; // para manejo de Listas 
import static Acceso_Datos.ClaseConexion.getConnection;
import Entidades.Producto;

/**
 *
 * @author Usuario
 */
public class ADProductos {
    private String _mensaje;
    
    //**********Metodo get mensaje************//
    public String getMensaje(){
        return _mensaje;
    }
    
    //************Constructor para la conexion****************//
    public ADProductos() throws Exception{
         _mensaje = "";    
    }
    
   
    
    //************Metodo para insertar un Producto**************//
    public int Insertar(Producto producto)throws Exception{
        int id = -1;
        Connection _cnn = null;
        String sentencia = "INSERT INTO PRODUCTOS(DESCRIPCION,PRECIO,EXISTENCIA)VALUES(?,?,?)";
        try {
            _cnn=ClaseConexion.getConnection();
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS );
            
            ps.setString(1,producto.getDescripcion());
            ps.setDouble(2, producto.getPrecio());
            ps.setDouble(3, producto.getExistencia()); 
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs !=null && rs.next()) {
                id = rs.getInt(1);
                _mensaje = "Producto ingresado satisfactoriamente";
            }
        } catch (Exception e) {
            id =-1;
            throw e;
        }
        finally{
            if (_cnn != null) ClaseConexion.close(_cnn);
        }
        return id;
    }
    
    public int Modificar(Producto productos) throws Exception{
        int resultado = 0;
        Connection _cnn = null;
        String sentencia = "update productos set descripcion =?,precio=?,existencia=? where id_producto =?";
        try {
            _cnn=ClaseConexion.getConnection();
            PreparedStatement ps = _cnn.prepareStatement(sentencia);           
            ps.setString(1, productos.getDescripcion());
            ps.setDouble(2, productos.getPrecio()); 
            ps.setDouble(3, productos.getExistencia()); 
            ps.setInt(4, productos.getIdProducto());
            resultado = ps.executeUpdate();
            if (resultado>0) {
                _mensaje = "Producto modificado satisfactoriamente";
            }
        } catch (Exception e) {
            resultado=-1;
            throw e;   
        }
        finally{
            if (_cnn != null) ClaseConexion.close(_cnn);
        }
        return resultado;
    }
    
       //***********Metodo Listar Registros***********************//
    public ResultSet ListarRegistros(String condicion, String orden)throws SQLException, Exception{
           ResultSet RS=null;
            String Sentencia;
            Sentencia = "SELECT ID_PRODUCTO,DESCRIPCION,PRECIO,EXISTENCIA FROM PRODUCTOS";

            if (!condicion.equals("")) {
                Sentencia = String.format("%s WHERE %s", Sentencia, condicion);
            }

            if(!orden.equals("")) {
                Sentencia = String.format("%s ORDER BY %s", Sentencia, orden);
            }

            try{
                Connection _Conexion=ClaseConexion.getConnection();
                Statement ST = _Conexion.createStatement();
                RS=ST.executeQuery(Sentencia);
                
            }catch(Exception e){
                throw e;
            }
            return RS;
    }
    
        //***********Metodo Listar Registros con lista***********************//
    public List<Producto> ListarRegistros(String condicion)throws SQLException, Exception{
        ResultSet rs = null;
        Connection _cnn = null;
        List<Producto> lista = new ArrayList();
        try {
            _cnn=ClaseConexion.getConnection(); 
            Statement stm = _cnn.createStatement();
            String sentencia = "SELECT ID_PRODUCTO,DESCRIPCION,PRECIO,EXISTENCIA FROM PRODUCTOS";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }
            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
               lista.add(new Producto(rs.getInt("id_producto"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getDouble("existencia")));                   
            }            
        } catch (Exception e) {
            throw e;
        }
        finally{
            if (_cnn != null) ClaseConexion.close(_cnn);
        }
        return lista;
    }
    
    //**************Obtenemos el registro 
    public Producto ObtenerRegistros(String condicion) throws SQLException, Exception{
        ResultSet RS=null;
            Producto EntidadProducto=new Producto();
            String vlc_Sentencia;
            Connection _Conexion=null;
            vlc_Sentencia = "SELECT ID_PRODUCTO,DESCRIPCION,PRECIO,EXISTENCIA FROM PRODUCTOS";

            if (!condicion.equals("")) {
                vlc_Sentencia = String.format("%s WHERE %s", vlc_Sentencia, condicion);
            }

            try{
                _Conexion=ClaseConexion.getConnection();
                Statement ST = _Conexion.createStatement();
                RS=ST.executeQuery(vlc_Sentencia);
                if(RS.next()){
                    EntidadProducto.setIdProducto(RS.getInt(1));
                    EntidadProducto.setDescripcion(RS.getString(2));
                    EntidadProducto.setPrecio(RS.getDouble(3));
                    EntidadProducto.setExistencia(RS.getDouble(4));
                    
                    EntidadProducto.setExisteRegistro(true);
                    
                }else{
                    EntidadProducto.setExisteRegistro(false);
                    
                }
                   
            }catch(Exception ex){
                throw ex;
            }finally{
                if (_Conexion != null) ClaseConexion.close(_Conexion);
            }
            return EntidadProducto;
        }
    
       //***********Metodo para eliminar*************//
    public int Eliminar(Producto productos) throws Exception{
        int resultado =0;
        Connection _cnn=null;
        String sentencia = "delete Productos where id_producto =?";
        try {
            _cnn=ClaseConexion.getConnection();
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, productos.getIdProducto());
            resultado = ps.executeUpdate();
            if (resultado > 0) {
                _mensaje ="Producto eliminado Satisfactoriamente";
            }
        } catch (Exception e) {
            resultado=-1;
            throw e;
        }
        finally{
            if (_cnn != null) ClaseConexion.close(_cnn);
        }
        return resultado;
    }
}
