
import dataAccess.ConnectionBD;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Test de Conexión ---");

        // Intentamos obtener la conexión
        Connection instance = ConnectionBD.getInstance().getCon();

        if (instance != null) {
            System.out.println("✅ TEST SUPERADO: Java ha leído el XML y se ha conectado a MySQL.");

        } else {
            System.err.println("❌ TEST FALLIDO: Revisa los errores en la consola superior.");
        }
    }
}
