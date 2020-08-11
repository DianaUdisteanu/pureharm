package busymachines.pureharm.internals.time
import busymachines.pureharm.effects._
import java.{time => jt}

object LocalDateTime {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.LocalDateTime] =
    Sync[F].delay(jt.LocalDateTime.now(config.zoneId))

  //TODO: adapt error e.g TimeFormatException in TimeFormatAnomaly
  def parse[F[_] : ApplicativeAttempt](s: String)(implicit config: TimeConfiguration): F[jt.LocalDateTime] =
    ApplicativeAttempt[F].catchNonFatal(jt.LocalDateTime.parse(s, config.localDateTimeFormat))

  //TODO: see solution for cats.PartialOrder
  implicit val localDateTimeEq : cats.Eq[jt.LocalDateTime] = cats.Eq.fromUniversalEquals[jt.LocalDateTime]

  implicit val localDateTimeOrdering : scala.Ordering[jt.LocalDateTime] = scala.Ordering.by(_.toLocalDate)

  implicit def showLocalDateTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalDateTime] = Show.show(_.format(config.localDateTimeFormat))
}

