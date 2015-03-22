package pages.modules

import geb.Module

class ACWButtons extends Module {
	static def content = {
		foreground {
			$('a', text: "Foreground")
		}
		background {
			$('a', text: "Background")
		}
		invert {
			$('a', text: "Invert")
		}
		update {
			$('input#update')
		}
	}
}