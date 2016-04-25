package edu.brown.cs32.tempo.publisher;

import java.util.ArrayList;
import java.util.List;

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

      }
    }
  }
}
