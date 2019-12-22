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
public class CtrlReportes implements ActionListener, MouseListener{
    IMenu menu;
    Reportes reportes;
    Date fecha;
    DefaultTableModel modelo;
    public CtrlReportes(IMenu menu, Reportes reportes)
    {
        this.menu = menu;
        this.reportes = reportes;
        this.fecha = new Date();
        this.menu.btnReporteMensaul.addActionListener(this);
        this.menu.tblReporte.addMouseListener(this);
        iniciar();
    }
    public void iniciar()
    {
        MostrarFiltroReporte(fecha, fecha);
        this.menu.jcFecha1.setDate(fecha);
        this.menu.jcFecha2.setDate(fecha);
        SumaTotalFiltroReporte();
    }
     @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(e.getSource() == menu.btnReporteMensaul)
        {
            Date fecha1 = menu.jcFecha1.getDate(), fecha2 = menu.jcFecha2.getDate();
            MostrarFiltroReporte(fecha1,fecha2);
            SumaTotalFiltroReporte();
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       if(e.getSource() == menu.tblReporte)
       {
            if(e.getClickCount() == 2)
       {
           int id = 0, filaseleccionada = menu.tblReporte.getSelectedRow();
           try {
            if(filaseleccionada == -1)
           {
               
           }else
           {
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
    
    public void SumaTotalFiltroReporte()
    {
        float totalFiltroReporte = 0, creditoMensual = 0, totalCaja = 0, totalGastos = 0, totalExist = 0, totalPagos = 0;
        this.modelo = (DefaultTableModel) menu.tblReporte.getModel();
        int filas = this.modelo.getRowCount();
        for(int cont = 0;cont<filas;cont++)
        {
            totalFiltroReporte += Float.parseFloat(this.modelo.getValueAt(cont, 3).toString());
        }
        menu.lblTotalFiltroReporte.setText(String.valueOf(totalFiltroReporte));
        creditoMensual = Float.parseFloat(menu.lblTotalCreditosFiltroReporte.getText());
        totalGastos = Float.parseFloat(menu.lblGastos.getText());
        totalPagos = Float.parseFloat(menu.lblTotalPagos.getText());
        totalCaja = totalPagos + (totalFiltroReporte - creditoMensual);
        totalExist = totalFiltroReporte - creditoMensual - totalGastos;
        menu.lblTotalCajaFiltroReporte.setText(""+totalCaja);
        menu.lblTotalExistencia.setText(""+totalExist);
    }
    public void MostrarFiltroReporte(Date fecha1, Date fecha2)//metodo para llenar la tabla de reortes por rango o mensual del menu reportes
    {
        long f1 = fecha1.getTime(), f2 = fecha2.getTime();//
        java.sql.Date fechaInicio = new java.sql.Date(f1);//convertir la fecha a formato sql
        java.sql.Date fechaFinal = new java.sql.Date(f2);//convertir la fecha a formato sql
        String totalCreditoMensual = reportes.TotalCreditosMensual(fechaInicio, fechaFinal);//obtengo el valor de total de creditos cn la funcion TotalCreditoMensula de la clase reortes
        String totalGastos = reportes.TotalGastos(fechaInicio, fechaFinal);//total de gastos 
        String totalPagos = reportes.totalPagos(fechaInicio, fechaFinal);//total pagos 
        float creditosPendiente = Float.parseFloat(totalCreditoMensual)-Float.parseFloat(totalPagos);//refleja el creditos menos los pagos
        menu.tblReporte.getTableHeader().setFont(new Font("Sugoe UI",Font.PLAIN, 14));
        menu.tblReporte.getTableHeader().setOpaque(false);
        menu.tblReporte.getTableHeader().setBackground(new Color(100,100,100));
        menu.tblReporte.getTableHeader().setForeground(new Color(255,255,255));
        try {
            menu.tblReporte.setModel(reportes.ReporteMensual(fechaInicio, fechaFinal));
            menu.lblTotalCreditosFiltroReporte.setText(totalCreditoMensual);//lleno el lblTotalCreditoFiltroRepote con el  total creditos 
            menu.lblGastos.setText(totalGastos);//lleno lblGastos con el total de gastos
            menu.lblTotalPagos.setText(totalPagos);
        } catch (Exception err) {
            
        }
        
    }
    public void MostrarDetalleFactura(int id)//metodo para llenar la tabla que muestra el detalle de las facturas de reportes
    {
        menu.tblMostrarDetalleFactura.getTableHeader().setFont(new Font("Sugoe UI",Font.PLAIN, 14));
        menu.tblMostrarDetalleFactura.getTableHeader().setOpaque(false);
        menu.tblMostrarDetalleFactura.getTableHeader().setBackground(new Color(100,100,100));
        menu.tblMostrarDetalleFactura.getTableHeader().setForeground(new Color(255,255,255));
        menu.tblMostrarDetalleFactura.setModel(reportes.DetalleFactura(id));
    }

   
}
