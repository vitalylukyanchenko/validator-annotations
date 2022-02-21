import java.time.LocalDate;

import telran.validation.constraints.*;

public class Employee {
	private static final int MIN_VALUE = 100_000;
	private static final int MAX_VALUE = 999_999;
	
	@Min(value = MIN_VALUE, message = "Id can not be less than " + MIN_VALUE)
	@Max(value = MAX_VALUE, message = "Id can not be greater than " + MAX_VALUE)
	long id;
	
	@NotEmpty(message = "Name can not be empty")
	String name;
	
	@Email(message = "Email is not valid")
	String email;
	
	@Past(message = "Birthdate can not be in the future")
	LocalDate birthDate;
	
	@Future(message = "Job finish date can not be in the past")
	LocalDate jobFinishDate;
	
	@Valid
	Address address;

	public Employee(long id, String name, String email, LocalDate birthDate, LocalDate jobFinishDate, Address address) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.jobFinishDate = jobFinishDate;
		this.address = address;
	}
	
}
