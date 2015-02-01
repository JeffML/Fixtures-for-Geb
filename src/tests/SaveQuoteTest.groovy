package tests

import static org.junit.Assert.*
import geb.*
import geb.junit4.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pages.AnyPage


@RunWith(Parameterized.class)
class SaveQuoteTest extends FixturedTest {
	SaveQuoteTest(key, url, page_) {
		super(key, url, page_)
	}
	
	@Test
	void testSaveQuote() {
		
		def fixtures = this.page.fixtures;
		
		fixtures.each {key, eset ->
			if (eset.isActive) {
				loadEntrySet(key, eset);
				
				if (eset.result && eset.result.isActive) {
					readResultSet(key, eset.result)
				}
			}
		}
/*
		quoteInfoSets.each { key, qset ->
			def dset = dataSets[key]
			if (dset.isActive && qset.isActive) {
				loadDataSet(key, dset)

				saveQuote.click()
				waitFor 10, {at SaveQuotePopup}
				email = "jeff.l@mavericklabel.com"
				zip = "98020"
				note = "Flum!\nFlim!\nFlam!"
				submit.click()

				waitFor 25, {at ShipEstPopup}		// VS will more than likely fail at this timeout setting; issue #1499
				
				def pcode = owner.productCode
				println "text: ${savedText.text()}, productCode: $pcode"
				def m = (savedText.text() =~ /.+?([$pcode|CM]\d{3}-\d{3}-\d{3}).+?/)
				assertTrue("Missing quote number", m.matches())
				def quoteNo = m[0][1]
				println "quoteNo = $quoteNo"
				
				assertTrue("Missing quote batch", (batchLink.find('span').text() =~ /.+?\?quote_batch\=\d{7,9}/).matches())
				
				def n = 0;
				
				qset.fields.eachWithIndex {obj, i ->
					def isShown = obj.value.isShownInDetails == null? true : obj.value.isShownInDetails
					if (isShown) {
						assertEquals("Value mismatch on $obj.key", obj.value.text, quoteInfo[n++].text());
					}
				}

				assertNotEquals("no prices shown", 0, priceTableRows.size())

				assertTrue("Wrong or missing zip code", shipEstText.contains('98020'))
				assertNotEquals("No shipping estimates", 0, shipEstTableRows.size())
			}
		}*/
	}
}
