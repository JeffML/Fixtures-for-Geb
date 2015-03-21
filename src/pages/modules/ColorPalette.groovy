package pages.modules

import java.util.Spliterator;
import java.util.function.Consumer;

import geb.Module
import geb.navigator.Navigator;

class ColorPalette extends Module {
	static def content = {
		launcher { $('img', alt: ~/[Cc]olor[\s\-][Pp]alette/) }
		colors { $('#facebox input', type: 'checkbox') }
		close { $('#facebox a.close') }
	}
}
