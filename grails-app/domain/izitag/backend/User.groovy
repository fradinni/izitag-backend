package izitag.backend

class User {

    String name

    static hasMany = [actions : Action, events : Event, tags : Tag]

    static constraints = {
        actions nullable: true
    }

    static mapping = {
        version false
    }
}
