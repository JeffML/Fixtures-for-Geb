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
		],
		// a more complicated example:
		"Roll Labels": [
			isActive: true,
			inputs: [
				url: "/products/labels-on-rolls-prices.html",
				title: "Labels On Rolls Prices",
				atPage: {at PricingPage},
				values: [
					shape: [name: '7[]', value: 913],		//Square Corner Rect
					width: [name: '53[]', value: 260 ],	// 1 1/2"
					height: [name: '54[]', value: 270],    // 1/2"
					usage: [name: '93[]', value: 616],		// abrasion resistant
					material: [name: '6[]', value: 137],	// thermal transfer
					color: [
						preAction: {colorPalette.launcher.click(); waitFor {colorPalette.colors.present}}, 
						action: {colorPalette.colors = 17},   	    // 424 Grey
						postAction:{colorPalette.close.click()}
						],	
					core: [name: '10[]', value: 128],		// 3"
					perf: [name: '16[]', value: 4591],		// Yes
					unwind: [name: '9[]', value: '119' ],	// most common
					quantity: [name: 'qty[]', value: 4464],
					proof: [name: '5[]', value: 'true']
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
							} else if (classes.contains('7')) {
								gleaned.Shape = it.text().trim()
							} else if (classes.contains('2')) {
								gleaned.Size = it.text().trim()
							} else if (classes.contains('93')) {
								gleaned.Usage = it.text()
							} else if (classes.contains('6')) {
								gleaned.Material = it.text()
							} else if (classes.contains('1')) {
								gleaned.Color = it.text();
							}
						}
						return gleaned
					},
					expected: [
						Quantity: "4464",
						Shape: "Square Corner Rectangles & Squares",
						Usage: 'Abrasion Resistant',
						Color: '424 Grey',
						Size: '1 1/2" x 1/2"',
						Material: 'Thermal Transfer'
					],
				],
				[
					preAction: {
						proceedToOrder.click()
						waitFor(10) {at DetailsPage}
					},
					gleaner: {
						def gleaned = [:]
						def specNames = [
							"Size/Shape",
							"Usage",
							"Material Printed On",
							"Ink Colors Used",
							"Core Size",
							"Perfs",
							"Proof",
						]
						
						def details = specs

						details.eachWithIndex {s, i ->
							gleaned[specNames[i]] = s.text();
						}

						return gleaned
					},
					expected: [
						"Size/Shape": '1 1/2" X 1/2" Square Cornered Rectangle',
						"Usage": 'Abrasion Resistant - High Gloss Laminate',
						"Material Printed On": 'Thermal Transfer',
						"Ink Colors Used": '424 Gray',
						"Core Size": '3"', 
						"Perfs": "Yes", 
						"Proof": 'Yes', 
					]
				]
			]
		],
	]
}


