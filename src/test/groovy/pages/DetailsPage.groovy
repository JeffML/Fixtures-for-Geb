package pages

import geb.Page

class DetailsPage extends Page {
	static url = "products/details-page.html"	
	static at = { title : startsWith("Save Your Quote") }

	static content = {
		specs (wait:15){$('div.prod-table-spec').find('span.blueText')}
		
		specialInstructions { $('#SpecialInstructions') }
		
		addToCart {$('input', alt: "Add To Cart")}
		
		editQuote {$('button#editQuote')}
	}
}
