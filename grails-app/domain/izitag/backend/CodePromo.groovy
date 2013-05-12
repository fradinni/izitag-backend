package izitag.backend

import org.apache.commons.lang.RandomStringUtils

class CodePromo {

    String code

    User user

    Merchant merchant

    boolean isConsumed
    //static belongsTo = [user : User, merchant : Merchant]


    static constraints = {
        code unique: true
    }

    static mapping = {
        version false

    }


}
