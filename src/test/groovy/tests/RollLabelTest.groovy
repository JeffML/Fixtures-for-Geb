package tests

import org.junit.Test

import fixtures.Example

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
