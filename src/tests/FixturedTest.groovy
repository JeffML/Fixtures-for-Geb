package tests

import static org.junit.Assert.*

import org.junit.Before
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver

import fixtures.Example
import tests.BaseTest
import pages.AnyPage
import geb.*
import geb.junit4.*

abstract class FixturedTest extends BaseTest {
	def page;
	def name;

	/**
	 * Determines the parameters for this test. 
	 * @return The parameters for each iteration of the test
	 */
	@Parameters(name = "{0}: {1}")
	public static Collection<Object[]> getPages() {
		pages = Example.pages.collect {[it.key, it.value.url, it.value]}
		return pages as Object[][];
	}
	
	//@Override
	@Before
	void setup() {
		AnyPage.url = this.page.url
		AnyPage.titleText = this.page.title
	}
	
	FixturedTest(key, url, page_) {
		this.name = key
		page = page_
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
	
	protected loadEntrySet(key, eset) {
		eset.fields.each { name, field -> populateField(name, field) }
	}
	
	protected readResultSet(key, rset) {
	}
	
}
