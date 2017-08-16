package com.pyapps.practice.Models.users;

import java.util.Map;

public class User{
	private int gender;
	private String phone;
	private String about;
	private String name;
	private Messages messages;
	private int weight;
	private String email;
	private String height;
	private Map<String,Boolean> photoUrls;

	public User() {
	}

	public Map<String, Boolean> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(Map<String, Boolean> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public void setGender(int gender){
		this.gender = gender;
	}

	public int getGender(){
		return gender;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMessages(Messages messages){
		this.messages = messages;
	}

	public Messages getMessages(){
		return messages;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	public int getWeight(){
		return weight;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setHeight(String height){
		this.height = height;
	}

	public String getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"User{" +
			",gender = '" + gender + '\'' +
			",phone = '" + phone + '\'' + 
			",about = '" + about + '\'' + 
			",name = '" + name + '\'' + 
			",messages = '" + messages + '\'' + 
			",weight = '" + weight + '\'' + 
			",email = '" + email + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}
