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
            System.out.println("Enviando requisição para o servidor...");
            paraServidor.writeBytes(arquivo + "\n");
            System.out.println("Recebendo requisição do servidor...");
            arquivoResposta = doServidor.readLine();
            System.out.println("Resposta: " + arquivoResposta);
            paraServidor.close();
            socketCliente.close();
        } catch (IOException e) {
            System.out.println("Falha na conexão com o servidor!");
        }
    }
}
