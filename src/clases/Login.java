package clases;

import formularios.ILogin;
import java.sql.*;
import javax.swing.JOptionPane;
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
        String consultaUsuario = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND password = ?";
        boolean bandera = false;
        try {
            PreparedStatement pst = cn.prepareStatement(consultaUsuario);
            pst.setString(1, this.nombreUsuario);
            pst.setString(2, this.passUsuario);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("nombreUsuario").equals(this.nombreUsuario) && rs.getString("password").equals(this.passUsuario)) {

                    IMenu menu = new IMenu();
                    menu.setVisible(true);
                    bandera = true;
                } else{

                    JOptionPane.showMessageDialog(null, "Nombre o Contraseña Incorecta");
                    bandera = false;
                }

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return bandera;
    }

}
