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
public class PagosCreditos extends Conexiondb {

    DefaultTableModel modelo;
    Connection cn;
    PreparedStatement pst;
    String consulta;
    String[] resgistros;
    int banderin;

    public PagosCreditos() {
        this.cn = null;
        this.consulta = null;
        this.pst = null;
        this.banderin = 0;
    }
    //metodo para Guardar pagos
    public void Guardar(int credito, float monto, Date fecha) {
        this.consulta = "INSERT INTO pagoscreditos(credito,monto,fecha) VALUES(?,?,?)";
        cn = Conexion();
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setInt(1, credito);
            pst.setFloat(2, monto);
            pst.setDate(3, fecha);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Pago Guardado Exitosamete", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para eliminar pago
    public void Eliminar(int id) {
        this.consulta = "DELETE FROM pagoscreditos WHERE id = ?";
        cn = Conexion();
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setInt(1, id);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Pago Eliminado Exitosamete", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para Actualizar Pagos
    public void Actualizar(String id, int credito, float monto, Date fecha) {
        this.consulta = "UPDATE pagoscreditos SET credito = ?, monto = ?, fecha = ? WHERE id = ?";
        cn = Conexion();
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setInt(1, credito);
            pst.setFloat(2, monto);
            pst.setDate(3, fecha);
            pst.setString(4, id);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Pago Actualizado Exitosamete", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para mostrar todos los pagos
    public DefaultTableModel Mostrar(String buscar) {
        cn = Conexion();
        this.consulta = "SELECT pagoscreditos.id AS idPago, monto as montoPago, credito, pagoscreditos.fecha, clientes.nombres,apellidos FROM pagoscreditos INNER JOIN creditos ON(pagoscreditos.credito = creditos.id) INNER JOIN clientes ON(creditos.cliente = clientes.id) WHERE CONCAT(pagoscreditos.id, pagoscreditos.credito, pagoscreditos.fecha, clientes.nombres, clientes.apellidos) LIKE '%" + buscar + "%'";
        this.resgistros = new String[6];
        String[] titulos = {"Id Pago", "Monto de Pago", "Al Credito", "Fecha De Pago", "Nombres Cliente", "Apellidos Cliente"};
        this.modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            pst = this.cn.prepareStatement(this.consulta);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                this.resgistros[0] = rs.getString("idPago");
                this.resgistros[1] = rs.getString("montoPago");
                this.resgistros[2] = rs.getString("credito");
                this.resgistros[3] = rs.getString("fecha");
                this.resgistros[4] = rs.getString("nombres");
                this.resgistros[5] = rs.getString("apellidos");
                this.modelo.addRow(resgistros);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return this.modelo;
    }
    //funcion que me obtiene el total de pagos que tiene el cliete
    public float PagosCliente(String id) {
        cn = Conexion();
        float credito = 0;
        this.consulta = "SELECT SUM(pagoscreditos.monto) AS pago FROM pagoscreditos INNER JOIN creditos ON(pagoscreditos.credito=creditos.id) INNER JOIN clientes ON(creditos.cliente = clientes.id) WHERE clientes.id = ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                credito = rs.getFloat("pago");//total de pagos de cliente
            }
            cn.close();
        } catch (SQLException e) {
        }
        return credito;
    }

}
