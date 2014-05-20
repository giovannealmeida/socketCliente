package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private DataOutputStream paraServidor = null;
    public String arquivoResposta;
    public boolean isConnected;
    public boolean isServerOn;

    public boolean sendRequest(String arquivo) {
        FileOutputStream fos = null;
        InputStream is = null;

        try {
            System.out.println("Criando socket...");
            socketCliente = new Socket("localhost", 6789);
            System.out.println("Criando InputStream...");
            is = socketCliente.getInputStream();
            
            System.out.println("Criando OutputStream...");
            fos = new FileOutputStream(new File("C:\\Users\\Giovanne\\Desktop\\Server_donwloads\\do-servidor.txt"));
            
            System.out.println("Criando bufferedReader...");
            doServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            System.out.println("Criando dataOutputStream...");
            paraServidor = new DataOutputStream(socketCliente.getOutputStream());
            System.out.println("Enviando solicitação...");
            paraServidor.writeBytes(arquivo + "\n");

            byte[] cbuffer = new byte[1024];
            int bytesRead;

            System.out.println("Lendo resposta...");
            while ((bytesRead = is.read(cbuffer)) != -1) {
                fos.write(cbuffer, 0, bytesRead);
                fos.flush();
            }
            System.out.println("Reposta recebida!");
//            socketCliente = new Socket("localhost", 6789);
//            paraServidor = new DataOutputStream(socketCliente.getOutputStream());
//            doServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
//            paraServidor.writeBytes(arquivo + "\n");
//            arquivoResposta = doServidor.readLine();
//            System.out.println("Resposta: " + arquivoResposta);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            //paraServidor.close();
            socketCliente.close();
            fos.close();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
