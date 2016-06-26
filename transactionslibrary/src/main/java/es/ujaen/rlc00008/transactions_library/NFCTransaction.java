package es.ujaen.rlc00008.transactions_library;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class NFCTransaction {

	public static final DateFormat OPERATION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final TimeZone SYSTEM_TIMEZONE = TimeZone.getTimeZone("Europe/Madrid");

	private final String operationDate;
	private final String commerceName;
	private final BigDecimal amount;
	private final String currency;
	private final String concept;

	public NFCTransaction(String commerceName, BigDecimal amount, String currency, String concept) {

		Calendar currentCalendar = Calendar.getInstance(SYSTEM_TIMEZONE);

		this.operationDate = OPERATION_DATE_FORMAT.format(currentCalendar.getTime());
		this.commerceName = commerceName;
		this.amount = amount;
		this.currency = currency;
		this.concept = concept;
	}

	public String getCommerceName() {
		return commerceName;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getConcept() {
		return concept;
	}
}
