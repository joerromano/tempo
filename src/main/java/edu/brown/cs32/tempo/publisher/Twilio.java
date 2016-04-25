package edu.brown.cs32.tempo.publisher;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import edu.brown.cs32.tempo.people.PhoneNumber;

public class Twilio {
  private static final String ACCOUNT_SID = "AC142b7e65818e891c2a77c168802c6f79";
  private static final String AUTH_TOKEN = "bf3cd93ffc5e5a63dab1bd494ab45ef4";

  public static void send(PhoneNumber num, List<String> workoutIds) {
    try {
      TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

      // Build a filter for the MessageList
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair("Body", workoutIds.toString()));
      params.add(new BasicNameValuePair("To", num.number));
      params.add(new BasicNameValuePair("From", "+1 518-380-2958"));

      MessageFactory messageFactory = client.getAccount().getMessageFactory();

      Message message = messageFactory.create(params);
      System.out.println(message.getSid());
    } catch (TwilioRestException e) {
      e.printStackTrace();
    }
  }
}
