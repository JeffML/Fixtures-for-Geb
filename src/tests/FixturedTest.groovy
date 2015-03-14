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
	def page;
	def name;

	@Before
	void setup(url, title) {
		AnyPage.url = url
		AnyPage.titleText = title
	}
	
	FixturedTest() {
	}

	protected populateField(name, field) {
		if (field.preAction) {
			field.preAction.delegate = this
			field.preAction()
		}
		
		if (field.selector){
			doSelectorAction(field)
		} else {
			try {
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
	

	protected doSelectorAction(field) {
		def isArray = field.selector instanceof java.util.Collection

		def f = isArray? $(field.selector[0]) : $(field.selector)

		if (isArray) {
			f = f.filter(field.selector[1])
		}

		if (field.action) {
			if (field.params != null) {
				f."$field.action"(field.params)
			} else {
				f."$field.action"()  	// == f."click"() == f.click()
			}
		}
	}
	
	protected loadInputSet(inputs) {
		to AnyPage
		at AnyPage
		inputs.each { name, field -> populateField(name, field) }
	}
	
	void testFixture(fixture, fixtureName) {
		loadInputSet(fixture.inputs)
		
		fixture.results.each {
			it.preAction.delegate = this
			it.preAction()
			it.gleaner.delegate = this
			def results = it.gleaner()
			def expected = it.expected
			results.each { v, k -> 
				assertEquals(expected[k], v, "for result $k")
			}
		}
	}
	
}
