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
public class Laboratorios {
    DefaultTableModel modelo;
    Conexiondb conexion;
    public Laboratorios() {
        conexion = new Conexiondb();
    }
    public void Guardar(String nombre, String descripcion)
    {
        Connection cn = conexion.Conexion();
        String Consulta = "INSERT INTO laboratorios(nombre, descripcion) VALUES(?,?)";
        try
        {
            PreparedStatement pst = cn.prepareStatement(Consulta);
            pst.setString(1,nombre);
            pst.setString(2,descripcion);
            int banderin = pst.executeUpdate();
            if(banderin>0)
            {
                JOptionPane.showMessageDialog(null, "Laboratorio Guardado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Actualizar(String id, String nombre, String descripcion)
    {
        Connection cn = conexion.Conexion();
        String consulta = "UPDATE laboratorios SET nombre=?, descripcion=? WHERE id="+id;
        try
        {
            PreparedStatement pst = cn.prepareStatement(consulta);
            pst.setString(1, nombre);
            pst.setString(2, descripcion);
            int banderin = pst.executeUpdate();
            if(banderin >0)
            {
                JOptionPane.showMessageDialog(null, "Dato Actualizado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void Eliminar(String id)
    {
        Connection cn = conexion.Conexion();
        String consulta = "DELETE FROM laboratorios WHERE id="+id;
        try
        {
            PreparedStatement pst = cn.prepareStatement(consulta);
            int banderin = pst.executeUpdate();
            if(banderin >0)
            {
                JOptionPane.showMessageDialog(null, "Dato Eliminado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public DefaultTableModel Consulta(String Buscar)
    {
        Connection cn = conexion.Conexion();
        String consulta = "SELECT * FROM laboratorios WHERE CONCAT(nombre, descripcion) LIKE '%"+Buscar+"%'";
        String[] registros = new String[3];
        String[] titulos = {"Id","Nombre","Descripcion"};
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
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
                registros[0]=rs.getString("id");
                registros[1]=rs.getString("nombre");
                registros[2]=rs.getString("descripcion");
                modelo.addRow(registros);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        return modelo;
    }
   
}
