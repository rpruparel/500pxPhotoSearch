package com.example.inclass6;

import java.io.Serializable;

public class Photo implements Serializable {
	String photoTitle;
	String photoURL;
	String ownerName;

	//Constructor
	public Photo(String photoTitle, String photoURL, String ownerName) {
		super();
		this.photoTitle = photoTitle;
		this.photoURL = photoURL;
		this.ownerName = ownerName;
	}

	//Constructor
	public Photo() {
		super();
	}
	public String getPhotoTitle() {
		return photoTitle;
	}
	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Override
	public String toString() {
		return "Photo [photoTitle=" + photoTitle + ", photoURL=" + photoURL + ", ownerName=" + ownerName + "]";
	}





}