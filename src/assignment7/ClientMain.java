/* Chat Program <MyClass.java>
 * EE422C Project 7 submission by
 * Replace <...> with your actual data.
 * <Prawal Sharma>
 * <ps27933>
 * <16280>
 * <Eric Su>
 * <es34826>
 * <16275>
 * Group on Canvas:10
 * Slip days used: <1>
 * Fall 2017
 * GitHub URL:https://github.com/EE422C-F17-HW3/422c-proj7-F17-422c-pr7-f17-pair10
 */
package assignment7;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public final class ClientMain extends Application
{
	private static Boolean socketconnected=false;
	private static Socket login_socket;
	private static Socket chat_socket;
	private static PrintWriter login_writer;
	private static PrintWriter	chat_writer;
	private static BufferedReader login_reader;
	private static BufferedReader chat_reader;
	
	private static ArrayList<String> friends= new ArrayList<String>();
	
	private static int encryptionoffset = 1;
	
	private static String command;
	private static String parameter;
	

	private static TextArea chatArea = new TextArea();
	private static String activeconversation;
	
	
	
	public static void main(String[] args) {launch();}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
		
	}
	/**
	 * setup  socket
	 * @throws Exception
	 */
	private void setupNetworking() throws Exception 
	{
		try 
		{
		//	login_socket= new Socket(ports.host, ports.login);
			chat_socket= new Socket(ports.host,ports.login);
		}
		catch (Exception e){e.printStackTrace();}
		
		try 
		{
		//	login_writer= new PrintWriter(login_socket.getOutputStream());
		//	login_reader= new BufferedReader(new InputStreamReader(login_socket.getInputStream()));
			chat_writer= new PrintWriter(chat_socket.getOutputStream());
			chat_reader= new BufferedReader(new InputStreamReader(chat_socket.getInputStream()));
		}
		catch(Exception e1){e1.printStackTrace();}
		socketconnected=true;
	}
	
	
	
	
	/**
	 * listen to server commands
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private static void listen() throws IOException {
		
		if (chat_reader.ready()) 
		{
			command = chat_reader.readLine();
			switch(command) 
			{

			case Commands.AddFriend:
				parameter = chat_reader.readLine();
				if (!friends.contains(parameter)) 
				{

					addfriend();
				}
				break;
			case Commands.UpdateChat:
				parameter = chat_reader.readLine();

				if (parameter.equals(activeconversation)) 
				{
					chatArea.setText("");
					parameter = chat_reader.readLine();
					while (!parameter.equals(Commands.StopUpdating)) 
					{
						chatArea.appendText(decode(parameter,encryptionoffset)+"\n");
						parameter = chat_reader.readLine();
					}

				}
				else 
				{
					
					while (!parameter.equals(Commands.StopUpdating)) 
					{
						parameter = chat_reader.readLine();
					}
				}

				break;

			case Commands.RecieveMessage:
				parameter= chat_reader.readLine();
				if (parameter.equals(activeconversation))
				{
					parameter=chat_reader.readLine();
					chatArea.appendText(decode(parameter,encryptionoffset) +"\n\n");
				}


				break;

			default :
				break;

			}
		}
	}

	private static String deocde(String parameter2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void addfriend() {
		// TODO Auto-generated method stub
		
	}
	private static String encode(String str, int offset)
	{
		
		
		
		return str;
		
	}
	private static String decode(String str, int offset)
	{
		
		
		return str;
		
	}
	
	
}