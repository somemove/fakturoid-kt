package ee.smmv.fakturoid

class FakturoidException : RuntimeException {

	constructor(s: String) : super(s)
	constructor(s: String, e: Throwable) : super(s, e)

	private lateinit var apiErrors: List<ErrorMessages>

	fun getApiErrors() = this.apiErrors

	fun setApiErrors(apiError: ErrorMessages): FakturoidException {
		this.apiErrors = listOf(apiError)

		return this
	}

	fun setApiErrors(apiErrors: List<ErrorMessages>): FakturoidException {
		this.apiErrors = apiErrors

		return this
	}

}
