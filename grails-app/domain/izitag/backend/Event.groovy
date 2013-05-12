package izitag.backend

class Event {


    Date dateCreated

    Date endDate

    int counter = 0

    User user

    Tag tag

    boolean isCurrent

    //static hasOne = [tamere : Tag , user : User ]


    static constraints = {
        endDate nullable: true
    }

    static mapping = {
        version false
    }


}

