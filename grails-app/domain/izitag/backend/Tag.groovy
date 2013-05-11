package izitag.backend

class Tag {

    String tagId

    String name

    int treshold

    static belongsTo = [ merchant : Merchant ]


    static constraints = {
    }

    static mapping = {
        version false
    }
}
