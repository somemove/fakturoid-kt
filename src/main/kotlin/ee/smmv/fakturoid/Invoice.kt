package ee.smmv.fakturoid

import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZonedDateTime

data class Invoice(
	var id: Int? = null,
	var customId: String? = null,
	var proforma: Boolean? = null,
	var partialProforma: Boolean? = null,
	var number: String? = null,
	var variableSymbol: String? = null,
	var yourName: String? = null,
	var yourStreet: String? = null,
	var yourStreet2: String? = null,
	var yourCity: String? = null,
	var yourZip: String? = null,
	var yourCountry: String? = null,
	var yourRegistrationNo: String? = null,
	var yourVatNo: String? = null,
	var yourLocalVatNo: String? = null,
	var clientName: String? = null,
	var clientStreet: String? = null,
	var clientStreet2: String? = null,
	var clientCity: String? = null,
	var clientZip: String? = null,
	var clientCountry: String? = null,
	var clientRegistrationNo: String? = null,
	var clientVatNo: String? = null,
	var clientLocalVatNo: String? = null,
	var subjectId: Int? = null,
	var subjectCustomId: String? = null,
	var generatorId: Int? = null,
	var relatedId: Int? = null,
	var correction: Boolean? = null,
	var correctionId: Int? = null,
	var token: String? = null,
	var status: String? = null,
	var orderNumber: String? = null,
	var issuedOn: LocalDate? = null,
	var taxableFulfillmentDue: LocalDate? = null,
	var due: Int? = null,
	var dueOn: LocalDate? = null,
	var sentAt: ZonedDateTime? = null,
	var paidAt: ZonedDateTime? = null,
	var reminderSentAt: ZonedDateTime? = null,
	var acceptedAt: ZonedDateTime? = null,
	var cancelledAt: ZonedDateTime? = null,
	var note: String? = null,
	var footerNote: String? = null,
	var privateNote: String? = null,
	var tags: List<String>? = null,
	var bankAccountId: Int? = null,
	var bankAccount: String? = null,
	var iban: String? = null,
	var swiftBic: String? = null,
	var paymentMethod: String? = null,
	var currency: String? = null,
	var exchangeRate: String? = null,
	var paypal: Boolean? = null,
	var gopay: Boolean? = null,
	var language: String? = null,
	var transferredTaxLiability: Boolean? = null,
	var supplyCode: Int? = null,
	var euElectronicService: Boolean? = null,
	var vatPriceMode: String? = null,
	var roundTotal: Boolean? = null,
	var subtotal: BigDecimal? = null,
	var nativeSubtotal: BigDecimal? = null,
	var total: BigDecimal? = null,
	var nativeTotal: BigDecimal? = null,
	var remainingAmount: BigDecimal? = null,
	var remainingNativeAmount: BigDecimal? = null,
	var paidAmount: BigDecimal? = null,
	var eet: String? = null,
	var eetCashRegister: String? = null,
	var eetStore: String? = null,
	var eetRecords: List<String>? = null,
	var lines: List<Line>? = null,
	var htmlUrl: String? = null,
	var publicHtmlUrl: String? = null,
	var url: String? = null,
	var pdfUrl: String? = null,
	var subjectUrl: String? = null,
	var createdAt: ZonedDateTime? = null,
	var updatedAt: ZonedDateTime? = null
)
