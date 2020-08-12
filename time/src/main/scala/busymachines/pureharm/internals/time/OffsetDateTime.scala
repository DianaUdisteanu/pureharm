package busymachines.pureharm.internals.time
import java.time.chrono.ChronoLocalDateTime
import java.time.temporal.ChronoUnit

import busymachines.pureharm.effects._
import java.{time => jt}

object OffsetDateTime {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.OffsetDateTime] =
    Sync[F].delay(jt.OffsetDateTime.now(config.zoneId))

  //TODO: adapt error e.g TimeFormatException in TimeFormatAnomaly
  def parse[F[_] : ApplicativeAttempt](s: String)(implicit config: TimeConfiguration): F[jt.OffsetDateTime] =
    ApplicativeAttempt[F].catchNonFatal(jt.OffsetDateTime.parse(s, config.offsetDateTimeFormat))

  //def toLocalDateTime[F[_] : Sync](offsetDateTime: jt.OffsetDateTime)(implicit config : TimeConfiguration): F[jt.LocalDateTime] =
    //Sync[F].delay(offsetDateTime.atZoneSimilarLocal(config.zoneId).toLocalDateTime.truncatedTo(ChronoUnit.SECONDS))

  def toLocalDate[F[_] : Sync](offsetDateTime: jt.OffsetDateTime) : F[jt.LocalDate] =
    Sync[F].delay(offsetDateTime.toLocalDate)

  //def toLocalTime[F[_] : Sync](offsetDateTime: jt.OffsetDateTime)(implicit config: TimeConfiguration) : F[jt.LocalTime] =
    //Sync[F].delay(offsetDateTime.atZoneSimilarLocal(config.zoneId).toLocalTime)

  //TODO: see solution for cats.PartialOrder
  implicit val offsetDateTimeEq : cats.Eq[jt.OffsetDateTime] = cats.Eq.fromUniversalEquals[jt.OffsetDateTime]

  implicit val offsetDateTimeOrder : cats.Order[jt.OffsetDateTime] = cats.Order.fromComparable[jt.OffsetDateTime]

  implicit val offsetDateTimeOrdering : scala.Ordering[jt.OffsetDateTime] = scala.Ordering.by(_.toEpochSecond)

  implicit def showOffsetDateTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.OffsetDateTime] = Show.show(_.format(config.offsetDateTimeFormat))
}
