/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Date;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Reportes {
    String consulta;
    Conexiondb conexion;
    Connection cn;
    DefaultTableModel modelo;
    public Reportes()
    {
        this.consulta = "";
        this.conexion = new Conexiondb();
        this.cn = conexion.Conexion();
    }
    
    public DefaultTableModel ReporteDiario(Date Fecha)
    {
        this.consulta = "SELECT facturas.id, fecha, impuestoISV, totalFactura, cliente, tipoVenta.tipoVenta from facturas INNER JOIN tipoVenta ON(tipoVenta.id = facturas.tipoVenta) WHERE facturas.fecha = '"+Fecha+"'";
        String[] Resultados = new String[6];
        String[] titulos = {"Factura","Fecha", "Iva", "Total", "Cliente","Forma Pago"}; 
        this.modelo = new DefaultTableModel(null, titulos)
        {
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while(rs.next())
            {
                Resultados[0] = rs.getString("id");
                Resultados[1] = rs.getString("fecha");
                Resultados[2] = rs.getString("impuestoISV");
                Resultados[3] = rs.getString("totalFactura");
                Resultados[4] = rs.getString("cliente");
                Resultados[5] = rs.getString("tipoVenta");
                modelo.addRow(Resultados);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
    
    public DefaultTableModel ReporteMensual(Date fecha1, Date fecha2)
    {
        String[] registros = new String[6];
        String[] titulos = {"Factura","Fecha", "Iva", "Total", "Cliente","Forma Pago"};
        this.consulta = "SELECT facturas.id, fecha, impuestoISV, totalFactura, cliente, tipoVenta.tipoVenta from facturas INNER JOIN tipoVenta ON(tipoVenta.id = facturas.tipoVenta) WHERE facturas.fecha BETWEEN '"+fecha1+"' AND '"+fecha2+"'";
        this.modelo = new DefaultTableModel(null, titulos){
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while(rs.next())
            {
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("fecha");
                registros[2] = rs.getString("impuestoISV");
                registros[3] = rs.getString("totalFactura");
                registros[4] = rs.getString("cliente");
                registros[5] = rs.getString("tipoVenta");
                this.modelo.addRow(registros);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
}
