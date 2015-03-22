package f4g

import geb.junit4.GebReportingTest;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.openqa.selenium.UnhandledAlertException

/*
 * This is a JUnit rule that wraps any exceptions coming from the tests (including assertions) and 
 * trims the cruft out of the stack trace 
 */
class ExceptionWrapperRule implements TestRule {
	GebReportingTest test;

	static def packages = [
		"sun.reflect",
		"java.lang",
		"java.util",
		"groovy.lang",
		"org.gradle",
		"org.junit",
		"geb",
		"org.codehaus",
	]

	ExceptionWrapperRule(GebReportingTest test) {
		this.test = test;
	}

	def filters = /(${packages.join('|')})\..*/

	private void handleAlert() {
		def alert = test.browser.driver.switchTo().alert()
		def alertText = alert.text
		alert.accept()
		test.report("Alert: $alertText")
	}

	private void handleCauses(cause) {
		if (cause) {
			def st = cause.getStackTrace()
			def filteredStackTrace = filterTrace(st)
			cause.setStackTrace(filteredStackTrace as StackTraceElement[])
			handleCauses(cause.getCause())
		}
	}
	
	private StackTraceElement[] filterTrace(st) {
		st.grep { it ->
			def clazz = it.declaringClass.toString()
			def filtered = (clazz ==~ filters)
			return !filtered
		}
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {
			public void evaluate() throws Throwable {
				try {
					base.evaluate();
				} catch(MultipleFailureException e){
					println "MultipleFailureException"
                    if (e.failures.any { it in UnhandledAlertException }) {
                        handleAlert()
                    }
                    throw e
				} catch (UnhandledAlertException e) {
					println "UnhandledAlertException"
					handleAlert();
					throw e
				} catch (Throwable cause) {
					handleCauses(cause)
					throw cause;
				}
			}
		};
	}
}