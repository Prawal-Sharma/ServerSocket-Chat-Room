package assignment7;
import java.io.*;
import java.net.*;
import java.util.*;

public class Chat_R_Obj extends Observable {
	public String name_of_chat = ""; 
	public ArrayList<String> chatters_in_order; 
	public String time = ""; 
	public int number_of_chat; 
	
	Chat_R_Obj(ArrayList<String> chatters) throws FileNotFoundException{
		synchronized(ServerMain.o){
			number_of_chat=ServerMain.num_chat;
			ServerMain.num_chat+=1;
			chatters_in_order = new ArrayList<String>();
			for (String s: chatters){
				chatters_in_order.add(s);
			}
			
			Collections.sort(chatters_in_order);
			for (String g: chatters_in_order){
				name_of_chat+=g;
				
			}
			System.out.println("Conversation Is Not Empty");
		
	ServerMain.message_list.put(name_of_chat, this);
			
		}
		for(String p : chatters){
			 System.out.println("Not a null value");
			this.addObserver(ServerMain.users_connected.get(p));
		}
		
		
	}
	
	
	public void LogPrint(String arg) throws IOException{
		File tracker = new File(ServerMain.Info.getAbsolutePath()+java.io.File.separator+arg+".txt");
		if(!tracker.exists()){
			tracker.createNewFile();
		}
		Scanner read = new Scanner(tracker);
		setChanged();
		notifyObservers("UpdateChat\n"+name_of_chat);
		System.out.println("Printing old cha");
		
		while (read.hasNextLine()){
			Updater(read.nextLine());
		}   setChanged();
		    notifyObservers("zxcvbnm");
			read.close();
	}
	
	public void Updater(String update_message) throws IOException{
		time+=update_message;
		 System.out.println("updatec not null");
		 System.out.println(name_of_chat);
		setChanged();
		
		notifyObservers(update_message+"\n");
	}
	
	
	public void ChatUpdate(String update_message) throws IOException{
		time+=update_message;
		
		setChanged();
	  System.out.println("Notifying observers");
		notifyObservers("Addchit\n"+name_of_chat+"\n"+update_message+"\n");
		System.out.println("UC"+name_of_chat);
		System.out.println(update_message);
		File nonsense;
		PrintWriter convotracker;
       nonsense = new File(ServerMain.Info.getAbsolutePath()+java.io.File.separator+name_of_chat+".txt");
       if(!nonsense.exists()){
    	   nonsense.createNewFile();
       }
        try {
			convotracker = new PrintWriter(new FileWriter(nonsense,true));
			
		
		 convotracker.append(update_message);
		 convotracker.append("\n");
		 convotracker.close();
        } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	

}
