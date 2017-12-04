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


import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;



import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	
	private static Stage newStage;
	private static Scene signinScene;
	private static Scene chatScene;
	private static GridPane scenePane ;
	private static GridPane controlPane ;
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
	
	private static Timeline listener;
	private static int encryptionoffset = 1;
	
	private Media sound = new Media (new File("ding.wav").toURI().toString());
	private MediaPlayer mediaplayer;
	private static String command;
	private static String parameter;
	private static String activeconversation;
	
	
	
	public static void main(String[] args) {launch();}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		
		
		initjavafx();
		startliseners();
		mediaplayer=new MediaPlayer(sound);
		primaryStage.setScene(signinScene);
		primaryStage.show();
		
		
		
	}
	private void startliseners() 
	{
		// TODO Auto-generated method stub
		
		
		signinlistener();
		createnewuserlistner();
		sendbuttonlistener();
		sendchatenterlisterner();
		startchatlistener();
		addfriendbuttonlistener();
		closechatlistner();
		addfriendbuttonlistner();
		acceptbuttonlistner();
		denybuttonlistener();
		changepassowrdlistener();
		exitbuttonlistener();
		
	}
	private void addfriendbuttonlistener() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * allows for chat to be sent with press of enterbutton
	 */
	private void sendchatenterlisterner() 
	{
		// TODO Auto-generated method stub
		chatField.setOnKeyPressed(new EventHandler< KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode().equals(KeyCode.ENTER))
				{
					if (chatField.getText().equals("")){}		//ensure field is not empty
					else {sendButton.fire();}				//do send message event 
				}
			}
			
		});
	}

	private void addfriendbuttonlistner() 
	{
		// TODO Auto-generated method stub
		
	}

	private void closechatlistner() {
		// TODO Auto-generated method stub
		closechatButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				listener.stop();			//stop listening to server commands
				chat_writer.println(Commands.CloseChat);
				chat_writer.flush();
				chat_writer.println(activeconversation);
				chat_writer.flush();
				chat_writer.println(userName);
				chat_writer.flush();
				chatArea.clear();
				listener.play();
				
			}			
			
		});
	}

	private void denybuttonlistener() {
		// TODO Auto-generated method stub
		
	}

	private void acceptbuttonlistner() {
		// TODO Auto-generated method stub
		
	}

	private void startchatlistener() {
		// TODO Auto-generated method stub
		startchatButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				listener.stop();
				groupchatmembers.add(userName);
				activeconversation= new String();
				Collections.sort(groupchatmembers);
				for (String friend: groupchatmembers)
				{
					activeconversation+=friend;
					chat_writer.println(friend);
					chat_writer.flush();
				}
				chat_writer.println(Commands.EndofList);
				chat_writer.flush();
				chatArea.clear();
				groupchatmembers.remove(userName);
				listener.play();
			}
			
		});
	}

	private void changepassowrdlistener() 
	{
		// TODO Auto-generated method stub
		changepasswordButton.setOnAction(new EventHandler<ActionEvent>() 
		{

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				listener.stop();
				chat_writer.println(Commands.ChangePassword);
				chat_writer.flush();
				chat_writer.println(changepasswordField.getText());
				chat_writer.flush();
				changepasswordField.clear();
				listener.play();
			}
			
		});
	}

	private void exitbuttonlistener() {
		// TODO Auto-generated method stub
		exitButton.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				chat_writer.println(Commands.Quit);
				chat_writer.flush();
				listener.stop();
				newStage.close(); 
		//		System.exit(0);
			}
			
		});
		
	}

	private void createnewuserlistner() {
		// TODO Auto-generated method stub
		createButton.setOnAction(new EventHandler<ActionEvent>() 
		{

			@Override
			public void handle(ActionEvent event) 
			{
				// TODO Auto-generated method stub
				if (socketconnected == false) 
				{
					try {
						setupNetworking();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				chat_writer.println(Commands.MakeUser);
				chat_writer.flush();
				chat_writer.println(usernameField.getText());
				chat_writer.flush();
				chat_writer.println(passwordField.getText());
				chat_writer.flush();

				String read = null;
				try {
					for(;;)										//wait for server to process request
					{
						read=null;
						read=chat_reader.readLine();
						if (!(read==null)) 
						{
							break;
						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (read==Commands.LoginSuccess)
				{
					usernameLabel.setText(usernameField.getText());
					userName=usernameField.getText();

					configurechatcontrols();

					chatScene= new Scene(controlPane,Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight());  		//make full screen
					newStage.setScene(chatScene);
					newStage.show();

					//setup a listener that polls server every .2 seconds
					listener = new Timeline(new KeyFrame(Duration.seconds(0.2), ae -> 
					{
						try { listentoserver(); }
						catch (IOException e) { e.printStackTrace(); }
					}));
					listener.setCycleCount(Animation.INDEFINITE);
					listener.play();
				}

			}
		}); 
	}

	private void initjavafx()
	{
		scenePane = new GridPane();
		controlPane = new GridPane();
		chatArea = new TextArea();
		chatArea.setWrapText(true);
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
		
		initsigninpane();
		initcontrolpane();
		signinScene=new Scene(signinPane );
		
	}
	
	private void initcontrolpane() {
		// TODO Auto-generated method stub
		
		controlPane.setHgap(24);
		controlPane.setAlignment(Pos.CENTER);
		controlPane.add(controlVBox, 0,0 );
		controlPane.add(chatPane, 1,0);
	}

	private void initsigninpane() {
		// TODO Auto-generated method stub
		signinPane.setAlignment(Pos.TOP_CENTER);
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
	 * listener to sign in user
	 */
	private void signinlistener()
	{
		loginButton.setOnAction( new EventHandler<ActionEvent>() 
		{

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (socketconnected == false) 
				{
					try {
						setupNetworking();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				chat_writer.println(Commands.Login);
				chat_writer.flush();
				chat_writer.println(usernameField.getText());
				chat_writer.flush();
				chat_writer.println(passwordField.getText());
				chat_writer.flush();
				
				String read = null;
				try {
					for(;;)										//wait for server to process request
					{
						read=null;
						read=chat_reader.readLine();
						if (!(read==null)) 
						{
							break;
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (read==Commands.LoginSuccess)
				{
					usernameLabel.setText(usernameField.getText());
					userName=usernameField.getText();
					
					configurechatcontrols();
					
					chatScene= new Scene(controlPane,Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight());  		//make full screen
					newStage.setScene(chatScene);
					newStage.show();
				
					//setup a listener that polls server every .2 seconds
					listener = new Timeline(new KeyFrame(Duration.seconds(0.2), ae -> 
					{
						try { listentoserver(); }
						catch (IOException e) { e.printStackTrace(); }
					}));
					listener.setCycleCount(Animation.INDEFINITE);
					listener.play();
				}
				
				
			}

			
			
		});
	}
	
	/**
	 * listener to send messages
	 */
	private void sendbuttonlistener()
	{
		sendButton.setOnAction( new EventHandler<ActionEvent >() 
		{

			@Override
			public void handle(ActionEvent event) 
			{
				// TODO Auto-generated method stub
				listener.stop();
				chat_writer.println(Commands.SendMessage);
				chat_writer.flush();
				chat_writer.println(activeconversation);
				chat_writer.flush();
				chat_writer.println(userName+ ": "+encode(chatField.getText(), encryptionoffset));
				chat_writer.flush();
				chatField.setText("");
				listener.play();
			}
			
		});
	}
	
	private void configurechatcontrols() 
	{
			// TODO Auto-generated method stub
				
	}
	
	/**
	 * listen to server commands
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private static void listentoserver() throws IOException 
	{
		
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
					chatArea.clear();
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
	private static void addfriend() 
	{
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