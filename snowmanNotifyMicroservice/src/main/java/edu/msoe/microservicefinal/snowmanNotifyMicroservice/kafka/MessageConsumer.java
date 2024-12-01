package edu.msoe.microservicefinal.snowmanNotifyMicroservice.kafka;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.mailjet.client.resource.Emailv31;

import java.util.Scanner;

@Component
public class MessageConsumer {
    @Autowired
    private MailjetClient mailjetClient;

    private void sendNotification(String city, String email, String location) throws MailjetSocketTimeoutException, MailjetException {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "aeclox.boardm@gmail.com")
                                        .put("Name", "Snowmen Tracker"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", email)))
                                .put(Emailv31.Message.SUBJECT, "New Snowman in "+city)
                                .put(Emailv31.Message.TEXTPART, "Location : "+location)));
        mailjetClient.post(request);
    }

    @KafkaListener(topics = "Milwaukee", groupId = "my-group-id")
    public void listenMilwaukee(String message) throws MailjetSocketTimeoutException, MailjetException {
        System.out.println("Received message: " + message);
        Scanner scan = new Scanner(message);
        String email = scan.nextLine();
        String location = scan.nextLine();
        sendNotification("Milwaukee", email, location);
    }
    @KafkaListener(topics = "Chicago", groupId = "my-group-id")
    public void listenChicago(String message) throws MailjetSocketTimeoutException, MailjetException {
        System.out.println("Received message: " + message);
        Scanner scan = new Scanner(message);
        String email = scan.nextLine();
        String location = scan.nextLine();
        sendNotification("Chicago", email, location);
    }
    @KafkaListener(topics = "Dubai", groupId = "my-group-id")
    public void listenDubai(String message) throws MailjetSocketTimeoutException, MailjetException {
        System.out.println("Received message: " + message);
        Scanner scan = new Scanner(message);
        String email = scan.nextLine();
        String location = scan.nextLine();
        sendNotification("Dubai", email, location);
    }

}
