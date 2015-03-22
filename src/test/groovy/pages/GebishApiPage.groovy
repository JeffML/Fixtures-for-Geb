package pages

import geb.Page

class GebishApiPage extends Page {
	static at = { title : startsWith("Overview") }
	
	static content = {
		enableFrames(required:false) { $('a', text: 'Frames') }
	}
}
