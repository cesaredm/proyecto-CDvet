package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Usuarios extends Conexiondb {

    DefaultTableModel modelo;
    Connection cn;
    PreparedStatement pst;
    String consulta;
    String[] resgistros;
    int banderin;

    public Usuarios() {
        this.pst = null;
        this.cn = null;
        this.consulta = null;
        this.banderin = 0;
    }
    //metodo para guardar Usuarios
    public void Guardar(String nombre, String password, String permiso) {
        this.consulta = "INSERT INTO usuarios(nombreUsuario, password, permiso) VALUES(?,?,?)";
        cn = Conexion();
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, nombre);
            pst.setString(2, password);
            pst.setString(3, permiso);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Usuario Agregado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para Actualizar Usuarios
    public void Actualizar(String id, String nombre, String password, String permiso) {
        cn = Conexion();
        this.consulta = "UPDATE usuarios SET nombreUsuario = ?, password = ?, permiso = ? WHERE id = " + id;
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, nombre);
            pst.setString(2, password);
            pst.setString(3, permiso);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Usuario Actualizado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para Eliminar usuarios
    public void Eliminar(int id) {
        cn = Conexion();
        this.consulta = "DELETE FROM usuarios WHERE id =" + id;
        try {
            pst = this.cn.prepareStatement(this.consulta);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Usuario Eliminado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para Mostrar Usuarios
    public DefaultTableModel Mostrar(String buscar) {
        cn = Conexion();
        this.consulta = "SELECT * FROM usuarios WHERE CONCAT(nombreUsuario, permiso) LIKE '%" + buscar + "%'";
        String[] titulos = {"Id", "Nombre de Usuario", "Contraseña", "Permisos"};
        this.resgistros = new String[4];
        this.modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while (rs.next()) {
                this.resgistros[0] = rs.getString("id");
                this.resgistros[1] = rs.getString("nombreUsuario");
                this.resgistros[2] = rs.getString("password");
                this.resgistros[3] = rs.getString("permiso");
                this.modelo.addRow(this.resgistros);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return this.modelo;
    }
}
