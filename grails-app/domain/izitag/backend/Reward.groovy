package izitag.backend

import liquibase.integration.ant.MarkNextChangeSetRanTask

class Reward {

    String name

    String description

    boolean isActif
    Merchant merchant
    //static belongsTo = [merchant : Merchant]

    static constraints = {
    }

}
