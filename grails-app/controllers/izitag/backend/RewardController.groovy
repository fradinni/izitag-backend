package izitag.backend

import org.springframework.dao.DataIntegrityViolationException

class RewardController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [rewardInstanceList: Reward.list(params), rewardInstanceTotal: Reward.count()]
    }

    def create() {
        [rewardInstance: new Reward(params)]
    }

    def save() {
        def rewardInstance = new Reward(params)
        if (!rewardInstance.save(flush: true)) {
            render(view: "create", model: [rewardInstance: rewardInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reward.label', default: 'Reward'), rewardInstance.id])
        redirect(action: "show", id: rewardInstance.id)
    }

    def show(Long id) {
        def rewardInstance = Reward.get(id)
        if (!rewardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reward.label', default: 'Reward'), id])
            redirect(action: "list")
            return
        }

        [rewardInstance: rewardInstance]
    }

    def edit(Long id) {
        def rewardInstance = Reward.get(id)
        if (!rewardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reward.label', default: 'Reward'), id])
            redirect(action: "list")
            return
        }

        [rewardInstance: rewardInstance]
    }

    def update(Long id, Long version) {
        def rewardInstance = Reward.get(id)
        if (!rewardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reward.label', default: 'Reward'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (rewardInstance.version > version) {
                rewardInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reward.label', default: 'Reward')] as Object[],
                        "Another user has updated this Reward while you were editing")
                render(view: "edit", model: [rewardInstance: rewardInstance])
                return
            }
        }

        rewardInstance.properties = params

        if (!rewardInstance.save(flush: true)) {
            render(view: "edit", model: [rewardInstance: rewardInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reward.label', default: 'Reward'), rewardInstance.id])
        redirect(action: "show", id: rewardInstance.id)
    }

    def delete(Long id) {
        def rewardInstance = Reward.get(id)
        if (!rewardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reward.label', default: 'Reward'), id])
            redirect(action: "list")
            return
        }

        try {
            rewardInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reward.label', default: 'Reward'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reward.label', default: 'Reward'), id])
            redirect(action: "show", id: id)
        }
    }
}
