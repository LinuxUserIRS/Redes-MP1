import java.net.*;
import java.nio.ByteBuffer;
import java.io.*;
public class Client{
	private Socket sock;
	//Crio o socket
	public Client(String host, int port, String file, String fileSize){
		try{
			sock = new Socket(host,port);
			sendFile(file, fileSize, host);
			sock.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendFile(String file, String fileSize, String host) throws IOException{
		//===================================================
		/*
		InetAddress IPServer = InetAddress.getByName(host);
		DatagramSocket RTTSock = new DatagramSocket();
		byte[] sendPkt;
		sendPkt = ("1").getBytes();
		DatagramPacket RTTpkt = new DatagramPacket(sendPkt, sendPkt.length, IPServer, 7188);
		
		long timer = System.nanoTime();
		RTTSock.send(RTTpkt);
		byte[] receiveRTT = new byte[1];
		DatagramPacket receivedPkt = new DatagramPacket(receiveRTT, receiveRTT.length);
		RTTSock.receive(receivedPkt);
		System.out.println("RTT: " + (System.nanoTime()-timer)/1000);
		RTTSock.close();
		*/
		//===================================================
		
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