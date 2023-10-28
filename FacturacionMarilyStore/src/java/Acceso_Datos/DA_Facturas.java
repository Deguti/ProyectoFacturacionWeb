package Acceso_Datos;

import Entidades.DetalleFactura;
import Entidades.Facturas;
import java.sql.*;
import java.util.*;

public class DA_Facturas {
    // ATRIBUTOS
    private String _Mensaje;

    public String getMensaje() {
        return _Mensaje;
    }
    
    // CONSTRUCTORES
    public DA_Facturas() {
        _Mensaje = "";
    }
    
    // METODOS
       public int Insertar(Facturas EntidadFactura, DetalleFactura EntidadDetalle) throws Exception{
        //ResultSet RSDatos; // no se esta utilizando
        CallableStatement CS;
        int resultado = 0;
        int idFactura = 0;
        Connection _Conexion = null;
        try {
            _Conexion = ClaseConexion.getConnection(); 
            // por defecto el objeto Connection trabaja las transacciones con confirmacion automatica
            // Es decir que cada vez que se ejecuta un COMANDO automáticamente realiza un COMMIT (actualizar la BD)
            
            /*  Pero en este ejemplo no queremos eso, porque deseamos realizar varias transacciones y que todas se 
                manejen como si fuera una sola, de forma que si todo sale bien se haga un COMMIT de TODAS juntas, 
                y si algo sale mal NO SE HAGA COMMIG DE NINGUNA. 
                Esto es lo que se conoce como manejo de TRANSACCIONES (se hacen todas las operaciones correctamente, 
                o no se hace ninguna)
            */
            
            // Transacciones Implicitas y Explicitas:
            _Conexion.setAutoCommit(false); // en TRUE automaticamente hace COMMIT cada vez que se ejecute un COMANDO,
            // en FALSE significa que de ahora en adelante todas las operaciones QUE VAMOS A hacer en este metodo INSERTAR, 
            // se hagan en LA MISMA TRANSACCION
            
            /*  Esta configuración se aplica al objeto Connection, en este caso definimos la conexión dentro del método,
                entonces esta característica de autoCommit en FALSE se mantendrá durante todo el método. Fuera del método no. 
            */
            
            // el COMMIT se hará únicamente cuando uno indique que haga COMMIT 
            // esto es para garantizar que se hagan todas las operaciones en UNA SOLA TRANSACCIÓN 
            
            
            CS = _Conexion.prepareCall("{call Guardar_Factura(?,?,?,?,?)}");
            
            CS.setInt(1, EntidadFactura.getIdFactura());
            CS.setInt(2, EntidadFactura.getIdCliente());
            CS.setDate(3, EntidadFactura.getFecha());
            CS.setString(4, EntidadFactura.getEstado());
            CS.setString(5, _Mensaje);
            CS.registerOutParameter(1, Types.INTEGER); // en la BD el parametro 1 es de salida para obtener el IDENTITY generado
                      
            resultado = CS.executeUpdate();               
            idFactura = CS.getInt(1);
            
            CS = _Conexion.prepareCall("{call Guardar_Detalle(?,?,?,?,?)}");
            CS.setInt(1, idFactura); // llama a la variable que acabamos de declarar
            CS.setInt(2, EntidadDetalle.getIdProducto());
            CS.setInt(3, EntidadDetalle.getCantidad());
            CS.setDouble(4, (double)EntidadDetalle.getPrecio()); 
            CS.setString(5, _Mensaje);
            
            //registrar mensaje para salida
            CS.registerOutParameter(5, Types.VARCHAR);
            
            resultado = CS.executeUpdate();
            
            //SE RECIBE DEL SP
            _Mensaje = CS.getString(5);
            
            _Conexion.commit();
            /*
                Hacemos el COMMIT porque ya hicimos todas las operaciones que deseabamos, 
                sabemos que si llegó hasta este punto sin "caerse" significa que todo salió bien.
                Por ello hacemos el Commit para que las operaciones queden actualizadas 
                en la BD. 
            */
                   
            
        } catch (ClassNotFoundException | SQLException ex) { // tambien pudo usarse Exception que es general
            
            _Conexion.rollback(); // Si algo salió mal se DESHACEN todas las transacciones con un ROLLBACK
                                   // para que la BD quede en un estado consistente. 
            
            throw ex;
        }finally{
            if(_Conexion != null) ClaseConexion.close(_Conexion);
        }
        return idFactura;
    } // fin de insertar
    
    
    public int ModificarEstado(Facturas EntidadFactura)  throws Exception{
        int resultado = 0;
        Connection _Conexion = null;  
        // dentro del try no se puede declarar, porque entonces no podriamos cerrarla en el Finally
        // O, podria declararse y cerrarse dentro del Try
        try{
            _Conexion = ClaseConexion.getConnection(); 
            PreparedStatement PS = _Conexion.prepareStatement("update FacturaS set Estado = ? where NUM_FACTURA = ?");
            
            PS.setString(1, EntidadFactura.getEstado());
            PS.setInt(2, EntidadFactura.getIdFactura());
            
            resultado = PS.executeUpdate();
            _Mensaje = "Factura Cancelada satisfactoriamente";
        }catch(Exception ex){
            resultado = -1;
            throw  ex;
        }finally{
            if(_Conexion != null) ClaseConexion.close(_Conexion);
            // .close y .getConnection son metodos estaticos de la clase ClaseConexion, si uno quisiera escribirlos sin 
            // ClaseConexion.  entonces solamente debe importar la clase
        }
        return resultado;
    } // fin ModificarEstado
    
    
    public int ModificarCliente(Facturas EntidadFactura)  throws Exception{
        int idfactura = 0;
        Connection _Conexion = null; 
        try{
            _Conexion = ClaseConexion.getConnection(); 
            PreparedStatement PS = _Conexion.prepareStatement("update FacturaS set ID_CLIENTE = ? where NUM_FACTURA = ?");
            
            PS.setInt(1, EntidadFactura.getIdCliente());
            PS.setInt(2, EntidadFactura.getIdFactura());
            
            PS.executeUpdate();
            idfactura = EntidadFactura.getIdFactura(); // este devuelve el ID FACTURA
            
        }catch(Exception ex){
            throw  ex;
        }finally{
            if(_Conexion != null) ClaseConexion.close(_Conexion);
        }
        return idfactura;
    } // fin Modificar ID del cliente
    
    
    public List<Facturas> ListarRegistros(String Condicion) throws Exception{
        ResultSet RS = null;
        Facturas Entidad;
        List<Facturas> ListaF = new ArrayList<>();
        Connection _Conexion = null;
        try {
            _Conexion = ClaseConexion.getConnection();
            Statement ST = _Conexion.createStatement();
            String Sentencia;
            
            Sentencia = "SELECT NUM_FACTURA,F.ID_CLIENTE,NOMBRE,FECHA,ESTADO FROM Facturas F INNER JOIN CLIENTES ON CLIENTES.ID_CLIENTE=F.ID_CLIENTE";
            if(!Condicion.equals("")){
                Sentencia = String.format("%s WHERE %s", Sentencia, Condicion);
            }
            RS = ST.executeQuery(Sentencia);
            while(RS.next()){
                Entidad = new Facturas(RS.getInt("NUM_FACTURA"),
                        RS.getInt("Id_Cliente"),
                        RS.getString("Nombre"),
                        RS.getDate("Fecha"),
                        RS.getString("Estado"));
                        
                        
                ListaF.add(Entidad);
            }

            } catch (Exception ex) {
                throw ex;
            }finally{
            if(_Conexion != null) ClaseConexion.close(_Conexion);
        }
        return ListaF;
    } // fin Listar Registros
           
