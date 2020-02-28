/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Categorias extends Conexiondb {

    DefaultTableModel modelo;
    Connection cn;
    PreparedStatement pst;
    String consulta;

    public Categorias() {
        cn = null;
        pst = null;
        consulta = null;
    }

    public void Guardar(String nombre, String descripcion) {
        cn = Conexion();
        consulta = "INSERT INTO categorias(nombre, descripcion) VALUES(?,?)";
        try {
            pst = cn.prepareStatement(consulta);
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Categoria Creada Exitosamente");
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Actualizar(String id, String nombre, String descripcion) {
        cn = Conexion();
        consulta = "UPDATE categorias SET nombre=?, descripcion=? WHERE id=" + id;
        try {
            pst = cn.prepareStatement(consulta);
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Dato Actualizado Exitosamente");
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Eliminar(String id) {
        cn = Conexion();
        consulta = "DELETE FROM categorias WHERE id=" + id;
        try {
            pst = cn.prepareStatement(consulta);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Dato Borrado Exitosamente");
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public DefaultTableModel Consulta(String Buscar) {
        cn = Conexion();
        consulta = "SELECT * FROM categorias WHERE CONCAT(id, nombre, descripcion) LIKE '%" + Buscar + "%'";
        String[] registros = new String[3];
        String[] titulos = {"Id", "Nombre", "Descripcion"};
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
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("descripcion");
                modelo.addRow(registros);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
}
