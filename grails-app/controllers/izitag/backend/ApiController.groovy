package izitag.backend

import grails.converters.JSON

class ApiController {

    static allowedMethods = [merchantList: "GET", reward: "GET", checkin: "POST"]
    CodePromoService codePromoService

    // Infos sur un commercant par id (plus le nombre de checkins pour ce user chez ce commercant et le treshold pour ce tag)

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

    //  /api/reward?rewardId=1 (optionnal)
    def reward() {
        if(!params.rewardId){
            render([missingParameter:"rewardId"] as JSON)
            return
        }

        def reward = Reward.get(params.rewardId)
        if (!reward){
            render([objectNotFound : "reward"])
        }
        render(reward as JSON)
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
               /* sendMail {
                    from "serty2@gmail.com"
                    to user.email
                    subject "Vous avez une nouvelle promotion chez ${tag.merchant.name}"
                    body "Votre promotion est la suivante : ${tag.merchant.reward.description}"
                }*/

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
            to "damien.pacaud@gmail.com"
            subject "Vous avez une nouvelle promotion chez "
            body "Votre promotion est la suivante :"
        }
    }
    // /api/addTag?tagId=aaaa&name=tata&userId=1
    /*def addTag() {

        println "tagId : ${params.tagId}"
        println "name : ${params.name}"
        println "userId : ${params.merchantId}"

        if(!params.tagId){
            render([missingMandatoryParameter:"tagId"] as JSON)
            return
        }
        if(!params.name){
            render([missingMandatoryParameter:"name"] as JSON)
            return
        }
        if(!params.merchantId){
            render([missingMandatoryParameter:"merchantId"] as JSON)
            return
        }


        def merchant = Merchant.findById(params.merchantId)

        if(!merchant){
            render([merchantNotExists:true] as JSON)
            return
        }

        def tag = Tag.findByTagId(params.tagId)

        if(tag){
            render([tagAlreadyExists : true] as JSON)
        }
        else {
            tag = new Tag(name : params.name , tagID : params.tagId )
            user.addToTags(tag)
            if(!tag.save() || !user.save()){
                log.error "There was an error while saving tag : [${params.tagId} - ${params.name}]"
                tag.errors?.each {
                    println it
                }
                user.errors?.each {
                    println it
                }
                render ([error : true , message : "there was an error while saving tag : [${params.tagId} - ${params.name}]"] as JSON)
            }
            render ([tag: tag , message : "tag created : [${params.tagId} - ${params.name}]"] as JSON)
        }
    }
    */
    /*
    // /apigetTags?userId=1
    def getTags(){
        println "---- userId : ${params.userId}"

        if(!params.userId){
            render([missingMandatoryParameter:"userId"] as JSON)
            return
        }

        def user = Merchant.findById(params.userId)

        if(!user){
            render([userNotExists:true] as JSON)
            return
        }

        def tags = Tag.findAllByUser(user)


        render (tags as JSON)
    }
    */
    /*
     // /api/addTag?type=COUNTER&name=tata&description=whatever&userId=1
    def addAction() {
        println "type : ${params.type}"
        println "name : ${params.name}"
        println "description : ${params.description}"
        println "userId : ${params.userId}"

        if(!params.name){
            render([missingMandatoryParameter:"name"] as JSON)
            return
        }
        if(!params.type){
            render([missingMandatoryParameter:"type"] as JSON)
            return
        }
        if(!params.userId){
            render([missingMandatoryParameter:"userId"] as JSON)
            return
        }

        def user = Merchant.findById(params.userId)

        if(!user){
            render([userNotExists:true] as JSON)
            return
        }

        //Check type is supported
        if(params.type != "COUNTER" && params.type != "DURATION"){
            render([typeNotSupported:true] as JSON)
            return
        }

        def action = new Action(type: params.type , name: params.name , description: params.description )

        user.addToActions(action)

        if(!action.save() || !user.save()){
            log.error "There was an error while saving action : [${params.name} - ${params.type}]"
            action.errors.each {
                println it
            }
            user.errors?.each {
                println it
            }
            render ([error: true, message : "there was an error while saving action : [${params.name} - ${params.type}]"] as JSON)
        }
        render ([action: action , message : "action created : [${params.name} - ${params.type}]"] as JSON)
    }

    def addTagsToAction () {


    }

    // /api/addEvent?tagId=tagId&userId=1
    def addEvent() {

        if(!params.tagId){
            render([missingMandatoryParameter:"tagId"] as JSON)
            return
        }
        if(!params.userId){
            render([missingMandatoryParameter:"userId"] as JSON)
            return
        }

        def user = Merchant.findById(params.userId)

        if(!user){
            render([userNotExists:true] as JSON)
            return
        }

        def tag = Tag.findByTagID(params.tagId)

        if(!tag){
            // Le tag n'existe pas, il faut le créer
            render([tagExists : false] as JSON)
            return
        }
        def action = Action.findById(tag.actionId)
        if(!action) {
            render([actionAssociatedToTag:false , tag : tag ])
            return
        }
        if(action.type == "DURATION"){
            def myEvent = Event.findByUserAndActionAndEndDateIsEmpty(user,action)
            if(myEvent){
                println "event found"
                myEvent.endDate = new Date()
            }
            else {
                println "event not found -  creating one"
                myEvent = new Event()
            }

        }
        else if(action.type == "COUNTER"){
            def event = new Event(user : user, action : action)
        }
    }
     */
    /* sendMail {
            from "serty2@gmail.com"
            to "damien.pacaud@gmail.com"
            subject "test mailjet"
            body 'bouya'
        } */
}
