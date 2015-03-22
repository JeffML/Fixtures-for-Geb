package pages

import geb.Page

class GooglePage extends Page {
	static at = {title: "Google"}
	static content = {
		form { $('form', name: 'f') }
	}
}

class GoogleResultPage extends Page {
	static at = {title: startsWith('geb api')}
	static content = {	
		links(wait: 10) { $('h3 a') }
	}
}
