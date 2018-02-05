package org.lappsgrid.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class StaticExample
{
	public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		for (Class theClass : ServiceTest.subclasses()) {
			Method m = theClass.getMethod("run", null);
			m.invoke(null);
		}
	}
}

