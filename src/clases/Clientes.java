package clases;

import java.sql.*;
import javax.swing.JOptionPane;
import formularios.IMenu;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Clientes {

    Conexiondb conexion;
    String nombres, apellidos, telefono, direccion;
    IMenu menu;

    public Clientes() {
        conexion = new Conexiondb();
    }

    public void Guardar(String nombres, String apellidos, String telefono, String direccion) {
        Connection cn = conexion.Conexion();
        String Consulta = "INSERT INTO clientes VALUES(null,?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(Consulta);
            pst.setString(1, nombres);
            pst.setString(2, apellidos);
            pst.setString(3, telefono);
            pst.setString(4, direccion);
            int cont = pst.executeUpdate();
            if (cont > 0) {
                JOptionPane.showMessageDialog(null, "Cliente Guardado Exitosamente");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Actualzar(String id, String nombres, String apellidos, String telefono, String direccion) {
        Connection cn = conexion.Conexion();
        String Consulta = " UPDATE clientes SET nombres = ?, apellidos = ?, telefono = ?, direccion = ? WHERE id="+id;
        try {
            PreparedStatement pst = cn.prepareStatement(Consulta);
            pst.setString(1, nombres);
            pst.setString(2, apellidos);
            pst.setString(3, telefono);
            pst.setString(4, direccion);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Dato Actualizado Correctamente");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, id);
        }
    }

    public void Eliminar(String id) {
        Connection cn = conexion.Conexion();
        String consulta = "DELETE FROM clientes WHERE id="+id;
        try
        {
            PreparedStatement pst = cn.prepareStatement(consulta);
            int banderin = pst.executeUpdate();
            if(banderin>0)
            {
                JOptionPane.showMessageDialog(null, "Dato Borrado Exitosamente");
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }

    public DefaultTableModel Consulta(String Buscar) {
        Connection cn = conexion.Conexion();
        String Consulta = "SELECT id, nombres, apellidos, telefono, direccion FROM clientes WHERE CONCAT(nombres,apellidos) LIKE '%" + Buscar + "%'";
        String[] registro = new String[5];
        String[] titulos = {"Id", "Nombres", "Apellidos", "Telefono", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos)
            {
                public boolean isCellEditable(int row, int col)
                {
                return false;
                }
            };
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(Consulta);
            while (rs.next()) {
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("nombres");
                registro[2] = rs.getString("apellidos");
                registro[3] = rs.getString("telefono");
                registro[4] = rs.getString("direccion");
                modelo.addRow(registro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
}
