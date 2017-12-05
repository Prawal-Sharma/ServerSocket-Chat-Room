package assignment7;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client_H implements Runnable {

	private BufferedReader reader;
	private O_Print writer;
	private Socket thissocket;
	private String username;
	private String password;
	private ArrayList<String> friendlist = new ArrayList<String>();
	private boolean loggedin = false;

	public Client_H(Socket clientsock) {
		this.thissocket = clientsock;
		try {
			reader = new BufferedReader(new InputStreamReader(thissocket.getInputStream()));
			writer = new O_Print((thissocket.getOutputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean incomplete = true;
		while (incomplete && !(thissocket.isClosed())) {

			String event = null;
			try {
				event = reader.readLine();

				while (event == null) {
					event = reader.readLine();
				}

				
				String chkusername = reader.readLine();
				
				String chkpassword = reader.readLine();
				

			
				if (event.equals(Commands.MakeUser)) {

					if (ServerMain.unp.containsKey(chkusername)) {
						writer.println("exists");
						writer.flush();

					}

					else {

						writer.println(Commands.LoginSuccess);
						writer.flush();

						password = chkpassword;
						username = chkusername;
						ServerMain.user_socket.add(thissocket);
						ServerMain.names_sock.add(chkusername);
						ServerMain.unp.put(chkusername, chkpassword);
						PrintWriter uptracker;
						uptracker = new PrintWriter(new FileWriter(ServerMain.PeopleInfo, true));

						uptracker.append(chkusername);
						uptracker.append("\n");
						uptracker.append(chkpassword);
						uptracker.append("\n");
						uptracker.close();

						ServerMain.online.add(1);
						incomplete = false;
						loggedin = true;
						ServerMain.users_connected.put(chkusername, writer);
						ServerMain.friendreq.put(chkusername, reader);

					}
				}

				else if (event.equals(Commands.Login) && ServerMain.unp.containsKey(chkusername)) {

					String value = (String) ServerMain.unp.get(chkusername);
					if (value.equals(chkpassword)) {
						writer.println(Commands.LoginSuccess);
						writer.flush();
						password = chkpassword;
						username = chkusername;
						for (int i = 0; i < ServerMain.user_socket.size(); i++) {
							if (ServerMain.names_sock.get(i).equals(username)) {
								ServerMain.user_socket.set(i, thissocket);
								ServerMain.online.add(1);
								incomplete = false;
								loggedin = true;
								ServerMain.users_connected.put(username, writer);
								ServerMain.friendreq.put(username, reader);
							}

						}

					}

					else {
						writer.println("Invalid password");
						writer.flush();
					}
				}

				else {
					writer.println("Invalid password");
					writer.flush();

				}
			} catch (Exception j) {
				j.printStackTrace();
			}
			

		}

		while (loggedin) {
			System.out.println("Here");
			String command = "";
			try {

				while ((command = reader.readLine()) == null) {
					Thread.sleep(150);
				}
				System.out.println("should switch");
				switch (command) {
				
				
				case Commands.Quit:
					File popfriends = new File(
							ServerMain.Info.getAbsolutePath() + java.io.File.separator + username + ".txt");
					if (!popfriends.exists()) {
						popfriends.createNewFile();
					}
					PrintStream pr = new PrintStream(popfriends);
					if (!friendlist.isEmpty()) {
						for (String jh : friendlist) {
							pr.println(jh);
							System.out.println(jh);
						}
					}
					pr.close();
					ServerMain.users_connected.remove(username);
					ServerMain.friendreq.remove(username);
					loggedin = false;

					break;
					
				case Commands.MakeNewChat:

					ArrayList<String> peoplechatting = new ArrayList<String>();
					System.out.println("Making a new chat");
					String p;
					while (!(p = reader.readLine()).equals(Commands.EndofList)) {
						peoplechatting.add(p);
						System.out.println(p);
					}
					ArrayList<String> checkifexists = new ArrayList<String>();
					for (String count : peoplechatting) {
						checkifexists.add(count);
					}
					Collections.sort(checkifexists);
					String g = "";
					for (String q : checkifexists) {
						g += q;
					}

					File checker;

					checker = new File(ServerMain.Info.getAbsolutePath() + java.io.File.separator + g + ".txt");
					boolean qq = checker.exists();
					System.out.println(qq + "check ");

					if (qq) {
						writer.println(Commands.UpdateChat+"\n" + g);
						
						Scanner updation = new Scanner(checker);
						while (updation.hasNextLine()) {

							writer.println(updation.nextLine());

						}
						updation.close();
						writer.println(Commands.StopUpdating);
					}

					if (!ServerMain.message_list.isEmpty()) {
						System.out.println("Check one");
						if (ServerMain.message_list.containsKey(g)) {
							System.out.println("Printing past");

							ServerMain.message_list.get(g).LogPrint(g);
						}

						else {
							System.out.println("Creating chatroom");

							new Chat_R_Obj(peoplechatting);
						}
					}
					if (ServerMain.message_list.isEmpty()) {

						new Chat_R_Obj(peoplechatting);
					}

					
					break;
					// eric
				case Commands.CloseChat:
					String b = reader.readLine();
					ServerMain.message_list.get(b).deleteObserver(ServerMain.users_connected.get(username));

					break;
				case Commands.ChangePassword:
					String newpassword = reader.readLine();
					File changepword = new File(
							ServerMain.Info.getAbsolutePath() + java.io.File.separator + "userdata.txt");
					if (!changepword.exists()) {
						changepword.createNewFile();
					}
					ArrayList<String> up = new ArrayList<String>();
					Scanner sca = new Scanner(changepword);
					while (sca.hasNextLine()) {
						String temporary = sca.nextLine();
						up.add(temporary);
						if (temporary.equals(username)) {

							sca.nextLine();
							up.add(newpassword);

						} else {
							up.add(sca.nextLine());
						}
					}
					PrintWriter pra = new PrintWriter(changepword);
					for (String z : up) {
						pra.println(z);
					}
					pra.close();
					ServerMain.unp.put(username, newpassword);
					break;
				
				// eric 
				case Commands.GetFriendList:
					if (!friendlist.isEmpty()) {
						for (String jk : friendlist) {
							writer.println(jk);
							writer.flush();

						}
					}

					File readfriend = new File(
							ServerMain.Info.getAbsolutePath() + java.io.File.separator + username + ".txt");
					if (!readfriend.exists()) {
						readfriend.createNewFile();
					}
					Scanner sc = new Scanner(readfriend);
					while (sc.hasNextLine()) {
						String j = sc.nextLine();
						System.out.println(j);
						if (!friendlist.contains(j)) {
							friendlist.add(j);
						}
						writer.println(j);
						writer.flush();
					}
					sc.close();

					writer.println(Commands.EndofList);
					writer.flush();

					break;
					
					// eric
					
				case Commands.AddFriend: 
					String b1 = reader.readLine();
					
					if (ServerMain.users_connected.containsKey(b1)) {
						ServerMain.users_connected.get(b1).println(Commands.AddFriend);
						ServerMain.users_connected.get(b1).flush();
						ServerMain.users_connected.get(b1).println(b1);
						ServerMain.users_connected.get(b1).flush();
						ServerMain.users_connected.get(b1).println(username);
						ServerMain.users_connected.get(b1).flush();
						
						friendlist.add(b1);
//						writer.println("newfriend");
//						writer.flush();
						
					}
					
					else {

						writer.println("No Such online User");
						writer.flush();
					}

					
					break;
				case Commands.FriendConfirm:
					String b11= reader.readLine();
					
					if (ServerMain.users_connected.containsKey(b11)) {
						ServerMain.users_connected.get(b11).println(Commands.FriendConfirm);
						ServerMain.users_connected.get(b11).flush();
						ServerMain.users_connected.get(b11).println(b11);
						ServerMain.users_connected.get(b11).flush();
						ServerMain.users_connected.get(b11).println(username);
						ServerMain.users_connected.get(b11).flush();
						
						friendlist.add(b11);
					}
					
					
					/* eric
				case "DeleteFriend": 
					String oldfriend = null;
					while (((oldfriend = reader.readLine()) == null)) {
					}
					while (!(oldfriend.equals("Over"))) {
						for (int l = 0; l < friendlist.size(); l++) {
							if (friendlist.get(l).equals(oldfriend)) {
								friendlist.remove(l);

							}
						}
						oldfriend = reader.readLine();
					}

					break;
					
			*/
					// eric
				case Commands.SendMessage:
					System.out.println(Commands.RecieveMessage);
					String identifier = reader.readLine();
					System.out.println("q not null");
					String message = reader.readLine();
					System.out.println("r not null");
					ServerMain.message_list.get(identifier).ChatUpdate(message);
					System.out.println("s not null");

					break;

				}
			} catch (Exception k) {
				k.printStackTrace();
			}

		}

	}

}
