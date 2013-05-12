package izitag.backend

class Tag {

    String tagId

    String name

    int treshold

    Merchant merchant
    //static belongsTo = [ merchant : Merchant ]


    static constraints = {
    }

    static mapping = {
        version false
    }

}
