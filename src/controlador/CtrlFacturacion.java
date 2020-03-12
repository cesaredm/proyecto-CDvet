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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CtrlFacturacion implements ActionListener, CaretListener, MouseListener {

    IMenu menu;
    Facturacion factura;
    Date fecha;
    DefaultTableModel modelo;
    CtrlProducto productos;
    CtrlReportes reportes;
    Productos modelProduct;
    Reportes r;
    static float total;
    float subTotal, isv, descuento;
    String[] nD;

    public CtrlFacturacion(IMenu menu, Facturacion factura) {
        this.fecha = new Date();
        this.total = 0;
        this.isv = 0;
        this.subTotal = 0;
        this.descuento = 0;
        this.menu = menu;
        this.factura = factura;
        this.menu.cmbFormaPago.setModel(factura.FormasPago());
        this.r = new Reportes();
        this.modelo = new DefaultTableModel();
        this.reportes = new CtrlReportes(menu, r);
        this.modelProduct = new Productos();
        this.productos = new CtrlProducto(modelProduct, menu);
        menu.btnRetornar.setVisible(false);
        menu.btnActualizarFactura.setVisible(false);
        this.menu.btnGuardarFactura.addActionListener(this);
        this.menu.btnActualizarFactura.addActionListener(this);
        this.menu.btnEliminarFilaFactura.addActionListener(this);
        this.menu.btnNuevaFactura.addActionListener(this);
        this.menu.tblAddCreditoFactura.addMouseListener(this);
        this.menu.tblAddProductoFactura.addMouseListener(this);
        this.menu.btnEditarImpuesto.addActionListener(this);
        this.menu.btnAgregarProductoFactura.addActionListener(this);
        this.menu.btnEditarFactura.addActionListener(this);
        this.menu.btnRetornar.addActionListener(this);
        this.menu.Descuento.addActionListener(this);
        EstiloTablaFacturacion();
        editarISV("");
        DeshabilitarBtnGuardarFactura();
        this.menu.jcFechaFactura.setDate(fecha);
        menu.txtNumeroFactura.setText(factura.ObtenerIdFactura());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.btnGuardarFactura) {
            try {
                this.modelo = (DefaultTableModel) menu.tblFactura.getModel();//obtengo el modelo de tabla factura y sus datos
                int filas = this.modelo.getRowCount();//Cuento las filas de la tabla Factura
                Date fecha;
                String factura, id, cantidad, precio, totalDetalle, iva, totalFactura, formaPago, idFormaPago, comprador;//variables para capturar los datos a guardar
                comprador = menu.txtCompradorFactura.getText();
                fecha = menu.jcFechaFactura.getDate();//capturo la fecha del dateshooser
                long fechaF = fecha.getTime();//
                java.sql.Date fechaFactura = new java.sql.Date(fechaF);//convertir la fecha obtenida a formato sql
                iva = menu.txtImpuesto.getText();//obtengo el iva
                totalFactura = menu.txtTotal.getText();//obtengo total de factura
                formaPago = (String) menu.cmbFormaPago.getSelectedItem();//capturo el nombre de forma de pago 
                idFormaPago = this.factura.ObtenerFormaPago(formaPago);//capturo el id de la forma de pago que retorna la funcion obtenerformapago de la clase facturacion
                this.factura.GuardarFactura(fechaFactura, comprador, idFormaPago, iva, totalFactura);//envio los datos a guardar de la factura
                for (int cont = 0; cont < filas; cont++)//for para recorrer la tabla factura
                {
                    id = (String) this.modelo.getValueAt(cont, 0);//capturo el id de producto para guardar en detallefactura
                    cantidad = (String) this.modelo.getValueAt(cont, 2);//capturo la cantidad de producto de la columna dos y la paso a String para guardar en detallefactura
                    precio = (String) this.modelo.getValueAt(cont, 4);//capturo el precio de producto para guardar en detallefactura
                    totalDetalle = (String) this.modelo.getValueAt(cont, 5);//capturo el total de detalle compra de producto para guardar en detallefactura
                    factura = menu.txtNumeroFactura.getText();//capturo id de factura ala que pertenece el detalle de factura
                    this.factura.DetalleFactura(factura, id, precio, cantidad, totalDetalle);//envio los datos a guardar de los detalles
                    this.factura.Vender(id, cantidad);//funcion para diminuir el stock segun la cantidad que se venda
                }
                menu.txtNumeroFactura.setText(this.factura.ObtenerIdFactura());//Actualizo el campo numero de factura con la funcion obtenerIdFactura
                LimpiarTablaFactura();//limpio la factura
                DeshabilitarBtnGuardarFactura();
                productos.MostrarProductos("");
                productos.MostrarProductosVender("");
                reportes.MostrarFiltroReporte(this.fecha, this.fecha);
                reportes.SumaTotalFiltroReporte();
                reportes.inversion();
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        if (e.getSource() == menu.btnActualizarFactura) {
            try {
                this.modelo = (DefaultTableModel) menu.tblFactura.getModel();//obtengo el modelo de tabla factura y sus datos
                int filas = this.modelo.getRowCount();//Cuento las filas de la tabla Factura
                if (filas == nD.length)//nD quiere decir numero de detalles condicion para guardar solo los cambios de las filas de la factura actual no se pueda agregar mas productos ni quitar solo cambiar ya que solo es edicio de la facura
                {
                    Date fecha;
                    String factura, id, cantidad, precio, totalDetalle, idCredito, iva, totalFactura, formaPago, idFormaPago, nombreComprador;//variables para capturar los datos a guardar
                    fecha = menu.jcFechaFactura.getDate();//capturo la fecha del dateshooser
                    long fechaF = fecha.getTime();//
                    java.sql.Date fechaFactura = new java.sql.Date(fechaF);//convertir la fecha obtenida a formato sql
                    nombreComprador = menu.txtCompradorFactura.getText();
                    iva = menu.txtImpuesto.getText();//obtengo el iva
                    totalFactura = menu.txtTotal.getText();//obtengo total de factura
                    formaPago = (String) menu.cmbFormaPago.getSelectedItem();//capturo el nombre de forma de pago 
                    idFormaPago = this.factura.ObtenerFormaPago(formaPago);//capturo el id de la forma de pago que retorna la funcion obtenerformapago de la clase facturacion
                    factura = menu.txtNumeroFactura.getText();//capturo id de factura ala que pertenece el detalle de factura
                    this.factura.ActualizarFactura(factura, fechaFactura, nombreComprador, idFormaPago, iva, totalFactura);//envio los datos a actualizar de la factura
                    for (int cont = 0; cont < filas; cont++)//for para recorrer la tabla factura
                    {
                        id = (String) this.modelo.getValueAt(cont, 0);//capturo el id de producto para guardar en detallefactura
                        cantidad = (String) this.modelo.getValueAt(cont, 2);//capturo la cantidad de producto de la columna dos y la paso a String para guardar en detallefactura
                        precio = (String) this.modelo.getValueAt(cont, 4);//capturo el precio de producto para guardar en detallefactura
                        totalDetalle = (String) this.modelo.getValueAt(cont, 5);//capturo el total de detalle compra de producto para guardar en detallefactura
                        this.factura.ActualizarDetalle(nD[cont], id, precio, cantidad, totalDetalle);//envio los datos para actualizar los detalles de la factura
                        this.factura.Vender(id, cantidad);//funcion para diminuir el stock segun la cantidad que se venda
                    }
                    menu.txtNumeroFactura.setText(this.factura.ObtenerIdFactura());//Actualizo el campo numero de factura con la funcion obtenerIdFactura
                    LimpiarTablaFactura();//limpio la factura
                    DeshabilitarBtnGuardarFactura();//deshabilito el boton guadar factura
                    menu.pnlVentas.setVisible(false);
                    menu.pnlReportes.setVisible(true);
                    menu.btnRetornar.setVisible(false);
                    menu.btnActualizarFactura.setVisible(false);
                    productos.MostrarProductos("");//actualizar la tabla de productos inventario
                    productos.MostrarProductosVender("");//actualizar la tabla de productos a vender
                    reportes.SumaTotalFiltroReporte();
                    reportes.MostrarFiltroReporte(this.fecha, this.fecha);
                } else {
                    JOptionPane.showMessageDialog(null, "la factura depende de " + nD.length + " filas");
                }

            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        if (e.getSource() == menu.btnEditarFactura) {
            String nombre = "", apellido = "";
            //obtengo la fila seleccionda de la tabla reporte diario    obtengo el numero las filas de la tabla detalleFactura
            int filaseleccionada = menu.tblReporte.getSelectedRow(), filas = menu.tblMostrarDetalleFactura.getRowCount();
            //la variable modelo va a tomar el modelo de la tabla factura
            this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
            //variables para obtener los valores que se ocupan para la actualizacion
            String idFactura = "", idP = "", codBarra = "", nombreP = "", precioP = "", cantidadP = "", importe = "", pago = "", detalle = "", comprador = "", fecha = "", credito = "";
            //convertir el formato sql a Date con simpleDateFormat
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            //nD quiere decir numero de detalles es la variable que guarda el numero de detalles que van en la factura a editar
            this.nD = new String[filas];
            //variables float para hacer la operaciones 
            float totalFactura, iva, subTotal;
            try {
                //idFactura obtiene el id de factura de la tabla reporte diario
                idFactura = menu.tblReporte.getValueAt(filaseleccionada, 0).toString();
                //obtengo la fecha de la factura
                fecha = menu.tblReporte.getValueAt(filaseleccionada, 1).toString();
                //obtengo el nombre comprador
                comprador = menu.tblReporte.getValueAt(filaseleccionada, 4).toString();
                //obtengo el impuesto
                iva = Float.parseFloat(menu.tblReporte.getValueAt(filaseleccionada, 2).toString());
                //obtengo el total de factura
                totalFactura = Float.parseFloat(menu.tblReporte.getValueAt(filaseleccionada, 3).toString());
                //obtengo el credito
                credito = (String) menu.tblReporte.getValueAt(filaseleccionada, 7);
                //obtengo la forma de pago
                pago = menu.tblReporte.getValueAt(filaseleccionada, 5).toString();
                //realiza el calculo para obtener el subtotal
                subTotal = totalFactura - iva;
                //validacion de lo que estoy obteniendo en la variable credito
                if (credito == null) {
                    credito = "";
                }
                //lleno los campos del formulario factura
                menu.jcFechaFactura.setDate(spf.parse(fecha));
                menu.txtSubTotal.setText("" + subTotal);
                menu.txtTotal.setText("" + totalFactura);
                menu.txtImpuesto.setText("" + iva);
                menu.txtNumeroFactura.setText(idFactura);
                menu.txtCompradorFactura.setText(comprador);
                //for para recorrer la tabla detalleFactura
                for (int i = 0; i < filas; i++) {
                    detalle = menu.tblMostrarDetalleFactura.getValueAt(i, 0).toString();//obtengo numero de detalle
                    idP = menu.tblMostrarDetalleFactura.getValueAt(i, 1).toString();//obtengo id de producto
                    codBarra = menu.tblMostrarDetalleFactura.getValueAt(i, 2).toString();//obtengo cod barra del producto
                    nombreP = menu.tblMostrarDetalleFactura.getValueAt(i, 3).toString();//obtengo nombre del producto
                    cantidadP = menu.tblMostrarDetalleFactura.getValueAt(i, 4).toString();//obtengo cantidad de producto vendido en la factura
                    precioP = menu.tblMostrarDetalleFactura.getValueAt(i, 5).toString();//obtengo precio del producto
                    importe = menu.tblMostrarDetalleFactura.getValueAt(i, 6).toString();//obtengo total de venta del producto

                    nD[i] = detalle;//lleno el array con los detalles
                    String[] addFila = {idP, codBarra, cantidadP, nombreP, precioP, importe};//creo el arreglo con los datos obtenidos de la tabla detalle
                    this.modelo.addRow(addFila);//agrego la fila de array creado anteriormente a la tabla factura para la edicion
                }
                //System.out.println(nD[0]+" "+nD[1]);  
                menu.pnlVentas.setVisible(true);//mostrar panel de ventas 
                menu.btnRetornar.setVisible(true);//mostrar boton retornar
                menu.btnActualizarFactura.setVisible(true);//mostrar boton actualizar
                menu.btnGuardarFactura.setEnabled(false);//deshabilitar boton guardarFactura
                menu.pnlReportes.setVisible(false);//ocultar panel Reportes
                menu.vistaDetalleFacturas.setVisible(false);//ocultar la ventana de detalle de factura de reportes
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err + "guardar facturas");
            }
        }
        if (e.getSource() == menu.btnAgregarProductoFactura) {
            menu.AddProductoFactura.setSize(1071, 456);
            menu.AddProductoFactura.setVisible(true);
            menu.AddProductoFactura.setLocationRelativeTo(null);
            if (menu.rbBuscarNombreCodBarra.isSelected() == true) {
                menu.txtBuscarPorNombre.setEnabled(true);
                menu.txtBuscarPorNombre.requestFocus();
                menu.txtBuscarPorNombre.selectAll();
            }
        }
        if (e.getSource() == menu.btnEliminarFilaFactura) {
            int filaseleccionada = menu.tblFactura.getSelectedRow();
            float importe, totalActual;
            String cantidad, id;
            try {
                if (filaseleccionada == -1) {

                } else {
                    this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
                    id = (String) modelo.getValueAt(filaseleccionada, 0);
                    cantidad = (String) modelo.getValueAt(filaseleccionada, 2);
                    importe = Float.parseFloat(modelo.getValueAt(filaseleccionada, 5).toString());
                    totalActual = Float.parseFloat(menu.txtTotal.getText());
                    this.total = totalActual - importe;
                    this.isv = (float) (this.total * Float.parseFloat(menu.lblImpuestoISV.getText())) / 100;
                    this.subTotal = this.total - isv;
                    menu.txtTotal.setText("" + this.total);
                    menu.txtSubTotal.setText("" + this.subTotal);
                    menu.txtImpuesto.setText("" + this.isv);
                    modelProduct.AgregarProductoStock(id, cantidad);
                    productos.MostrarProductosVender("");
                    this.modelo.removeRow(filaseleccionada);
                    DeshabilitarBtnGuardarFactura();
                }
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        if (e.getSource() == menu.btnNuevaFactura) {
            LimpiarTablaFactura();
            DeshabilitarBtnGuardarFactura();
        }
        if (e.getSource() == menu.btnEditarImpuesto) {
            String isv = JOptionPane.showInputDialog("ISV:");
            menu.lblImpuestoISV.setText(isv);
        }
        if (e.getSource() == menu.Descuento) {
            int filaseleccionada = menu.tblAddProductoFactura.getSelectedRow();
            if (filaseleccionada == -1) {

            } else {
                this.descuento = Float.parseFloat(JOptionPane.showInputDialog(null, "Descuento:", "", JOptionPane.QUESTION_MESSAGE));
            }
        }
        if (e.getSource() == menu.btnRetornar) {
            /*VentanaDevoluciones.setSize(903, 290);
        VentanaDevoluciones.setVisible(true);
        VentanaDevoluciones.setLocationRelativeTo(null);*/
            this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
            menu.pnlVentas.setVisible(false);
            menu.pnlReportes.setVisible(true);
            menu.btnRetornar.setVisible(false);
            menu.btnActualizarFactura.setVisible(false);

            try {
                this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
                int filas = this.modelo.getRowCount();//numero de filas de la tabla factura
                for (int i = 0; i < filas; i++) {
                    this.modelo.removeRow(0);//remover filas de la tabla factura
                }
                productos.MostrarProductosVender("");//acturalizar tabla que muestra productos a vender
                //limpiar
                menu.btnGuardarFactura.setEnabled(true);
                menu.txtCompradorFactura.setText("");
                this.total = 0;
                this.subTotal = 0;
                this.isv = 0;
                //inicializar a 0.0
                menu.txtSubTotal.setText("" + this.total);
                menu.txtImpuesto.setText("" + this.subTotal);
                menu.txtTotal.setText("" + this.isv);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err);
            }
            menu.txtNumeroFactura.setText(factura.ObtenerIdFactura());//actualizar numero de factura
        }
        
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.tblAddProductoFactura) {
            if (e.getClickCount() == 2) {
                int filaseleccionada = menu.tblAddProductoFactura.getSelectedRow();
                try {
                    String id, codigo, nombre, precio, cantidad, total, importe, stockA;
                    float imp, calcula, impuesto, descProduct = 0, stock, cantidadPVender;//"descProduct = descuento de producto"

                    if (filaseleccionada == -1) {

                    } else {

                        if (this.descuento == 0) {   //no se aplica descuento
                            //capturar los datos de la tabla producto para mandarlos a tabla factura
                            this.modelo = (DefaultTableModel) menu.tblAddProductoFactura.getModel();
                            id = modelo.getValueAt(filaseleccionada, 0).toString();
                            codigo = modelo.getValueAt(filaseleccionada, 1).toString();
                            nombre = modelo.getValueAt(filaseleccionada, 2).toString();
                            precio = modelo.getValueAt(filaseleccionada, 3).toString();
                            stock = Float.parseFloat(modelo.getValueAt(filaseleccionada, 5).toString());
                            cantidad = JOptionPane.showInputDialog(null, "Cantidad:");
                            //si cantidad no recibe la cantidad se le va asignar 0
                            if (cantidad.equals("")) {
                                cantidad = "0";
                            }
                            //convertir a flota la variable cantidad
                            cantidadPVender = Float.parseFloat(cantidad);
                            //validacion para la venta sugun lo que hay en stock osea no se pueda vender mas de lo que hay en stock
                            if (cantidadPVender < stock || cantidadPVender == stock) {
                                //si cantidadPVender es igual a 0 no realizar ninguna accion
                                if (cantidadPVender == 0) {

                                } else {
                                    imp = (Float.parseFloat(precio) * cantidadPVender);//importe total de compra de producto
                                    importe = String.valueOf(imp);
                                    //realizando los calculos de importe
                                    this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
                                    //pasar producto de tabla productos a tabla de factura
                                    String[] FilaElementos = {id, codigo, cantidad, nombre, precio, importe};
                                    this.modelo.addRow(FilaElementos);
                                    calcula = (Float.parseFloat(importe));//convertir importe a float
                                    this.total = this.total + calcula;//calcular el total de factura
                                    impuesto = (float) (this.total * Float.parseFloat(menu.lblImpuestoISV.getText())) / 100;//calcular el impuesto
                                    this.isv = impuesto;//impuesto
                                    this.subTotal = this.total - this.isv;//clacular subtotal de factura

                                    menu.txtImpuesto.setText("" + this.isv);//establecer el valor impuesto en el campo impuesto de factura
                                    menu.txtSubTotal.setText("" + this.subTotal);//establecer el valor impuesto en el campo sub total de factura
                                    menu.txtTotal.setText("" + this.total);//establecer el valor impuesto en el campo Total de factura
                                    this.factura.Vender(id, cantidad);//llamar procedimiento sql para vender
                                    productos.MostrarProductosVender("");
                                    //productos.MostrarProductosVender("");
                                    menu.txtBuscarPorNombre.selectAll();
                                    DeshabilitarBtnGuardarFactura();
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "No hay Suficiente Producto en Stock para Realizar Esta Venta", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }

                        } else if (this.descuento > 0) {   //aplicar descuento
                            //capturar los datos de la tabla producto para mandarlos a tabla factura
                            this.modelo = (DefaultTableModel) menu.tblAddProductoFactura.getModel();
                            id = modelo.getValueAt(filaseleccionada, 0).toString();
                            codigo = modelo.getValueAt(filaseleccionada, 1).toString();
                            nombre = modelo.getValueAt(filaseleccionada, 2).toString();
                            precio = modelo.getValueAt(filaseleccionada, 3).toString();
                            stock = Float.parseFloat(modelo.getValueAt(filaseleccionada, 5).toString());
                            //obtengo el precio ya descuento 
                            descProduct = Float.parseFloat(precio) - this.descuento;
                            //la cantidad que se va a vender
                            cantidad = JOptionPane.showInputDialog(null, "Cantidad:");
                            if (cantidad.equals("")) {
                                cantidad = "0";
                            }
                            //convertir a float la variable cantidad
                            cantidadPVender = Float.parseFloat(cantidad);
                            //condicion para validar el estock con lo que se va a vender 
                            if (cantidadPVender < stock || cantidadPVender == stock) {
                                if (cantidadPVender == 0) {

                                } else {
                                    imp = (descProduct * cantidadPVender);//importe total de compra de producto con descuento
                                    importe = String.valueOf(imp);
                                    //realizando los calculos de importe
                                    this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
                                    //pasar producto de tabla productos a tabla de factura
                                    String[] FilaElementos = {id, codigo, cantidad, nombre, String.valueOf(descProduct), importe};
                                    this.modelo.addRow(FilaElementos);
                                    calcula = (Float.parseFloat(importe));//convertir importe a float
                                    this.total = this.total + calcula;//calcular el total de factura
                                    impuesto = (float) (this.total * Float.parseFloat(menu.lblImpuestoISV.getText())) / 100;//calcular el impuesto
                                    this.isv = impuesto;//impuesto
                                    this.subTotal = this.total - this.isv;//clacular subtotal de factura

                                    menu.txtImpuesto.setText("" + this.isv);//establecer el valor impuesto en el campo impuesto de factura
                                    menu.txtSubTotal.setText("" + this.subTotal);//establecer el valor impuesto en el campo sub total de factura
                                    menu.txtTotal.setText("" + this.total);//establecer el valor impuesto en el campo Total de factura
                                    this.descuento = 0;
                                    this.factura.Vender(id, cantidad);
                                    productos.MostrarProductosVender("");
                                    //productos.MostrarProductosVender("");
                                    menu.txtBuscarPorNombre.selectAll();
                                    DeshabilitarBtnGuardarFactura();
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "No hay Suficiente Producto en Stock para Realizar Esta Venta", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                this.descuento = 0;//si no hay suficiente producto en stock inicializamos la variable descuento a 0
                            }

                        }

                    }
                } catch (Exception err) {
                    //JOptionPane.showMessageDialog(null, e);
                }
            }

        }
        if (e.getSource() == menu.tblAddCreditoFactura) {
            if (e.getClickCount() == 2) {
                int filaseleccionada = menu.tblAddCreditoFactura.getSelectedRow();
                String nombre, apellido, credito;
                try {
                    if (filaseleccionada == -1) {

                    } else {
                        this.modelo = (DefaultTableModel) menu.tblAddCreditoFactura.getModel();
                        credito = (String) this.modelo.getValueAt(filaseleccionada, 0);
                        nombre = (String) this.modelo.getValueAt(filaseleccionada, 2);
                        apellido = (String) this.modelo.getValueAt(filaseleccionada, 3);
                        menu.AddCreditoFactura.setVisible(false);
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err);
                }
            }
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

    public void DeshabilitarBtnGuardarFactura() {
        if (menu.tblFactura.getRowCount() > 0 && !menu.btnRetornar.isVisible()) {
            menu.btnGuardarFactura.setEnabled(true);
        } else {
            menu.btnGuardarFactura.setEnabled(false);
        }
    }

    public void LimpiarTablaFactura()//metodo para limpiar la factura
    {
        String id, cantidad;
        try {
            this.modelo = (DefaultTableModel) menu.tblFactura.getModel();
            int filas = this.modelo.getRowCount();
            for (int i = 0; i < filas; i++) {
                id = this.modelo.getValueAt(i, 0).toString();
                cantidad = this.modelo.getValueAt(i, 2).toString();
                modelProduct.AgregarProductoStock(id, cantidad);
            }
            for (int i = 0; i < filas; i++) {
                this.modelo.removeRow(0);
            }
            productos.MostrarProductosVender("");
            menu.txtCompradorFactura.setText("");
            this.total = 0;
            this.subTotal = 0;
            this.isv = 0;
            menu.txtSubTotal.setText("" + this.total);
            menu.txtImpuesto.setText("" + this.subTotal);
            menu.txtTotal.setText("" + this.isv);
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, err);
        }

    }

    public void EstiloTablaFacturacion() {
        menu.tblFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
        menu.tblFactura.getTableHeader().setOpaque(false);
        menu.tblFactura.getTableHeader().setBackground(new Color(100, 100, 100));
        menu.tblFactura.getTableHeader().setForeground(new Color(255, 255, 255));
    }

    //metodo para editar el impuesto de la factura
    public void editarISV(String isv) {
        if (isv.equals("")) {
            menu.lblImpuestoISV.setText("15");
        } else {
            menu.lblImpuestoISV.setText(isv);
        }
    }
}
