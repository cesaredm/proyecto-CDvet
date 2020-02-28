package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.IMenu;

public class CtrlLaboratorio implements ActionListener, CaretListener {

    IMenu menu;
    Laboratorios laboratorio;
    String id;
    DefaultTableModel modelo;

    public CtrlLaboratorio(IMenu menu, Laboratorios L) {
        this.menu = menu;
        this.laboratorio = L;
        this.id = null;
        this.modelo = new DefaultTableModel();
        MostrarLaboratorio("");
        DeshabilitarLaboratorio();
        this.menu.btnGuardarLaborotorio.addActionListener(this);
        this.menu.btnActualizarLaboratorio.addActionListener(this);
        this.menu.btnNuevoLaboratorio.addActionListener(this);
        this.menu.EditarLaboratorio.addActionListener(this);
        this.menu.BorrarLaboratorio.addActionListener(this);
        this.menu.txtBuscarLaboratorio.addCaretListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.btnGuardarLaborotorio) {
            String nombre = menu.txtNombreLaboratorio.getText(), descripcion = menu.txtDescripcionLaboratorio.getText();
            if (!nombre.equals("")) {
                laboratorio.Guardar(nombre, descripcion);
                MostrarLaboratorio("");
                LimpiarLaboratorio();
            } else {
                //JOptionPane.showMessageDialog(null, "Llene el campo Nombre", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if(e.getSource() == menu.btnActualizarLaboratorio)
        {
            String nombre = menu.txtNombreLaboratorio.getText(), descripcion = menu.txtDescripcionLaboratorio.getText();
            if (!nombre.equals("")) {
                laboratorio.Actualizar(id, nombre, descripcion);
                MostrarLaboratorio("");
                LimpiarLaboratorio();
                menu.btnActualizarLaboratorio.setEnabled(false);
                menu.btnGuardarLaborotorio.setEnabled(true);
        } else {
            //JOptionPane.showMessageDialog(null, "Llene el campo Nombre", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        }
        }
        if(e.getSource() == menu.btnNuevoLaboratorio)
        {
            HabilitarLaboratorio();
        }
        if(e.getSource() == menu.EditarLaboratorio)
        {
            int filaseleccionada;
        String id, nombre, descripcion;
        try {
            filaseleccionada = menu.tblLaboratorio.getSelectedRow();
            if (filaseleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una Fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                modelo = (DefaultTableModel) menu.tblLaboratorio.getModel();
                id = (String) modelo.getValueAt(filaseleccionada, 0);
                nombre = (String) modelo.getValueAt(filaseleccionada, 1);
                descripcion = (String) modelo.getValueAt(filaseleccionada, 2);
                HabilitarLaboratorio();
                LimpiarLaboratorio();
                menu.txtNombreLaboratorio.setText(nombre);
                menu.txtDescripcionLaboratorio.setText(descripcion);
                this.id = id;
                menu.btnActualizarLaboratorio.setEnabled(true);
                menu.btnGuardarLaborotorio.setEnabled(false);
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err);
        }
        }
        if(e.getSource() == menu.BorrarLaboratorio)
        {
            int filaseleccionada;
        String id;
        try {
            filaseleccionada = menu.tblLaboratorio.getSelectedRow();
            if (filaseleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una Fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                int confirmar = JOptionPane.showConfirmDialog(null, "Seguro que Quieres Borrar Este Laboratorio", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    modelo = (DefaultTableModel) menu.tblLaboratorio.getModel();
                    id = (String) modelo.getValueAt(filaseleccionada, 0);
                    laboratorio.Eliminar(id);
                    MostrarLaboratorio("");
                }
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err);
        }
        }
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        if (e.getSource() == menu.txtBuscarLaboratorio) {
            MostrarLaboratorio(menu.txtBuscarLaboratorio.getText());
        }
    }

    //metodo para llenar la tabla de laboratorios con filtro por Nombre de laboratorio
    public void MostrarLaboratorio(String Buscar) {
        menu.tblLaboratorio.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
        menu.tblLaboratorio.getTableHeader().setOpaque(false);
        menu.tblLaboratorio.getTableHeader().setBackground(new Color(100, 100, 100));
        menu.tblLaboratorio.getTableHeader().setForeground(new Color(255, 255, 255));
        menu.tblLaboratorio.setModel(laboratorio.Consulta(Buscar));
    }

    //metodo para limpiar campos de El formulario Laboratorio
    public void LimpiarLaboratorio() {
        menu.txtNombreLaboratorio.setText("");
        menu.txtDescripcionLaboratorio.setText("");
    }
    //metodo para Habilitar los elementos inabilitados por el metodo DeshabilitarLaboratorio
    public void HabilitarLaboratorio() {
        menu.txtNombreLaboratorio.setEnabled(true);
        menu.txtDescripcionLaboratorio.setEnabled(true);
        menu.btnNuevoLaboratorio.setEnabled(true);
        menu.btnGuardarLaborotorio.setEnabled(true);
        menu.btnActualizarLaboratorio.setEnabled(false);
    }
    //metodo para dehabilitar elementos de formulario Laboratorio
    public void DeshabilitarLaboratorio() {
        menu.txtNombreLaboratorio.setEnabled(false);
        menu.txtDescripcionLaboratorio.setEnabled(false);
        menu.btnNuevoLaboratorio.setEnabled(true);
        menu.btnGuardarLaborotorio.setEnabled(false);
        menu.btnActualizarLaboratorio.setEnabled(false);
    }
}
