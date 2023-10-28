
package Acceso_Datos;

import Entidades.Cliente;

import java.sql.*; // Las clases de SQL de JDBC
import java.util.*; // para manejo de Listas 
import static Acceso_Datos.ClaseConexion.getConnection;


public class ADClientes {
    //************Atributos abrir la conexxion y guardar mensajes**************/
    private String _mensaje;
    
    //**********Metodo get mensaje************//
    public String getMensaje(){
        return _mensaje;
    }
     public ADClientes() {
        _mensaje = "";
    }
    
     
    //************Metodo para insertar un cliente**************//
    public int Insertar(Cliente cliente)throws Exception{
        int id = -1;
        Connection _cnn = null;
        String sentencia = "INSERT INTO CLIENTES(NOMBRE,APELLIDO1,APELLIDO2, TELEFONO, DIRECCION)VALUES(?,?,?,?,?)";
        try {
            _cnn = getConnection();
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS );
            ps.setString(1,cliente.getNombre());
            ps.setString(2, cliente.getApellido1());
            ps.setString(3, cliente.getApellido2());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs !=null && rs.next()) {
                id = rs.getInt(1);
                _mensaje = "Cliente ingresado satisfactoriamente";
            }
        } catch (Exception e) {
            _mensaje = "Error inesperado, intente más tarde";
            throw e;
        }
        finally{
            if (_cnn != null) {
                ClaseConexion.close(_cnn);
            }
        }
        return id;
    }
    
    public int Modificar(Cliente cliente) throws Exception{
        int resultado = 0;
        Connection _cnn = null;
        String sentencia = "update clientes set nombre =?,apellido1=?,apellido2=?, telefono =?, direccion =? where id_cliente =?";
        try {
            _cnn = getConnection();
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido1());
            ps.setString(3, cliente.getApellido2());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getId_cliente());
            resultado = ps.executeUpdate();
            if (resultado>0) {
                _mensaje = "Registro modificado satisfactoriamente";
            }
        } catch (Exception e) {
            _mensaje = "Error inesperado, intente más tarde";
            throw e;
        }
        finally{
            _cnn = null;
        }
        return resultado;
    }
    
       //***********Metodo Listar Registros***********************//
    public ResultSet ListarRegistros(String condicion, String orden)throws SQLException, Exception{
        ResultSet rs = null;
        Connection _cnn = null;
        try {
            _cnn = getConnection();
            Statement stm = _cnn.createStatement();
            String sentencia = "SELECT ID_CLIENTE,NOMBRE,APELLIDO1,APELLIDO2,TELEFONO,DIRECCION FROM CLIENTES";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }
            if (!orden.equals("")) {
                sentencia = String.format("%s order by %s", sentencia, orden);
            }
            rs = stm.executeQuery(sentencia);
        } catch (Exception e) {
            throw e;
        }
        finally{
            _cnn = null;
        }
        return rs;
    }
    
        //***********Metodo Listar Registros con lista***********************//
    public List<Cliente> ListarRegistros(String condicion)throws SQLException, Exception{
        ResultSet rs = null;
        Connection _cnn = null;
        List<Cliente> lista = new ArrayList();
        try {
            _cnn = getConnection();
            Statement stm = _cnn.createStatement();
            String sentencia = "SELECT ID_CLIENTE,NOMBRE,APELLIDO1,APELLIDO2,TELEFONO,DIRECCION FROM CLIENTES";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }
            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
               lista.add(new Cliente(rs.getInt("id_cliente"),rs.getString("nombre"),rs.getString("APELLIDO1"),rs.getString("apellido2"),rs.getString("telefono"),rs.getString("direccion")));                   
            }            
        } catch (Exception e) {
            throw e;
        }
        finally{
            _cnn = null;
        }
        return lista;
    }
    
    public Cliente ObtenerRegistros(String condicion) throws SQLException, Exception{
        Cliente cliente = new Cliente();
        ResultSet rs = null;
        Connection _cnn = null;
        try {
            _cnn = getConnection();
            Statement stm = _cnn.createStatement();
            String sentencia ="SELECT ID_CLIENTE,NOMBRE,APELLIDO1,APELLIDO2,TELEFONO,DIRECCION FROM CLIENTES";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }
            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                cliente.setId_cliente(rs.getInt(1));
                cliente.setNombre(rs.getString(2));
                cliente.setApellido1(rs.getString(3));
                cliente.setApellido2(rs.getString(4));
                cliente.setTelefono(rs.getString(5));
                cliente.setDireccion(rs.getString(6));
                cliente.setExiste(true);
            }
        } catch (Exception e) {
            throw e;
        }
        finally{
            _cnn = null;
        }
        return cliente;
    }
    
       //***********Metodo para eliminar*************//
    public int Eliminar(Cliente cliente) throws Exception{
        int resultado =0;
        Connection _cnn = null;
        String sentencia = "delete Clientes where id_cliente =?";
        try {
            _cnn = getConnection();
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, cliente.getId_cliente());
            resultado = ps.executeUpdate();
            if (resultado > 0) {
                _mensaje ="Registro eliminado Satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        }
        finally{
            _cnn = null;
        }
        return resultado;
    }
}
