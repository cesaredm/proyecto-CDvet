package clases;

import formularios.ILogin;
import java.sql.*;
import javax.swing.*;
import formularios.IMenu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Login {

    String nombreUsuario, passUsuario;
    Conexiondb conectar;

    public Login(String nombreUsuario, String passUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.passUsuario = passUsuario;
    }

    public boolean Validacion() {
        conectar = new Conexiondb();
        Connection cn = conectar.Conexion();
        String consultaUsuario = "SELECT * FROM usuarios";
        boolean bandera = true;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consultaUsuario);
            while (rs.next()) {

                if (rs.getString("nombreUsuario").equals(this.nombreUsuario) && rs.getString("password").equals(this.passUsuario)) {

                    IMenu menu = new IMenu();
                    menu.setVisible(true);
                    bandera=true;
                } else {

                    JOptionPane.showMessageDialog(null, "Nombre o Contraseña Incorecta");
                    bandera=false;
                }

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return bandera;
    }

}
