/*
	This is the Geb configuration file.
	
	See: http://www.gebish.org/manual/current/configuration.html
*/

import java.util.concurrent.TimeUnit
import java.util.logging.Level

import org.openqa.selenium.UnexpectedAlertBehaviour
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

waiting {
	timeout = 2
}

reportsDir = "build/reports"
environments {

	firefox {
		driver = {
			DesiredCapabilities dc = DesiredCapabilities.firefox();
			LoggingPreferences prefs = new LoggingPreferences();
			prefs.enable(LogType.BROWSER, Level.WARNING);
			dc.setCapability(CapabilityType.LOGGING_PREFS, prefs);
			
			FirefoxProfile p = new FirefoxProfile();
			p.setPreference("webdriver.log.file", "/tmp/firefox_console");
			p.setPreference("toolkit.telemetry.enabled", false);
			p.setPreference("geo.enabled", false);
			p.setPreference("plugins.update.notifyUser", false);
			
			p.setPreference("datareporting.healthreport.service.enabled", false);
			p.setPreference("datareporting.healthreport.uploadEnabled", false);
			p.setPreference("datareporting.policy.dataSubmissionEnabled",false);
			p.setPreference("datareporting.healthreport.service.firstRun", false);
			p.setPreference("datareporting.healthreport.logging.consoleEnabled", false);
			
			dc.setCapability(FirefoxDriver.PROFILE, p);
			
			def driver = new FirefoxDriver(dc)
			driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS)
			return driver
		}
	}

	chrome {
		def options = new ChromeOptions()
		options.addArguments("test-type")
		
		DesiredCapabilities dc = DesiredCapabilities.chrome();
//		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		dc.setCapability(ChromeOptions.CAPABILITY, options);
		
		driver = { new ChromeDriver(dc) }
	}
}
