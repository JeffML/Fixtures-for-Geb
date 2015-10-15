package f4g

import static org.junit.Assert.*
import geb.*
import geb.junit4.*
import java.util.regex.Pattern

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

	def protected inputTestData(input) {
		assertNotNull("Did not find form $form; did you forget to define it in the page definition?", form)
		
		def me = this;

		input.each {
			if (it.key.startsWith('_CLOSURE_')) {
				it.value.delegate = me
				it.value()
			} else if (it.value instanceof List) {
				def name = it.key;
				it.value.each {
					println "name=$name, value=$it"
					form[name] = it;
				}
			} else {
				sleep 2000
				println "name=$it.key, value=$it.value"
				form[it.key] = it.value
			}
			sleep(750)
		}
	}

	def protected checkResult(result) {
		def me = this
		
		result.each {
			def value = it.value

			if (it.key.startsWith('_CLOSURE_')) {
				it.value.delegate = me
				it.value()
			} else {
				if (value.expected instanceof Pattern) {
					assertTrue("$it.key was $value.actual; expected to match $value.expected", value.actual =~ value.expected)
				} else {
					assertEquals("$it.key was $value.actual; expected $value.expected", value.expected, value.actual);
				} 
			}
		}
	}

	def protected compare(Map actual, Map expected) {
		expected.each {k, v -> assertEquals(v, actual[k])}
	}

	void testFixture() {
		def currPage = this;

		to AnyPage
		at AnyPage

		fixture.inputs.atPage.delegate = currPage;
		fixture.inputs.atPage()

		inputTestData(fixture.inputs.values)

		checkResult(fixture.results)
	}
}
