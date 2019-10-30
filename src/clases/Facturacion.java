package clases;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Facturacion {

    String consulta;
    DefaultTableModel modelo;
    Conexiondb conexion;
    Connection cn;
    DefaultComboBoxModel combo;
    int banderin;

    public Facturacion() {
        this.conexion = new Conexiondb();
        this.cn = this.conexion.Conexion();
    }
//Guardar Factura
    public void GuardarFactura(String cliente, String pago, String iva, String total) {
        this.consulta = "INSERT INTO facturas(cliente, tipoVenta, impuestoISV, totalFactura) VALUES(?,?,?,?)";
        int idCliente = 0, formaPago = Integer.parseInt(pago);
        float impuestoIVA = Float.parseFloat(iva), totalFactura = Float.parseFloat(total);
        if (!cliente.equals("")) {
            idCliente = Integer.parseInt(cliente);
            try {
                PreparedStatement pst = this.cn.prepareStatement(this.consulta);
                pst.setInt(1, idCliente);
                pst.setInt(2, formaPago);
                pst.setFloat(3, impuestoIVA);
                pst.setFloat(4, totalFactura);
                this.banderin = pst.executeUpdate();
                if (banderin > 0) {
                    JOptionPane.showMessageDialog(null, "Factura Guardada Exitosamente", "Informacion", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            try {
                PreparedStatement pst = this.cn.prepareStatement(this.consulta);
                pst.setNull(1, java.sql.Types.INTEGER);
                pst.setInt(2, formaPago);
                pst.setFloat(3, impuestoIVA);
                pst.setFloat(4, totalFactura);
                this.banderin = pst.executeUpdate();
                if (banderin > 0) {
                    JOptionPane.showMessageDialog(null, "Factura Guardada Exitosamente", "Informacion", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }
//Guardar detalleFactura
    public void DetalleFactura(String factura, String producto, String precio, String cantidad, String total) {
        int idFactura = Integer.parseInt(factura), idProducto = Integer.parseInt(producto);
        float precioP = Float.parseFloat(precio), cantidadP = Float.parseFloat(cantidad), totalD = Float.parseFloat(total);
        this.consulta = "INSERT INTO detalleFactura(factura, producto, precioProducto, cantidadProducto, totalVenta) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = this.cn.prepareStatement(this.consulta);
            pst.setInt(1, idFactura);
            pst.setInt(2, idProducto);
            pst.setFloat(3, precioP);
            pst.setFloat(4, cantidadP);
            pst.setFloat(5, totalD);
            this.banderin = pst.executeUpdate();
            if (banderin > 0) {
                //JOptionPane.showMessageDialog(null, "detalle guardado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//metodo para busqueda general y por nombre y cod. barra de producto
    public DefaultTableModel BusquedaGeneralProductoVender(String buscar) {
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioVenta, fechaVencimiento, stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(productos.codigoBarra, productos.nombre) LIKE '%" + buscar + "%'";
        String[] registros = new String[10];
        String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria", "Laboratorio", "Ubicacion", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("codigoBarra");
                registros[2] = rs.getString("nombreProducto");
                registros[3] = rs.getString("precioVenta");
                registros[4] = rs.getString("fechaVencimiento");
                registros[5] = rs.getString("stock");
                registros[6] = rs.getString("nombreCategoria");
                registros[7] = rs.getString("nombreLaboratorio");
                registros[8] = rs.getString("ubicacion");
                registros[9] = rs.getString("descripcion");
                this.modelo.addRow(registros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
//metodo para filtro de busqueda del producto por categoria de producto
    public DefaultTableModel BuscarPorCategoria(String categoria) {
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioVenta, fechaVencimiento, stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(categorias.nombre) LIKE '%" + categoria + "%'";
        String[] registros = new String[10];
        String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria", "Laboratorio", "Descuento", "Ubicacion", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("codigoBarra");
                registros[2] = rs.getString("nombreProducto");
                registros[3] = rs.getString("precioVenta");
                registros[4] = rs.getString("fechaVencimiento");
                registros[5] = rs.getString("stock");
                registros[6] = rs.getString("nombreCategoria");
                registros[7] = rs.getString("nombreLaboratorio");
                //registros[8] = rs.getString("descuento");
                registros[8] = rs.getString("ubicacion");
                registros[9] = rs.getString("descripcion");
                this.modelo.addRow(registros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
//metodo para filtro de busqueda del producto por laboratorio
    public DefaultTableModel BuscarPorLaboratorio(String laboratorio) {
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioVenta, fechaVencimiento, stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(laboratorios.nombre) LIKE '%" + laboratorio + "%'";
        String[] registros = new String[10];
        String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria", "Laboratorio", "Descuento", "Ubicacion", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("codigoBarra");
                registros[2] = rs.getString("nombreProducto");
                registros[3] = rs.getString("precioVenta");
                registros[4] = rs.getString("fechaVencimiento");
                registros[5] = rs.getString("stock");
                registros[6] = rs.getString("nombreCategoria");
                registros[7] = rs.getString("nombreLaboratorio");
                //registros[8] = rs.getString("descuento");
                registros[8] = rs.getString("ubicacion");
                registros[9] = rs.getString("descripcion");
                this.modelo.addRow(registros);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
//metodo para obtener los tipos de pago
    public DefaultComboBoxModel FormasPago() {
        this.consulta = "SELECT * FROM tipoVenta";
        combo = new DefaultComboBoxModel();
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while (rs.next()) {
                combo.addElement(rs.getString("tipoVenta"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return combo;
    }
//metodo que me retorna el id de la factura y sumo 1 para mostrar la factura siguiente 
    public String ObtenerIdFactura() {
        int sumaId = 0;
        String id = "", obtenerId = "";
        this.consulta = "SELECT MAX(id) AS id FROM facturas";
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while (rs.next()) {
                obtenerId = rs.getString("id");
            }
            if (!obtenerId.equals(null)) {
                sumaId = Integer.parseInt(obtenerId) + 1;
                id = String.valueOf(sumaId);
            } else {
                obtenerId = "0";
                sumaId = Integer.parseInt(obtenerId) + 1;
                id = String.valueOf(sumaId);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
    //metodo para obtener el id de la forma de pago segun el metodo de pago que recibe
    public String ObtenerFormaPago(String pago)
    {
        this.consulta = "SELECT id FROM tipoVenta WHERE tipoVenta = '"+pago+"'";
        String id = "";
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while(rs.next())
            {
                id = rs.getString("id");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
}
