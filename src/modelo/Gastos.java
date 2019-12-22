package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Gastos extends Conexiondb {

    DefaultTableModel modelo;
    Connection cn;
    PreparedStatement pst;
    String consulta;
    String[] resgistros;
    int banderin;

    public Gastos() {
        this.cn = null;
        this.pst = null;
        this.consulta = "";
        this.banderin = 0;
    }

    public void Guardar(float monto, Date fecha, String Descripcion) {
        cn = Conexion();
        this.consulta = "INSERT INTO gastos(monto, fecha, descripcion) VALUES(?,?,?)";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setFloat(1, monto);
            pst.setDate(2, fecha);
            pst.setString(3, Descripcion);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Gasto Guardado Exitosamente", "Infromacion", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Actualizar(int id, float monto, Date fecha, String Descripcion) {
        cn = Conexion();
        this.consulta = "UPDATE gastos SET monto = ?, fecha = ?, descripcion = ? WHERE id = ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setFloat(1, monto);
            pst.setDate(2, fecha);
            pst.setString(3, Descripcion);
            pst.setInt(4, id);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Gasto Actualizado Exitosamente", "Infromacion", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Eliminar(int id) {
        cn = Conexion();
        this.consulta = "DELETE FROM gastos WHERE id = ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setInt(1, id);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Gasto Borrado Exitosamente", "Infromacion", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public DefaultTableModel Mostrar(String Buscar) {
        cn = Conexion();
        this.resgistros = new String[4];
        String[] titulos = {"Id", "Monto", "Fecha", "Descripcion"};
        this.modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        this.consulta = "SELECT * FROM gastos WHERE CONCAT(id, monto, fecha, descripcion) LIKE '%" + Buscar + "%'";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                this.resgistros[0] = rs.getString("id");
                this.resgistros[1] = rs.getString("monto");
                this.resgistros[2] = rs.getString("fecha");
                this.resgistros[3] = rs.getString("descripcion");
                this.modelo.addRow(this.resgistros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return this.modelo;
    }
}
