package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
        // Aquí pon tus datos de Clever Cloud directamente
        // Reemplaza con tus valores reales
        String cleverHost = "bdxuqnlkigtvty8vhtym-mysql.services.clever-cloud.com";
        String cleverPort = "3306"; // usualmente 3306
        String cleverDb   = "bdxuqnlkigtvty8vhtym";
        String cleverUser = "uysbkhl5azbhzp0r";
        String cleverPass = "INjuQ9tV1iy29N8sKc8L";

        // Local
        String localHost = "localhost";
        String localPort = "3306";
        String localDb   = "bdxuqnlkigtvty8vhtym";
        String localUser = "uysbkhl5azbhzp0r";
        String localPass = "INjuQ9tV1iy29N8sKc8L";

        // Cambia esta bandera según quieras usar Local o Clever Cloud
        boolean usarCleverCloud = true;

        if (usarCleverCloud) {
            URL  = "jdbc:mysql://" + cleverHost + ":" + cleverPort + "/" + cleverDb +
                    "?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            USER = cleverUser;
            PASS = cleverPass;
        } else {
            URL  = "jdbc:mysql://" + localHost + ":" + localPort + "/" + localDb +
                    "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            USER = localUser;
            PASS = localPass;
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

