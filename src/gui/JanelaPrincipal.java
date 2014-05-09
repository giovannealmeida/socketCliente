package gui;

import cliente.Cliente;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Giovanne
 */
class JanelaPrincipal extends JFrame implements ActionListener {

    private final int altura = 600;
    private final int largura = 600;
    
    private final Cliente cliente = new Cliente();

    private JButton btnUpdate;
    
    private JButton btnSearch;
    private JTextField txtSearch;
    private JTable tbTable;
    private ImageIcon imgStart;
    private ImageIcon imgStop;

    private JPanel panel = new JPanel(null);
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel = new JLabel("Status");

    public JanelaPrincipal() {

        btnUpdate = new JButton("Atualizar");
        btnUpdate.addActionListener(this);
        btnUpdate.setBounds(10, 10, 100, 30);

        btnSearch = new JButton("Buscar");
        btnSearch.addActionListener(this);
        btnSearch.setBounds(320, 50, 100, 30);

        txtSearch = new JTextField();
        txtSearch.setBounds(10, 50, 300, 30);

        tbTable = new JTable(3, 5);
        tbTable.setBounds(10, 100, 570, 300);
        statusPanel.add(statusLabel);
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 30));

        panel.add(btnUpdate);
        panel.add(btnSearch);
        panel.add(txtSearch);
        panel.add(tbTable);

        this.setLayout(new BorderLayout());
        this.add(panel);
        this.add(statusPanel, BorderLayout.SOUTH);
        this.setSize(altura, largura);
        Dimension TamTela = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((TamTela.width - largura) / 2, 100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Cliente");
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            if (!cliente.sendRequest(txtSearch.getText())) {
                JOptionPane.showMessageDialog(rootPane, "Servidor não encontrado");
            }
        }

        if (e.getSource() == btnUpdate) {

        }
    }
}
