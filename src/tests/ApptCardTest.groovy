package tests

import groovy.lang.MetaClass
import fixtures.Example
import org.junit.Before
import org.junit.Test

class ApptCardTest extends FixturedTest {
	static def final FIXTURE_NAME = "Appointment Card"
	static def fixture = Example.fixtures[FIXTURE_NAME]

	@Before
	void setup() {
		super.setup(fixture.inputs.url, fixture.inputs.title);
	}
	
	@Test
	void testFixture() {
		super.testFixture(fixture, FIXTURE_NAME);
	}
}
