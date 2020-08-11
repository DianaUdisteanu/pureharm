package busymachines.pureharm.internals.time
import busymachines.pureharm.effects._
import java.{time => jt}

object OffsetDateTime {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.OffsetDateTime] =
    Sync[F].delay(jt.OffsetDateTime.now(config.zoneId))

  //TODO: adapt error e.g TimeFormatException in TimeFormatAnomaly
  def parse[F[_] : ApplicativeAttempt](s: String)(implicit config: TimeConfiguration): F[jt.OffsetDateTime] =
    ApplicativeAttempt[F].catchNonFatal(jt.OffsetDateTime.parse(s, config.offsetDateTimeFormat))

  implicit val offsetDateTimeEq : cats.Eq[jt.OffsetDateTime] = cats.Eq.fromUniversalEquals[jt.OffsetDateTime]

  implicit def showOffsetDateTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.OffsetDateTime] = Show.show(_.format(config.offsetDateTimeFormat))
}
