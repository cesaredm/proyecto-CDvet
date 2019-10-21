package clases;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Descuentos {

    DefaultTableModel modelo;
    Conexiondb conexion;

    public Descuentos() {
        conexion = new Conexiondb();
    }

    public void Guardar(int descuento) {
        Connection cn = conexion.Conexion();
        String consulta = "INSERT INTO descuentos(porcentajeDescuento) VALUES(?)";
        try {
            PreparedStatement pst = cn.prepareStatement(consulta);
            pst.setInt(1, descuento);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Descuento Agregado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Actualizar(String id) {

    }

    public void Eliminar(String id) {

    }

    public DefaultTableModel Consulta(String buscar) {
        Connection cn = conexion.Conexion();
        String consulta = "SELECT * FROM descuentos WHERE porcentajeDescuento LIKE '%" + buscar + "%'";
        String[] registros = new String[2];
        String[] titulos = {"Id", "Porcentaje %"};
        modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("porcentajeDescuento");
                modelo.addRow(registros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
}
