import java.net.*;
import java.nio.ByteBuffer;
import java.io.*;
public class Client{
	private Socket sock;
	//Crio o socket
	public Client(String host, int port, String file, String fileSize){
		try{
			sock = new Socket(host,port);
			sendFile(file, fileSize);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendFile(String file, String fileSize) throws IOException{
		//===========================Metadados
		DataOutputStream fileNameOut = new DataOutputStream(sock.getOutputStream());
		//Enviando nome
		String teste = file + '\n';
		fileNameOut.write(teste.getBytes());
		System.out.println("Client: O nome do arquivo foi enviado");
		//Enviando tamanho
		String fileSize2 = fileSize + '\n';
		fileNameOut.write(fileSize2.getBytes());
		System.out.println("Client: Tamanho do arquivo foi enviado");
		
		
		
		//===========================Dados
		DataOutputStream dataOut = new DataOutputStream(sock.getOutputStream());
		FileInputStream fileIn = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		//Loop para enviar o arquivo por inteiro
		while(fileIn.read(buffer) > 0){
			dataOut.write(buffer);
		}
		System.out.println("Client: O arquivo foi enviado");
		
		fileIn.close();
		dataOut.close();
	}
	
}