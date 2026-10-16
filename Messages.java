package com.mycompany.main;

import java.util.*;
import java.io.*;

public class Messages {
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static List<Messages> sentMessagesList = new ArrayList<>();
    private static int totalSent = 0;

    public Messages(int num, String recipient, String text) {
        this.messageID = generateID();
        this.numMessagesSent = num;
        this.recipient = recipient;
        this.messageText = text;
        this.messageHash = createMessageHash();
    }

    private String generateID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient!= null && recipient.startsWith("+") && recipient.length() <= 13 && recipient.length() >= 11) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        if (messageText == null || messageText.isEmpty()) return messageID.substring(0,2) + ":0:EMPTY";
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z]", "");
        return (firstTwo + ":" + numMessagesSent + ":" + (first + last)).toUpperCase();
    }

    public String checkMessageLength() {
        if (messageText.length() > 250) {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce the size.";
        }
        return "Message ready to send.";
    }

    public String SentMessage(int option) {
        switch (option) {
            case 1:
                sentMessagesList.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                totalSent++;
                return "Message successfully stored.";
            default:
                return "Invalid option";
        }
    }

    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Messages m : sentMessagesList) {
            sb.append("ID: ").append(m.messageID)
            .append(" Hash: ").append(m.messageHash)
            .append(" To: ").append(m.recipient)
            .append(" Msg: ").append(m.messageText).append("\n");
        }
        return sb.toString();
    }

    public int returnTotalMessagesess() {
        return totalSent;
    }

    public void storeMessage() {
        try {
            File file = new File("messages.json");
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append(" \"messageID\": \"").append(messageID).append("\",\n");
            json.append(" \"messageHash\": \"").append(messageHash).append("\",\n");
            json.append(" \"recipient\": \"").append(recipient).append("\",\n");
            json.append(" \"message\": \"").append(messageText.replace("\"", "'")).append("\"\n");
            json.append("}\n");

            FileWriter fw = new FileWriter(file, true);
            fw.write(json.toString());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}