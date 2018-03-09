package ee.smmv.fakturoid

import java.time.ZonedDateTime

data class Subject (
	var id: Int? = null,
	var customId: String? = null,
	var type: String = "customer",
	var name: String = "",
	var street: String? = null,
	var street2: String? = null,
	var city: String? = null,
	var zip: String? = null,
	var country: String? = null,
	var registrationNo: String? = null,
	var vatNo: String? = null,
	var localVatNo: String? = null,
	var bankAccount: String? = null,
	var iban: String? = null,
	var variableSymbol: String? = null,
	var enabledReminders: Boolean = true,
	var fullName: String? = null,
	var email: String? = null,
	var emailCopy: String? = null,
	var phone: String? = null,
	var web: String? = null,
	var privateNote: String? = null,
	var avatarUrl: String? = null,
	var htmlUrl: String? = null,
	var url: String? = null,
	var createdAt: ZonedDateTime? = null,
	var updatedAt: ZonedDateTime? = null
)
