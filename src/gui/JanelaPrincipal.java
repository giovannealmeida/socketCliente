package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

    private JButton btnConnect;
    private JButton btnSearch;
    private JTextField txtSearch;
    private JTable tbTable;
    private ImageIcon imgStart;
    private ImageIcon imgStop;

    private JPanel panel = new JPanel(null);
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel = new JLabel("Desconectado");

    public JanelaPrincipal() {

        btnConnect = new JButton("Conectar");
//        btnConnect.setBorder(BorderFactory.createEmptyBorder());
//        btnConnect.setContentAreaFilled(false);
//        btnConnect.setOpaque(false);
        btnConnect.addActionListener(this);
        btnConnect.setBounds(10, 10, 100, 30);
        
        btnSearch = new JButton("Buscar");
        btnSearch.addActionListener(this);
        btnSearch.setBounds(320, 50, 100, 30);
        
        txtSearch = new JTextField();
        txtSearch.setBounds(10, 50, 300, 30);
//        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        tbTable = new JTable(3, 5);
        tbTable.setBounds(10,100,570,300);
        statusPanel.add(statusLabel);
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 30));
        
        panel.add(btnConnect);
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
            
        }
    }
}
