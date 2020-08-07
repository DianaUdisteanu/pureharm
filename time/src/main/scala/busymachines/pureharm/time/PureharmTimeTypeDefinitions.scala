package busymachines.pureharm.time

import java.{time => jt}

trait PureharmTimeTypeDefinitions {
  type LocalTime = jt.LocalTime
  val LocalTime = busymachines.pureharm.internals.time.LocalTime

}
