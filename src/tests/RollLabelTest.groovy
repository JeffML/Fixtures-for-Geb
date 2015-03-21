package tests

import groovy.lang.MetaClass
import fixtures.Example
import org.junit.Before
import org.junit.Test

class RollLabelTest extends FixturedTest {
	static def final fixtureName;
	static def final fixture;

	static {
		fixtureName = "Roll Labels"
		fixture = Example.fixtures[fixtureName]
	}
	
	RollLabelTest() {
		super(fixture, fixtureName)
	}
	
	@Test
	void testFixture() {
		super.testFixture();
	}
}
