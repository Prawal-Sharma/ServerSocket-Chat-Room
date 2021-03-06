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

public class Chat_R_Obj extends Observable {
	public String name_of_chat = new String(""); 
	public ArrayList<String> chatters_in_order; 
	public String time = new String(""); 
	public int number_of_chat; 
	
	Chat_R_Obj(ArrayList<String> chatters) throws FileNotFoundException{
		synchronized(ServerMain.o){
			number_of_chat=ServerMain.num_chat;
			ServerMain.num_chat+=1;
			chatters_in_order = new ArrayList<String>();
			//for(int i = 0; i < chatters.size(); i++) {
			//	chatters_in_order.add(chatters.get(i));
			//}
			
			for (String s: chatters){
				chatters_in_order.add(s);
			}
			
			
			Collections.sort(chatters_in_order);
			//for(int i = 0; i < chatters_in_order.size(); i++) {
				//name_of_chat += chatters_in_order.get(i); 
			//}
			
			for (String c: chatters_in_order){
				name_of_chat+=c;
				
			}
			
			System.out.println("Non_Empty Convo");
			System.out.println("name_of_chat: " + name_of_chat);
		
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
		notifyObservers(Commands.UpdateChat+"\n"+name_of_chat);
		System.out.println("Outputing previous convo");
		
		while (read.hasNextLine()){
			Updater(read.nextLine());
		}   
		setChanged();
			// secret code
		    notifyObservers(Commands.StopUpdating);
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
	  		
		notifyObservers(Commands.RecieveMessage+"\n"+name_of_chat+"\n"+update_message+"\n");
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
