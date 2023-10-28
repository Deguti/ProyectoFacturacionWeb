/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Acceso_Datos;
import java.sql.*;
/**
 *
 *Derick Gutierrez Mejias
 */
public class ClaseConexion {
     private static final String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=PROYECTOFACTURACION;user=sa;password=sa;";
       public static Connection getConnection() throws SQLException, ClassNotFoundException{
               
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(connectionString);
    }
      
    public static void close(ResultSet rs) throws SQLException{
        rs.close();
    }
    
    public static void close(Statement st) throws SQLException{
        st.close();
    }
    
    public static void close(PreparedStatement pst) throws SQLException{
        pst.close();
    }
    
    public static void close(CallableStatement cst) throws SQLException{
        cst.close();
    }
    
    public static void close(Connection conexion) throws SQLException{
        conexion.close();
    }
}
