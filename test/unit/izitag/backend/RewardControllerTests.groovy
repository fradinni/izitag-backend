package izitag.backend



import org.junit.*
import grails.test.mixin.*

@TestFor(RewardController)
@Mock(Reward)
class RewardControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/reward/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.rewardInstanceList.size() == 0
        assert model.rewardInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.rewardInstance != null
    }

    void testSave() {
        controller.save()

        assert model.rewardInstance != null
        assert view == '/reward/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/reward/show/1'
        assert controller.flash.message != null
        assert Reward.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/reward/list'

        populateValidParams(params)
        def reward = new Reward(params)

        assert reward.save() != null

        params.id = reward.id

        def model = controller.show()

        assert model.rewardInstance == reward
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/reward/list'

        populateValidParams(params)
        def reward = new Reward(params)

        assert reward.save() != null

        params.id = reward.id

        def model = controller.edit()

        assert model.rewardInstance == reward
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/reward/list'

        response.reset()

        populateValidParams(params)
        def reward = new Reward(params)

        assert reward.save() != null

        // test invalid parameters in update
        params.id = reward.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/reward/edit"
        assert model.rewardInstance != null

        reward.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/reward/show/$reward.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        reward.clearErrors()

        populateValidParams(params)
        params.id = reward.id
        params.version = -1
        controller.update()

        assert view == "/reward/edit"
        assert model.rewardInstance != null
        assert model.rewardInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/reward/list'

        response.reset()

        populateValidParams(params)
        def reward = new Reward(params)

        assert reward.save() != null
        assert Reward.count() == 1

        params.id = reward.id

        controller.delete()

        assert Reward.count() == 0
        assert Reward.get(reward.id) == null
        assert response.redirectedUrl == '/reward/list'
    }
}
