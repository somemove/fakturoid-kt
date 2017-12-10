package ee.smmv.fakturoid

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpStatus.CREATED
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
import org.springframework.web.util.UriComponentsBuilder
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

		@JvmStatic private val TYPE_LIST_OF_SUBJECTS = object: TypeReference<List<Subject>>() {}
	}

	private val restTemplate : RestTemplate = RestTemplate(
		mutableListOf<HttpMessageConverter<*>>(
			StringHttpMessageConverter(StandardCharsets.UTF_8)
		)
	)

	private val mapper : ObjectMapper = ObjectMapper()

	init {
		mapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
		mapper.setSerializationInclusion(Include.NON_NULL)
		mapper.registerModule(JavaTimeModule())
	}

	fun create(subject : Subject) : Subject {
		val url : URI = urlFor("subjects.json")
		val json = mapper.writeValueAsString(subject)

		val responseEntity : ResponseEntity<String> = restTemplate.postForEntity(url, HttpEntity<String>(json, headers()), String::class.java)

		when (responseEntity.statusCode) {
			CREATED -> {
				val s = mapper.readValue<Subject>(responseEntity.body, Subject::class.java)
				return s
			}
			else -> {
				throw RuntimeException("Could not create subject")
			}
		}
	}

	fun find(id : Int) : Subject? {
		val url : URI = urlFor("subjects/$id.json")

		val requestEntity : HttpEntity<String> = HttpEntity(headers())
		val responseEntity : ResponseEntity<String> = restTemplate.exchange(url, GET, requestEntity, String::class.java)

		return when (responseEntity.statusCode) {
			OK -> {
				mapper.readValue<Subject>(responseEntity.body, Subject::class.java)
			}
			else -> {
				throw RuntimeException("Could not find Subject id=$id")
			}
		}
	}

	fun findAll() : List<Subject> {
		val url : URI = urlFor("subjects.json")

		val requestEntity : HttpEntity<String> = HttpEntity(headers())
		val responseEntity : ResponseEntity<String> = restTemplate.exchange(url, GET, requestEntity, String::class.java)

		return when (responseEntity.statusCode) {
			OK -> {
				mapper.readValue(responseEntity.body, TYPE_LIST_OF_SUBJECTS)
			}
			else -> {
				throw RuntimeException("Could not find subjects")
			}
		}
	}

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

	fun search(query : String) : List<Subject> {
		val url : URI = urlFor("subjects/search.json", mapOf("query" to query))

		val requestEntity : HttpEntity<String> = HttpEntity(headers())
		val responseEntity : ResponseEntity<String> = restTemplate.exchange(url, GET, requestEntity, String::class.java)

		when (responseEntity.statusCode) {
			OK -> {
				return mapper.readValue(responseEntity.body, TYPE_LIST_OF_SUBJECTS)
			}
			else -> {
				throw RuntimeException("Could not search for subjects")
			}
		}
	}

	private fun urlFor(localPath : String) : URI = URL("$API_BASE/$slug/$localPath").toURI()

	private fun urlFor(localPath: String, params: Map<String, String>) : URI =
		with (UriComponentsBuilder.fromHttpUrl("$API_BASE/$slug/$localPath")) {
			params.forEach { key, value -> queryParam(key, value) }

			build().encode().toUri()
		}

	private fun headers(headers : HttpHeaders = HttpHeaders()) : HttpHeaders {
		val cred : String = Base64.getEncoder().encodeToString("$email:$apiKey".toByteArray(StandardCharsets.UTF_8))

		with (headers) {
			contentType = MediaType.APPLICATION_JSON
			set("User-Agent", userAgent)
			set("Authorization", "Basic $cred")
		}

		return headers
	}

}