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
public class Laboratorios extends Conexiondb {

    DefaultTableModel modelo;
    String consulta;
    PreparedStatement pst;
    Connection cn;

    public Laboratorios() {
        this.cn = null;
        this.consulta = null;
        this.pst = null;
    }

    public void Guardar(String nombre, String descripcion) {
        cn = Conexion();
        consulta = "INSERT INTO laboratorios(nombre, descripcion) VALUES(?,?)";
        try {
            pst = cn.prepareStatement(consulta);
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Laboratorio Guardado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Actualizar(String id, String nombre, String descripcion) {
        cn = Conexion();
        consulta = "UPDATE laboratorios SET nombre=?, descripcion=? WHERE id=" + id;
        try {
            pst = cn.prepareStatement(consulta);
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Dato Actualizado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Eliminar(String id) {
        cn = Conexion();
        consulta = "DELETE FROM laboratorios WHERE id=" + id;
        try {
            pst = cn.prepareStatement(consulta);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Dato Eliminado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public DefaultTableModel Consulta(String Buscar) {
        cn = Conexion();
        consulta = "SELECT * FROM laboratorios WHERE CONCAT(nombre, descripcion) LIKE '%" + Buscar + "%'";
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
