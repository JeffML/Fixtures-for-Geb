package tests

import static org.junit.Assert.*

import org.junit.Before
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver

import tests.BaseTest
import pages.AnyPage
import geb.*
import geb.junit4.*

abstract class FixturedTest extends BaseTest {
	private def final fixture
	private def final fixtureName
	
	FixturedTest(fixture, fixtureName) {
		this.fixture = fixture
		this.fixtureName = fixtureName
	}
	
	@Before
	void setup() {
		AnyPage.url = fixture.inputs.url
		AnyPage.titleText = fixture.inputs.title
	}

	protected populateField(name, field) {
		assertNotNull("Did not find form $form", form)
		
		if (field.preAction) {
			field.preAction.delegate = this
			field.preAction()
		}
		
		if (field.action){
			doAction(field)
		} else {
			try {
				println "name is $name; $field.name, $field.value"
				form[field.name] = field.value
			}
			catch (org.openqa.selenium.ElementNotVisibleException e) {
				throw new Exception("Exception accessing field $field.name", e)
			}

			if (field.doBlur) {
				def sel = form.find('input', name: field.name)
				sel.jquery.blur()
			}
		}

		if (field.postAction) {
			field.postAction.delegate = this
			field.postAction()
		}
	}
	

	protected doAction(field) {
		if (field.action) {
			field.action.delegate = this;
			field.action();
		}
	}
	
	void testFixture() {
		def currPage = this;
		
		to AnyPage
		at AnyPage
		
		fixture.inputs.atPage.delegate = currPage;
		fixture.inputs.atPage()
		
		fixture.inputs.values.each { name, field -> populateField(name, field) }
		
		fixture.results.each {
			it.preAction.delegate = currPage
			it.preAction()
			
			it.gleaner.delegate = currPage
			
			def gleaned = it.gleaner()
			def expected = it.expected
			
			gleaned.each { k, v -> 
				assertEquals("for result $k in $fixtureName", expected[k], v)
			}
		}
	}
	
}
