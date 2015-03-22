# Fixtures for Geb

A data-driven approach to populating forms and reading resulting output by way of Geb.
## Fixtured Tests
Tests are a combination of fixtures, page definitions and page modules. A FixturedTest is based on a single fixture, whose id is a string called the *fixture name*.

A fixtured test is a simple affair. It extends the FixturedTest base class passes to its constructor a fixture name and fixture. It also has a single test method to test the fixture.

### An Example 
```groovy
package tests

import org.junit.Test

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
```

## Fixture Components
  - url
  - title
  - entrySets
    - set name
    - isActive
    - fields
        * name
        * value
        * preEntryActions
        * postEntryActions
  - readSets
    - set name
    - isActive
    - fields
      * name
      * value
