package f4g


import geb.Page

class AnyPage extends Page {
	static url
	static titleText

	static at = { title.contains(titleText)  }

	static content = {
	}
}

