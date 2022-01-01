package service.core;

import lombok.Getter;
import lombok.Setter;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */
public class Quotation {
	public Quotation(){

	}
	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;
		
	}
	@Getter
	@Setter
	private String company;
	@Getter
	@Setter
	private String reference;
	@Getter
	@Setter
	private double price;
}
