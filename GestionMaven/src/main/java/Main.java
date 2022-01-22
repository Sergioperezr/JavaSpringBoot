import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import bd.ConexionSQLServer;
import forms.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, SQLException {
        JFrame frame = new Formulario("Prueba");
        frame.setVisible(true);
    }
}
