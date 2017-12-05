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
			for(int i = 0; i < chatters.size(); i++) {
				chatters_in_order.add(chatters.get(i));
			}
			
			
			Collections.sort(chatters_in_order);
			for(int i = 0; i < chatters_in_order.size(); i++) {
				name_of_chat += chatters_in_order.get(i); 
			}
			
			System.out.println("Non_Empty Convo");
		
	ServerMain.message_list.put(name_of_chat, this);
			
		}
		
		for(int i = 0; i < chatters.size(); i++) {
			System.out.println("Not a null value");
			this.addObserver(ServerMain.users_connected.get(chatters.get(i)));
		}
		
		
		
	}
	
	
	public void LogPrint(String arg) throws IOException{
		File getter = new File(ServerMain.Info.getAbsolutePath()+java.io.File.separator+arg+".txt");
		if(getter.exists() == false){
			getter.createNewFile();
		}
		Scanner read = new Scanner(getter);
		setChanged();
		notifyObservers("UpdateChat\n"+name_of_chat);
		System.out.println("Outputing previous convo");
		
		while (read.hasNextLine()){
			Updater(read.nextLine());
		}   
		setChanged();
			// secret code
		    notifyObservers("qwertyuiop");
			read.close();
	}
	
	public void Updater(String update_message) throws IOException{
		time = time + update_message;
		 System.out.println("Updater is not null");
		 System.out.println(name_of_chat);
		setChanged();
		
		notifyObservers(update_message+"\n");
	}
	
	
	public void ChatUpdate(String update_message) throws IOException{
		time = time + update_message;
		
		setChanged();
	  System.out.println("Letting Observers Know");
	  		
		notifyObservers("Addchit\n"+name_of_chat+"\n"+update_message+"\n");
		System.out.println("UC"+name_of_chat);
		System.out.println(update_message);
		File random;
		PrintWriter c_track;
       random = new File(ServerMain.Info.getAbsolutePath()+java.io.File.separator+name_of_chat+".txt");
       if(random.exists() == false){
    	   random.createNewFile();
       }
        try {
			c_track = new PrintWriter(new FileWriter(random,true));
			
		
		 c_track.append(update_message);
		 c_track.append("\n");
		 c_track.close();
        } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	

}
