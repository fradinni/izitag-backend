package izitag.backend

class Tag {

    String tagID

    String name

    static hasOne = [user : User , action : Action]


    static constraints = {
       user nullable: true
       action nullable: true
    }

    static mapping = {
        version false
    }
}
