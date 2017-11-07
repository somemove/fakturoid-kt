package ee.smmv.fakturoid

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatus.PAYMENT_REQUIRED
import org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE
import org.springframework.http.HttpStatus.TOO_MANY_REQUESTS
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Base64

class Fakturoid(
	private val slug: String,
	private val email: String,
	private val apiKey : String,
	private val userAgent : String
) {

	companion object {
		const val API_BASE : String = "https://app.fakturoid.cz/api/v2/accounts"
	}

	private val restTemplate : RestTemplate = RestTemplate(
		mutableListOf<HttpMessageConverter<*>>(
			StringHttpMessageConverter(StandardCharsets.UTF_8)
		)
	)
	private val mapper : ObjectMapper = jacksonObjectMapper()

	fun fireEvent(invoiceID : Int, eventName : String) {
		val url : URI = urlFor("invoices/$invoiceID/fire.json?event=$eventName")

		val responseEntity : ResponseEntity<String> = restTemplate.postForEntity(url, HttpEntity<Any>(headers()), String::class.java)

		val httpMessage = "HTTP ${responseEntity.statusCode} ${responseEntity.statusCodeValue}"

		when (responseEntity.statusCode) {
			OK -> {}
			UNPROCESSABLE_ENTITY -> throw IllegalArgumentException(httpMessage)
			PAYMENT_REQUIRED, TOO_MANY_REQUESTS, SERVICE_UNAVAILABLE -> throw IllegalStateException(httpMessage)
			else -> throw IllegalStateException(httpMessage)
		}

	}

	private fun urlFor(localPath : String) : URI = URL("${API_BASE}/${slug}/$localPath").toURI()

	private fun headers(headers : HttpHeaders = HttpHeaders()) : HttpHeaders {
		val cred : String = Base64.getEncoder().encodeToString("${email}:${apiKey}".toByteArray(StandardCharsets.UTF_8))

		with (headers) {
			contentType = MediaType.APPLICATION_JSON
			set("User-Agent", userAgent)
			set("Authorization", "Basic $cred")
		}

		return headers
	}

}