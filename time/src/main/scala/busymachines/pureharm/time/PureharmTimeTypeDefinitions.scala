package busymachines.pureharm.time

import java.{time => jt}

trait PureharmTimeTypeDefinitions {
  type LocalTime = jt.LocalTime
  val LocalTime = busymachines.pureharm.internals.time.LocalTime

  type LocalDate = jt.LocalDate
  val LocalDate = busymachines.pureharm.internals.time.LocalDate
}
