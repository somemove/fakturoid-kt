package ee.smmv.fakturoid

import java.math.BigDecimal

data class Line(
	var id: Int? = null,
	var name: String? = null,
	var quantity: BigDecimal? = null,
	var unitName: String? = null,
	var unitPrice: BigDecimal? = null,
	var vatRate: BigDecimal? = null,
	var unitPriceWithoutVat: BigDecimal? = null,
	var unitPriceWithVat: BigDecimal? = null
)
