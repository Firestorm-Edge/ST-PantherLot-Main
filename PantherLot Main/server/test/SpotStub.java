package server.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SpotStub extends Thread {
	private Socket sock;
    private String spotNumber = "101", address = "localhost", spotType;
    private String answer = "";
    private int port = 777;
    private InputStream in;
    private OutputStream out;
    private Scanner scan;
    private PrintWriter printOut;
    
    private String command = "";
    
    //Constructor:  connects to listener, then listens for responses.
    public SpotStub(String output, int port){
        spotNumber = output;
        this.port = port;
    }
    
    @Override
    public void run(){
        
        try{
            System.out.println("check 0");
            
            sock = new Socket( address, port );
            
            in = sock.getInputStream();
            out = sock.getOutputStream();
        
            scan = new Scanner(in);                     //reads input
            printOut = new PrintWriter(out, true);      //sends output
            System.out.println("check 0.5");
            printOut.println(spotNumber);
            System.out.println("check 0.75");
            
        
            System.out.println("check 1");
        
            String oneLine = scan.nextLine();
            System.out.println(oneLine);
            System.out.println("bleh");
            answer = oneLine;
        
        
            System.out.println("check 2");
        
            //WAITS FOR MORE COMMANDS
            int x = 0;
            while (x == 0){
                sleep(50);
                if (command.equals("reListen")){
                    System.out.println("COMMAND GET!");
                    reListen();
                    command = "";
                }
                else if(command.equals("wrong")){
                	System.out.println("COMMAND GET! WROONG");
                    wrong();
                    command = "";
                }
                else if(command.equals("leave")){
                	System.out.println("COMMAND GET! leaves");
                    leave();
                    command = "";
                }
                else if(command == "quit"){
                    x++;
                }
            }
            
        
        
        }
        catch(Exception e){
            System.out.println("WTF AM BROKES");
        }
    }
    
    synchronized public String getAnswer(){
        return answer;
    }
    
    public void setReListen(){
        this.command = "reListen";
        System.out.println("attempting command R.");
    }
    public void setWrong(){
    	this.command = "wrong";
    }
    public void setLeave(){
    	this.command = "leave";
    }
    
    public void resetAnswer(){
        answer = "";
    }
    public void quit(){
        this.command = "quit";
    }
    void reListen(){
        int x = 0;
        while (x == 0){
            String oneLine = scan.nextLine();
            if (oneLine != ""){
                answer = oneLine;
                x++;
            }
        }
        System.out.println(answer);
    }
    void wrong(){
    	printOut.println("wrong");
    }
    void leave(){
    	printOut.println("leave");
    }
    
    
    
    
}
