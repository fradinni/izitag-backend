package izitag.backend

class Action {

    String name

    String description

    // TYPE is : COUNTER or DURATION
    String type

    static hasMany = [tags : Tag]

    static constraints = {
        description nullable: true
    }

    static mapping = {
        version false
    }
}
