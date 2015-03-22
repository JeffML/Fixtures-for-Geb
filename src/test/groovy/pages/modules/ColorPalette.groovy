package pages.modules

import geb.Module


class ColorPalette extends Module {
	static def content = {
		launcher { $('img', alt: ~/[Cc]olor[\s\-][Pp]alette/) }
		colors { $('#facebox input', type: 'checkbox') }
		close { $('#facebox a.close') }
	}
}
