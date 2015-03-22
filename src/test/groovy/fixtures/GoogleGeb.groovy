package fixtures

import pages.GooglePage
import pages.GoogleResultPage
import pages.GebishApiPage

import org.openqa.selenium.Keys

class GoogleGeb {
	static def fixtures = [
		"Search": [
			isActive: true,
			inputs: [
				url: "/",
				title: "Google",
				atPage: { at GooglePage },
				values: [
					input: [name: 'q', value: "Geb API 0.10", postAction: {
							$('input', name: 'q') << Keys.ENTER
						}],
				]
			],
			results: [
				[
					preAction: {
						waitFor(10) {at GoogleResultPage}
						links[0].click()
						waitFor(10) {at GebishApiPage}
					},
					gleaner: {
						def gleaned = [:]
						if (enableFrames.present) {
							enableFrames.click()
						}
						
						withFrame($('frame', name: 'packageListFrame'), {
							def packages = $('ul', title: 'Packages').find('li a')
							packages.eachWithIndex { p, i ->
								gleaned[i] = p.text();
							}
						})
						return gleaned
					},
					expected: [
						0: "geb",
						1: "geb.binding",
						3: 'geb.content',
						5: 'geb.download.helper',
						//6: 'geb.foo'
					],
				],
			]
		]
	]
}


