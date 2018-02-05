package org.lappsgrid.example;

/**
 *
 */
public abstract class ServiceTest {
	protected ServiceTest() { }

	public static void run() {
		throw new UnsupportedOperationException("The run method has not been implemented.");
	}

	public abstract void test();

	public static Class[] subclasses() {
		return new Class[] {
				TestJsonSchema.class,
				TestServiceRequirements.class,
				TestServiceOutputRelativeToServiceMetadata.class
		};
	}
}

class TestJsonSchema extends ServiceTest {
	public static void run() {
		System.out.println(TestJsonSchema.class.getName());
	}
	public void test() {
		System.out.println(TestJsonSchema.class.getName());
	}
}
class TestServiceRequirements extends ServiceTest {
	public static void run() {
		System.out.println(TestServiceRequirements.class.getName());
	}
	public void test() {
		System.out.println(TestServiceRequirements.class.getName());
	}
}
class TestServiceOutputRelativeToServiceMetadata extends ServiceTest {
	public static void run() {
		System.out.println(TestServiceOutputRelativeToServiceMetadata.class.getName());
	}
	public void test() {
		System.out.println(TestServiceOutputRelativeToServiceMetadata.class.getName());
	}
}