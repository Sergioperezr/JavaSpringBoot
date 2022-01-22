package forms;

import models.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.Array;
import java.util.List;

public class VentanaListado extends JFrame  {
    public JList listClientes;
    private JPanel mainPanel;
    private JTable jTable;

    public VentanaListado(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        };


    public void ActualizarLista(List<Cliente> lista){
        this.listClientes.removeAll();
        this.jTable.removeAll();
        DefaultListModel datos = new DefaultListModel();

        for (int i = 0;i<lista.size(); i++){
            String nombre;
            Integer id;
            nombre = lista.get(i).getNombre();
            id = lista.get(i).getId();
            datos.addElement(id+","+nombre);
        }
        this.listClientes.setModel(datos);
        String data[][] = new String[lista.size()][4];
        for (int x = 0; x<data.length; x++){
            data[x][0] = lista.get(x).getNombre();
            data[x][1] = lista.get(x).getApellido();
            data[x][2] = lista.get(x).getEmail();
            data[x][3] = lista.get(x).getCelular();
        }


        this.jTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Nombre","Apellido","Email","Celular"}
        ));

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

