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


//add as we go

//contains all the commands we will send between server and client to do specified action
//strings have *&$ at the front to make it unique and unreplicable 
public final class Commands{
	public static final String InvalidUsername = "*&$invalusername";
	public static final String MakeUser = "*&$makeusr";
	public static final String Login = "*&$login";
	public static final String ChangePassword = "*&$changepswd";
	public static final String UpdateChat = "*&$updatechat";
	public static final String Quit = "*&$Quit";
	public static final String CloseChat = "*&$closechat";
	public static final String EndofList = "*&$endof list";
	public static final String StopUpdating = "*&$stopupdating";
	public static final String AddFriend= "*&$AddFriend";
	public static final String FriendConfirm = "*&$friendcfm";
	public static final String FriendReject = "*&$friendrej";
	public static final String GetFriendList = "*&$friendlist";
	public static final String MakeNewChat = "*&$makenewchat";
	public static final String LoginSuccess = "*&$loginsucess";
	public static final String RecieveMessage = "*&$addchatmessage";		//server to client for client to recieve and output a message sent to them
	public static final String SendMessage ="*&$addchatmessage";
}