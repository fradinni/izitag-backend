package izitag.backend

class Event {


    Date dateCreated

    static hasOne = [user : User, tag : Tag ]


    static constraints = {

    }

    static mapping = {
        version false
    }
}

