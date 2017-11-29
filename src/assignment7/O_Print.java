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
