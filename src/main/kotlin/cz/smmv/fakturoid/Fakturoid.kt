package cz.smmv.fakturoid

import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Base64

class Fakturoid(val slug: String, val email: String, val apiKey : String, val userAgent : String) {

	fun fireEvent(invoiceID : Int, eventName : String) {
		val url : URL = URL("https://app.fakturoid.cz/api/v2/accounts/${slug}/invoices/${invoiceID}/fire.json?event=${eventName}")

		val conn : HttpURLConnection = url.openConnection() as HttpURLConnection
		val cred : String = Base64.getEncoder().encodeToString("${email}:${apiKey}".toByteArray(StandardCharsets.UTF_8))

		conn.requestMethod = "POST"
		conn.setRequestProperty("Authorization", "Basic ${cred}")
		conn.setRequestProperty("Content-Type", "application/json")
		conn.setRequestProperty("User-Agent", userAgent)
		conn.useCaches = false
		conn.doOutput = true
		conn.doInput = true

		conn.connect()

		val httpMessage = "HTTP ${conn.responseCode} ${conn.responseMessage}"

		when (conn.responseCode) {
			HttpURLConnection.HTTP_OK -> {}
			// HTTP 422 Unprocessable Entity
			422 -> throw IllegalArgumentException(httpMessage)
			// HTTP 429 Too Many Requests
			HttpURLConnection.HTTP_PAYMENT_REQUIRED, 429, HttpURLConnection.HTTP_UNAVAILABLE -> throw IllegalStateException(httpMessage)
		}

	}

}