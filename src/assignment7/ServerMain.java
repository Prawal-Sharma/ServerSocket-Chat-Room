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

import java.io.*;
import java.net.*;
import java.util.*; 



public final class ServerMain 
{
	// Data
	public static File Info; 
	
	// UserData
	public static File PeopleInfo; 
	
	// chatnumber
	public static int num_chat = 0; 
	
	// Data Structures
	
	// People who are online
	public static ArrayList<Integer> online = new ArrayList<Integer>(); 
	
	// bemyfriend
	public static HashMap<String, BufferedReader> friendreq; 
	
	// Sockusernames
	public static ArrayList<String> names_sock = new ArrayList<String>(); 
	
	// UserSocks 
	public static ArrayList<Socket> user_socket = new ArrayList<Socket>(); 
	
	//convolist
	public static HashMap <String, Chat_R_Obj> message_list = new HashMap<String, Chat_R_Obj>(); 
	
	//conectedusr
	public static HashMap<String, O_Print> users_connected; 
	
	// user name and password
	public static HashMap<String, String> unp = new HashMap<String, String>(); 
	
	public static Object o = new Object(); 
	
	public static void main(String[] args)  throws Exception
	{
		try{
			boolean controller=true;
			
			ServerSocket Server  = new ServerSocket(4580);
			FileStruct();
			 users_connected = new HashMap<String, O_Print>();
			 friendreq  =new HashMap<String,BufferedReader> ();
			
			while (controller){
				
				Socket newsock = Server.accept();
				System.out.println("Network Established");
				
				
				Client_H newclient =new Client_H(newsock);
				
				Thread t = new Thread(newclient);
				t.start();
				
			
			}
			Server.close();
			
		   }catch(Exception e){e.printStackTrace();}
		
	}
	
	public static void FileStruct() throws Exception{
		String nameJAR = new java.io.File(ServerMain.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
		nameJAR=URLDecoder.decode(nameJAR,"UTF-8");
		
		 String registry = ServerMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		 registry=URLDecoder.decode(registry,"UTF-8");
		 registry=registry.substring(0,registry.length()-nameJAR.length());
		 
	
		
		   Info = new File(registry+"information");
		    boolean exists = Info.exists();
		    if(!exists){
		    	Info.mkdir();
		    }
		   PeopleInfo = new File(Info.getAbsolutePath()+java.io.File.separator+"userdata.txt");
		   boolean existsa = PeopleInfo.exists();
		   if(!existsa){
			   PeopleInfo.createNewFile();
		 }
		   
		   Scanner scanfordata = new Scanner(PeopleInfo);
		   while(scanfordata.hasNext()){
			   String uname = scanfordata.next();
			   String pword = scanfordata.next();
			   unp.put(uname,pword);
			   user_socket.add(null);
			   names_sock.add(uname);
			   online.add(0);
			  
			   
		   }
		  scanfordata.close();

		
		
	}
	
	
	
}