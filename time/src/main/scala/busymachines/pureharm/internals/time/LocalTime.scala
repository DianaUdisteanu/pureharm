package busymachines.pureharm.internals.time

import java.{time => jt}

import busymachines.pureharm.effects._

object LocalTime {

  def now[F[_] : Sync](implicit config: TimeConfiguration): F[jt.LocalTime] =
    Sync[F].delay(jt.LocalTime.now(config.zoneId))

  //TODO: adapt error e.g TimeFormatException in TimeFormatAnomaly
  def parse[F[_] : ApplicativeAttempt](s: String)(implicit config: TimeConfiguration): F[jt.LocalTime] =
    ApplicativeAttempt[F].catchNonFatal(jt.LocalTime.parse(s, config.localTimeFormat))

  implicit def showLocalTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalTime] = Show.show(_.format(config.localTimeFormat))
}
