package datasource;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Team;

public class SQLFillDataUnitTest {
	private static SQLDatasource datasource;
	private static SQLFillData filler;
	private static Random random; 

  @BeforeClass
  public static void setUp() {
    Db.setURL("tempoNew.sqlite3");
    datasource = new SQLDatasource();
    random = new Random();
    filler = new SQLFillData();
  }
  
  @Test
  public void coachFillTeamsTest() {
  	String randoEmail = "coach_email2_" + new BigInteger(80, random).toString(32) + "@yahoo.com";
  	Coach addedCoach = datasource.addCoach("coach joe", randoEmail, new PostalCode("12345"), "another_pwd");
  	datasource.addTeam(addedCoach, "bwtf");
  	Coach authenticatedCoach = datasource.authenticate(randoEmail, "another_pwd");
  	
  	Collection<Team> teams = authenticatedCoach.getTeams();
  	assertEquals(teams.size(), 1);
  }
  
  @Test
  public void teamFillAthletesTest() {
  	String email1 = "jj" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	String randoEmail = "coach_email2_" + new BigInteger(80, random).toString(32);
  	Coach addedCoach = datasource.addCoach("coach joe", randoEmail, new PostalCode("12345"), "another_pwd");
  	Team addedTeam = datasource.addTeam(addedCoach, "bwtf");
  	Athlete addedAth1 = datasource.addMember(addedTeam, email1, "5041236870", "jj", new PostalCode("12345"));
  	
  	Team retrievedTeam = datasource.getTeam(addedTeam.getId());
  	assertEquals(retrievedTeam.getRoster().size(), 1);
  	
  }

}
