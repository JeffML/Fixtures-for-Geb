package fixtures

import org.openqa.selenium.Keys

import pages.AccessibilityColorWheelPage
import f4g.Fixture

class AccessibilityColorWheel extends Fixture{
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
					(cl()): {clear(color1); color1.value("#a0a")},
					(cl()): {clear(color2); color2.value("#0a0")},
					(cl()): {buttons.update.click()}
				]
			],
			results: [
				//using selectors here, but these fields could be defined in page content
				deut: [actual: {$('#c10').value()}, expected:"#0b1c9f"],
				prota: [actual: {$('#c20').value()}, expected:"#0006ae"],
				trita: [actual: {$('#c30').value()}, expected:"#8c0208"],
				(cl()): { buttons.invert.click() },
				deut2: [actual: {$('#c11').value()}, expected:"#0b1c9f"],
				prota2: [actual: {$('#c21').value()}, expected:"#0006ae"],
				trita2: [actual: {$('#c31').value()}, expected:"#8c0208"],
			]
		]
	]
}