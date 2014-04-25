package server.test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

import java.awt.Point;
import java.io.PrintWriter;

import javax.swing.UIManager;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;

import client.maindisplay.DisplayDirections;
import client.maindisplay.ParkingNotification;
import client.maindisplay.SpotNumberDisplay;
import client.maindisplay.WelcomeDisplay;
import server.controller.AccessControlMain;
import server.controller.AccessControlServer;
import server.controller.EntranceDisplayController;
import server.controller.StudentUser;
import server.storage.ParkedUsers;
import server.storage.ParkingSpot;

@PowerMockIgnore("javax.swing.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(TestSubsystem.class)
public class TestSubsystem {

	
	
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ParkedUsers.instance("garage.txt");
	}

	@Before
	public void setUp() throws Exception {
		reset();
	}
	
	private void reset(){
		ParkedUsers garage = ParkedUsers.getInstance();
		ParkingSpot spot = garage.searchBySpotNumber("312");
		spot.setPrintWriter(null);
		spot.removeAssignedUser();
	}

	
	
	@Test
	public void testReportDuplicate() throws Exception{
		ParkedUsers garage = ParkedUsers.getInstance();
		ParkingSpot spot = garage.searchBySpotNumber("312");
		spot.setPrintWriter(new PrintWriter(System.out));		//2223432
		
		
		String testedSpot = "312";
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning student parking spot";
        String direction = "Student";
		
		WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        TestEntranceDisplayController.runDisplayMockSetup(wDisp, pDisp, sDisp, dDisp, spot, userType, testedID, expectValid, expectAssign, direction);
        
        expectValid = "Duplicate ID! ";
        expectAssign = "Press next to notify the security officer";
        direction = "Student";
        
        TestEntranceDisplayController.runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
		
		
        
		ThreadDriver thread = new ThreadDriver();
		thread.start();
        
		
		SpotStub stub = new SpotStub("security", 3738);
		stub.start();
		
		
		String ans = "";
		int x = 0;
		while(x != -1 && x < 100){ //if no response is received in 10 seconds, test is deemed failed.
            x++;
            stub.setReListen();
            sleep(100);
            ans = stub.getAnswer();
            sleep(100);
            if(ans.equals("duplicate")){
                x = -1;
            }
            else{
            	System.out.println(ans);
            }
        }
        assertEquals("duplicate",ans);
        stub.quit();
	}
	
	
	
	
	@Test
	public void testSpots(){
		AccessControlServer ACS = new AccessControlServer(8857);
		ACS.start();
		
		SpotStub stub = new SpotStub("312",8857);
		stub.start();
		
		EntranceDisplayController EDC = new EntranceDisplayController();
		EDC.runDisplays();//2223432
		
		ParkedUsers garage = ParkedUsers.getInstance();
		ParkingSpot spot = garage.searchBySpotNumber("312");
		assertEquals(spot.getUser().toString(),"Student");
	}
	
	@Test
	public void testReserve() throws Exception{
		ThreadDriver thread = new ThreadDriver();
		thread.start();
		
		SpotStub stub = new SpotStub("312",3738);
		
		String ans = "";
		int x = 0;
		while(x != -1 && x < 100){ //if no response is received in 10 seconds, test is deemed failed.
            x++;
            stub.setReListen();
            sleep(100);
            ans = stub.getAnswer();
            sleep(100);
            if(ans.equals("reserve")){
                x = -1;
            }
            else{
            	System.out.println(ans);
            }
        }
        assertEquals("reserve",ans);
        stub.quit();
	}
}
