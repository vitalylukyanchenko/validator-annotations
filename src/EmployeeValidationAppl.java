import java.time.LocalDate;
import java.util.*;

import telran.validation.Validator;

public class EmployeeValidationAppl {

	public static void main(String[] args) {
		Address addr = new Address("Ramat-Gan", "Byalik", 1, 10);
		Employee empl = new Employee(100_000, "Vasya", "vasya@mail.com",
				LocalDate.of(1985, 9, 12), LocalDate.of(2025, 9, 12), addr);
		List<String> validationRes = Validator.validate(empl);
		
		System.out.println(validationRes.isEmpty() ? "Validation passed" : validationRes);

	}

}
