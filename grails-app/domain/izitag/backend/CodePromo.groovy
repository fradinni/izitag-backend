package izitag.backend

import org.apache.commons.lang.RandomStringUtils

class CodePromo {

    String code

    boolean isConsumed
    static belongsTo = [user : User, merchant : Merchant]


    static constraints = {
        code unique: true
    }

    static mapping = {
        version false

    }

}
