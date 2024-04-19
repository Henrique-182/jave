package br.com.jave.integrationtests.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String fullname;
	private String password;
	
	public AccountCredentialsVO() {}

	public AccountCredentialsVO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AccountCredentialsVO(String username, String fullname, String password) {
		this.username = username;
		this.fullname = fullname;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return Objects.hash(fullname, password, username);
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
				&& Objects.equals(username, other.username);
	}
	
}
