/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaNegocio;

import java.util.*;
import Acceso_Datos.DA_Facturas;
import Entidades.DetalleFactura;
import Entidades.Facturas;

public class BL_Factura {
    private String _Mensaje;

    public String getMensaje() {
        return _Mensaje;
    }
    
    public int Insertar(Facturas Entidad, DetalleFactura EntidadDetalle) throws Exception{
        int Resultado= 0;
            
        try{
                
            DA_Facturas DA = new DA_Facturas();
            Resultado=DA.Insertar(Entidad, EntidadDetalle);
            _Mensaje=DA.getMensaje();              
        }catch(Exception ex){
            Resultado=-1;
            throw ex;
        }
        return Resultado;
    }
    
    
    public int ModificarEstado(Facturas Entidad) throws Exception {
        int Resultado= 0;
        
        try{

            DA_Facturas DA = new DA_Facturas();

            Resultado = DA.ModificarEstado(Entidad);   
            _Mensaje=DA.getMensaje(); 
        }catch(Exception ex){
            throw ex;
        }
        return Resultado;
    }
    
    public int ModificarCliente(Facturas Entidad) throws Exception {
        int idfactura= 0;
        
        try{
            DA_Facturas DA = new DA_Facturas();
            idfactura = DA.ModificarCliente(Entidad);          
        }catch(Exception ex){
            throw ex;
        }

        return idfactura;
    }

    public List<Facturas> ListarRegistros(String condicion) throws Exception{
        List<Facturas> Datos;
        
        try{
            
            DA_Facturas DA = new DA_Facturas();

            Datos = DA.ListarRegistros(condicion);
        }catch (Exception ex){
            Datos=null;
            throw ex;
        }

        return Datos;
    }
    
    public Facturas ObtenerRegistro(String condicion) throws Exception{
        Facturas Entidad=null;
        
        try{
            
            DA_Facturas DA = new DA_Facturas();

            Entidad = DA.ObtenerRegistro(condicion);
        }catch (Exception ex){
            throw ex;
        }
        return Entidad;
    }

       
}
