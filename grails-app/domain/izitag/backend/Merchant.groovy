package izitag.backend

class Merchant {

    String name

    String adress

    String codePostal

    String city

    String imgUrl

    static hasOne = [reward : Reward , tag : Tag ]

    static constraints = {
        reward nullable: true
        tag nullable: true
    }

    static mapping = {
        version false
    }

}
