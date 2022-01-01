package service.core;

import lombok.Getter;
import lombok.Setter;
import service.messages.MySerializable;

/**
 * Interface to define the state to be stored in ClientInfo objects
 * 
 * @author Rem
 *
 */
public class ClientInfo implements MySerializable {
	public static final char MALE				= 'M';
	public static final char FEMALE				= 'F';


	public ClientInfo(String name, char sex, int age, int points, int noClaims, String licenseNumber) {
		this.name = name;
		this.gender = sex;
		this.age = age;
		this.points = points;
		this.noClaims = noClaims;
		this.licenseNumber = licenseNumber;
	}
	
	public ClientInfo() {}

	/**
	 * Public fields are used as modern best practice argues that use of set/get
	 * methods is unnecessary as (1) set/get makes the field mutable anyway, and
	 * (2) set/get introduces additional method calls, which reduces performance.
	 */
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private char gender;
	@Getter
	@Setter
	private int age;
	@Getter
	@Setter
	private int points;
	@Getter
	@Setter
	private int noClaims;
	@Getter
	@Setter
	private String licenseNumber;
}
