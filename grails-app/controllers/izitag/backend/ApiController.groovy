package izitag.backend

import grails.converters.JSON

class ApiController {

    static allowedMethods = [addTag: "POST", addAction : "POST", getTags: "GET"]

    // /api/addTag?tagId=aaaa&name=tata&userId=1
    def addTag() {

        println "tagId : ${params.tagId}"
        println "name : ${params.name}"
        println "userId : ${params.userId}"

        def user = User.findById(params.userId)

        if(!user){
            render([message : "User does not exist"] as JSON)
            return
        }

        def tag = Tag.findByTagId(params.tagId)

        if(tag){
            render([message : "Tag already exists"] as JSON)
        }
        else {
            tag = new Tag(name : params.name , tagID : params.tagId )
            user.addToTags(tag)
            if(!tag.save() || !user.save()){
                log.error "There was an error while saving tag : [${params.tagId} - ${params.name}]"
                tag.errors.each {
                    println it
                }
                render ([message : "there was an error while saving tag : [${params.tagId} - ${params.name}]"] as JSON)
            }
            render ([message : "tag created : [${params.tagId} - ${params.name}]"] as JSON)
        }
    }

    // /apigetTags?userId=1
    def getTags(){
        println "---- userId : ${params.userId}"

        def user = User.findById(params.userId)

        if(!user){
            render([message : "User does not exist"] as JSON)
            return
        }

        def tags = Tag.findAllByUser(user)

        render (tags as JSON)
    }


    def addAction() {
        println "type : ${params.type}"
        println "name : ${params.name}"
        println "description : ${params.description}"
        println "userId : ${params.userId}"

        //def action = new
    }
}
