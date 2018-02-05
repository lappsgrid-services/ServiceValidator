package org.lappsgrid.example;

/**
 *
 */
public class DynamicExample
{
	public static void main(String[] args) throws IllegalAccessException, InstantiationException
	{
		for (Class theClass : ServiceTest.subclasses()) {
			ServiceTest test = (ServiceTest) theClass.newInstance();
			test.test();
		}
	}
}

