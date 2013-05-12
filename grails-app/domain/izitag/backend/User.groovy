package izitag.backend

class User {

    String email

    String password

    List<Event> events

    List<CodePromo> codes

    static hasMany = [events : Event , codes : CodePromo]

    static constraints = {
        events nullable: true
    }

    static mapping = {
        version false
    }

}
