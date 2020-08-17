package busymachines.pureharm.internals.time

import java.{time => jt}

import busymachines.pureharm.effects._

import scala.concurrent.duration.Duration

object LocalTime {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.LocalTime] =
    Sync[F].delay(jt.LocalTime.now(config.zoneId))

  def parse[F[_]](s: String)(implicit config: TimeConfiguration, AT: ApplicativeAttempt[F]): F[jt.LocalTime] =
    AT.adaptError(
      AT.pure(jt.LocalTime.parse(s, config.localTimeFormat))) { case e => TimeFormatAnomaly(s) }

  def addFiniteDuration[F[_] : Sync](duration: Duration, localTime: jt.LocalTime) : F[jt.LocalTime] =
    Sync[F].delay(localTime.plusNanos(duration.toNanos))

  def toOffsetDateTime[F[_] :Sync](localTime: jt.LocalTime)(implicit config: TimeConfiguration) : F[jt.OffsetDateTime] =
    Sync[F].delay(localTime.atDate(jt.LocalDate.now()).atZone(config.zoneId).toOffsetDateTime)

  def toLocalDateTime[F[_] : Sync](localTime: jt.LocalTime) : F[jt.LocalDateTime] =
    Sync[F].delay(localTime.atDate(jt.LocalDate.now()))

  implicit val localTimeEq : cats.Eq[jt.LocalTime] = cats.Eq.fromUniversalEquals[jt.LocalTime]

  implicit val localTimeOrder : cats.Order[jt.LocalTime] = cats.Order.fromComparable[jt.LocalTime]

  implicit val localTimeOrdering : scala.Ordering[jt.LocalTime] = scala.Ordering.by(_.toSecondOfDay)

  implicit def showLocalTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalTime] = Show.show(_.format(config.localTimeFormat))
}
