import java.net.*;
import java.io.*;
public class Server extends Thread{
	public ServerSocket sock;
	public static double percentage=0;
	
	//Inicio o socket
	public Server(int port){
		try{
			sock = new ServerSocket(port);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	//Aceito a conexão com o socket
	public void run(){
		while(true){
			try{
				Socket clientSock = sock.accept();
				saveFile(clientSock);
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void saveFile(Socket clientSock) throws IOException{
		//==================================================================================
		//Esses são usados para ler o nome do arquivo
		InputStreamReader nameIn = new InputStreamReader(clientSock.getInputStream());
		BufferedReader fileNameReader = new BufferedReader(nameIn);
		//Lendo nome
		String fileName = fileNameReader.readLine();
		System.out.println("Server: O nome do arquivo é: " + fileName);
		//Lendo tamanho
		String fileSize = fileNameReader.readLine();
		System.out.println("Server: O tamanho do arquivo é: "+fileSize);
		//=================================================================================
		
		
		
		
		//=================================================================================
		//Crio o inputstream para receber o arquivo e crio o outputstream para escrever no arquivo
		DataInputStream dataIn = new DataInputStream(clientSock.getInputStream());
		//Note que por padrão está um arquivo zip, isso deve ser mudado de acordo com o arquivo
		FileOutputStream fileIn = new FileOutputStream("t"+fileName);
		System.out.println("Server: Até aqui tudo bem");
		//==================================================================================
		
		DatagramSocket serverSocket = new DatagramSocket(7188);
		byte[] receiveData = new byte[1];
		byte[] sendData;
		InetAddress clientIP;
		int port;
		
		
		
		
		byte[] buffer = new byte[4096];
		//Variaveis auxialiares
		int filesize = Integer.parseInt(MainView.fileSize.getText());
		int read=0;
		int totalRead=0;
		int remaining = filesize;
		int total = filesize;
		int teste = 0;
		//Loop para receber o arquivo inteiro
		while((read=dataIn.read(buffer,0,Math.min(buffer.length, remaining)))>0){
			/*
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			clientIP = receivePacket.getAddress();
			port = receivePacket.getPort();
			sendData = ("2").getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, port);
			serverSocket.send(sendPacket);
			*/
			
			
		//======================================================================
			totalRead += read;
			remaining -= read;
			//Cálculo usado para a barra de progresso
			percentage = (totalRead*100)/total;
			System.out.println("Server: Transferido: " + percentage);
			
			
			//Para fins de debug
			System.out.println("Server: Remaining: "+remaining);
			System.out.println("Server: Read: "+totalRead);
			System.out.println("=====================");
			//Escrevo no arquivo
			fileIn.write(buffer, 0, read);
		//=====================================================================
		}
		System.out.println("Server: Terminado");
		percentage=100;
		fileIn.close();
		dataIn.close();
	}
	
	
}
