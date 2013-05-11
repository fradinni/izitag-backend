package izitag.backend

class Action {

    // TYPE is : COUNTER or DURATION
    String type

    static hasMany = [tags : Tag]

    static constraints = {

    }

    static mapping = {
        version false
    }
}
