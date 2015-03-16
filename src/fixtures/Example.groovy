package fixtures
import pages.PricingPage
import pages.DetailsPage

class Example {
	static def fixtures = [
		"Appointment Card": [
			isActive: true,
			inputs: [
				url: "/products/appointment-label-cards-prices.html",
				title: "Appointment Label Cards Price Quoter",
				atPage: {at PricingPage},
				values: [
					style: [name: '147[]', value: 7462],	//AC-302
					proof: [name: '64[]', value: '482'],	//online
					quantity: [name: 'qty[]', value: '500']
				]
			],
			results: [
				[
					preAction: {},
					gleaner: {
						def gleaned = [:]
						floatDiv.floatingDivSpecs.each {
							def classes = it.classes()
							if (classes.contains('qty')) {
								gleaned.Quantity = it.text()
							} else if (classes.contains('147')) {
								gleaned.Style = it.text().trim()
							} else if (classes.contains('64')) {
								gleaned.Proof = it.text().trim()
							}
						}
						return gleaned
					},
					expected: [
						Quantity: "500",
						Style: "AC - 302",
						Proof: 'Online Proof'
					],
				],
				[
					preAction: {
						proceedToOrder.click()
						waitFor(10) {at DetailsPage}
					},
					gleaner: {
						def gleaned = [:]
						def details = specs
						
						details.eachWithIndex {s, i ->
							switch(i) {
								case 0:
									gleaned.Style = s.text()
									break;
								case 1:
									gleaned.Color = s.text()
									break;
								case 2: 
									gleaned.Proof = s.text()
									break;
								default:
									break;
							}
						}
						
						return gleaned
					},
					expected: [
						Style: "AC-302 - Appointment Card with Heart Sticker",
						Color: "CMYK",
						Proof: "Digital Proof" 
					]
				]
			]
		]
	]
}


