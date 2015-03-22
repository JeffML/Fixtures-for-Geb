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
* a postAction closure
** this is executed after the form input value is assigned; useful for cases where a button needs to be clicked or blur event triggered.

A values map with single value for inputting a google search term on Google's home page might look as follows:
```groovy
		"Search": [
			isActive: true,
			inputs: [
				url: "/",
				title: "Google",
				atPage: { at GooglePage },
				values: [
					input: [name: 'q', value: "Geb API 0.10", postAction: {
							$('input', name: 'q') << Keys.ENTER
						}],
				]
			],
```

Here, the input field named 'q' (search input field) is set to _Geb API 0.10_ and the postAction sends an ENTER key to the field after the input has been set (this triggers the search).

### results
There can be zero or more result fixtures associated with an input fixture. A result fixture consists of:
* a preAction closure
** this instructs the test what to do before attempting to 'glean' results
* a gleaner closure
** this builds a _gleaned_ map of actual results. The data to be gleaned can be defined by a Page definition, or by selectors inside the closure. 
* expected
** compared against the gleaned map

For our Google search for Geb's API, we might define the following result fixture:
```groovy
			results: [
				[
					preAction: {
						waitFor(10) {at GoogleResultPage}
						links[0].click()
						waitFor(10) {at GebishApiPage}
					},
					gleaner: {
						def gleaned = [:]
						withFrame($('frame', name: 'packageListFrame'), {
							def packages = $('ul', title: 'Packages').find('li a')
							packages.eachWithIndex { p, i ->
								gleaned[i] = p.text();
							}
						})
						return gleaned
					},
					expected: [
						0: "geb",
						1: "geb.binding",
						3: 'geb.content',
						5: 'geb.download.helper',
						//6: 'geb.foo'
					],
				],
```
After the ENTER key was sent to the search input field via the input fixture, we expect to wind up at a Google results page. We have a Geb Page definition for that, with content that contains _links_.  The preAction clicks the first one, and waits for the Geb API page to appear.

The gleaner then builds a _gleaned_ map for comparison with the results we expect. In this case, we have to deal with frames, so we use Geb's withFrame method to track down the package list for Geb. We store each package in the _gleaned_ map.

In the expected part of this result fixture, we do a partial comparison of the gleaned map against some values we expect it to have.

## Running the examples
The example FixturedTests in this project use JUnit as the test framework. Future versions will support other frameworks. The .gradle.properties settings may have to be changed for your environment.  JDK 1.8 is required.

### command line
* To run the GoogleTest, the command line is:
  * ``` gradle -Dtest.single=GoogleTest -Dhost.url=http://google.com clean chromeTest```
* For the ACWTest, the command line is:
  * ``` gradle -Dtest.single=ACWTest -Dhost.url=http://gmazzocato.altervista.org clean chromeTest```

