package br.com.ibpt.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String fullname;
	private String password;
	
	public AccountCredentialsVO() {}

	public AccountCredentialsVO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public AccountCredentialsVO(String userName, String fullname, String password) {
		this.userName = userName;
		this.fullname = fullname;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullname, password, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountCredentialsVO other = (AccountCredentialsVO) obj;
		return Objects.equals(fullname, other.fullname) && Objects.equals(password, other.password)
				&& Objects.equals(userName, other.userName);
	}

}
