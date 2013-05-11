<%@ page import="izitag.backend.Reward" %>



<div class="fieldcontain ${hasErrors(bean: rewardInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="reward.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${rewardInstance?.description}" />
</div>

<div class="fieldcontain ${hasErrors(bean: rewardInstance, field: 'isActif', 'error')} ">
	<label for="isActif">
		<g:message code="reward.isActif.label" default="Is Actif" />
		
	</label>
	<g:checkBox name="isActif" value="${rewardInstance?.isActif}" />
</div>

<div class="fieldcontain ${hasErrors(bean: rewardInstance, field: 'merchant', 'error')} ">
	<label for="merchant">
		<g:message code="reward.merchant.label" default="Merchant" />
		
	</label>
	<g:select id="merchant" name="merchant.id" from="${izitag.backend.Merchant.list()}" optionKey="id" required="" value="${rewardInstance?.merchant?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rewardInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="reward.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${rewardInstance?.name}" />
</div>

