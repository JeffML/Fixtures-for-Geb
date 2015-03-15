package tests

import groovy.lang.MetaClass
import fixtures.Example
import org.junit.Before
import org.junit.Test

class ApptCardTest extends FixturedTest {
	static def final fixtureName;
	static def final fixture;
	static def final form;

	static {
		fixtureName = "Appointment Card"
		fixture = Example.fixtures[fixtureName]
		form = "form#maverick"
	}
	
	ApptCardTest() {
		super(fixture, fixtureName, form)
	}
	
	@Test
	void testFixture() {
		super.testFixture();
	}
}
