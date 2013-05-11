import izitag.backend.User

class BootStrap {

    def init = { servletContext ->


        def firstUser = User.findByName("Damien")
        if (!firstUser)
        {
           new User(name: "Damien").save(flush: true)
        }


    }
    def destroy = {
    }
}
