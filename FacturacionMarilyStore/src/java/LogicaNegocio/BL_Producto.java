package LogicaNegocio;

import Acceso_Datos.ADProductos;
import Entidades.Producto;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author dcruz
 */
public class BL_Producto {
    private String _Mensaje;

    public String getMensaje() {
        return _Mensaje;
    }
    
    public int Insertar(Producto Entidad) throws Exception{
            int Resultado= 0;

            try{
                
                ADProductos DA = new ADProductos();

                Resultado = DA.Insertar(Entidad);                
            }catch(Exception ex){
                Resultado=-1;
                throw ex;
            }

            return Resultado;
    }

    public int Modificar(Producto Entidad) throws Exception{
        int Resultado= 0;
        
        try{

            ADProductos DA = new ADProductos();

            Resultado = DA.Modificar(Entidad);           
        }catch(Exception ex){
            Resultado=-1;
            throw ex;
        }
        return Resultado;
    }
    
    public int Eliminar(Producto Entidad) throws Exception {
        int Resultado= 0;       
        try{

            ADProductos DA = new ADProductos();

            Resultado = DA.Eliminar(Entidad);
            _Mensaje = DA.getMensaje();          
        }catch(Exception ex){
            Resultado=-1;
            throw ex;
        }
        return Resultado;
    }

    public ResultSet ListarRegistros(String condicion, String orden) throws Exception{
        ResultSet RS;
       
        try{
            
            ADProductos DA = new ADProductos();

            RS = DA.ListarRegistros(condicion, orden);           
        }catch (Exception ex){
            RS=null;
            throw ex;
        }
        return RS;
    }

    public List<Producto> ListarRegistros(String condicion) throws Exception{
        List<Producto> Datos;
       
        try{
            
            ADProductos DA = new ADProductos();

            Datos = DA.ListarRegistros(condicion);            
        }catch (Exception ex){
            Datos=null;
            throw ex;
        }
        return Datos;
    }
    
    public Producto ObtenerRegistro(String condicion) throws Exception{
        Producto Entidad=null;
        
        try{
            
            ADProductos DA = new ADProductos();

            Entidad = DA.ObtenerRegistros(condicion);            
        }catch (Exception ex){
            throw ex;
        }
        return Entidad;
    }
}
