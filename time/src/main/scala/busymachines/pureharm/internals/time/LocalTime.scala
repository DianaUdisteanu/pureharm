package busymachines.pureharm.internals.time

import java.{time => jt}

import busymachines.pureharm.effects._

object LocalTime {

  def now[F[_]: Sync]: F[jt.LocalTime] =
    Sync[F].delay(jt.LocalTime.now())

  implicit val showLocalTime: Show[busymachines.pureharm.time.LocalTime] = Show.fromToString
}
