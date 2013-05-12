package izitag.backend

class Reward {

    String name

    String description

    boolean isActif

    static belongsTo = [merchant : Merchant]

    static constraints = {
    }

}
