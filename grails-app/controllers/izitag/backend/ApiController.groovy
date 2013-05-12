package izitag.backend

import grails.converters.JSON
import org.hibernate.FetchMode

class ApiController {

    static allowedMethods = [merchantList: "GET", reward: "GET", checkin: "POST", testMail: "GET", merchant: "GET"]

    CodePromoService codePromoService

    // Liste des codes promos non utilisés par userId (le retour JSON doit contenir les infos du merchant)


    // /api/merchantList?userId=1 (optionnal)
    def merchantList(){
        if(params.userId){
            def user = User.findById(params.userId)
            if(!user) {
                render([userNotExists:true] as JSON)
                return
            }
           render(user.merchants as JSON)
        }
        else {
            render(Merchant.list() as JSON)
        }
    }

    //  /api/merchant?merchantId=1&userId=1
    def merchant() {
        if(!params.merchantId){
            render([missingParameter:"merchantId"] as JSON)
            return
        }
        def merchant = Merchant.findById(params.merchantId)

        if(!merchant){
            render([objectNotFound : "merchant"])
            return
        }
        //Tried everything, I think Eager fetching ain't working with mongodb plugin...
        //gotta do what u gotta do....
        def tag = Tag.get(merchant.tag.id)

        // On commence à construire la réponse.
        def response = [merchant : merchant , tag: tag]
        def event
        if(params.userId){

            def user = User.findById(params.userId)
            if (!user){
                render([userNotExists:true] as JSON)
                return
            }
            event = Event.findByIsCurrentAndUserAndTag(true,user,tag)
            if (!event){
                render([noEventForUserWithMerchant:true] as JSON)
                return
            }
            else {
                response += [event : event]
            }
        }
        render response as JSON
    }

    //  /api/reward?rewardId=1
    def reward() {
        if(!params.rewardId){
            render([missingParameter:"rewardId"] as JSON)
            return
        }

        def reward = Reward.get(params.rewardId)
        if (!reward){
            render([objectNotFound : "reward"])
            return
        }
        render(reward as JSON)
    }

    // Liste des codes promos non utilisés par userId (le retour JSON doit contenir les infos du merchant)
    // /api/codes?userId=1
    def codes(){
        if(!params.userId){
            render([missingParameter:"userId"] as JSON)
            return
        }
        def user = User.findById(params.userId)
        if (!user) {
            render([userNotExists:true] as JSON)
            return
        }

        def codes = CodePromo.findAllByUserAndIsConsumed(user, false)

        render ([codes : codes, merchant: user.merchants] as JSON)

    }

    // Chekin par tagId : lorsqu'on checkin on incrément un compteur jusquà un treshold
    // Arrivé au treshold, on reset le compteur apres avoir crée un code promo
    //  /api/checkin?tagId=AZERTYUIOP&userId=1
    def checkin(){
        if(!params.tagId){
            render([missingParameter:"tagId"] as JSON)
            return
        }
        if(!params.userId){
            render([missingParameter:"userId"] as JSON)
            return
        }
        def user = User.findById(params.userId)

        if (!user){
            render([userNotExists:true] as JSON)
            return
        }
        def tag = Tag.findByTagId(params.tagId)
        if (!tag) {
            render([tagNotExists:true] as JSON)
            return
        }

        def event = Event.findByIsCurrentAndUserAndTag(true,user,tag)

        if (!event){
            event = new Event(user : user, tag : tag, isCurrent: true)
            println "event tag " + event.tag
            event.counter++
            event.save(flush: true, failOnError: true)
            render([event:event, tag:tag] as JSON)
        }
        else {
            if(event.counter == (tag.treshold -1)) {
                //println "sending mail"
                sendMail {
                    from "serty2@gmail.com"
                    to user.email
                    subject "Vous avez une nouvelle promotion chez ${tag.merchant.name}"
                    body "Votre promotion est la suivante : ${tag.merchant.reward.description}"
                }

            }

            if(event.counter >= tag.treshold) {
                // TODO : SEND A MAIL
                def codePromo = codePromoService.createCodePromo(user,tag.merchant)
                event.endDate = new Date()
                event.isCurrent = false
                event.save(flush: true,failOnError: true)
                def response = [codePromo:codePromo, event:event, tag: tag]
                render response as JSON
                return
            }
            else {
                event.counter++
                event.save(flush: true,failOnError: true)
                render([event:event, tag:tag] as JSON)
            }
        }
    }

    def testMail(){
        sendMail {
            from "serty2@gmail.com"
            to "fradinni@gmail.com"
            subject "Ceci est un test !!"
            body "bou"
        }
    }


    /* sendMail {
            from "serty2@gmail.com"
            to "damien.pacaud@gmail.com"
            subject "test mailjet"
            body 'bouya'
        } */
}
