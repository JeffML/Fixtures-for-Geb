package fixtures

import pages.AccessibilityColorWheelPage
import org.openqa.selenium.Keys

class AccessibilityColorWheel {
	static void clear(c){
		c << Keys.BACK_SPACE * 4
	}
	
	static def fixtures = [
		"Foreground": [
			isActive: true,
			inputs: [
				url: "/colorwheel/wheel.php",
				title: "Accessibility Color Wheel",
				atPage: {at AccessibilityColorWheelPage},
				values: [
					color1: [action: { clear(color1); color1.value("#a0a")}],
					color2: [action: { clear(color2); color2.value("#0a0")}, postAction: {buttons.update.click()}],
				]
			],
			results: [
				[
					gleaner: {
						def gleaned = [:]
						//using selectors here, but these fields could be defined in page content
						gleaned.deut = $('#c10').value()
						gleaned.prota = $('#c20').value()
						gleaned.trita = $('#c30').value()
						return gleaned;
					},
					expected: [
						deut: "#0b1c9f",
						prota: "#0006ae",
						trita: "#8c0208"
					]],
				[
					preAction: { buttons.invert.click(); },
					gleaner: {
						def gleaned = [:]
						gleaned.deut = $('#c11').value()
						gleaned.prota = $('#c21').value()
						gleaned.trita = $('#c31').value()
						return gleaned;
					},
					expected: [
						deut: "#0b1c9f",
						prota: "#0006ae",
						trita: "#8c0208"
					]],

			]
		]
	]
}