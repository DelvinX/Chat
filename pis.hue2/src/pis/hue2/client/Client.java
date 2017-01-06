package pis.hue2.client;
//GUI

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

public class Client {

	Socket client;
	PrintWriter writer;
	BufferedReader reader;
	
	public static void main(String [] args){
		Client c = new Client ();
		c.createGUI();
		  
	}
	
	public void createGUI(){
		
		
		
	}
}
