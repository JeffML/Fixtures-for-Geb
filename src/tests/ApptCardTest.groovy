package tests

import org.junit.Test

import fixtures.Example

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
