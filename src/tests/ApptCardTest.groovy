package tests

import groovy.lang.MetaClass
import fixtures.Example
import org.junit.Before
import org.junit.Test

class ApptCardTest extends FixturedTest {
	static def final fixtureName;
	static def final fixture;

	static {
		fixtureName = "Appointment Card"
		fixture = Example.fixtures[fixtureName]
	}
	
	ApptCardTest() {
		super(fixture, fixtureName)
	}
	
	@Test
	void testFixture() {
		super.testFixture();
	}
}
