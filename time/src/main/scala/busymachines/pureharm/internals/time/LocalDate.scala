package busymachines.pureharm.internals.time
import java.{time => jt}

import busymachines.pureharm.effects._

object LocalDate {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.LocalDate] =
    Sync[F].delay(jt.LocalDate.now(config.zoneId))

  def parse[F[_]](s: String)(implicit config: TimeConfiguration, AT: ApplicativeAttempt[F]): F[jt.LocalDate] =
    AT.adaptError(
      AT.pure(jt.LocalDate.parse(s, config.localDateFormat))) { case e => TimeFormatAnomaly(s) }

  def toLocalDateTime[F[_] : Sync](localDate: jt.LocalDate): F[jt.LocalDateTime] =
    Sync[F].delay(localDate.atTime(jt.LocalTime.now()))

  implicit val localDateEq: cats.Eq[jt.LocalDate] = cats.Eq.fromUniversalEquals[jt.LocalDate]

  implicit val localDateOrdering: scala.Ordering[jt.LocalDate] = scala.Ordering.by(_.toEpochDay)

  implicit def showLocalDate(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalDate] = Show.show(_.format(config.localDateFormat))
}

