package com.example.demo.entity;




import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Users {
	
	@Id
	private ObjectId _id;
	
	private String username;
	private String password;
	private String email;
	private String profilePic;
	private boolean pic;
	private String bio;
	private List<String> followedSeries;
	private List<String> likedChapters;
	private List<String> producedSeries;
	private String securityAnswer; 
	private List<String> editorPics;
	
	
	
//	public Users(String username, String password, String email) {
//		this.username = username;
//		this.password = password;
//		this.email  = email;
//		pic = false;
//	}
	
	
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<String> getFollowedSeries() {
		return followedSeries;
	}

	public void setFollowedSeries(List<String> followedSeries) {
		this.followedSeries = followedSeries;
	}

	public List<String> getProducedSeries() {
		return producedSeries;
	}

	public void setProducedSeries(List<String> producedSeries) {
		this.producedSeries = producedSeries;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
	public boolean isPic() {
		return pic;
	}

	public void setPic(boolean pic) {
		this.pic = pic;
	}

	public String get_id() {
		return _id.toHexString();
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getLikedChapters() {
		return likedChapters;
	}

	public void setLikedChapters(List<String> likedChapters) {
		this.likedChapters = likedChapters;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public List<String> getEditorPics() {
		return editorPics;
	}

	public void setEditorPics(List<String> editorPics) {
		this.editorPics = editorPics;
	}
	
	

	
}
