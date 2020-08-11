package busymachines.pureharm.internals.time
import java.{time => jt}
import busymachines.pureharm.effects._

object LocalDate {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.LocalDate] =
    Sync[F].delay(jt.LocalDate.now(config.zoneId))

  //TODO: adapt error e.g TimeFormatException in TimeFormatAnomaly
  def parse[F[_] : ApplicativeAttempt](s: String)(implicit config: TimeConfiguration): F[jt.LocalDate] =
    ApplicativeAttempt[F].catchNonFatal(jt.LocalDate.parse(s, config.localDateFormat))

  //TODO: see solution for cats.PartialOrder
  implicit val localDateEq : cats.Eq[jt.LocalDate] = cats.Eq.fromUniversalEquals[jt.LocalDate]

  implicit val localDateOrdering : scala.Ordering[jt.LocalDate] = scala.Ordering.by(_.toEpochDay)

  implicit def showLocalDate(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalDate] = Show.show(_.format(config.localDateFormat))
}

