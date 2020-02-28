package modelo;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Facturacion extends Conexiondb {

    String consulta;
    DefaultTableModel modelo;
    Connection cn;
    PreparedStatement pst;
    DefaultComboBoxModel combo;
    int banderin;

    public Facturacion() {
        this.cn = null;
        this.pst = null;
    }
//Guardar Factura

    public void GuardarFactura(Date fecha, String nombreComprador, String credito, String pago, String iva, String total) {
        cn = Conexion();
        this.consulta = "INSERT INTO facturas(fecha, nombre_comprador, credito, tipoVenta, impuestoISV, totalFactura) VALUES(?,?,?,?,?,?)";
        int idCredito = 0, formaPago = Integer.parseInt(pago);
        float impuestoIVA = Float.parseFloat(iva), totalFactura = Float.parseFloat(total);
        if (!credito.equals("")) {
            idCredito = Integer.parseInt(credito);
            try {
                pst = this.cn.prepareStatement(this.consulta);
                pst.setDate(1, fecha);
                pst.setString(2, nombreComprador);
                pst.setInt(3, idCredito);
                pst.setInt(4, formaPago);
                pst.setFloat(5, impuestoIVA);
                pst.setFloat(6, totalFactura);
                this.banderin = pst.executeUpdate();
                if (banderin > 0) {
                    JOptionPane.showMessageDialog(null, "Factura Guardada Exitosamente", "Informacion", JOptionPane.WARNING_MESSAGE);
                }
                cn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            try {
                pst = this.cn.prepareStatement(this.consulta);
                pst.setDate(1, fecha);
                pst.setString(2, nombreComprador);
                pst.setNull(3, java.sql.Types.INTEGER);
                pst.setInt(4, formaPago);
                pst.setFloat(5, impuestoIVA);
                pst.setFloat(6, totalFactura);
                this.banderin = pst.executeUpdate();
                if (banderin > 0) {
                    JOptionPane.showMessageDialog(null, "Factura Guardada Exitosamente", "Informacion", JOptionPane.WARNING_MESSAGE);
                }
                cn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }
//Guardar detalleFactura

    public void DetalleFactura(String factura, String producto, String precio, String cantidad, String total) {
        cn = Conexion();
        int idFactura = Integer.parseInt(factura), idProducto = Integer.parseInt(producto);
        float precioP = Float.parseFloat(precio), cantidadP = Float.parseFloat(cantidad), totalD = Float.parseFloat(total);
        this.consulta = "INSERT INTO detalleFactura(factura, producto, precioProducto, cantidadProducto, totalVenta) VALUES(?,?,?,?,?)";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setInt(1, idFactura);
            pst.setInt(2, idProducto);
            pst.setFloat(3, precioP);
            pst.setFloat(4, cantidadP);
            pst.setFloat(5, totalD);
            this.banderin = pst.executeUpdate();
            if (banderin > 0) {
                //JOptionPane.showMessageDialog(null, "detalle guardado");
            }
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//metodo para busqueda general y por nombre y cod. barra de producto

    public DefaultTableModel BusquedaGeneralProductoVender(String buscar) {
        cn = Conexion();
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
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
//metodo para filtro de busqueda del producto por categoria de producto

    public DefaultTableModel BuscarPorCategoria(String categoria) {
        cn = Conexion();
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
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
//metodo para filtro de busqueda del producto por laboratorio

    public DefaultTableModel BuscarPorLaboratorio(String laboratorio) {
        cn = Conexion();
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
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
//metodo para obtener los tipos de pago

    public DefaultComboBoxModel FormasPago() {
        cn = Conexion();
        this.consulta = "SELECT * FROM formapago";
        combo = new DefaultComboBoxModel();
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while (rs.next()) {
                combo.addElement(rs.getString("tipoVenta"));
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return combo;
    }
//metodo que me retorna el id de la factura y sumo 1 para mostrar la factura siguiente 

    public String ObtenerIdFactura() {
        cn = Conexion();
        int sumaId = 0, s;
        String id = "", obtenerId = "";
        this.consulta = "SELECT MAX(id) AS id FROM facturas";
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while (rs.next()) {
                obtenerId = rs.getString("id");
            }
            if (obtenerId != null) {
                sumaId = Integer.parseInt(obtenerId) + 1;
                id = String.valueOf(sumaId);
            } else {
                obtenerId = "0";
                sumaId = Integer.parseInt(obtenerId) + 1;
                id = String.valueOf(sumaId);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }

    //metodo para obtener el id de la forma de pago segun el metodo de pago que recibe
    public String ObtenerFormaPago(String pago) {
        cn = Conexion();
        this.consulta = "SELECT id FROM formapago WHERE tipoVenta = '" + pago + "'";
        String id = "";
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while (rs.next()) {
                id = rs.getString("id");
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }

    public void Vender(String id, String cantidad) {
        cn = Conexion();
        Float cantidadP = Float.parseFloat(cantidad);
        int idP = Integer.parseInt(id);
        this.consulta = "{CALL venderProductoStock(?,?)}";
        try {
            CallableStatement cst = this.cn.prepareCall(this.consulta);
            cst.setInt(1, idP);
            cst.setFloat(2, cantidadP);
            cst.execute();
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ActualizarFactura(String id, Date fecha, String nombreComprador, String credito, String pago, String iva, String total) {
        cn = Conexion();
        if (credito.equals("")) {
            credito = null;
        }
        this.consulta = "UPDATE facturas SET credito = ?, nombre_comprador = ?,fecha = ? , tipoVenta = ?, impuestoISV = ?, totalFactura = ? WHERE id=?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, credito);
            pst.setString(2, nombreComprador);
            pst.setDate(3, fecha);
            pst.setString(4, pago);
            pst.setString(5, iva);
            pst.setString(6, total);
            pst.setString(7, id);
            pst.execute();
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "factura actualizada correctamente");
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ActualizarDetalle(String id, String producto, String precio, String cant, String total) {
        cn = Conexion();
        this.consulta = "UPDATE detalleFactura SET producto = ?, precioProducto = ?, cantidadProducto = ?, totalVenta = ? WHERE id=?";
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, producto);
            pst.setString(2, precio);
            pst.setString(3, cant);
            pst.setString(4, total);
            pst.setString(5, id);
            pst.execute();
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {

            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

}
