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
	//Aceito a conex�o com o socket
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
		//Esses s�o usados para ler o nome do arquivo
		InputStreamReader nameIn = new InputStreamReader(clientSock.getInputStream());
		BufferedReader fileNameReader = new BufferedReader(nameIn);
		String fileName = fileNameReader.readLine();
		System.out.println("O nome do arquivo �: " + fileName);
		
		
		
		//Crio o inputstream para receber o arquivo e crio o outputstream para escrever no arquivo
		DataInputStream dataIn = new DataInputStream(clientSock.getInputStream());
		//Note que por padr�o est� um arquivo zip, isso deve ser mudado de acordo com o arquivo
		FileOutputStream fileIn = new FileOutputStream("t"+fileName);
		System.out.println("At� aqui tudo bem");
		
		byte[] buffer = new byte[4096];
		//Variaveis auxialiares
		int filesize = Integer.parseInt(MainView.fileSize.getText());
		int read=0;
		int totalRead=0;
		int remaining = filesize;

		//Loop para receber o arquivo inteiro
		while((read=dataIn.read(buffer,0,Math.min(buffer.length, remaining)))>0){
			totalRead += read;
			remaining -= read;
			//C�lculo usado para a barra de progresso
			if(remaining!=0){
				percentage = ((totalRead*100)/filesize);
			}
			
			//Para fins de debug
			System.out.println(remaining);
			System.out.println(read);
			System.out.println("=====================");
			//Escrevo no arquivo
			fileIn.write(buffer, 0, read);
		}
		System.out.println("Terminado");
		percentage=100;
		fileIn.close();
		dataIn.close();
	}
	
	
}
