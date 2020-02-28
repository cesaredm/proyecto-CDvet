package modelo;

import controlador.CtrlProducto;
import java.awt.List;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Productos extends Conexiondb {

    DefaultTableModel modelo;
    DefaultComboBoxModel combo;
    Connection cn;
    PreparedStatement pst;
    String consulta;
    int banderin;

    public Productos() {
        this.cn = null;
        this.combo = new DefaultComboBoxModel();
        this.pst = null;
    }

    public void Guardar(String codigoBarra, String nombre, String precioCompra, String precioVenta, Date fechaVencimiento, String stock, String categoria, String laboratorio, String ubicacion, String descripcion) {
        cn = Conexion();
        this.consulta = "INSERT INTO productos(codigoBarra, nombre, precioCompra, precioVenta, fechaVencimiento, stock, categoria, laboratorio, ubicacion, descripcion) VALUES(?,?,?,?,?,?,?,?,?,?)";
        float compra = Float.parseFloat(precioCompra), venta = Float.parseFloat(precioVenta), cantidad = Float.parseFloat(stock);
        int Idcategoria = Integer.parseInt(categoria), Idlaboratorio = Integer.parseInt(laboratorio);
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, codigoBarra);
            pst.setString(2, nombre);
            pst.setFloat(3, compra);
            pst.setFloat(4, venta);
            pst.setDate(5, fechaVencimiento);
            pst.setFloat(6, cantidad);
            pst.setInt(7, Idcategoria);
            pst.setInt(8, Idlaboratorio);
            pst.setString(9, ubicacion);
            pst.setString(10, descripcion);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Producto Guardado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Actualizar(String id, String codigoBarra, String nombre, String precioCompra, String precioVenta, Date fechaVencimiento, String stock, String categoria, String laboratorio, String ubicacion, String descripcion) {
        cn = Conexion();
        this.consulta = "UPDATE productos SET codigoBarra=?, nombre=?, precioCompra=?, precioVenta=?, fechaVencimiento=?, stock=?, categoria=?, laboratorio=?, ubicacion=?, descripcion=? WHERE id =" + id;
        float compra = Float.parseFloat(precioCompra), venta = Float.parseFloat(precioVenta), cantidad = Float.parseFloat(stock);
        int Idcategoria = Integer.parseInt(categoria), Idlaboratorio = Integer.parseInt(laboratorio);
        try {
            pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, codigoBarra);
            pst.setString(2, nombre);
            pst.setFloat(3, compra);
            pst.setFloat(4, venta);
            pst.setDate(5, fechaVencimiento);
            pst.setFloat(6, cantidad);
            pst.setInt(7, Idcategoria);
            pst.setInt(8, Idlaboratorio);
            pst.setString(9, ubicacion);
            pst.setString(10, descripcion);
            this.banderin = pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Producto Actualizado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Eliminar(String id) {
        cn = Conexion();
        this.consulta = "DELETE FROM productos WHERE id=" + id;
        try {
            pst = this.cn.prepareStatement(consulta);
            this.banderin = pst.executeUpdate();
            if (banderin > 0) {
                JOptionPane.showMessageDialog(null, "Dato Borrado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public DefaultTableModel Consulta(String buscar) {
        cn = Conexion();
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioCompra, precioVenta, fechaVencimiento, stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(productos.codigoBarra, productos.nombre) LIKE '%" + buscar + "%'";
        String[] registros = new String[12];
        String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioCompra", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria", "Laboratorio", "Ubicacion", "Descripcion"};
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
                registros[3] = rs.getString("precioCompra");
                registros[4] = rs.getString("precioVenta");
                registros[5] = rs.getString("fechaVencimiento");
                registros[6] = rs.getString("stock");
                registros[7] = rs.getString("nombreCategoria");
                registros[8] = rs.getString("nombreLaboratorio");
                registros[9] = rs.getString("ubicacion");
                registros[10] = rs.getString("descripcion");
                this.modelo.addRow(registros);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return modelo;
    }
    //Mostrar todas la categorias para agregra al producto
    public DefaultTableModel MostrarCategorias(String nombre) {
        cn = Conexion();
        this.consulta = "SELECT * FROM categorias WHERE nombre LIKE '%" + nombre + "%'";
        String[] resultados = new String[3];
        String[] titulos = {"Id", "Nombre", "Descripcion"};
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

                resultados[0] = rs.getString("id");
                resultados[1] = rs.getString("nombre");
                resultados[2] = rs.getString("descripcion");
                this.modelo.addRow(resultados);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
    //Mostrar todas la Laboratorio para agregra al producto
    public DefaultTableModel MostrarLaboratorios(String nombre) {
        cn = Conexion();
        this.consulta = "SELECT * FROM laboratorios WHERE nombre LIKE '%" + nombre + "%'";
        String[] resultados = new String[3];
        String[] titulos = {"Id", "Nombre", "Descripcion"};
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

                resultados[0] = rs.getString("id");
                resultados[1] = rs.getString("nombre");
                resultados[2] = rs.getString("descripcion");
                modelo.addRow(resultados);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
    
    public String ObtenerIdLaboratorio(String nombre)//metodo para obtener Id de laboratorio para modificar producto
    {
        String id = "";
        cn = Conexion();
        this.consulta = "SELECT id FROM laboratorios WHERE nombre='" + nombre + "'";
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

    public String ObtenerIdCategoria(String nombre)//metodo para obtener Id de categoria para modificar producto
    {
        String id = "";
        cn = Conexion();
        this.consulta = "SELECT id FROM categorias WHERE nombre='" + nombre + "'";
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

    public void AgregarProductoStock(String id, String cantidad)//metodo para agregar producto al stock
    {
        cn = Conexion();
        float c = Float.parseFloat(cantidad);
        int idP = Integer.parseInt(id);
        this.consulta = "{CALL agregarProductoStock(?,?)}";
        try {
            CallableStatement cst = this.cn.prepareCall(this.consulta);
            cst.setInt(1, idP);
            cst.setFloat(2, c);
            this.banderin = cst.executeUpdate();
            if (banderin > 0) {
                //JOptionPane.showMessageDialog(null, "Se Agrego Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public DefaultTableModel MinimoStock(String categoria, float cantidad) {
        cn = Conexion();
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioCompra, precioVenta, fechaVencimiento,stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE productos.stock < " + cantidad + " AND categorias.nombre LIKE '%" + categoria + "%'";
        String[] titulos = {"Id", "Codigo Barra", "Nombre", "precioCompra", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria", "Laboratorio", "Ubicacion", "Descripcion"};
        String[] registros = new String[12];
        modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        try {
            Statement pst = this.cn.createStatement();
            //pst.setInt(1, cantidad);
            //pst.setString(2, categoria);
            ResultSet rs = pst.executeQuery(consulta);
            while (rs.next()) {
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("codigoBarra");
                registros[2] = rs.getString("nombreProducto");
                registros[3] = rs.getString("precioCompra");
                registros[4] = rs.getString("precioVenta");
                registros[5] = rs.getString("fechaVencimiento");
                registros[6] = rs.getString("stock");
                registros[7] = rs.getString("nombreCategoria");
                registros[8] = rs.getString("nombreLaboratorio");
                registros[9] = rs.getString("ubicacion");
                registros[10] = rs.getString("descripcion");
                this.modelo.addRow(registros);
            }
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return this.modelo;
    }
    
    public void GenerarReporteStockMin(String categ, float cantidad) throws SQLException
    {
        try {
            this.cn = Conexion();
                JasperReport Reporte = null;
                String path = "/Reportes/minStock.jasper";
                Map parametros = new HashMap();
                parametros.put("cantidad", cantidad);
                parametros.put("categoria", categ);
                //Reporte = (JasperReport) JRLoader.loadObject(path);
                Reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/minStock.jasper"));
                JasperPrint jprint = JasperFillManager.fillReport(Reporte, parametros, cn);
                JasperViewer vista = new JasperViewer(jprint,false);
                vista.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                vista.setVisible(true);
                cn.close();
            } catch (JRException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
