package forms;
import bd.ConexionSQLServer;
import models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Formulario extends JFrame {

    private JPanel mainPanel;
    private JButton btnGuardar;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JButton btnEliminar;
    private JLabel lblID;
    private JButton btnEditar;
    private JButton btnNuevo;
    private VentanaListado ventanaListado;
    private Connection conexion;
    private ConexionSQLServer classConexion;
    private String query;

    public Formulario(String titulo){
        super(titulo);

        ventanaListado = new VentanaListado();
        ventanaListado.setVisible(true);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        //BD
        classConexion = new ConexionSQLServer();
        try {
        conexion = classConexion.conectar();
        }catch (Exception ex){
            Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE,null,ex);
            System.out.println("Error en conexion base de datos");
        }
        ActualizarLista();


        //BOTONES
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuardarCliente();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarCliente();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDatos();
            }
        });

        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCajasTexto();
            }
        });

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void GuardarCliente() {
        //List<Cliente> lista = new ArrayList<Cliente>();
        String strNombre = this.txtNombre.getText();
        String strApellido = this.txtApellido.getText();
        String strEmail = this.txtEmail.getText();
        String strCelular = this.txtCelular.getText();
        Integer intID = 0;
        try{
            intID = Integer.parseInt(this.lblID.getText());
        }catch (NumberFormatException e){
            intID = 0;
        }
        Cliente a = new Cliente();
        a.setNombre(strNombre);
        a.setApellido(strApellido);
        a.setEmail(strEmail);
        a.setCelular(strCelular);
        a.setId(intID);
        if (this.lblID.getText().isEmpty() || this.lblID.getText().isBlank()) {

            //lista.add(a);
            JOptionPane.showMessageDialog(rootPane, a.getNombreCompleto());
            //Inserta en BD
            query = "INSERT INTO [dbo].[Cliente]\n" +
                    "           ([Nombre]\n" +
                    "           ,[Apellido]\n" +
                    "           ,[Email]\n" +
                    "           ,[Celular])\n" +
                    "     VALUES\n" +
                    "           ('" + a.getNombre() + "'\n" +
                    "           ,'" + a.getApellido() + "'\n" +
                    "           ,'" + a.getEmail() + "'\n" +
                    "           ,'" + a.getCelular() + "')";
            try {
                ResultSet rs = classConexion.ejecutarQuery(query, conexion);
                if (Objects.nonNull(rs)) {
                    System.out.println(rs.toString());
                }
            } catch (Exception ex) {
                Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error ejecutando query");
            }
        }else{
            query = "UPDATE [dbo].[Cliente]\n" +
                    "   SET [Nombre] = '"+a.getNombre()+"'\n" +
                    "      ,[Apellido] = '"+a.getApellido()+"'\n" +
                    "      ,[Email] = '"+a.getEmail()+"'\n" +
                    "      ,[Celular] = '"+a.getCelular()+"'\n" +
                    " WHERE ID='"+a.getId()+"'";
            try {
                ResultSet rs = classConexion.ejecutarQuery(query, conexion);
                if (Objects.nonNull(rs)) {
                    System.out.println(rs.toString());
                }
            } catch (Exception ex) {
                Logger.getLogger(ConexionSQLServer.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error ejecutando query");
            }
        }
        limparCajasTexto();
        ActualizarLista();


    }

    private void ActualizarLista(){
        List<Cliente> lista = new ArrayList<Cliente>();
        query = "SELECT * FROM Cliente";
        try {
            ResultSet rs = classConexion.ejecutarQuery(query,conexion);
            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellido(rs.getString("Apellido"));
                cliente.setEmail(rs.getString("Email"));
                cliente.setCelular(rs.getString("Celular"));
                cliente.setId(rs.getInt("ID"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ventanaListado.ActualizarLista(lista);

    }

    private void EliminarCliente(){
        String clienteID = ventanaListado.listClientes.getSelectedValue().toString().split(",")[0];
        query = "DELETE FROM Cliente WHERE ID = '"+clienteID+"'";

        try {
            ResultSet rs = classConexion.ejecutarQuery(query,conexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ActualizarLista();
    }

    private void limparCajasTexto(){
        this.txtNombre.setText("");
        this.txtApellido.setText("");
        this.txtEmail.setText("");
        this.txtCelular.setText("");
        this.lblID.setText("");
    }

    private void editarDatos(){
        String clienteID = ventanaListado.listClientes.getSelectedValue().toString().split(",")[0];
        query = "SELECT * FROM Cliente WHERE ID='"+clienteID+"'";

        Cliente cliente = new Cliente();
        try {
            ResultSet rs = classConexion.ejecutarQuery(query,conexion);
            while (rs.next()){
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellido(rs.getString("Apellido"));
                cliente.setEmail(rs.getString("Email"));
                cliente.setCelular(rs.getString("Celular"));
                cliente.setId(rs.getInt("ID"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.txtNombre.setText(cliente.getNombre());
        this.txtApellido.setText(cliente.getApellido());
        this.txtEmail.setText(cliente.getEmail());
        this.txtCelular.setText(cliente.getCelular());
        this.lblID.setText(cliente.getId().toString());

    }
}
