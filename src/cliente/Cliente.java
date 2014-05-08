package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Giovanne
 */
public class Cliente {
    
    public void conectar() {
        String arquivo;
        String arquivoResposta;
        Socket socketCliente = null;

        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        try {
            socketCliente = new Socket("localhost", 6789);

            DataOutputStream paraServidor = new DataOutputStream(socketCliente.getOutputStream());
            BufferedReader doServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            System.out.println("Digite a entrada: ");
            arquivo = entrada.readLine();
            paraServidor.writeBytes(arquivo + "\n");
//        arquivoResposta = doServidor.readLine();
//        System.out.println("Resposta: "+arquivoResposta);
            socketCliente.close();
        } catch (IOException e) {
            System.out.println("Falha na conexão com o servidor!");
            return;
        }
    }
}
