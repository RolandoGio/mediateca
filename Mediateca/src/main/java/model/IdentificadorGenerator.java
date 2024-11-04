package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdentificadorGenerator {
    public static String generarIdentificador(Connection connection, String tipo) throws SQLException {
        String prefijo = "";
        String tabla = "";
        String columna = "";

        switch (tipo) {
            case "LIB":
                prefijo = "LIB";
                tabla = "Libros";
                columna = "LibroID";
                break;
            case "REV":
                prefijo = "REV";
                tabla = "Revistas";
                columna = "RevistaID";
                break;
            case "CDA":
                prefijo = "CDA";
                tabla = "CDsAudio";
                columna = "CDID";
                break;
            case "DVD":
                prefijo = "DVD";
                tabla = "DVDs";
                columna = "DVDID";
                break;
            case "AUT":
                prefijo = "AUT";
                tabla = "Autores";
                columna = "IDAutor";
                break;
            case "EDI":
                prefijo = "EDI";
                tabla = "Editoriales";
                columna = "EditorialID";
                break;
            case "CLI":
                prefijo = "CLI";
                tabla = "Clientes";
                columna = "IDCliente";
                break;
            case "PRE":
                prefijo = "PRE";
                tabla = "Prestamos";
                columna = "IDPrestamo";
                break;
            default:
                throw new IllegalArgumentException("Tipo de identificador no soportado: " + tipo);
        }

        String sql = "SELECT MAX(CAST(SUBSTRING(" + columna + ", 4, LEN(" + columna + ") - 3) AS INT)) AS MaxID FROM " + tabla;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                int maxID = resultSet.getInt("MaxID");
                return prefijo + String.format("%05d", maxID + 1);
            } else {
                return prefijo + "00001";
            }
        }
    }
}

