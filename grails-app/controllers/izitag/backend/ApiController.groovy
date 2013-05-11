package izitag.backend

import grails.converters.JSON

class ApiController {

    static allowedMethods = [addTag: "POST", addAction : "POST"]

    // /api/addTag?tagId=aaaa&name=tata
    def addTag() {
        log.debug params.tagId
        log.debug params.name
        log.debug params.userId

        def user = User.findById(params.userId)

        if(!user){
            render([message : "User does not exist"] as JSON)
        }

        def tag = Tag.findByTagId(params.tagId)

        if(tag){
            render([message : "Tag already exists"] as JSON)
        }
        else {
            tag = new Tag(name:params.name,tagID: params.tagId, user : user)
            if(!tag.save()){
                log.error "There was an error while saving tag : [${params.tagId} - ${params.name}]"
                render ([message : "there was an error while saving tag : [${params.tagId} - ${params.name}]"] as JSON)
            }
            render ([message : "tag created : [${params.tagId} - ${params.name}]"] as JSON)
        }
    }

    def addAction() {
        log.debug params.type
        log.debug params.name
        log.debug params.description
        log.debug params.userId

        //def action = new
    }
}
