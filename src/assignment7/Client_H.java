package assignment7;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client_H implements Runnable{

	private BufferedReader reader ;
	private O_Print writer;
	private  Socket thissocket;
	private String username;
	private String password;
	private ArrayList <String> friendlist = new ArrayList<String>();
	private boolean loggedin =false;
	public Client_H(Socket clientsock){
		this.thissocket= clientsock;
		try{
               reader = new BufferedReader(new InputStreamReader(thissocket.getInputStream()));
               writer = new O_Print ((thissocket.getOutputStream()));
             
			
	}catch(Exception f){f.printStackTrace();}
	}
	
	
	@Override
	public void run() {
		
		
	}

}
