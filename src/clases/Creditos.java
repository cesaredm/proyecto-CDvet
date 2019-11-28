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
public class Creditos {

    DefaultTableModel modelo;
    Connection cn;
    Conexiondb conexion;
    String consulta;
    String[] resgistros;
    int banderin;

    public Creditos() {
        this.conexion = new Conexiondb();
        this.cn = this.conexion.Conexion();
        this.consulta = "";
        this.banderin = 0;
    }

    public DefaultTableModel Mostrar() {
        this.consulta = "SELECT SUM(facturas.totalFactura) AS totalCredito, clientes.id AS idCliente, nombres, apellidos FROM facturas INNER JOIN clientes on(facturas.cliente=clientes.id) INNER JOIN tipoventa ON(facturas.tipoVenta = tipoventa.id) WHERE tipoventa.tipoVenta = 'Credito' GROUP BY clientes.nombres";
        String[] titulos = {"Total de Credito","Id Cliente","Nombre","Apellido"};
        this.resgistros = new String[4];
        this.modelo = new DefaultTableModel(null, titulos)
        {
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
                this.resgistros[0] = rs.getString("TotalCredito");
                this.resgistros[1] = rs.getString("idCliente");
                this.resgistros[2] = rs.getString("nombres");
                this.resgistros[3] = rs.getString("apellidos");
                this.modelo.addRow(resgistros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return this.modelo;
    }
}
