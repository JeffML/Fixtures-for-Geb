package fixtures

class Example {
	static def pages = [
		"Appointment Cards" [
			url: "/products/appointment-label-cards.html",
			title: "Label Cards and Calendar Stickers - Easy & Secure Online Ordering",
			fixtures: [
				entry1: [
					isActive: true,
					fields: [
						style: [name: '147[]', valueToSet: 7462],	//AC-302
						proof: [name: '64[]', valueToSet: '482'],	//online
						quantity: [name: 'qty[]', valueToSet: '500']
					],
					result:[
						isActive: true,
						fields: [
							Style: [text: "AC-302 - Appointment Card with Heart Sticker", isShownInCart: false],
							Color: [text: 'CMYK'],
							Proof: [text: 'Digital Proof']
						]
					]
				],
			],
		],
	];	
}
