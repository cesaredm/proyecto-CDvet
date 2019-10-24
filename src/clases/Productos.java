package clases;

import java.awt.List;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Productos {

    DefaultTableModel modelo;
    DefaultComboBoxModel combo;
    Conexiondb conexion;
    Connection cn;
    String consulta;
    int banderin;
    public Productos() {
        this.conexion = new Conexiondb();
        this.cn = conexion.Conexion();
        this.combo = new DefaultComboBoxModel();
    }

    public void Guardar(String codigoBarra, String nombre, String precioCompra, String precioVenta, String fechaVencimiento, String stock, String categoria, String descuento, String laboratorio, String ubicacion, String descripcion) {
        this.consulta = "INSERT INTO productos(codigoBarra, nombre, precioCompra, precioVenta, fechaVencimiento, stock, categoria, descuento, laboratorio, ubicacion, descripcion) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        float compra=Float.parseFloat(precioCompra), venta=Float.parseFloat(precioVenta), cantidad=Float.parseFloat(stock);
        int Idcategoria = Integer.parseInt(categoria), Idlaboratorio = Integer.parseInt(laboratorio), Iddescuento = Integer.parseInt(descuento);
        try
        {
            PreparedStatement pst = this.cn.prepareStatement(this.consulta);
            pst.setString(1, codigoBarra);
            pst.setString(2, nombre);
            pst.setFloat(3, compra);
            pst.setFloat(4, venta);
            pst.setString(5, fechaVencimiento);
            pst.setFloat(6, cantidad);
            pst.setInt(7, Idcategoria);
            pst.setInt(8, Iddescuento);
            pst.setInt(9, Idlaboratorio);
            pst.setString(10, ubicacion);
            pst.setString(11, descripcion);
            this.banderin = pst.executeUpdate();
            if(this.banderin>0)
                {
                  JOptionPane.showMessageDialog(null, "Producto Guardado Exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);      
                }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Actualizar(String id, String codigoBarra, String nombre, String precioCompra, String precioVenta, String fechaVencimiento, String stock, String categoria, String descuento, String laboratorio, String ubicacion, String descripcion) {
        
    }

    public void Eliminar(String id) {
        this.consulta = "DELETE FROM productos WHERE id="+id;
        try
        {
            PreparedStatement pst = this.cn.prepareStatement(consulta);
            this.banderin = pst.executeUpdate();
            if(banderin>0)
            {
                JOptionPane.showMessageDialog(null, "Dato Borrado Exitosamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public DefaultTableModel Consulta(String buscar) {
        this.consulta = "SELECT productos.id, productos.codigoBarra, productos.nombre AS nombreProducto, precioCompra, precioVenta, fechaVencimiento, stock, descuento, ubicacion, productos.descripcion, categorias.nombre AS nombreCategoria, laboratorios.nombre as nombreLaboratorio FROM productos INNER JOIN categorias ON(productos.categoria=categorias.id) INNER JOIN laboratorios ON(productos.laboratorio=laboratorios.id) WHERE CONCAT(productos.codigoBarra, productos.nombre) LIKE '%"+buscar+"%'";
        String[] registros = new String[12];
        String[] titulos = {"Id", "Codigo Barra","Nombre", "precioCompra", "precioVenta", "Fecha Vencimiento", "Stock", "Categoria","Laboratorio","Descuento", "Ubicacion", "Descripcion"};
        modelo = new DefaultTableModel(null, titulos)
        {
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
                registros[3] = rs.getString("precioCompra");
                registros[4] = rs.getString("precioVenta");
                registros[5] = rs.getString("fechaVencimiento");
                registros[6] = rs.getString("stock");
                registros[7] = rs.getString("nombreCategoria");
                registros[8] = rs.getString("nombreLaboratorio");
                registros[9] = rs.getString("descuento");
                registros[10] = rs.getString("ubicacion");
                registros[11] = rs.getString("descripcion");
                this.modelo.addRow(registros);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return modelo;
    }

    public DefaultTableModel MostrarCategorias(String nombre) {
        this.consulta = "SELECT * FROM categorias WHERE nombre LIKE '%"+nombre+"%'";
        String[] resultados = new String[3];
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
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
                
                resultados[0] = rs.getString("id");
                resultados[1] = rs.getString("nombre");
                resultados[2] = rs.getString("descripcion");
                modelo.addRow(resultados);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }
    public DefaultTableModel MostrarLaboratorios(String nombre) {
        this.consulta = "SELECT * FROM laboratorios WHERE nombre LIKE '%"+nombre+"%'";
        String[] resultados = new String[3];
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
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
                
                resultados[0] = rs.getString("id");
                resultados[1] = rs.getString("nombre");
                resultados[2] = rs.getString("descripcion");
                modelo.addRow(resultados);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }

    /*public DefaultTableModel MostrarDescuentos() {
        this.consulta = "SELECT * FROM laboratorios WHERE nombre LIKE '%"+nombre+"%'";
        String[] resultados = new String[3];
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
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next())
            {
                
                resultados[0] = rs.getString("id");
                resultados[1] = rs.getString("nombre");
                resultados[2] = rs.getString("descripcion");
                modelo.addRow(resultados);
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return modelo;
    }*/
    public String ObtenerIdLaboratorio(String nombre)
    {
        String id = "";
        
        this.consulta = "SELECT id FROM categorias WHERE nombre='"+nombre+"'";
        try
        {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while(rs.next())
            {
                id = rs.getString("id");
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
    public String ObtenerIdCategoria(String nombre)
    {
        String id = "";
        this.consulta = "SELECT id FROM categorias WHERE nombre="+nombre;
        try
        {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(this.consulta);
            while(rs.next())
            {
                id = rs.getString("id");
            }
        }catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
}
