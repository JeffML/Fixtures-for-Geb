package pages

import geb.Page
import geb.Module
import pages.modules.FloatingDiv

class PricingPage extends Page {

	static at = {waitFor(message: "Quoter Page not loaded? Can't find Save Quote button.", 10) {proceedToOrder.isDisplayed()}}
	
	static content = {
		saveQuote(required:false) {$('input.saveQuoteButton')}
		proceedToOrder {$('input.sendToQuoterButton')}

		form { $('form#maverick') }
				
		floatDiv(wait:true) {module FloatingDiv}
	}
}
