/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/*
 * Ticket.java
 * 
 * Copyright 2013 Josue Camara <picharras@picharras-HP-Folio>
 * 
 * Este programa es software libre; puede redistribuirlo y/o modificarlo
 * bajo los términos de la Licencia Pública General GNU publicada por
 * la Free Software Foundation; versión 2 de la Licencia, o
 * (a su elección) cualquier versión posterior.
 * 
 * Este programa se distribuye con la esperanza de que sea útil,
 * pero SIN NINGUNA GARANTIA; sin siquiera la garantía implícita de
 * COMERCIABILIDAD O IDONEIDAD PARA UN FIN PARTICULAR. Ver el
 * Licencia Pública General GNU para más detalles.
 * 
 * Debería haber recibido una copia de la Licencia Pública General GNU
 * junto con este programa; si no, escriba al Software Libre
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, ESTADOS UNIDOS.
 * 
 */

import java.awt.*;
import java.awt.print.*;
import javax.swing.JOptionPane;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.Doc;
import javax.print.ServiceUI;
import javax.print.attribute.*;

public class Ticket {
  
  //Ticket attribute content
  private String contentTicket = ""+
    "          {{nameLocal}}\n"+
    "           Dirección\n"+
    "{{direccion}}\n"+
    "=================================N \n"+
    "MERIDA, XXXXXXXXXXXX\n"+
    "RFC: XXX-020226-XX9\n"+
    "Caja # {{box}} - Ticket # {{ticket}}\n"+
    "ATENDIO: {{Cajero}} \n"+
    "Cliente: {{cliente}} \n"+
    "Fecha:   {{dateTime}} \n"+
          "\n"+
    "DESCRIP   CANT.  PRECIO  IMPORTE\n"+
    "=================================N \n"+
    "{{items}} \n"+
    "=================================N \n"+
    "SUBTOTAL: {{subTotal}}\n"+
    "IVA: {{iva}}\n"+
    "TOTAL:{{total}} \n"+
    "RECIBIDO: {{recibo}}\n"+
    "CAMBIO: {{change}}\n"+
    "=================================N \n"+
    "GRACIAS POR SU COMPRA..\n"+
    "ESPERAMOS SU VISITA NUEVAMENTE.\n"+
    "N"+
    "N";
    
  //El constructor que setea los valores a la instancia
  Ticket(String nameLocal, String direccion, String box, String ticket, String caissier, String cliente, String dateTime, String[] items, String subTotal, String iva, String total, String recibo, String change) {
    StringBuffer a = new StringBuffer("");
    this.contentTicket = this.contentTicket.replace("{{nameLocal}}", nameLocal);
    this.contentTicket = this.contentTicket.replace("{{direccion}}", direccion);
    this.contentTicket = this.contentTicket.replace("{{box}}", box);
    this.contentTicket = this.contentTicket.replace("{{ticket}}", ticket);
    this.contentTicket = this.contentTicket.replace("{{cajero}}", caissier);
    this.contentTicket = this.contentTicket.replace("{{cliente}}", cliente);
    this.contentTicket = this.contentTicket.replace("{{dateTime}}", dateTime);
    //recorro el array de Productos ITEMS
    for(int i=0;i<items.length;i++)
    {
        a.append(items[i]);
    }
    this.contentTicket = this.contentTicket.replace("{{items}}",a);
    this.contentTicket = this.contentTicket.replace("{{subTotal}}", subTotal);
    this.contentTicket = this.contentTicket.replace("{{iva}}", iva);
    this.contentTicket = this.contentTicket.replace("{{total}}", total);
    this.contentTicket = this.contentTicket.replace("{{recibo}}", recibo);
    this.contentTicket = this.contentTicket.replace("{{change}}", change);
  }
    
  public void print() {
      System.out.println(this.contentTicket);
    //Especificamos el tipo de dato a imprimir
    //Tipo: bytes; Subtipo: autodetectado
    DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
    
    //Aca obtenemos el servicio de impresion por defatul
    //Si no quieres ver el dialogo de seleccionar impresora usa esto
    //PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
    
    
    //Con esto mostramos el dialogo para seleccionar impresora
    //Si quieres ver el dialogo de seleccionar impresora usalo
    //Solo mostrara las impresoras que soporte arreglo de bits
    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
    PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
    PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
    PrintService service = ServiceUI.printDialog(null, 700, 200, printService, defaultService, flavor, pras);
      
    //Creamos un arreglo de tipo byte
    byte[] bytes;

    //Aca convertimos el string(cuerpo del ticket) a bytes tal como
    //lo maneja la impresora(mas bien ticketera :p)
    bytes = this.contentTicket.getBytes();

    //Creamos un documento a imprimir, a el se le appendeara
    //el arreglo de bytes
    Doc doc = new SimpleDoc(bytes,flavor,null);
      
    //Creamos un trabajo de impresión
    DocPrintJob job = service.createPrintJob();

    //Imprimimos dentro de un try de a huevo
    try {
      //El metodo print imprime
      job.print(doc, null);
    } catch (Exception er) {
      JOptionPane.showMessageDialog(null,"Error al imprimir: " + er.getMessage());
    }
  }

}

