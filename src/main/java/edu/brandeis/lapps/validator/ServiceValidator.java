/*
 * 
 */

package edu.brandeis.lapps.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceValidator {
	
	public static void main(String[] args) {		
		Service s = new Service();
		validate(s);
	}

	public static void validate(Service s) {
		Report r = new Report(s);
		try {
			runTests(s, r);
		} catch (NoSuchMethodException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException ex) {
			Logger.getLogger(ServiceValidator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void runTests(Service s, Report r)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class[] args = new Class[1];
		args[0] = String.class;
		for (Class c : ServiceTest.subclasses()) {
			Method m = c.getMethod("run", args);
			//System.out.println(m.toString());
			m.invoke(null, "hopsasa");
		}
		
	}
}
