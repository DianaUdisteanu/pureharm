package busymachines.pureharm.time

import cats.Show

trait PureharmTimeImplicits {
  implicit val localTimeShow: Show[LocalTime] = busymachines.pureharm.internals.time.LocalTime.showLocalTime
}
