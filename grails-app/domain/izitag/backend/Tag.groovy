package izitag.backend

class Tag {

    String tagID

    String Name

    static hasOne = [user : User , action : Action]


    static constraints = {

    }

    static mapping = {
        version false
    }
}
