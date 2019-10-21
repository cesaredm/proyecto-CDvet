/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Categorias {

    DefaultTableModel modelo;
    Conexiondb conexion;

    public Categorias() {
        conexion = new Conexiondb();
    }

    public void Guardar(String nombre, String descripcion) {
        Connection cn = conexion.Conexion();
        String consulta = "INSERT INTO categorias(nombre, descripcion) VALUES(?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(consulta);
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            int banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Categoria Creada Exitosamente");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Actualizar(String id, String nombre, String descripcion) {
        Connection cn = conexion.Conexion();
        String consulta = "UPDATE categorias SET nombre=?, descripcion=? WHERE id="+id;
        try
        {
            PreparedStatement pst = cn.prepareStatement(consulta);
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            int banderin = pst.executeUpdate();
            if(banderin > 0)
            {
                JOptionPane.showMessageDialog(null, "Dato Actualizado Exitosamente");
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Eliminar(String id) {
        Connection cn = conexion.Conexion();
        String consulta = "DELETE FROM categorias WHERE id="+id;
        try
        {
            PreparedStatement pst = cn.prepareStatement(consulta);
            int banderin = pst.executeUpdate();
            if(banderin > 0)
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
        String Consulta = "SELECT * FROM categorias WHERE CONCAT(id, nombre, descripcion) LIKE '%"+Buscar+"%'";
        String[] registros= new String[3];
        String[] titulos = {"Id", "Nombre", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos)
        {
             public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        try
        {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(Consulta);
            while(rs.next())
            {
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("nombre");
                registros[2]=rs.getString("descripcion");
                modelo.addRow(registros);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
}