    public Facturas ObtenerRegistro(String Condicion) throws Exception{
        ResultSet RsDatos = null;
        Facturas Entidad = new Facturas();
        String Sentencia;
        Connection _Conexion = null;
        Sentencia = "SELECT NUM_FACTURA,F.ID_CLIENTE,NOMBRE,FECHA,ESTADO FROM FACTURAS F "
                + "INNER JOIN CLIENTES ON CLIENTES.ID_CLIENTE=F.ID_CLIENTE";
        if(!Condicion.equals("")){
                Sentencia = String.format("%s WHERE %s", Sentencia, Condicion);
            }
        try {
            _Conexion = ClaseConexion.getConnection();
            Statement ST = _Conexion.createStatement();
            RsDatos = ST.executeQuery(Sentencia);
            if(RsDatos.next()){
                Entidad.setIdFactura(RsDatos.getInt("NUM_FACTURA"));
                Entidad.setIdCliente(RsDatos.getInt("Id_Cliente"));
                Entidad.setNombreCliente(RsDatos.getString("Nombre"));
                Entidad.setFecha(RsDatos.getDate("Fecha"));
                Entidad.setEstado(RsDatos.getString("Estado"));
                Entidad.setExisteRegistro(false);
            }else{
                Entidad.setExisteRegistro(false);
            }
            } catch (Exception ex) {
                throw ex;
            }finally{
            if(_Conexion != null) ClaseConexion.close(_Conexion);
        }
        return Entidad;
    } // fin Listar Registros
}
