package pages

import geb.Page
import geb.Module
import pages.modules.ACWButtons

class AccessibilityColorWheelPage extends Page {
	static at = {title: "Accessibility Color Wheel"}
	static content = {
		form(wait:10) { $('form')[0] }
		buttons {module ACWButtons}
		
		color1 {form.find('input#c00')}
		color2 {form.find('input#c01')}
	}
}

