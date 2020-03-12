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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Reportes;
import vista.IMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlReportes implements ActionListener, MouseListener {

    IMenu menu;
    Reportes reportes;
    Date fecha;
    DefaultTableModel modelo;
    String idCliente = "";
    private boolean estadoC = true;

    public CtrlReportes(IMenu menu, Reportes reportes) {
        this.menu = menu;
        this.reportes = reportes;
        this.fecha = new Date();
        this.menu.btnReporteMensaul.addActionListener(this);
        this.menu.tblReporte.addMouseListener(this);
        this.menu.btnMostrarInversion.addActionListener(this);
        iniciar();
    }

    public void iniciar() {
        MostrarFiltroReporte(fecha, fecha);
        this.menu.jcFecha1.setDate(fecha);
        this.menu.jcFecha2.setDate(fecha);
        SumaTotalFiltroReporte();
    }

    public boolean getEstadoC() {
        return estadoC;
    }

    public void setEstadoC(boolean estadoC) {
        this.estadoC = estadoC;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.btnReporteMensaul) {
            Date fecha1 = menu.jcFecha1.getDate(), fecha2 = menu.jcFecha2.getDate();
            MostrarFiltroReporte(fecha1, fecha2);
            SumaTotalFiltroReporte();
        }
        if(e.getSource() == menu.btnMostrarInversion)
        {
            inversion();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.tblReporte) {
            if (e.getClickCount() == 2) {
                int id = 0, filaseleccionada = menu.tblReporte.getSelectedRow();
                try {
                    if (filaseleccionada == -1) {

                    } else {
                        this.modelo = (DefaultTableModel) menu.tblReporte.getModel();
                        id = Integer.parseInt(this.modelo.getValueAt(filaseleccionada, 0).toString());
                        MostrarDetalleFactura(id);
                        menu.vistaDetalleFacturas.setSize(746, 306);
                        menu.vistaDetalleFacturas.setVisible(true);
                        menu.vistaDetalleFacturas.setLocationRelativeTo(null);
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //metodo para calcular la inversion aun no esta listo
    public void inversion()
    {
       int filas = menu.tblProductos.getRowCount();
       float inversion = 0, precio = 0, cantidad = 0;
       for(int i = 0;i<filas;i++)
       {
           precio = Float.parseFloat(menu.tblProductos.getValueAt(i, 3).toString());
           cantidad = Float.parseFloat(menu.tblProductos.getValueAt(i, 6).toString());
           inversion += precio*cantidad;
       }
        menu.lblInversion.setText(""+inversion);
    }
    public void SumaTotalFiltroReporte() {
        //variables a 
        float totalFiltroReporte = 0, creditoMensual = 0, totalCaja = 0, totalGastos = 0, totalExist = 0, totalPagos = 0;
        this.modelo = (DefaultTableModel) menu.tblReporte.getModel();
        //fiilas de la tabla reportes
        int filas = this.modelo.getRowCount();
        //for para recorrer la tabla de reportes 
        for (int cont = 0; cont < filas; cont++) {
            //ontengo todos los datos de la columna Total de factura y los sumo para obtener el total vendido
            totalFiltroReporte += Float.parseFloat(this.modelo.getValueAt(cont, 3).toString());
        }
        totalGastos = Float.parseFloat(menu.lblGastos.getText());
        totalExist = (totalFiltroReporte - totalGastos);
        menu.lblTotalCajaFiltroReporte.setText("" + totalFiltroReporte);
        menu.lblTotalExistencia.setText("" + totalExist);
    }

    public void MostrarFiltroReporte(Date fecha1, Date fecha2)//metodo para llenar la tabla de reortes por rango o mensual del menu reportes
    {
        long f1 = fecha1.getTime(), f2 = fecha2.getTime();//
        java.sql.Date fechaInicio = new java.sql.Date(f1);//convertir la fecha a formato sql
        java.sql.Date fechaFinal = new java.sql.Date(f2);//convertir la fecha a formato sql
        String totalGastos = reportes.TotalGastos(fechaInicio, fechaFinal);//total de gastos
        menu.tblReporte.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
        menu.tblReporte.getTableHeader().setOpaque(false);
        menu.tblReporte.getTableHeader().setBackground(new Color(100, 100, 100));
        menu.tblReporte.getTableHeader().setForeground(new Color(255, 255, 255));
        try {
            menu.tblReporte.setModel(reportes.ReporteMensual(fechaInicio, fechaFinal));
            menu.lblGastos.setText(totalGastos);//lleno lblGastos con el total de gastos
        } catch (Exception err) {

        }

    }

    public void MostrarDetalleFactura(int id)//metodo para llenar la tabla que muestra el detalle de las facturas de reportes
    {
        menu.tblMostrarDetalleFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
        menu.tblMostrarDetalleFactura.getTableHeader().setOpaque(false);
        menu.tblMostrarDetalleFactura.getTableHeader().setBackground(new Color(100, 100, 100));
        menu.tblMostrarDetalleFactura.getTableHeader().setForeground(new Color(255, 255, 255));
        menu.tblMostrarDetalleFactura.setModel(reportes.DetalleFactura(id));
    }

}
