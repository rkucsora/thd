package thd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Main {

	public static String MY_NAME = "TWO_AND_A_HALF_DEV";
	private static int passCounter;
	
	public static void main(String[] args) throws Exception {
		System.out.println("NEW GAME @ " + new Date());
		BufferedReader stReader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "/startup-info")));
		String[] srvAddr = stReader.readLine().split(":");
		System.out.println(srvAddr[0]);
		String pwd = stReader.readLine();
		stReader.close();
		
		Socket socket = new Socket(srvAddr[0], Integer.parseInt(srvAddr[1]));
		socket.setSoTimeout(30000);
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		
		InputStream inputStream = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		writer.println("AUTH " + pwd);
		writer.flush();
		
		System.out.println("auth sent");

		MainRequestProcessor proc = new MainRequestProcessor();
		
		try
		{
			
			String line = null;
			while((line = reader.readLine()) != null) {
				if (line.trim().endsWith("[")) {
					String subLine = null;
					do {
						subLine = reader.readLine();
						if (subLine != null) {
							line += "\n" + subLine;
						}
					} while(subLine != null && !subLine.trim().equals("]"));
				}
				System.out.println("->" + line);
				if (line.startsWith("ERROR") || line.startsWith("GameEnd"))
				{
					break;
				}
				String resp = proc.processRequest(line);
				if (resp != null)
				{
					if(line.startsWith("?Move"))
					{
						System.out.print("!!!! ");
						if("pass".equals(resp))
						{
							
							if(++passCounter == 3)
							{
								resp = "drop";
								passCounter = 0;
							}
						}
						else
						{
							passCounter = 0;
						}
					}
					System.out.println("<-" + resp);
					writer.println(resp);
					writer.flush();
				}
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		socket.close();
	}

}
