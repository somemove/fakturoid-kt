package ee.smmv.fakturoid

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class ErrorMessagesDeserializer : JsonDeserializer<ErrorMessages>() {
	override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ErrorMessages {
		val errorsNode: JsonNode = p.codec.readTree(p)

		if (errorsNode.has("errors")) {
			val errorsObject = errorsNode.get("errors")

			return ErrorMessages(
				errors =
					errorsObject
						.fields()
						.asSequence()
						.map {
							val valueNode = it.value

							if (valueNode.isArray) {
								ErrorMessage(
									key = it.key,
									messages = valueNode
										.elements()
										.asSequence()
										.map { it.textValue() }
										.toList()
								)
							} else {
								throw IllegalArgumentException("Error Message does not contain array of errors")
							}
						}.toList()
			)
		} else {
			throw IllegalArgumentException("Error Messages payload is missing 'errors' attribute.")
		}
	}

}
