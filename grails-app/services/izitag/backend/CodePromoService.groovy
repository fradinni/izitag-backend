package izitag.backend

import org.apache.commons.lang.RandomStringUtils

class CodePromoService {

    def createCodePromo(user, merchant) {

        def code = RandomStringUtils.random(9, true, true)
        def codePromo = new CodePromo(code : code, isConsumed: false, user:user, merchant: merchant)
        codePromo.save(flush: true, failOnError: true)
        codePromo
    }
}
