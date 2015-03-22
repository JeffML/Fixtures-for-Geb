package pages.modules

import java.util.Spliterator;
import java.util.function.Consumer;

import geb.Module
import geb.navigator.Navigator;

class FloatingDiv extends Module {
    static content = {
        quotePrice(wait:30){ 
            def qp = $('span', id:'quotePrice')
            if (qp.find('label')) {
                qp = qp.find('label')
            }
            return qp       // explicit return of qp required
        }
        
        sendToQuoter { $('input', class: 'sendToQuoterButton') }
        
        floatingDivSpecs { $('#float-table-prod').find('label.blueText') }
        
        hasPrice { 
            def price = quotePrice.text()
            return price.size() > 0 && price =~ /\$[\d,]+\.\d{2}/
        }
    }
}