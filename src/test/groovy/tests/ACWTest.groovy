package tests

import org.junit.Test

import f4g.FixturedTest;
import fixtures.AccessibilityColorWheel


class ACWTest extends FixturedTest {
	static def final fixtureName;
	static def final fixture;

	static {
		fixtureName = "Foreground"
		fixture = AccessibilityColorWheel.fixtures[fixtureName]
	}
	
	ACWTest() {
		super(fixture, fixtureName)
	}
	
	@Test
	void testFixture() {
		super.testFixture();
	}
}
