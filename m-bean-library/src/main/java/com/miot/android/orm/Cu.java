package com.miot.android.orm;

public class Cu extends Object {

	private String password = "";
	private String nickname = "";
	private String mobile = "";
	private String email="";

//	private List<Home> homes = new ArrayList<Home>();
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	public List<Home> getHomes() {
//		return homes;
//	}
//
//	public void setHomes(List<Home> homes) {
//		this.homes = homes;
//	}


//	@Override
//	public String toString() {
//		String result= "{\"id\":\""+getId()+"\",\"name\":\""+getName()+"\",\"password\":\""+password+"\"}";
//		return result;
//	}
}
