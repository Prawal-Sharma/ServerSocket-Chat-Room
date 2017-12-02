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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;

public final class ClientMain extends Application
{
	
	private Socket login_socket;
	private Socket chat_socket;
	private PrintWriter login_writer;
	private PrintWriter	chat_writer;
	private BufferedReader login_reader;
	private BufferedReader chat_reader;
	
	
	
	public static void main(String[] args) {launch();}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private void setupNetworking() throws Exception 
	{
		try 
		{
			login_socket= new Socket(ports.host, ports.login);
			chat_socket= new Socket(ports.host,ports.login);
		}
		catch (Exception e){e.printStackTrace();}
		
		try 
		{
			login_writer= new PrintWriter(login_socket.getOutputStream());
			login_reader= new BufferedReader(new InputStreamReader(login_socket.getInputStream()));
			chat_writer= new PrintWriter(chat_socket.getOutputStream());
			chat_reader= new BufferedReader(new InputStreamReader(chat_socket.getInputStream()));
		}
		catch(Exception e1){e1.printStackTrace();}
		
	}
	
	
	
	
	
}