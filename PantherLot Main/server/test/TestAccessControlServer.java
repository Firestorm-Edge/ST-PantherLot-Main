package server.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import server.controller.AccessControlServer;
import server.storage.ParkedUsers;
import server.storage.ParkingSpot;
import static java.lang.Thread.sleep;

public class TestAccessControlServer {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ParkedUsers.instance("garage.txt");
	}

	@Before
	public void setUp() throws Exception {
		resetSpots();
	}
	
	

	@Test
    public void testSendMessage() throws Exception {
        
        AccessControlServer server = new AccessControlServer(1925);
        server.start();                     //start listening for connection.
        
        SpotStub spot = new SpotStub("101",1925);
        spot.start();
        
        sleep(100);
        
        String ans = "";
        int x = 0;
        while(x == 0){
            ans = spot.getAnswer();
            //sleep(100);
            if(ans != ""){
                
                x = 1;
            }
        }
        assertEquals(ans,"Faculty");
        spot.quit();
        resetSpots();
        //System.out.println("sendMessage");
        //String msg = "TEST MESSAGE";
        //PrintWriter pout = null;
    }
    
    @Test
    public void testSendStatus() throws Exception{
        AccessControlServer server = new AccessControlServer(1926);
        server.start();
        
        SpotStub security = new SpotStub("Security",1926);
        security.start();
        
        String ans = "";
        int x = 0;
        while(x == 0){
            ans = security.getAnswer();
            //sleep(100);
            if(ans != ""){
                
                x = 1;
            }
        }
        assertEquals(ans,"successful connection!");
        
        security.resetAnswer();
        security.setReListen();
        server.sendStatus();
        
        ans = "";
        x = 0;
        while(x == 0){
            ans = security.getAnswer();
            //sleep(100);
            if(ans != ""){
                
                x = 1;
            }
        }
        assertEquals(ans,"4   Parking #312  Type:Student     Floor:3  Direction:North Status:Free  Connection:Off");
    }
    
    @Test
    public void testReserveSpot() throws Exception{
        resetSpots();
        
        AccessControlServer server = new AccessControlServer(1927);
        server.start();
        
        SpotStub spot = new SpotStub("101",1927);
        spot.start();
        
        String ans = "";
        int x = 0;
        while(x == 0){
            ans = spot.getAnswer();
            //sleep(100);
            if(ans != ""){
                
                x = 1;
            }
        }
        assertEquals(ans,"Faculty");
        
        spot.resetAnswer();
        spot.setReListen();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spotRef = garage.searchBySpotNumber("101");
        
        server.reserveSpot(spotRef, "1515");
        
        ans = "";
        x = 0;
        while(x == 0){
            ans = spot.getAnswer();
            //sleep(100);
            if(ans != ""){
                
                x = 1;
            }
        }
        assertEquals("reserve",ans);
        
        spot.resetAnswer();
        spot.setReListen();
        
        ans = "";
        x = 0;
        while(x == 0){
            ans = spot.getAnswer();
            //sleep(100);
            if(ans != ""){
                
                x = 1;
            }
        }
        assertEquals("1515",ans);
        
    }
    
    @Test
    public void testIsConnectionAvailableDisconnect() throws Exception{
        resetSpots();
        
        AccessControlServer server = new AccessControlServer(1928);
        server.start();
        
        //assertEquals(false,server.isConnectionAvailable("101"));
       // assertEquals(false,server.isConnectionAvailable("9999"));
        
        SpotStub spot = new SpotStub("101",1928);
        spot.start();
        
        assertEquals(true,server.isConnectionAvailable("101"));
        
        spot.quit();
        sleep(100);
        
        assertEquals(false,server.isConnectionAvailable("101"));
    }
    @Test
    public void testIsConnectionAvailablePreConnect() throws Exception{
        resetSpots();
        
        AccessControlServer server = new AccessControlServer(1910);
        server.start();
        
        assertEquals(false,server.isConnectionAvailable("101"));
       // assertEquals(false,server.isConnectionAvailable("9999"));
        
        SpotStub spot = new SpotStub("101",1910);
        spot.start();
        
        assertEquals(true,server.isConnectionAvailable("101"));
        
        spot.quit();
        
    }
    
    //startServer() can't be accessed outside of a thread or it will lock up the main thread forever, which is bad for Unit testing.
    //However, Run() doesn't do anything but access startServer(), so we start the thread to test it.
    @Test
    public void testRunStartServerSpot()throws Exception{
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spotRef = garage.searchBySpotNumber("101");
        
        assertEquals(null,spotRef.getPrintWriter());
        
        AccessControlServer server = new AccessControlServer(1929);
        server.start();
        SpotStub spot = new SpotStub("101",1929);
        spot.start();
        
        sleep(1000);
        
        assertNotEquals(null,spotRef.getPrintWriter());
    }
    @Test
    public void testRunStartServerInvalidSpot()throws Exception{
        AccessControlServer server = new AccessControlServer(1930);
        server.start();
        SpotStub spot = new SpotStub("999",1930);
        spot.start();
        
        sleep(1000);
        
        String ans = "";
        int x = 0;
        while(x != -1 && x<=50){ //if no response is recieved in 10 seconds, test is deemed failed.
            x++;
            ans = spot.getAnswer();
            sleep(100);
            if(ans != ""){
                
                x = -1;
            }
            
        }
        assertNotEquals("",ans);
    }
    @Test
    public void testRunStartServerSpotStudent()throws Exception{
        AccessControlServer server = new AccessControlServer(1931);
        server.start();
        SpotStub spot = new SpotStub("312",1931);
        spot.start();
        
        sleep(1000);
        
        String ans = "";
        int x = 0;
        while(x != -1 && x<=50){ //if no response is recieved in 10 seconds, test is deemed failed.
            x++;
            ans = spot.getAnswer();
            sleep(100);
            if(ans != ""){
                x = -1;
            }
        }
        assertEquals("Student",ans);
    }
    @Test
    public void testRunStartServerSpotFaculty()throws Exception{
        AccessControlServer server = new AccessControlServer(1932);
        server.start();
        SpotStub spot = new SpotStub("101",1932);
        spot.start();
        
        sleep(1000);
        
        String ans = "";
        int x = 0;
        while(x != -1 && x<=50){ //if no response is recieved in 10 seconds, test is deemed failed.
            x++;
            ans = spot.getAnswer();
            sleep(100);
            if(ans != ""){
                x = -1;
            }
        }
        assertEquals("Faculty",ans);
    }
    @Test
    public void testRunStartServerSpotGuest()throws Exception{
        AccessControlServer server = new AccessControlServer(1933);
        server.start();
        SpotStub spot = new SpotStub("139",1933);
        spot.start();
        
        sleep(1000);
        
        String ans = "";
        int x = 0;
        while(x != -1 && x<=50){ //if no response is recieved in 10 seconds, test is deemed failed.
            x++;
            ans = spot.getAnswer();
            sleep(100);
            if(ans != ""){
                x = -1;
            }
        }
        assertEquals("Guest",ans);
    }
    @Test
    public void testRunStartServerDuplicateSpots()throws Exception{
        AccessControlServer server = new AccessControlServer(1934);
        server.start();
        SpotStub spot = new SpotStub("139",1934);
        spot.start();
        SpotStub spot2 = new SpotStub("139",1934);
        spot2.start();
        
        sleep(1000);
        
        String ans = "";
        int x = 0;
        while(x != -1 && x<=50){ //if no response is received in 10 seconds, test is deemed failed.
            x++;
            ans = spot2.getAnswer();
            sleep(100);
            if(ans != ""){
                x = -1;
            }
        }
        assertEquals("another",ans);
    }
    
    @Test
    public void testWrongUser() throws Exception{
    	AccessControlServer server = new AccessControlServer(1934);
        server.start();
        SpotStub spot = new SpotStub("139",1934);
        spot.start();
        SpotStub security = new SpotStub("security",1934);
        security.start();
        
        security.reListen();
        spot.wrong();
        
        sleep(100);
        
        String ans = "";
        int x = 0;
        while(x != -1 && x<=50){ //if no response is received in 10 seconds, test is deemed failed.
            x++;
            ans = security.getAnswer();
            sleep(100);
            if(ans != ""){
                x = -1;
            }
        }
        assertEquals("wrong",ans);
    }
    
    private void resetSpots(){
        ParkedUsers garage = ParkedUsers.getInstance();
        
        ParkingSpot spot = garage.searchBySpotNumber("101");
        spot.setPrintWriter(null);
        spot.removeAssignedUser();
        
        spot = garage.searchBySpotNumber("312");
        spot.setPrintWriter(null);
        spot.removeAssignedUser();
        
        spot = garage.searchBySpotNumber("139");
        spot.setPrintWriter(null);
        spot.removeAssignedUser();
    }

}
