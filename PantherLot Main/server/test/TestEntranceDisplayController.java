package server.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.PrintWriter;

import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import client.maindisplay.DisplayDirections;
import client.maindisplay.ParkingNotification;
import client.maindisplay.SpotNumberDisplay;
import client.maindisplay.WelcomeDisplay;
import server.controller.EntranceDisplayController;
import server.controller.GuestUser;
import server.storage.ParkedUsers;
import server.storage.ParkingSpot;


@RunWith(PowerMockRunner.class)
@PrepareForTest(EntranceDisplayController.class)
public class TestEntranceDisplayController {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ParkedUsers.instance("garage.txt");
	}

	@Test
    public void testFakeID() throws Exception{
        String testedSpot = "139";
        String userType = "asdf";
        String testedID = "1";
        String expectValid = "Invalid ID! ";
        String expectAssign = "Assigning guest parking spot";
        String direction = new GuestUser().toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        assertEquals("Guest",spot.getUser().toString());
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
	
	@Test
    public void testFakeIDNoSpot() throws Exception{
        String userType = "asdf";
        String testedID = "1";
        String expectValid = "Invalid ID! ";
        String expectAssign = "There are no guest spots avialable";
        String direction = new GuestUser().toString();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,null,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        
        //assertEquals();
        
    }
    
    @Test
    public void testGuest() throws Exception{
        String testedSpot = "139";
        String userType = "guest";
        String testedID = "";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning guest parking spot";
        String direction = new GuestUser().toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        assertEquals("Guest",spot.getUser().toString());
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testStudent() throws Exception{
        String testedSpot = "312";
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning student parking spot";
        String direction = "Student";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        assertEquals("Student",spot.getUser().toString());
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testStudentGuest() throws Exception{
        String testedSpot = "139";
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "There are no student spots available";
        String direction = "Student";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        try{
        assertEquals("Student",spot.getUser().toString());
        }
        catch(Exception e){
        	assertFalse(true);
        }
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testFaculty() throws Exception{
        String testedSpot = "101";
        String userType = "faculty";
        String testedID = "1663314";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning faculty parking spot";
        String direction = "Faculty";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        assertEquals("Faculty",spot.getUser().toString());
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testFacultyGuest() throws Exception{
        String testedSpot = "139";
        String userType = "faculty";
        String testedID = "1663314";
        String expectValid = "Valid Request ";
        String expectAssign = "There are no faculty spots available";
        String direction = "Student";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        try{
        	assertEquals("Faculty",spot.getUser().toString());
        }
        catch(Exception e){
        	assertFalse(true);
        }
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testStudentFaculty() throws Exception{
        String testedSpot = "101";
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "There are no student spots available";
        String direction = "Student";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        assertEquals(null,spot.getUser());
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testFacultyStudent() throws Exception{
        String testedSpot = "312";
        String userType = "faculty";
        String testedID = "1663314";
        String expectValid = "Valid Request ";
        String expectAssign = "There are no faculty spots available";
        String direction = "Faculty";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        assertEquals(null,spot.getUser());
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testHandicapped() throws Exception{
    	String testedSpot = "120";
        String userType = "handicap";
        String testedID = "1654333";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning handicapped parking spot";
        String direction = "Handicapped";
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        test.runDisplays();
        
        spot = garage.searchBySpotNumber(testedSpot);
        assertEquals("Handicapped",spot.getUser().toString());
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    
    private void runDisplayMockSetup(WelcomeDisplay wDisp, ParkingNotification pDisp, SpotNumberDisplay sDisp, DisplayDirections dDisp, ParkingSpot spot,  String userType, String testedID, String expectValid, String expectAssign, String direction) throws Exception{
        wDisp.setLocation(new Point(0,0));                      //      line 109
        wDisp.setVisible(true);                                 //      line 110
        EasyMock.expect(wDisp.displayNext()).andReturn(false);
        EasyMock.expect(wDisp.displayNext()).andReturn(true);            //skips first dialog box.   line 111
        EasyMock.expect(wDisp.getUserType()).andReturn(userType);          //sets the UserType.  line 123
        EasyMock.expect(wDisp.getID()).andReturn(testedID);             //sets the UserID.  Line 124
        pDisp.updateParkingNotification(expectValid, expectAssign);  //      line 132
        EasyMock.expect(wDisp.getLocation()).andReturn(new Point(0,0));  //sets a point?  line 139
        pDisp.setLocation(new Point(0,0));                      //      line 140
        pDisp.setVisible(true);                                 //      142
        EasyMock.expect(pDisp.displayNext()).andReturn(false);
        EasyMock.expect(pDisp.displayNext()).andReturn(true);            //skips second dialog box.  line 143
        EasyMock.expect(pDisp.isCanceled()).andReturn(false);            //sets whether or not the menu was cancelled.  line 155
        EasyMock.expect(pDisp.getLocation()).andReturn(new Point(0,0));  //      line 171
        if(spot != null)sDisp.updateParkingSpotNumberLabel("Your spot number is " + spot.getParkingNumber()); //      line 173
        sDisp.setLocation(new Point(0,0));                      //      line 175
        sDisp.setVisible(true);                                 //      line 176
        EasyMock.expect(sDisp.displayNext()).andReturn(false);
        EasyMock.expect(sDisp.displayNext()).andReturn(true);            //skips third dialog box.  line 177
        EasyMock.expect(sDisp.isCanceled()).andReturn(false);            //sets whether or not the menu was cancelled.  line 188
        EasyMock.expect(sDisp.getLocation()).andReturn(new Point(0,0));  //      line 194
        dDisp.setLocation(new Point(0,0));
        if(spot != null)dDisp.updateDirections("1. Go to floor #" 
                + spot.getFloor() + "\n2. Head to the " 
                    + spot.getDirections() + " part." +
                    "\n3. Park on " + direction 
                            + " spot labeled #" + spot.getParkingNumber()+ ".");
        dDisp.setVisible(true);                                 //      line 202
        EasyMock.expect(dDisp.displayNext()).andReturn(false);
        EasyMock.expect(dDisp.displayNext()).andReturn(true);            //skips fourth dialog box. line 203
        EasyMock.expect(dDisp.isCanceled()).andReturn(false);            //sets fourth opportunity to cancel.  line 214
        EasyMock.expect(dDisp.getLocation()).andReturn(new Point(0,0));  //      line 221
        
        
    }
    
    
    @Test
    public void testIsDuplicate()throws Exception{  //Makes sure isDuplicate returns false (no duplicate), then adds a user to a spot, and checks isDuplicate again, to make sure it can find the duplicate.
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        assertEquals(false,test.isDuplicate("2223432"));                        //Makes sure value isn't duplicated at the start.
        
        
        String testedSpot = "312";
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning student parking spot";
        String direction = "Student";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        PowerMock.reset(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        test.runDisplays();
        
        assertEquals(true,test.isDuplicate("2223432"));
        
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void getDuplicate() throws Exception{
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);      //setup Mocks
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        String testedSpot = "312";      //changing varialbes and checks
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning student parking spot";
        String direction = "Student";//new StudentUser("","").toString();
        
        String expectAssign2 = "There are no student spots available";
        
        ParkedUsers garage = ParkedUsers.getInstance();     //create spot from variables
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign2,direction);
        pDisp.updateParkingNotification("Duplicate ID! ", "Press next to notify the security officer");
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        
        assertEquals(false,test.getDuplicate());
        test.runDisplays();
        assertEquals(false,test.getDuplicate());
        test.runDisplays();
        assertEquals(true,test.getDuplicate());
        
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testGetDuplicateParkingSpot() throws Exception{
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);      //setup Mocks
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        String testedSpot = "312";      //changing varialbes and checks
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning student parking spot";
        String direction = "Student";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();     //create spot from variables
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        
        assertEquals(null,test.getDuplicateParkingSpot(testedID));
        test.runDisplays();
        assertEquals(spot,test.getDuplicateParkingSpot(testedID));
        
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    
    @Test
    public void getCurrentUserID() throws Exception{
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);      //setup Mocks
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        
        String testedSpot = "312";      //changing varialbes and checks
        String userType = "student";
        String testedID = "2223432";
        String expectValid = "Valid Request ";
        String expectAssign = "Assigning student parking spot";
        String direction = "Student";//new StudentUser("","").toString();
        
        ParkedUsers garage = ParkedUsers.getInstance();     //create spot from variables
        ParkingSpot spot = garage.searchBySpotNumber(testedSpot);
        spot.setPrintWriter(new PrintWriter(System.out));
        spot.removeAssignedUser();
        
        runDisplayMockSetup(wDisp,pDisp,sDisp,dDisp,spot,userType,testedID,expectValid,expectAssign,direction);
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        
        assertEquals("",test.getCurrentUserID());
        test.runDisplays();
        assertEquals("2223432",test.getCurrentUserID());
        
        spot.removeAssignedUser();
        spot.setPrintWriter(null);
    }
    
    @Test
    public void testConstructor() throws Exception{
        WelcomeDisplay wDisp = PowerMock.createMock(WelcomeDisplay.class);      //setup Mocks
        PowerMock.expectNew(WelcomeDisplay.class).andReturn(wDisp).anyTimes();
        ParkingNotification pDisp = PowerMock.createMock(ParkingNotification.class);
        PowerMock.expectNew(ParkingNotification.class).andReturn(pDisp).anyTimes();
        SpotNumberDisplay sDisp = PowerMock.createMock(SpotNumberDisplay.class);
        PowerMock.expectNew(SpotNumberDisplay.class).andReturn(sDisp).anyTimes();
        DisplayDirections dDisp = PowerMock.createMock(DisplayDirections.class);
        PowerMock.expectNew(DisplayDirections.class).andReturn(dDisp).anyTimes();
        PowerMock.replay(wDisp, WelcomeDisplay.class, pDisp, ParkingNotification.class, sDisp, SpotNumberDisplay.class, dDisp, DisplayDirections.class);
        
        EntranceDisplayController test = new EntranceDisplayController();
        assertEquals("",test.getCurrentUserID());
        assertEquals(false,test.getDuplicate());
        assertEquals(null,test.getSpot());
        
    }

}
