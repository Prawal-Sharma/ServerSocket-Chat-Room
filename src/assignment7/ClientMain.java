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
import java.util.Base64;
import java.util.List;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private static ArrayList<String> groupchatmembers = new ArrayList<String>();
	
	private static Scene signinScene;
	private static Scene chatScene;
	private static GridPane scenePane ;
	private static GridPane contolpane ;
	private static TextArea chatArea ;
	private static TextField chatField ;
	private static TextField friendField;
	private static TextField changepasswordField;
	private static Button changepasswordButton;
	private static Button addfriendButton ;
	private static Button closechatButton;
	private static Button sendButton ;
	private static Button startchatButton;
	private static Button exitButton ;
	private static Button acceptButton ;
	
	private static Button denyButton ;
	private static GridPane friendPane ;
	private static GridPane requestPane;
	private static GridPane chatPane;
	private static HBox changePassHBox ;
	private static VBox controlVBox ;
	
	private static GridPane signinPane ;
	private static String userName;
	private static Label usernameLabel;
	private static Label passwordLabel;
	private static TextField usernameField ;
	private static TextField passwordField ;
	private static Button loginButton ;
	private static Button createButton ;
	private static VBox usernameVBox ;
	private static VBox passwordVBox ;
	private static HBox signinHBox ;
	private static HBox newuserHBox ;
	
	static Timeline listener;
	private static int encryptionoffset = 1;
	
	private static String command;
	private static String parameter;
	private static String activeconversation;
	
	
	
	public static void main(String[] args) {launch();}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		// TODO Auto-generated method stub
		
		initjavafx();
		
		primaryStage.setScene(signinScene);
		primaryStage.show();
		
		
		
	}
	private void initjavafx()
	{
		scenePane = new GridPane();
		contolpane = new GridPane();
		chatArea = new TextArea();
		chatField = new TextField();
		friendField = new TextField();
		changepasswordField = new TextField();
		changepasswordButton = new Button("Change Password");
		addfriendButton = new Button("Add Friend");
		closechatButton = new Button("Close Chat");
		sendButton = new Button("Send");
		startchatButton = new Button("Start Chat");
		exitButton = new Button("Sign out");
		acceptButton = new Button("Accep Friend Requestt");
		denyButton = new Button("Deny Friend Request");
		friendPane = new GridPane();
		requestPane = new GridPane();
		chatPane = new GridPane();
		changePassHBox = new HBox();
		controlVBox = new VBox();
		signinPane = new GridPane();
		usernameLabel = new Label("Username:");
		passwordLabel = new Label("Password:");
		usernameField = new TextField();
		passwordField = new TextField();
		loginButton = new Button("Sign In");
		createButton = new Button("New User");
		usernameVBox = new VBox();
		passwordVBox = new VBox();
		signinHBox = new HBox();
		newuserHBox = new HBox();
		signinScene=new Scene(signinPane );
		contolpane.add(controlVBox, 0,0 );
		contolpane.add(chatPane, 1,0);
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

	/**
	 * adds friend to friend list
	 */
	private static void addfriend() {
		// TODO Auto-generated method stub
		
	}
	private static String encode(String str, int offset)
	{
		
		String retstr= new String (Base64.getUrlEncoder().encodeToString(str.getBytes()) );
		
		return retstr;
		
	}
	private static String decode(String str, int offset)
	{
		
		String retstr= new String( Base64.getUrlDecoder().decode(str));
		return retstr;
		
	}
	
	
}