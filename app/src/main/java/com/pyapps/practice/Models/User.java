package com.pyapps.practice.Models;

import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by prudh on 7/27/2017.
 */

public class User {
    String email;
    String about;
    long gender;
    String height;
    String name;
    String phone;
    long weight;
    ArrayList<String> photoUrls;
    ArrayList<String> sentMessages;
    ArrayList<String> receivedMessages;

    public User(String email, String about, long gender, String height, String name, String phone, long weight, ArrayList<String> photoUrls, ArrayList<String> sentMessages, ArrayList<String> receivedMessages) {
        this.email = email;
        this.about = about;
        this.gender = gender;
        this.height = height;
        this.name = name;
        this.phone = phone;
        this.weight = weight;
        this.photoUrls = photoUrls;
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getGender() {
        return gender;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public String  getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(ArrayList<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(ArrayList<String> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public ArrayList<String> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(ArrayList<String> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
}
