
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
    public Facturacion()
    {
        this.conexion = new Conexiondb();
        this.cn = this.conexion.Conexion();
    }
    public void GuardarFacura()
    {
        
    }
    public void DetalleFactura()
    {
        
    }
    public DefaultTableModel BusquedaGeneralProductoVender(String buscar)
    {
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioVenta, fechaVencimiento, stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(productos.codigoBarra, productos.nombre) LIKE '%"+buscar+"%'";
        String[] registros = new String[10];
        String[] titulos = {"Id", "Codigo Barra","Nombre", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria","Laboratorio","Descuento", "Ubicacion", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        try
        {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
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
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return modelo;
    }
    public DefaultTableModel BuscarPorCategoria(String categoria)
    {
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioVenta, fechaVencimiento, stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(categorias.nombre) LIKE '%"+categoria+"%'";
        String[] registros = new String[10];
        String[] titulos = {"Id", "Codigo Barra","Nombre", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria","Laboratorio","Descuento", "Ubicacion", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        try
        {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
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
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return modelo;
    }
    public DefaultTableModel BuscarPorLaboratorio(String laboratorio)
    {
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioVenta, fechaVencimiento, stock, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(laboratorios.nombre) LIKE '%"+laboratorio+"%'";
        String[] registros = new String[10];
        String[] titulos = {"Id", "Codigo Barra","Nombre", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria","Laboratorio","Descuento", "Ubicacion", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        try
        {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
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
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return modelo;
    }
    public DefaultComboBoxModel FormasPago()
    {
        this.consulta = "SELECT * FROM tipoVenta";
        String[] Registros = new String[2];
        combo = new DefaultComboBoxModel();
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while(rs.next())
            {
                Registros[0] = rs.getString("id");
                Registros[1] = rs.getString("id");
                combo.addElement(Registros);
            }
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, e);
        }
        return combo;
    }
}
