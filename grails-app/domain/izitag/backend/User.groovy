package izitag.backend

class User {

    String email

    String password

    List<Event> events

    List<Merchant> merchants

    static hasMany = [events : Event , merchants : Merchant]

    static constraints = {
        merchants nullable: true
        events nullable: true
    }

    static mapping = {
        version false
    }

}
