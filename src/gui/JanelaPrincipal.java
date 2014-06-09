package gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Giovanne
 */
class JanelaPrincipal extends JFrame implements ActionListener, PropertyChangeListener {

    Cliente cliente;

    private final int largura = 435;
    private final int altura = 200;

    private JLabel lbInfo = new JLabel("Insira o diretório do arquivo desejado com extensão");

    private JButton btnSearch;
    private JTextField txtSearch;

    private ImageIcon imgStart;
    private ImageIcon imgStop;

    private JPanel panel = new JPanel(null);
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel = new JLabel("Status: Desconectado");

    private JProgressBar progressBar;

    public class Cliente extends SwingWorker<Void, Void> {

        private Socket socketCliente = null;
        private BufferedReader doServidor = null;
        private DataOutputStream paraServidor = null;
        FileOutputStream fos = null;
        InputStream is = null;

        String[] arqParts;
        public String arquivoResposta;
        public String arquivoPedido;

        public boolean isConnected;

        public boolean sendRequest() {
            try {
                System.out.println("Criando socket...");
                socketCliente = new Socket("localhost", 6789);
                isConnected = true;
                System.out.println("Criando InputStream...");
                is = socketCliente.getInputStream();

                System.out.println("Criando dataOutputStream...");
                paraServidor = new DataOutputStream(socketCliente.getOutputStream());
                System.out.println("Enviando solicitação...");
                paraServidor.writeBytes(arquivoPedido + "\n");

                byte[] flagBuffer = new byte[1024];
                byte[] cbuffer = new byte[1024];
                int bytesRead = 0;
                System.out.println("Lendo flag...");
                int flag = is.read(flagBuffer);
                System.out.println("Flag: " + flag);

                if (flag == -1) {
                    JOptionPane.showMessageDialog(null, "Arquivo não encontrado!");
                    return true;
                }
                byte[] tamanhoBuffer = new byte[1024];
                //int tamanhoArq = is.read(tamanhoBuffer);
                long tamanhoArq = (new DataInputStream(is)).readLong();
                System.out.println("Tamanho do arquivo: " + tamanhoArq);
                //Cria arquivo
                System.out.println("Criando OutputStream...");
                File solicitado = new File(arquivoPedido);
                arqParts = solicitado.getAbsolutePath().split("\\\\");
                fos = new FileOutputStream(new File("c:\\temp\\" + arqParts[arqParts.length - 1]));
                System.out.println("Salvando \"" + arqParts[arqParts.length - 1] + "\"");

                System.out.println("Lendo resposta...");
                setProgress(0);
                double progress = 0;
                double taxa = 100.0 / (tamanhoArq / 1024);
                statusLabel.setText("Baixando " + arqParts[arqParts.length - 1]);
                while (((bytesRead = is.read(cbuffer)) != -1)) {
                    fos.write(cbuffer, 0, bytesRead);
                    fos.flush();
                    progress += taxa;
                    setProgress(Math.min((int) progress, 100));
                }
                setProgress(0);
                System.out.println("Reposta recebida!");
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

            try {
                socketCliente.close();
                fos.close();
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }

        @Override
        protected Void doInBackground() {
            sendRequest();
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            btnSearch.setEnabled(true);
            txtSearch.setEditable(true);
            setCursor(null); //turn off the wait cursor
        }

    }

    public JanelaPrincipal() {

        lbInfo.setBounds(10, 18, 350, 30);

        btnSearch = new JButton("Buscar");
        btnSearch.addActionListener(this);
        btnSearch.setBounds(320, 50, 100, 30);

        txtSearch = new JTextField();
        txtSearch.setBounds(10, 50, 300, 30);

        statusPanel.add(statusLabel);
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 30));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBounds(10, 100, 410, 30);

        panel.add(lbInfo);
        panel.add(btnSearch);
        panel.add(txtSearch);
        panel.add(progressBar);

        this.setLayout(new BorderLayout());
        this.add(panel);
        this.add(statusPanel, BorderLayout.SOUTH);
        this.setSize(largura, altura);
        Dimension TamTela = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((TamTela.width - altura) / 2, 100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Cliente");
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            btnSearch.setEnabled(false);
            txtSearch.setEditable(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            cliente = new Cliente();
            cliente.arquivoPedido = txtSearch.getText();
            cliente.addPropertyChangeListener(this);
            cliente.execute();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!cliente.isConnected) {
                statusLabel.setText("Status: Desconectado");
                JOptionPane.showMessageDialog(rootPane, "Servidor não responde");
            } else {
                statusLabel.setText("Status: Conectado");
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }
}
