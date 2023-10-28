package LogicaNegocio;

import java.util.*;

import Acceso_Datos.DA_Detalle;
import Entidades.DetalleFactura;


public class BL_Detalle {

    private String _Mensaje;

    public String getMensaje() {
        return _Mensaje;
    }

    public int Eliminar(DetalleFactura Entidad) throws Exception {
        int Resultado = 0;

        try {
            DA_Detalle DA = new DA_Detalle();

            Resultado = DA.Eliminar(Entidad);

        } catch (Exception ex) {
            Resultado = -1;
            throw ex;
        }

        return Resultado;
    }

//    public ResultSet ListarRegistros(String condicion, String orden) throws Exception{
//        ResultSet RSDatos;
//        
//        try{
//            
//            DA_Detalle DA = new DA_Detalle();
//
//            RSDatos = DA.ListarRegistros(condicion, orden);
//            
//        }catch (Exception ex){
//            RSDatos=null;
//            throw ex;
//        }
//
//        return RSDatos;
//    }
    public List<DetalleFactura> ListarRegistros(String Condicion) throws Exception {
        List<DetalleFactura> Datos;
        DA_Detalle AccesoDatos;
        try {
            AccesoDatos = new DA_Detalle();

            Datos = AccesoDatos.ListarRegistros(Condicion);
        } catch (Exception ex) {
            Datos = null;
            throw ex;
        }

        return Datos;
    }

    public DetalleFactura ObtenerRegistro(String condicion) throws Exception {
        DetalleFactura Entidad = null;

        try {

            DA_Detalle DA = new DA_Detalle();

            Entidad = DA.ObtenerRegistro(condicion);

        } catch (Exception ex) {
            throw ex;
        }

        return Entidad;
    }
    
}//fin de la clase
