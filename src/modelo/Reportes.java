/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Reportes extends Conexiondb {

    String consulta;
    Connection cn;
    PreparedStatement pst;
    DefaultTableModel modelo;

    public Reportes() {
        this.pst = null;
        this.consulta = null;
        this.cn = null;
    }

    public DefaultTableModel ReporteDiario(Date Fecha) {
        cn = Conexion();
        this.consulta = "SELECT facturas.id,facturas.fecha AS fechaFactura, impuestoISV, totalFactura, nombre_comprador, formapago.tipoVenta, creditos.estado, creditos.id as idCredito from facturas LEFT JOIN formapago ON(formapago.id = facturas.tipoVenta) LEFT JOIN creditos ON(facturas.credito = creditos.id) WHERE facturas.fecha = ? ORDER BY facturas.id";
        String[] Resultados = new String[8];
        String[] titulos = {"Factura", "Fecha", "Iva", "Total", "Cliente", "Forma Pago", "Estado Credito", "N° Credito"};
        this.modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            pst = this.cn.prepareStatement(consulta);
            pst.setDate(1, Fecha);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Resultados[0] = rs.getString("id");
                Resultados[1] = rs.getString("fechaFactura");
                Resultados[2] = rs.getString("impuestoISV");
                Resultados[3] = rs.getString("totalFactura");
                Resultados[4] = rs.getString("nombre_comprador");
                Resultados[5] = rs.getString("tipoVenta");
                Resultados[6] = rs.getString("estado");
                Resultados[7] = rs.getString("idCredito");
                modelo.addRow(Resultados);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }

    public DefaultTableModel ReporteMensual(Date fecha1, Date fecha2) {
        cn = Conexion();
        String[] registros = new String[8];
        String[] titulos = {"Factura", "Fecha", "Iva", "Total", "Comprador", "Forma Pago", "Estado Credito", "N° Credito"};
        this.consulta = "SELECT facturas.id,facturas.fecha AS fechaFactura, impuestoISV, totalFactura, nombre_comprador, formapago.tipoVenta, creditos.estado,creditos.id AS idCredito from facturas LEFT JOIN formapago ON(formapago.id = facturas.tipoVenta) LEFT JOIN creditos ON(facturas.credito = creditos.id) WHERE facturas.fecha BETWEEN ? AND ? ORDER BY facturas.id";
        this.modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setDate(1, fecha1);
            pst.setDate(2, fecha2);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("fechaFactura");
                registros[2] = rs.getString("impuestoISV");
                registros[3] = rs.getString("totalFactura");
                registros[4] = rs.getString("nombre_comprador");
                registros[5] = rs.getString("tipoVenta");
                registros[6] = rs.getString("estado");
                registros[7] = rs.getString("idCredito");
                this.modelo.addRow(registros);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return modelo;
    }

    public DefaultTableModel DetalleFactura(int id) {
        cn = Conexion();
        this.consulta = "SELECT productos.id,codigoBarra,nombre, detallefactura.id AS idDetalle,precioProducto,cantidadProducto,totalVenta FROM productos INNER JOIN detallefactura ON(productos.id = detallefactura.producto) INNER JOIN facturas ON(detallefactura.factura = facturas.id) WHERE facturas.id = " + id;
        String[] registros = new String[7];
        String[] titulos = {"Detalle", "Id", "Codigo Barra", "Producto", "Cantidad", "Precio", "Total"};
        this.modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while (rs.next()) {
                registros[0] = rs.getString("idDetalle");
                registros[1] = rs.getString("id");
                registros[2] = rs.getString("codigoBarra");
                registros[3] = rs.getString("nombre");
                registros[4] = rs.getString("cantidadProducto");
                registros[5] = rs.getString("precioProducto");
                registros[6] = rs.getString("totalVenta");
                this.modelo.addRow(registros);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }

    public String TotalCreditosDiario(Date fecha) {
        cn = Conexion();
        String totalCreditos = "";
        this.consulta = "SELECT SUM(totalFactura) AS totalCredito FROM facturas INNER JOIN creditos ON(facturas.credito = creditos.id) WHERE creditos.estado = 'Pendiente' AND facturas.fecha = ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setDate(1, fecha);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                totalCreditos = rs.getString("totalCredito");
            }
            if (totalCreditos == null) {
                totalCreditos = "0.0";
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return totalCreditos;
    }

    public String TotalCreditosMensual(Date fechaInicio, Date fechaFinal) {
        cn = Conexion();
        String totalCreditos = "";
        this.consulta = "SELECT SUM(totalFactura) AS totalCreditoMensual FROM facturas INNER JOIN creditos ON(facturas.credito = creditos.id) WHERE creditos.estado = 'Pendiente' AND facturas.fecha BETWEEN ? AND ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setDate(1, fechaInicio);
            pst.setDate(2, fechaFinal);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                totalCreditos = rs.getString("totalCreditoMensual");
            }
            if (totalCreditos == null) {
                totalCreditos = "0.0";
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return totalCreditos;
    }

    public String TotalGastos(Date fecha1, Date fecha2) {
        cn = Conexion();
        String totalGasto = "";
        this.consulta = "SELECT SUM(monto) AS totalGasto FROM gastos WHERE fecha BETWEEN ? AND ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setDate(1, fecha1);
            pst.setDate(2, fecha2);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                totalGasto = rs.getString("totalGasto");
            }
            if (totalGasto == null) {
                totalGasto = "0.0";
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return totalGasto;
    }

    public String TotalGastosDiario(Date fecha1) {
        cn = Conexion();
        String totalGasto = "";
        this.consulta = "SELECT SUM(monto) AS totalGasto FROM gastos WHERE fecha = ?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setDate(1, fecha1);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                totalGasto = rs.getString("totalGasto");
            }
            if (totalGasto == null) {
                totalGasto = "0.0";
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return totalGasto;
    }

    public String totalPagos(Date fecha1, Date fecha2){
        cn = Conexion();
        String pagos = "";
        this.consulta = "SELECT SUM(monto) AS TotalPagos FROM pagoscreditos INNER JOIN creditos ON(pagoscreditos.credito = creditos.id) "
                + "WHERE creditos.estado = 'Pendiente' AND pagoscreditos.fecha BETWEEN ? AND ?";
        try {
            pst = cn.prepareStatement(consulta);
            pst.setDate(1, fecha1);
            pst.setDate(2, fecha2);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                pagos = rs.getString("totalPagos");
            }
            if(pagos == null)
            {
                pagos = "0";
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return pagos;
    }
    public String nombreCliente(String id)
    {
        cn = Conexion();
        String Nombres = "";
        this.consulta = "SELECT clientes.nombres FROM clientes INNER JOIN creditos ON(clientes.id = creditos.cliente) WHERE creditos.id = ?";
        try {
            this.pst = this.cn.prepareStatement(consulta);
            this.pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Nombres = rs.getString("nombres");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+"funcion nombres clientes");
        }
            return Nombres;
    }
    public String apellidoCliente(String id)
    {
        cn = Conexion();
        String apellidos = "";
        this.consulta = "SELECT clientes.apellidos FROM clientes INNER JOIN creditos ON(clientes.id = creditos.cliente) WHERE creditos.id = ?";
        try {
            this.pst = this.cn.prepareStatement(consulta);
            this.pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                apellidos = rs.getString("apellidos");                
            }

            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e+"funcion apellidos clientes");
        }
        return apellidos;
    }
}
