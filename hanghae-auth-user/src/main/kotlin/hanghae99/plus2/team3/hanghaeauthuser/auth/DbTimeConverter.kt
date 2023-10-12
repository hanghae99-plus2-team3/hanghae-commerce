package hanghae99.plus2.team3.hanghaeauthuser.auth

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Converter
object DbTimeConverter : AttributeConverter<LocalDateTime, Timestamp> {

    private const val DEFAULT_ZONE = "Asia/Seoul"

    override fun convertToDatabaseColumn(attribute: LocalDateTime?): Timestamp? {
        return attribute?.run { Timestamp.valueOf(this) }
    }

    override fun convertToEntityAttribute(dbData: Timestamp?): LocalDateTime? {
        return convertToEntityAttribute(dbData, DEFAULT_ZONE)
    }

    private fun convertToEntityAttribute(dbData: Timestamp?, zone: String = DEFAULT_ZONE): LocalDateTime? {
        val zoneId = ZoneId.of(zone)
        val offset = ZonedDateTime.now(zoneId).offset.totalSeconds
        return dbData?.toLocalDateTime()?.plusSeconds(offset.toLong())
    }
}
