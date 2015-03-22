package tests

import org.junit.Test

import f4g.FixturedTest;
import fixtures.GoogleGeb


class GoogleTest extends FixturedTest {
	static def final fixtureName;
	static def final fixture;

	static {
		fixtureName = "Search"
		fixture = GoogleGeb.fixtures[fixtureName]
	}
	
	GoogleTest() {
		super(fixture, fixtureName)
	}
	
	@Test
	void testFixture() {
		super.testFixture();
	}
}
