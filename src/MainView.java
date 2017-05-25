import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class MainView extends JFrame {

	private JPanel contentPane;
	public static JTextField host;
	public static JTextField fileName;
	public static JTextField fileSize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		//Código padrão gerado automaticamente da interface gráfica
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 36, 414, 29);
		contentPane.add(progressBar);
		
		JLabel progress = new JLabel("Progresso da Transfer\u00EAncia");
		progress.setBounds(148, 11, 139, 14);
		contentPane.add(progress);
		
		host = new JTextField();
		host.setBounds(77, 151, 86, 20);
		contentPane.add(host);
		host.setColumns(10);
		
		JLabel lblIpDoHost = new JLabel("IP do host");
		lblIpDoHost.setBounds(91, 131, 54, 14);
		contentPane.add(lblIpDoHost);
		
		fileName = new JTextField();
		fileName.setBounds(274, 172, 86, 20);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		JLabel lblNomeDoArquivo = new JLabel("Nome do arquivo (com extens\u00E3o)");
		lblNomeDoArquivo.setBounds(239, 152, 159, 14);
		contentPane.add(lblNomeDoArquivo);
		
		JButton btnTransferir = new JButton("Transferir");
		btnTransferir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Pego o host e o nome do arquivo dos campos de texto
				String fileName = MainView.fileName.getText();
				String host = MainView.host.getText();
				//Instancio e inicio o servidor
				Server fs = new Server(1988);
				fs.start();
				//Chamo o cliente
				Client fc = new Client(host, 1988, fileName, fileSize.getText());
				//Someço a setar o valor da barra de progresso
				progressBar.setValue((int)fs.percentage);
				//Esse pedaço é para a barra não ir de 0 a 100 do nada
				try {
					TimeUnit.MICROSECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				progressBar.setValue((int)fs.percentage);
				try {
					TimeUnit.MICROSECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				progressBar.setValue(100);
				
			}
		});
		
		//Mais código automático
		btnTransferir.setBounds(168, 227, 108, 23);
		contentPane.add(btnTransferir);
		
		JLabel lblTamanhoDoArquivo = new JLabel("Tamanho do arquivo (em bytes)");
		lblTamanhoDoArquivo.setBounds(231, 92, 167, 14);
		contentPane.add(lblTamanhoDoArquivo);
		
		fileSize = new JTextField();
		fileSize.setBounds(274, 117, 86, 20);
		contentPane.add(fileSize);
		fileSize.setColumns(10);
	}
	
	
}

