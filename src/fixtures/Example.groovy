package fixtures

class Example {
	static def fixtures = [
		"Appointment Card": [
			isActive: true,
			inputs: [
				url: "/products/appointment-label-cards-prices.html",
				title: "Appointment Label Cards Price Quoter",
				values: [
					style: [name: '147[]', value: 7462],	//AC-302
					proof: [name: '64[]', value: '482'],	//online
					quantity: [name: 'qty[]', value: '500']
				]
			],
			results: [
				[
					preAction: {},
					gleaner: {},
					expected: [
						Style: "AC-302 - Appointment Card with Heart Sticker",
						Color: 'CMYK',
						Proof: 'Digital Proof'
					]],
			]
		]
	]
}


