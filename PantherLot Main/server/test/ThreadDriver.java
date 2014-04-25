package server.test;

import server.controller.AccessControlMain;
import client.maindisplay.DisplayDirections;
import client.maindisplay.ParkingNotification;
import client.maindisplay.SpotNumberDisplay;
import client.maindisplay.WelcomeDisplay;


public class ThreadDriver extends Thread{
	
	WelcomeDisplay wDisp;
	ParkingNotification pDisp;
	SpotNumberDisplay sDisp;
	DisplayDirections dDisp;
	
	
		public ThreadDriver(){
		}
		
		
		@Override
		public void run(){
			
			try{
			  
				
				String[] x = {};
		        AccessControlMain.main(x);
		        
		        
			}catch(Exception e){
				System.out.println(e);
		    }
		}
		
	  

}
