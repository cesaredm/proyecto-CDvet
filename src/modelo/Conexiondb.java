/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Conexiondb {

    String db = "CDvet";
    String url = "jdbc:mysql://localhost/" + db;
    String user = "root";
    String pass = "";

    public Conexiondb() {

    }

    public Connection Conexion() {
        Connection link = null;
        try {
            //cargamos el Driver a 
            Class.forName("org.gjt.mm.mysql.Driver");

            link = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return link;
    }
}
