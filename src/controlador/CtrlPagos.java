/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlPagos implements ActionListener, CaretListener {

    IMenu menu;
    PagosCreditos pagos;
    CtrlReportes ctrlR;
    CtrlCreditos ctrlC;
    Creditos creditos;
    Reportes reportes;
    DefaultTableModel modelo;
    String id;
    Date fecha;

    public CtrlPagos(IMenu menu, PagosCreditos pagos) {
        this.menu = menu;
        this.pagos = pagos;
        this.reportes = new Reportes();
        this.creditos = new Creditos();
        this.ctrlR = new CtrlReportes(menu, reportes);
        this.ctrlC = new CtrlCreditos(menu, creditos);
        this.modelo = new DefaultTableModel();
        this.fecha = new Date();
        this.menu.btnGuardarPago.addActionListener(this);
        this.menu.btnActualizarPago.addActionListener(this);
        this.menu.btnNuevoPago.addActionListener(this);
        this.menu.EditarPago.addActionListener(this);
        this.menu.BorrarPago.addActionListener(this);
        this.menu.txtMontoPago.addCaretListener(this);
        this.menu.txtBuscarPago.addCaretListener(this);
        this.menu.btnMostrarPagosRegistrados.addActionListener(this);
        MostrarPagos("");
        DeshabilitarPagos();
        this.menu.jcFechaPago.setDate(fecha);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.btnGuardarPago) {
            int c;
            float montoPago;
            String credito = menu.txtCreditoPago.getText(), monto = menu.txtMontoPago.getText();
            Date f = menu.jcFechaPago.getDate();
            long fecha = f.getTime();
            java.sql.Date fechaPago = new java.sql.Date(fecha);
            if (!credito.equals("") && !monto.equals("")) {
                if (isNumeric(credito) && isNumeric(monto)) {
                    c = Integer.parseInt(credito);
                    montoPago = Float.parseFloat(monto);
                    pagos.Guardar(c, montoPago, fechaPago);
                    MostrarPagos("");
                    LimpiarPago();
                    ctrlC.MostrarCreditos("");
                    ctrlC.ActualizarEstadoCredito2();
                    ctrlC.ActualizarEstadoCredito();
                    ctrlC.MostrarCreditos("");
                    ctrlR.MostrarFiltroReporte(this.fecha, this.fecha);
                    ctrlR.SumaTotalFiltroReporte();
                    MostrarPagos("");
                    ctrlC.MostrarCreditosCreados("");
                    ctrlC.MostrarCreditosAddFactura("");
                    menu.btnGuardarPago.setEnabled(true);
                    menu.btnActualizarPago.setEnabled(false);
                }
            } else {

            }
        }
        if (e.getSource() == menu.btnActualizarPago) {
            int c;
            float montoPago;
            String credito = menu.txtCreditoPago.getText(), monto = menu.txtMontoPago.getText();
            Date f = menu.jcFechaPago.getDate();
            long fecha = f.getTime();
            java.sql.Date fechaPago = new java.sql.Date(fecha);
            if (!credito.equals("") && !monto.equals("")) {
                if (isNumeric(credito) && isNumeric(monto)) {
                    c = Integer.parseInt(credito);
                    montoPago = Float.parseFloat(monto);
                    pagos.Actualizar(this.id, c, montoPago, fechaPago);
                    MostrarPagos("");
                    ctrlC.MostrarCreditos("");
                    ctrlC.ActualizarEstadoCredito2();
                    ctrlC.ActualizarEstadoCredito();
                    LimpiarPago();
                    ctrlR.MostrarFiltroReporte(this.fecha, this.fecha);
                    ctrlR.SumaTotalFiltroReporte();
                    ctrlC.MostrarCreditosCreados("");
                    menu.btnGuardarPago.setEnabled(true);
                    menu.btnActualizarPago.setEnabled(false);
                }
            } else {

            }
        }
        if (e.getSource() == menu.btnNuevoPago) {
            HabilitarPago();
            LimpiarPago();
            menu.txtMontoPago.requestFocus();
        }
        if (e.getSource() == menu.EditarPago) {
            int filaseleccionada = menu.tblPagos.getSelectedRow();
            String id, monto, credito, f;
            Date fecha;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (filaseleccionada == -1) {

                } else {
                    this.modelo = (DefaultTableModel) menu.tblPagos.getModel();
                    id = (String) this.modelo.getValueAt(filaseleccionada, 0);
                    monto = (String) this.modelo.getValueAt(filaseleccionada, 1);
                    credito = (String) this.modelo.getValueAt(filaseleccionada, 2);
                    fecha = sdf.parse(this.modelo.getValueAt(filaseleccionada, 3).toString());
                    this.id = id;

                    HabilitarPago();
                    menu.txtMontoPago.setText(monto);
                    menu.txtCreditoPago.setText(credito);
                    menu.jcFechaPago.setDate(fecha);
                    menu.btnGuardarPago.setEnabled(false);
                    menu.btnActualizarPago.setEnabled(true);
                }
            } catch (Exception err) {
            }
        }
        if (e.getSource() == menu.BorrarPago) {
            int filaseleccionada = menu.tblPagos.getSelectedRow(), id = 0;
            try {
                if (filaseleccionada == -1) {

                } else {
                    this.modelo = (DefaultTableModel) menu.tblPagos.getModel();
                    id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
                    this.pagos.Eliminar(id);
                    MostrarPagos("");
                    this.ctrlC.MostrarCreditos("");
                    this.ctrlR.SumaTotalFiltroReporte();
                    ctrlR.MostrarFiltroReporte(this.fecha, this.fecha);
                    ctrlC.ActualizarEstadoCredito2();
                    ctrlC.ActualizarEstadoCredito();
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, e + " en la funcion Borrar Pago", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (e.getSource() == menu.btnMostrarPagosRegistrados) {
            menu.pagosAcreditos.setSize(648, 400);
            menu.pagosAcreditos.setVisible(true);
            menu.pagosAcreditos.setLocationRelativeTo(null);
        }
    }

    public void MostrarPagos(String buscar) {
        menu.jcFechaPago.setDate(this.fecha);
        menu.tblPagos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
        menu.tblPagos.getTableHeader().setOpaque(false);
        menu.tblPagos.getTableHeader().setBackground(new Color(100, 100, 100));
        menu.tblPagos.getTableHeader().setForeground(new Color(255, 255, 255));
        menu.tblPagos.setModel(this.pagos.Mostrar(buscar));
    }

    public boolean isNumeric(String cadena) {//metodo para la validacion de campos numericos
        try {
            Float.parseFloat(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //metodo para limpiar el formulario Pagos
    public void LimpiarPago() {
        menu.txtMontoPago.setText("");
    }

    public void HabilitarPago() {
        menu.btnActualizarPago.setEnabled(false);
        menu.btnGuardarPago.setEnabled(true);
    }
    //funcion para deshabilitar componentes de el formulario de pago

    public void DeshabilitarPagos() {
        menu.btnActualizarPago.setEnabled(false);
        menu.btnGuardarPago.setEnabled(false);
    }//lbls para hacer visible el pago en la ventana pago

    public void pago(String valor) {
        menu.lblPago.setText(valor);
        if (!valor.equals("")) {
            float credito = Float.parseFloat(menu.lblCredito.getText()), pago = Float.parseFloat(menu.lblPago.getText()), total = 0;
            total = credito - pago;
            menu.lblSaldo.setText("" + total);
        } else {

        }
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.txtMontoPago) {
            pago(menu.txtMontoPago.getText());
        }
        if (e.getSource() == menu.txtBuscarPago) {
            String valor = menu.txtBuscarPago.getText();
            MostrarPagos(valor);
        }
    }
}
