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
Value definitions are maps whose keys are user-friendly names for form fields.  A value definition can contain
* form field name and value to set
    * a form has to be defined in the page definition (the one the test is running against)
        * example:  ```field1: "foo"```  (this is equivalent to ```form[name] = value``` in Geb)
* a closure
    * closure keys must begin with "_CLOSURE_"; there is a helper method cl() that generates closure names:
        * ```(cl()): {clear(color1); color1.value("#a0a")},```

### results
There can be zero or more result fixtures associated with an input fixture. A result fixture is a map containing:
* a unique arbitrary key (for test failure output only)
  * a map, containing 
    * actual (a map) containing a selector and type ('text' or 'value')
    * expected containing the expected value (or text)
* or, a _CLOSURE_ key (see cl() helper method, above)
  * a closure containing actions to be performed 

For our Google search for Geb's API, we might define the following result fixture:
```groovy
        results: [
            (cl()): {
                waitFor(10) {
                    at GoogleResultPage
                }
                links[0].click()
                waitFor(10) {
                    at GebishApiPage
                }
            },
            (cl()): {
                def gleaned = [:]

                if (enableFrames.present) {
                    enableFrames.click()
                }

                withFrame($('frame', name: 'packageListFrame'), {
                    def packages = $('ul', title: 'Packages').find('li a')
                    packages.eachWithIndex {
                        p, i ->
                        gleaned[i] = p.text();
                    }
                })

                compare(gleaned, [
                    0: "geb",
                    1: "geb.binding",
                    3: 'geb.content',
                    5: 'geb.download.helper',
                    //6: 'geb.foo'
                ])
            }
        ]```
After the ENTER key was sent to the search input field via the input fixture, we expect to wind up at a Google results page. 
We have a Geb Page definition for that, with content that contains _links_.  The first link is clicked, then we wait for the Geb API page to appear.

A second closure is run that 'gleans' data from the links presented. In this case, we have to deal with frames, so we use Geb's withFrame method 
to track down the package list for Geb. We store each package in the _gleaned_ map.

We now use the Fixture class' compare() method which expects two maps, the first being the data gleaned and the second containing expected values for (some or all) keys

## Running the examples
The example FixturedTests in this project use JUnit as the test framework. Future versions will support other frameworks. The .gradle.properties settings may have to be changed for your environment.  JDK 1.8 is required.

### command line
* To run the GoogleTest, the command line is:
  * ``` gradle -Dtest.single=GoogleTest -Dhost.url=http://google.com clean chromeTest```
* For the ACWTest, the command line is:
  * ``` gradle -Dtest.single=ACWTest -Dhost.url=http://gmazzocato.altervista.org clean chromeTest```

#History
* 0.1 -- initial release
* 0.2 -- changed from a DSLish fixture to one that is scriptish