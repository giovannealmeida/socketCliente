package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giovanne
 */

public class Cliente {

    private Socket socketCliente = null;
    private BufferedReader doServidor = null;
    DataOutputStream paraServidor = null;
    public String arquivoResposta;
    public boolean isConnected;
    public boolean isServerOn;

    public boolean sendRequest(String arquivo) {
        try {
            socketCliente = new Socket("localhost", 6789);

            paraServidor = new DataOutputStream(socketCliente.getOutputStream());
            doServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            paraServidor.writeBytes(arquivo + "\n");
            arquivoResposta = doServidor.readLine();
            System.out.println("Resposta: " + arquivoResposta);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        try {
            paraServidor.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
}
