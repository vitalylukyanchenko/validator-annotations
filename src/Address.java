
import telran.validation.constraints.Min;
import telran.validation.constraints.NotEmpty;
import telran.validation.constraints.NotNull;

public class Address {
	
	@NotEmpty(message = "City can not be empty")
	String city;
	
	@NotNull(message = "Street must be defined")
	String street;
	
	@Min(value = 1, message = "House must be a positive number")
	int house;
	
	@Min(value = 1, message = "Appartment must be a positive number")
	int appartment;

	public Address(String city, String street, int house, int appartment) {
		super();
		this.city = city;
		this.street = street;
		this.house = house;
		this.appartment = appartment;
	}
	
	
}
