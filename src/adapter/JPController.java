/**
 * @author Ariana Fairbanks
 */

package adapter;

import java.io.File;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import model.JPSQLiteData;
import view.Frame;

public class JPController 
{
	public static JPanel errorPanel;
	public int dataRequested;
	public static int people, members, adults, feasts;
	public static String[] memberDataTableHeader = {"Last Name", "First Name", "SCA Name", "Membership #", "Expiration Date", "Is An Adult"},
			attendanceDataTableHeader = {"Last Name", "First Name", "Is An Adult", "Is A Member", "Attending Feast"};
	// Would probably use enums for tables with String[] values representing headers
	// if this were a larger/more serious program (if I weren't doing it for free).
	private JPSQLiteData database;
	private Frame frame;
	private JPViewStates state;
	
	public void start()
	{
		errorPanel = new JPanel();
		dataRequested = 0;
		clearTotals();
		database = new JPSQLiteData();
		database.getTotals();
		frame = new Frame(this);
		state = JPViewStates.DEFAULT;
	}
	
	public void changeState(JPViewStates nextState)
	{
		state = nextState;
		frame.updateState(state);
	}
		
	public void clearMemberData()
	{	database.clearMemberData();	}
	
	public ResultSet getMemberData()
	{	return database.getMemberData();	}
	
	public boolean addMemberData(String lastName, String firstName, String SCAName, int membershipNumber, String expirationDate,  boolean isAdult)
	{	return database.addMember(lastName, firstName, SCAName, membershipNumber, expirationDate, isAdult);	}
	
	public boolean deleteMemberData(String lastName, String firstName)
	{	return database.deleteMember(lastName, firstName);	}
	
	public void exportMembers(JTable table, File file)
	{
		boolean result = database.exportMembers(table, file);
		if(result)
		{	JOptionPane.showMessageDialog(errorPanel, "Member data successfully exported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong.", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public void importMembers(File file)
	{	
		int result = database.importMembers(file);	
		if(result == -1)
		{	JOptionPane.showMessageDialog(errorPanel, "Member data successfully imported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public void clearAttendaceData()
	{	database.clearAttendanceData();	}
	
	public ResultSet getAttendanceData()
	{	return database.getAttendanceData();	}
	
	public boolean addAttendanceData(String lastName, String firstName, boolean isAdult, boolean hadFeast)
	{	return database.addAttendee(lastName, firstName, isAdult, hadFeast);	}
	
	public boolean deleteAttendanceData(String lastName, String firstName)
	{	return database.deleteAttendee(lastName, firstName);	}
	
	public void exportAttendaceData(JTable table, File file)
	{
		boolean result = database.exportAttendanceData(table, file);
		if(result)
		{	JOptionPane.showMessageDialog(errorPanel, "Attendance data successfully exported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong.", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public static void clearTotals()
	{ people = 0; members = 0; adults = 0; feasts = 0; }

}
