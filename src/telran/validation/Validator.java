package telran.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import telran.validation.constraints.*;

public class Validator {
	/**
	 * 
	 * @param obj
	 * @return list of validation violation messages 
	 */
	public static List<String> validate(Object obj) {
		if(obj == null) {
			System.out.println("Validated object must be defined");
		}
		ArrayList<String> res = new ArrayList<>();
		try {
			Class<?> clazz = obj.getClass();
			List<Field> anFields = getAnnotatedFields(clazz);
			anFields.forEach(f -> {
				f.setAccessible(true);
				Object fieldValue;
				try {
					fieldValue = f.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					throw new RuntimeException(e1.toString());
				}
				Arrays.stream(f.getAnnotations()).forEach(an -> {
					Class<?> clazz1 = an.annotationType();
					if(clazz1.equals(Valid.class)) {
						res.addAll(validate(fieldValue));
					} else {
						// получаем метод класса Validator, который должен быть выполнен для данной аннотации
						Method validateMethod = getValidateMethod(an);
						// выполняем метод и получаем строку с сообщением, если строка не пустая, то добавляем эту строку в res
						String validationViolationMessage;
						try {
							validationViolationMessage = (String) validateMethod.invoke(null, fieldValue, f);
							if (Objects.equals(validationViolationMessage, "")) {
								return;
							}
							res.add(validationViolationMessage);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							throw new RuntimeException(e.toString());
						}
					}			
				});
			});
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(e.toString());
		}
		return res;
	}

	private static Method getValidateMethod(Annotation an) {
		
		try {
			return Validator.class.getDeclaredMethod("check" + an.annotationType().getSimpleName(), Object.class, Field.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e.toString());
		}
	}

	private static List<Field> getAnnotatedFields(Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(f -> f.getAnnotations().length != 0)
				.toList();
	}
	
	private static String checkMax(Object fieldValue, Field f) {
		String message = "";
		Max anMax =  f.getAnnotation(Max.class);
		int maxValue = anMax.value();
		if ( Integer.parseInt(fieldValue.toString()) > maxValue) {
			message = anMax.message();
		}	
		return message;
	}
	private static String checkMin(Object fieldValue, Field f) {
		String message = "";
		Min anMin =  f.getAnnotation(Min.class);
		int minValue = anMin.value();
		if ( Integer.parseInt(fieldValue.toString()) < minValue) {
			message = anMin.message();
		}	
		return message;
	}
	
	private static String checkNotEmpty(Object fieldValue, Field f) {
		String message = "";
		NotEmpty anNotEmpty =  f.getAnnotation(NotEmpty.class);
		if ( fieldValue == null || fieldValue.toString().isEmpty()) {
			message = anNotEmpty.message();
		}	
		return message;
	}
	
	private static String checkNotNull(Object fieldValue, Field f) {
		String message = "";
		NotNull anNotNull =  f.getAnnotation(NotNull.class);
		if ( fieldValue == null) {
			message = anNotNull.message();
		}	
		return message;
	}
	
	private static String checkEmail(Object fieldValue, Field f) {
		String message = "";
		Email anEmail =  f.getAnnotation(Email.class);
		if ( fieldValue == null || !fieldValue.toString().matches(Validator.emailRegEx())) {
			message = anEmail.message();
		}	
		return message;
	}
	
	private static String emailRegEx() {
		//email address comprises of two parts:
		//1st part: any symbols except comma and white ones (\\s)
		//2nd part - domains: min two domains, max four domains; domains separated by dot
		//domain could except only ASCI letters and "-"
		//@ separates these two parts
		
		String firstPart;
		String domain;
		
		firstPart = "[^,\\s]+";
		domain = "[a-zA-z-]+";
		return String.format("(%1$s)"
				+ "@"
				+ "((%2$s)\\.){1,3}(%2$s)",
				firstPart, domain);
	}
	
	private static String checkPast(Object fieldValue, Field f) {
		String message = "";
		Past anPast =  f.getAnnotation(Past.class);
		if ( fieldValue == null ||  LocalDate.now().isBefore((ChronoLocalDate) fieldValue) ) {
			message = anPast.message();
		}	
		return message;
	}
	
	private static String checkFuture(Object fieldValue, Field f) {
		String message = "";
		Future anFuture =  f.getAnnotation(Future.class);
		if ( fieldValue == null ||  LocalDate.now().isAfter((ChronoLocalDate) fieldValue) ) {
			message = anFuture.message();
		}	
		return message;
	}
} 
