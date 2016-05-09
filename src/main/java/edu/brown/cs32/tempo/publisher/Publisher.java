package edu.brown.cs32.tempo.publisher;

import java.text.SimpleDateFormat;

import javax.mail.internet.MimeMessage;

import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.PhoneNumber;

public class Publisher {
  public static void publish(Group g) {
    // TODO how does this work? publish by workout, or by group?
    System.out.println("Published group " + g);
    // List<String> workoutIds = new ArrayList<>();
    // for (Workout w : g.getWorkout()) {
    // workoutIds.add(w.getId());
    // }

    for (Athlete a : g.getMembers()) {
      PhoneNumber num = a.getNumber();
      String body = "View this weeks workouts @ localhost:4567/group/"
          + g.getId();
      if (num != null) {
        Twilio.send(num, body);
      }
      String email = a.getEmail();
      if (email != null) {
        SimpleDateFormat format = new SimpleDateFormat("MMM d");
        String subject = String.format("Workouts for week of %s",
            format.format(g.getDate()));
        String bodyText = String.format(
            "Hi %s,<br><br>" + "Here are your tempo workouts for the week of "
                + "<a href = 'http://localhost:4567/group/%s'>" + "%s</a>.",
            a.getName(), g.getId(), format.format(g.getDate()));
        try {
          MimeMessage message = GmailSender.createEmail(email,
              "tempo.brown.2016@gmail.com", subject, bodyText);
          message.setText(bodyText, "UTF-8", "html");
          GmailSender.sendMessage(GmailSender.getGmailService(), "me", message);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
