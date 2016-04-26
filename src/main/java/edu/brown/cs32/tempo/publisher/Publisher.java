package edu.brown.cs32.tempo.publisher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.PhoneNumber;
import edu.brown.cs32.tempo.workout.Workout;

public class Publisher {
  public static void publish(Group g) {
    // TODO how does this work? publish by workout, or by group?
    System.out.println("Published group " + g);
    List<String> workoutIds = new ArrayList<>();
    for (Workout w : g.getWorkout()) {
      workoutIds.add(w.getId());
    }

    for (Athlete a : g.getMembers()) {
      PhoneNumber num = a.getNumber();

      if (num != null) {
        Twilio.send(num, workoutIds);
      }
      String email = a.getEmail();
      if (email != null) {
        SimpleDateFormat format = new SimpleDateFormat("MMM d");
        String subject = String.format("Workouts for week of %s",
            format.format(g.getDate()));
        String bodyText = String.format(
            "Hi %s,\n\n"
                + "Here are your tempo workouts for the week of %s:\n\n" + "%s",
            a.getName(), format.format(g.getDate()), workoutIds);
        try {
          MimeMessage message = GmailSender.createEmail(email,
              "tempo.brown.2016@gmail.com", subject, bodyText);
          GmailSender.sendMessage(GmailSender.getGmailService(), "me", message);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
