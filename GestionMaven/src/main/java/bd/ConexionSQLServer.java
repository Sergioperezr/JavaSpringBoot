package bd;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionSQLServer {

    public ConexionSQLServer(){

    }

    public Connection conectar() throws ClassNotFoundException {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String serverName ="DESKTOP-MMLHIU2";
        String port ="1433";
        String usuario = "prueba1";
        String password = "1234";
        String databaseName = "Gestion";
        Class.forName(driver);
        String connectString = "jdbc:sqlserver://"+serverName+":"+port+";databaseName="+databaseName+";user="+usuario+";password="+password+";";
        Connection con = null;
        try {
            con = DriverManager.getConnection(connectString);
            System.out.println("Nos conectamos");
        } catch (Exception ex){
            Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE,null,ex);
            System.out.println("Error en conexion base de datos");
        }
        return con;
    }


    public ResultSet ejecutarQuery(String query, Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
        } catch (Exception ex){
            rs = null;
        }
        return rs;
    }

}
