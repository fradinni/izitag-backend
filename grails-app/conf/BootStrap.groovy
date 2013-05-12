
import izitag.backend.CodePromoService
import izitag.backend.Reward
import izitag.backend.Tag
import izitag.backend.Merchant
import izitag.backend.User

class BootStrap {

    def codePromoService

    def init = { servletContext ->
        /*
        def firstMerchant = Merchant.findByName("Castorama")
        if(!firstMerchant) {
            firstMerchant = new Merchant(name:"Castorama" ,adress: "12 rue du louvre", codePostal: "75001" , city: "Paris" , imgUrl:"http://www.mes-soldes.com/wp-content/logos/logo-castorama.gif" )
            firstMerchant.save(flush: true, failOnError: true)
        }

        def firstReward = Reward.findByName("Fidelite casto")
        if (!firstReward) {
            firstReward = new Reward(name: "Fidelite casto", description:  "Lorsque vous débloquez ce reward, nous vous offrons 10% sur votre prochain achat (valable 15 jours)", isActif: true)
            firstReward.merchant = firstMerchant
            firstReward.save(flush: true, failOnError: true)
        }

        def firstTag = Tag.findByTagId("AZERTYUIOP")
        if (!firstTag){
            firstTag =  new Tag(tagId: "AZERTYUIOP" , name: "Tag Fidelité Casto", merchant: firstMerchant, treshold : 10 )
            firstTag.save(flush: true, failOnError: true)
        }

        def firstUser = User.findByEmail("damien.pacaud@gmail.com")
        if (!firstUser)
        {
            firstUser = new User(email : "damien.pacaud@gmail.com", password: "test")
            //firstUser.addToMerchants(firstMerchant)
            firstUser.save(flush: true, failOnError: true)
        }
        /***************/
        /*
        // Ajout Starbucks
        def secondMerchant = Merchant.findByName("Starbucks Opéra")
        if(!secondMerchant) {
            secondMerchant = new Merchant(name:"Starbucks Opéra" ,adress: "26 Avenue de l'Opéra", codePostal: "75009" , city: "Paris" , imgUrl:"http://freethoughtblogs.com/taslima/files/2012/10/starbucks-coffee-logo.gif" )
            secondMerchant.save(flush: true, failOnError: true)
        }
        def secondReward = Reward.findByName("Fidelite Starbucks Opéra")
        if (!secondReward) {
            secondReward = new Reward(name: "Fidelite Starbucks Opéra", description:  "Lorsque vous débloquez ce reward, nous vous offrons un café gratuit", isActif: true)
            secondReward.merchant = secondMerchant
            secondReward.save(flush: true, failOnError: true)
        }

        def secondTag = Tag.findByTagId("BOUYASTARBUCKS")
        if (!secondTag){
            secondTag =  new Tag(tagId: "BOUYASTARBUCKS" , name: "Tag Fidelité Starbucks Opéra", merchant: secondMerchant, treshold : 3 )
            secondTag.save(flush: true, failOnError: true)
        }
        //firstUser.addToMerchants(firstMerchant)
        //firstUser.addToMerchants(secondMerchant)*/
    }

    def destroy = { //
    }
}
