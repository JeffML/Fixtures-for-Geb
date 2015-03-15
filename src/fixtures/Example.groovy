package fixtures
import pages.PricingPage

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
						return gleaned;
					},
					expected: [
						Quantity: "500",
						Style: "AC - 302",
						Proof: 'Online Proof'
					]],
			]
		]
	]
}


