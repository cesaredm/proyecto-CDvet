package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.ILogin;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlMenuOpciones implements MouseListener, ActionListener {

    Date fecha;
    IMenu menu;
    DefaultTableModel modelo;
    Productos p;
    Clientes c;
    Creditos cred;
    Facturacion factura;
    Reportes reportes;
    Usuarios usuarios;
    Gastos gastos;
    PagosCreditos pagos;
    CtrlClientes ctrlClient;
    CtrlProducto ctrlP;
    CtrlCreditos ctrlCred;
    CtrlFacturacion ctrlFact;
    CtrlReportes ctrlRepo;
    CtrlUsuarios ctrlUsua;
    CtrlGastos ctrlGastos;
    CtrlPagos ctrlPagos;

    public CtrlMenuOpciones(IMenu menu) {
        this.fecha = new Date();
        this.menu = menu;
        this.modelo = new DefaultTableModel();
        this.menu.btnInventario.addMouseListener(this);
        this.menu.btnClientes.addMouseListener(this);
        this.menu.btnCreditos.addMouseListener(this);
        this.menu.btnVentas.addMouseListener(this);
        this.menu.btnReportes.addMouseListener(this);
        this.menu.btnUsuarios.addMouseListener(this);
        this.menu.btnComprasGastos.addMouseListener(this);
        this.menu.btnNotificaciones.addMouseListener(this);
        this.menu.btnCerrarSesion.addMouseListener(this);
        this.menu.btnVerificarVencimientos.addActionListener(this);
        this.p = new Productos();
        this.c = new Clientes();
        this.cred = new Creditos();
        this.factura = new Facturacion();
        this.reportes = new Reportes();
        this.usuarios = new Usuarios();
        this.gastos = new Gastos();
        this.pagos = new PagosCreditos();
        ctrlClient = new CtrlClientes(menu, c);
        ctrlP = new CtrlProducto(p, menu);
        Notificacion();
        ctrlCred = new CtrlCreditos(menu, cred);
        ctrlFact = new CtrlFacturacion(menu, factura);
        ctrlRepo = new CtrlReportes(menu, reportes);
        ctrlUsua = new CtrlUsuarios(menu, usuarios);
        ctrlGastos = new CtrlGastos(menu, gastos);
        ctrlPagos = new CtrlPagos(menu, pagos);
    }

    public void iniciarMenu() {
        this.menu.setVisible(true);
        this.menu.setLocationRelativeTo(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.btnInventario) {
            menu.btnInventario.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuInventario.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Inventario");

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlClientes.setVisible(false);
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(false);
            menu.pnlInventario.setVisible(true);
            menu.pnlCreditos.setVisible(false);
            menu.pnlUsuarios.setVisible(false);
            menu.pnlNotificaciones.setVisible(false);
            menu.pnlComprasGastos.setVisible(false);

        }
        if (e.getSource() == menu.btnClientes) {
            menu.btnClientes.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuClientes.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Clientes");

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlClientes.setVisible(true);
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(false);
            menu.pnlInventario.setVisible(false);
            menu.pnlCreditos.setVisible(false);
            menu.pnlUsuarios.setVisible(false);
            menu.pnlNotificaciones.setVisible(false);
            menu.pnlComprasGastos.setVisible(false);
        }
        if (e.getSource() == menu.btnCreditos) {

            menu.btnCreditos.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Creditos");

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlClientes.setVisible(false);
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(false);
            menu.pnlInventario.setVisible(false);
            menu.pnlCreditos.setVisible(true);
            menu.pnlUsuarios.setVisible(false);
            menu.pnlNotificaciones.setVisible(false);
            menu.pnlComprasGastos.setVisible(false);
        }
        if (e.getSource() == menu.btnVentas) {
            menu.btnVentas.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuVentas.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Facturación");

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlClientes.setVisible(false);
            menu.pnlVentas.setVisible(true);
            menu.pnlReportes.setVisible(false);
            menu.pnlInventario.setVisible(false);
            menu.pnlCreditos.setVisible(false);
            menu.pnlUsuarios.setVisible(false);
            menu.pnlNotificaciones.setVisible(false);
            menu.pnlComprasGastos.setVisible(false);
        }
        if (e.getSource() == menu.btnReportes) {
            menu.btnReportes.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuReportes.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Reportes");

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 249, 252));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlClientes.setVisible(false);
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(true);
            menu.pnlInventario.setVisible(false);
            menu.pnlCreditos.setVisible(false);
            menu.pnlUsuarios.setVisible(false);
            menu.pnlNotificaciones.setVisible(false);
            menu.pnlComprasGastos.setVisible(false);
        }
        if (e.getSource() == menu.btnUsuarios) {
            menu.btnUsuarios.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Gestion de Usuarios");

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlClientes.setVisible(false);
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(false);
            menu.pnlInventario.setVisible(false);
            menu.pnlCreditos.setVisible(false);
            menu.pnlUsuarios.setVisible(true);
            menu.pnlNotificaciones.setVisible(false);
            menu.pnlComprasGastos.setVisible(false);
        }
        if (e.getSource() == menu.btnComprasGastos) {
            menu.btnComprasGastos.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblGastosMenu.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Compras y Otros Gastos");

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlComprasGastos.setVisible(true);
            menu.pnlClientes.setVisible(false);
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(false);
            menu.pnlInventario.setVisible(false);
            menu.pnlCreditos.setVisible(false);
            menu.pnlUsuarios.setVisible(false);
            menu.pnlNotificaciones.setVisible(false);
        }
        if (e.getSource() == menu.btnNotificaciones) {
            menu.btnNotificaciones.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(0, 222, 171));
            menu.lblTituloDeVentanas.setText("Notificaciones");

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 249, 252));

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 249, 252));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCerrarSesion.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            menu.pnlClientes.setVisible(false);
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(false);
            menu.pnlInventario.setVisible(false);
            menu.pnlCreditos.setVisible(false);
            menu.pnlUsuarios.setVisible(false);
            menu.pnlComprasGastos.setVisible(false);
            menu.pnlNotificaciones.setVisible(true);
        }
        if (e.getSource() == menu.btnCerrarSesion) {
            menu.btnCerrarSesion.setBackground(new java.awt.Color(239, 244, 245));
            menu.lblMenuCerrarSesion.setForeground(new java.awt.Color(0, 222, 171));

            menu.btnReportes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuReportes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnVentas.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuVentas.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnInventario.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuInventario.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnCreditos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuCreditos.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnClientes.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuClientes.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnUsuarios.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuUsuarios.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnNotificaciones.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 255, 255));

            menu.btnComprasGastos.setBackground(new java.awt.Color(64, 64, 64));
            menu.lblGastosMenu.setForeground(new java.awt.Color(255, 255, 255));

            ILogin login = new ILogin();
            menu.dispose();
            login.setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void Notificacion() {
        ArrayList lista = new ArrayList();//instancia de un nuevo array list
        DefaultListModel modeloLista = new DefaultListModel();//modelo para el Jlist
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//formateador de fecha
        Date fechaFinal, fechaInicio = this.fecha;//fecha de venvimiento de producto y fecha actual del sistema
        this.modelo = (DefaultTableModel) menu.tblProductos.getModel();
        int filas = this.modelo.getRowCount();//cuenta las filas de la tabla productos
        for (int i = 0; i < filas; i++)//recorro la tabla productos
        {
            try {
                fechaFinal = sdf.parse(this.modelo.getValueAt(i, 5).toString());//obtengo la fecha del producto de la tabla producto lo paso a formato Date
                int dias = (int) ((fechaFinal.getTime() - fechaInicio.getTime()) / 86400000);//calculo de diferencias de dias "conversion de milesegundos a dias"
                String nombre = (String) this.modelo.getValueAt(i, 2);//obtengo el nombre del producto de la tabla producto
                if (dias == 60)//
                {
                    menu.lblMenuNotificacion.setForeground(new java.awt.Color(255, 7, 5, 255));//cambio de color si hay productos cerca de vencer
                    lista.add("Quedan " + dias + " Dias para que el Producto " + nombre + " Caduque");//agrego un elemeto a lista
                    modeloLista.removeAllElements();//limpio el JList para que no repita los datos a mostrar
                    for (int l = 0; l < lista.size(); l++)//recorro la lista para ingresarla al modelo de Jlist
                    {
                        modeloLista.addElement(lista.get(l)); //add elemento a modeloLista   
                    }

                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err+"notificacines");
            }

        }
        menu.jlListaNotificaciones.setModel(modeloLista);//establesco el modeloLista  Jlist para mostrar las notificaciones
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.btnVerificarVencimientos) {
            Notificacion();
        }
    }
}
