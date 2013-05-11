package izitag.backend

class Event {


    Date dateCreated
    Date dateUpdated

    Date startDate
    Date endDate



    static hasOne = [user : User , action : Action]


    static constraints = {
       startDate nullable: true
       endDate   nullable: true
    }

    static mapping = {
        version false
    }
}

