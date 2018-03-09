package ee.smmv.fakturoid

data class ErrorMessage(
	var key: String? = null,
	var messages: List<String> = listOf()
)
