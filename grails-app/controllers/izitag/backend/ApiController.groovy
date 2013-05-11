package izitag.backend

import grails.converters.JSON

class ApiController {

    static allowedMethods = [addTag: "POST", addAction : "POST", getTags: "GET", addTagsToAction: "POST"]

    // /api/addTag?tagId=aaaa&name=tata&userId=1
    def addTag() {

        println "tagId : ${params.tagId}"
        println "name : ${params.name}"
        println "userId : ${params.userId}"

        if(!params.tagId){
            render([missingMandatoryParameter:"tagId"] as JSON)
            return
        }
        if(!params.name){
            render([missingMandatoryParameter:"name"] as JSON)
            return
        }
        if(!params.userId){
            render([missingMandatoryParameter:"userId"] as JSON)
            return
        }


        def user = User.findById(params.userId)

        if(!user){
            render([userNotExists:true] as JSON)
            return
        }

        def tag = Tag.findByTagID(params.tagId)

        if(tag){
            render([tagExists : true] as JSON)
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

    // /apigetTags?userId=1
    def getTags(){
        println "---- userId : ${params.userId}"

        if(!params.userId){
            render([missingMandatoryParameter:"userId"] as JSON)
            return
        }

        def user = User.findById(params.userId)

        if(!user){
            render([userNotExists:true] as JSON)
            return
        }

        def tags = Tag.findAllByUser(user)
        /* sendMail {
            from "serty2@gmail.com"
            to "damien.pacaud@gmail.com"
            subject "test mailjet"
            body 'bouya'
        } */

        render (tags as JSON)
    }

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

        def user = User.findById(params.userId)

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

        def user = User.findById(params.userId)

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
}
