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
        this.consulta = "SELECT facturas.id, fecha, impuestoISV, totalFactura, cliente, tipoVenta.tipoVenta from facturas INNER JOIN tipoVenta ON(tipoVenta.id = facturas.tipoVenta) WHERE facturas.fecha = '"+Fecha+"' ORDER BY facturas.id";
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
    public DefaultTableModel DetalleFactura(int id)
    {
        this.consulta = "SELECT productos.id,codigoBarra,nombre, detallefactura.id AS idDetalle,precioProducto,cantidadProducto,totalVenta FROM productos INNER JOIN detallefactura ON(productos.id = detallefactura.producto) INNER JOIN facturas ON(detallefactura.factura = facturas.id) WHERE facturas.id = "+id;
        String[] registros = new String[7];
        String[] titulos = {"Detalle","Id","Codigo Barra", "Producto", "Cantidad", "Precio","Total"};
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
                registros[0] = rs.getString("idDetalle");
                registros[1] = rs.getString("id");
                registros[2] = rs.getString("codigoBarra");
                registros[3] = rs.getString("nombre");
                registros[4] = rs.getString("cantidadProducto");
                registros[5] = rs.getString("precioProducto");
                registros[6] = rs.getString("totalVenta");
                this.modelo.addRow(registros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
    public String TotalCreditosDiario(Date fecha)
    {
        String totalCreditos = "";
        this.consulta = "SELECT SUM(totalFactura) AS totalCredito FROM facturas INNER JOIN tipoventa on(facturas.tipoVenta=tipoventa.id) WHERE facturas.fecha = ? AND tipoventa.tipoVenta='Credito'";
        try {
            PreparedStatement pst = this.cn.prepareStatement(this.consulta);
            pst.setDate(1, fecha);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                totalCreditos = rs.getString("totalCredito");
            }
            if(totalCreditos == null)
            {
               totalCreditos = "0.0";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return totalCreditos;
    }
    public String TotalCreditosMensual(Date fechaInicio, Date fechaFinal)
    {
        String totalCreditos = "";
        this.consulta = "SELECT SUM(totalFactura) AS totalCreditoMensual from facturas INNER JOIN tipoVenta ON(tipoVenta.id = facturas.tipoVenta) WHERE tipoVenta.tipoVenta = 'Credito' AND facturas.fecha BETWEEN ? AND ?";
        try {
            PreparedStatement pst = this.cn.prepareStatement(this.consulta);
            pst.setDate(1, fechaInicio);
            pst.setDate(2, fechaFinal);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                totalCreditos = rs.getString("totalCreditoMensual");
            }
            if(totalCreditos == null)
            {
               totalCreditos = "0.0";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return totalCreditos;
    }
}
    

