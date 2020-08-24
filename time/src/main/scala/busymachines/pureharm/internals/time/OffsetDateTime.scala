package busymachines.pureharm.internals.time
import busymachines.pureharm.effects._
import java.{time => jt}
import scala.concurrent.duration._

object OffsetDateTime {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.OffsetDateTime] =
    Sync[F].delay(jt.OffsetDateTime.now(config.zoneId))

  def parse[F[_]](s: String)(implicit config: TimeConfiguration, AT: ApplicativeAttempt[F]): F[jt.OffsetDateTime] =
    AT.adaptError(
      AT.catchNonFatal(jt.OffsetDateTime.parse(s, config.offsetDateTimeFormat))) { case e => TimeFormatAnomaly(s) }

  def addFiniteDuration[F[_] : Sync](duration: Duration, offsetDateTime: jt.OffsetDateTime) : F[jt.OffsetDateTime] =
    Sync[F].delay(offsetDateTime.plusNanos(duration.toNanos))

  def toLocalDateTime[F[_] : Sync](offsetDateTime: jt.OffsetDateTime): F[jt.LocalDateTime] =
    Sync[F].delay(offsetDateTime.toLocalDateTime)

  def toLocalDate[F[_] : Sync](offsetDateTime: jt.OffsetDateTime) : F[jt.LocalDate] =
    Sync[F].delay(offsetDateTime.toLocalDate)

  def toLocalTime[F[_] : Sync](offsetDateTime: jt.OffsetDateTime) : F[jt.LocalTime] =
    Sync[F].delay(offsetDateTime.toLocalTime)

  implicit val offsetDateTimeEq : cats.Eq[jt.OffsetDateTime] = cats.Eq.fromUniversalEquals[jt.OffsetDateTime]

  implicit val offsetDateTimeOrder : cats.Order[jt.OffsetDateTime] = cats.Order.fromComparable[jt.OffsetDateTime]

  implicit val offsetDateTimeOrdering : scala.Ordering[jt.OffsetDateTime] = scala.Ordering.by(_.toEpochSecond)

  implicit def showOffsetDateTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.OffsetDateTime] = Show.show(_.format(config.offsetDateTimeFormat))
}
