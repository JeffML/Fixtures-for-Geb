package fixtures

import pages.GooglePage
import pages.GoogleResultPage
import pages.GebishApiPage

import f4g.Fixture
import org.openqa.selenium.Keys

class GoogleGeb extends Fixture{
	static def fixtures = [
		"Search": [
			isActive: true,
			inputs: [
				url: "/",
				title: "Google",
				atPage: {
					at GooglePage
				},
				values: [
					q: "Geb API 0.10",
					(cl()): {
						$('input', name: 'q') << Keys.ENTER
					}
				],
			]
		],
		results: [
			(cl()): {
				waitFor(10) {
					at GoogleResultPage
				}
				links[0].click()
				waitFor(10) {
					at GebishApiPage
				}
			},
			(cl()): {
				def gleaned = [:]

				if (enableFrames.present) {
					enableFrames.click()
				}

				withFrame($('frame', name: 'packageListFrame'), {
					def packages = $('ul', title: 'Packages').find('li a')
					packages.eachWithIndex {
						p, i ->
						gleaned[i] = p.text();
					}
				})

				compare(gleaned, [
					0: "geb",
					1: "geb.binding",
					3: 'geb.content',
					5: 'geb.download.helper',
					//6: 'geb.foo'
				])
			}
		]
	]
}


