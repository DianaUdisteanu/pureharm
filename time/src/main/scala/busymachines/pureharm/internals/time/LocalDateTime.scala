package busymachines.pureharm.internals.time
import busymachines.pureharm.effects._
import java.{time => jt}

object LocalDateTime {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.LocalDateTime] =
    Sync[F].delay(jt.LocalDateTime.now(config.zoneId))

  def parse[F[_] : ApplicativeAttempt](s: String)(implicit config: TimeConfiguration): F[jt.LocalDateTime] =
    ApplicativeAttempt[F].catchNonFatal(jt.LocalDateTime.parse(s, config.localDateTimeFormat))

  def toOffsetDateTime[F[_] : Sync](localDateTime: jt.LocalDateTime)(implicit config: TimeConfiguration) : F[jt.OffsetDateTime] =
    Sync[F].delay(localDateTime.atZone(config.zoneId).toOffsetDateTime)

  def toLocalTime[F[_] : Sync](localDateTime: jt.LocalDateTime) : F[jt.LocalTime] =
    Sync[F].delay(localDateTime.toLocalTime)

  implicit val localDateTimeEq : cats.Eq[jt.LocalDateTime] = cats.Eq.fromUniversalEquals[jt.LocalDateTime]

  implicit val localDateTimeOrdering : scala.Ordering[jt.LocalDateTime] = scala.Ordering.by(_.toLocalDate)

  implicit def showLocalDateTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalDateTime] = Show.show(_.format(config.localDateTimeFormat))
}

