package f4g

import static org.junit.Assert.*
import geb.*
import geb.junit4.*

import org.junit.Before

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
			if (it.preAction) {
				it.preAction.delegate = currPage
				it.preAction()
			}
			
			it.gleaner.delegate = currPage
			
			def gleaned = it.gleaner()
			def expected = it.expected
			
			gleaned.each { k, v -> 
				if (expected[k]) {
					assertEquals("for result $k in $fixtureName", expected[k], v)
				}
			}
		}
	}
	
}
