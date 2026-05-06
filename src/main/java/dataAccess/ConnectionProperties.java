package dataAccess;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "connection") // Esta etiqueta DEBE ser la raíz del XML
public class ConnectionProperties implements Serializable {
    private String server;
    private String port;
    private String dataBase;
    private String user;
    private String password;

    public ConnectionProperties() {}

    public ConnectionProperties(String server, String port, String dataBase, String user, String password) {
        this.server = server;
        this.port = port;
        this.dataBase = dataBase;
        this.user = user;
        this.password = password;
    }

    // SETTERS (CRUCIALES para JAXB)
    public void setServer(String server) { this.server = server; }
    public void setPort(String port) { this.port = port; }
    public void setDataBase(String dataBase) { this.dataBase = dataBase; }
    public void setUser(String user) { this.user = user; }
    public void setPassword(String password) { this.password = password; }

    public String getUser() { return user; }
    public String getPassword() { return password; }

    // Método para facilitar la conexión
    public String getURL() {
        return "jdbc:mysql://" + server + ":" + port + "/" + dataBase + "?serverTimezone=UTC";
    }
}

