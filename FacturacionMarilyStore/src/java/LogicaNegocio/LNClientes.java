/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;
import Acceso_Datos.ADClientes;
import Entidades.Cliente;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class LNClientes {
    //atributos
    private String _mensaje;
    
    //Metodo de acceso - GET
    public String getMensaje(){
        return _mensaje;
    }
    
    public int Insertar(Cliente cliente) throws Exception{
        int id = -1;
        ADClientes adcliente;
        try {
            adcliente = new ADClientes();
            id = adcliente.Insertar(cliente);
            _mensaje = adcliente.getMensaje();
        } catch (Exception e) {
            throw e;
        }
        return id;
    }
    
    //*********Llamar al metodo Modificar***********//
    public int Modificar(Cliente cliente) throws Exception{
        int resultado = -1;
        ADClientes adcliente;
        try {
            adcliente = new ADClientes();
            resultado = adcliente.Modificar(cliente);
            _mensaje =adcliente.getMensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }
    
        //*************llamar ListarRegistro*******************//
    public ResultSet ListarRegistros(String condicion, String orden)throws Exception{
        ResultSet resultado;
        ADClientes adcliente;
        try {
            adcliente = new ADClientes();
            resultado = adcliente.ListarRegistros(condicion, orden);
        } catch (Exception e) {
            throw e;
        }
        return  resultado;
    }
    
    //*************llamar ListarRegistro lista clientes*******************//
   public List<Cliente> ListarRegistros(String condicion)throws Exception{
        List<Cliente> resultado = new ArrayList();
        ADClientes adcliente;
        try {
            adcliente = new ADClientes();
            resultado = adcliente.ListarRegistros(condicion);
        } catch (Exception e) {
            throw e;
        }
        return  resultado;
    }
   
   //***************Llamado a ObtenerRegistro*************//
    public Cliente ObtenerRegistros(String condicion) throws Exception{
       Cliente resultado;
       ADClientes adcliente = new ADClientes();
       try {
           adcliente = new ADClientes();
           resultado = adcliente.ObtenerRegistros(condicion);
           if (resultado.isExiste()) {
               _mensaje = "Cliente Recuperado exitosamente";
           }else{
               _mensaje = "El cliente no existe";
           }
       } catch (Exception e) {
           throw e;
       }
       return resultado;
   }
    
    //***********Llamado al metodo eliminar*******************//
    public int Eliminar(Cliente cliente) throws Exception{
        int resultado =-1;
        ADClientes adcliente;
        try {
            adcliente = new ADClientes();
            resultado = adcliente.Eliminar(cliente);
            _mensaje =adcliente.getMensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }
}
