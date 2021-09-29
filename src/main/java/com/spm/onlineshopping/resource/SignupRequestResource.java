package com.spm.onlineshopping.resource;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequestResource {
//    @NotBlank
//    @Size(min = 3, max = 20)
//    private String username;
    
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String roleName;
	
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String firstName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String lastName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 10, min = 1, message = "{userName.size}")
	private String username;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 15, min = 1, message = "{password.size}")
	private String password;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String addressLine1;
	
	@Size(max = 70, message = "{common-name.size}")
	private String addressLine2;
	
	@Size(max = 70, message = "{common-name.size}")
	private String addressLine3;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "{common.email-pattern}")
	private String email;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{phoneNumber.pattern}")
	@Size(max = 10, min = 10, message = "{phoneNumber.size}")
	private String phoneNumber;
	
	@NotBlank(message = "{common.not-null}")
	private String dob;
    
	@NotBlank(message = "{common.not-null}")
	private String nic;
  
//    public String getUsername() {
//        return username;
//    }
// 
//    public void setUsername(String username) {
//        this.username = username;
//    }
 
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}


    
}
