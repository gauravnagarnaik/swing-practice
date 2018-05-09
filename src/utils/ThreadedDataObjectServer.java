package utils;


import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedDataObjectServer {
    public static void main(String[] args ) {
	
      try {
          ServerSocket s = new ServerSocket(8000);
          for (;;) {
             Socket incoming = s.accept( );
             new ThreadedDataObjectHandler(incoming).start();
	   	 }   
      }
      catch (Exception e) {
          e.printStackTrace();
      } 
   } 
}