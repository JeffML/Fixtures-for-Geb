package fixtures

class Example {
	static def pages = [
		"Appointment Cards": [
			url: "/products/appointment-label-cards.html",
			title: "Label Cards and Calendar Stickers - Easy & Secure Online Ordering",
			fixtures: [
				entry1: [
					isActive: true,
					fields: [
						style: [name: '147[]', value: 7462],	//AC-302
						proof: [name: '64[]', value: '482'],	//online
						quantity: [name: 'qty[]', value: '500']
					],
					result:[
						isActive: true,
						fields: [
							Style: [text: "AC-302 - Appointment Card with Heart Sticker"],
							Color: [text: 'CMYK'],
							Proof: [text: 'Digital Proof']
						]
					]
				],
			],
		],
	];	
}
