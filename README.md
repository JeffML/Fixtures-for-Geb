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

A fixtured test is essentially a wrapper around the base class FixturedTest, which does all the work of interpreting the fixture and executing actions on the page.  

##Page Definitions
Pages are defined as any other Geb Page. For input from fixture data, a form content must be defined. One special page is AnyPage, which is used by the fixture interpreter to navigate to a URL dynamically.

```groovy
import geb.Page

class GooglePage extends Page {
	static at = {title: "Google"}
	static content = {
		form { $('form', name: 'f') }
	}
}
```
## Fixtures
Fixtures drive FixturedTests. Fixtures are defined as static structures inside a class. Each Fixture has a name. The top-level elements of a fixture are isActive, inputs, and results:

### isActive
The isActive flag indicates if the fixture is to be executed or ignored.

### inputs
The input structure consists of 
* a target URL
* a page title (for at checker)
* an atPage closure, that 'converts' an AnyPage to a user-defined Page object
* values

#### values
Value definitions are maps whose keys are user-friendly names for form fields.  A value fixture can contains
* a preAction closure
** this will be executed before any value is set
* a name and value, or an action closure
** both are intended to set the value of a form input; an action closure allows for more sophisticated input scenarios than simple ```form[name] = value``` situations.


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
