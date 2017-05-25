import java.net.*;
import java.nio.ByteBuffer;
import java.io.*;
public class Client{
	private Socket sock;
	//Crio o socket
	public Client(String host, int port, String file){
		try{
			sock = new Socket(host,port);
			sendFile(file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendFile(String file) throws IOException{
		//===========================Pedaço do nome
		//Crio o inputstream para ler o arquivo e o outputstream para enviar o arquivo
		DataOutputStream fileNameOut = new DataOutputStream(sock.getOutputStream());
		String teste = file + '\n';
		fileNameOut.write(teste.getBytes());
		System.out.println("O nome do arquivo foi enviado");

		//===========================Pedaço do arquivo
		DataOutputStream dataOut = new DataOutputStream(sock.getOutputStream());
		FileInputStream fileIn = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		//Loop para enviar o arquivo por inteiro
		while(fileIn.read(buffer) > 0){
			dataOut.write(buffer);
		}
		System.out.println("O arquivo foi enviado");
		
		fileIn.close();
		dataOut.close();
	}
	
}