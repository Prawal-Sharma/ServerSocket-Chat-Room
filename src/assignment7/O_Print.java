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
public class O_Print extends PrintWriter implements Observer {

	public O_Print(OutputStream val) {
		super(val); 
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Printing to those interested (Observers)");
		System.out.println(arg);
		this.println(arg);
		this.flush();
		
	}

}
