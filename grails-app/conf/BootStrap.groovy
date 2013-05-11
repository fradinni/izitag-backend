import com.gmongo.GMongo
import izitag.backend.Action
import izitag.backend.Tag
import izitag.backend.User

class BootStrap {

    def init = { servletContext ->

        def firstTag = Tag.findByTagID("AZERTYUIOP")
        if (!firstTag){
            firstTag =  new Tag(tagID: "AZERTYUIOP" , name: "My First Tag")
            firstTag.save(flush: true)
        }

        def firstAction = Action.findByName("Toilet")
        if (!firstAction) {
           firstAction =  new Action(name: "Toilet", type:"COUNTER", description: "How many time did you go ? :p")
           firstAction.addToTags(firstTag)
           firstAction.save(flush: true)
        }

        def firstUser = User.findByName("Damien")
        if (!firstUser)
        {
            firstUser = new User(name: "Damien")
            firstUser.addToActions(firstAction)
            firstUser.save(flush: true)
        }

    }
    def destroy = {
    }
}
