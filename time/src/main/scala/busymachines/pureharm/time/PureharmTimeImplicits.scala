package busymachines.pureharm.time

import busymachines.pureharm.effects.Show
import busymachines.pureharm.internals.time.TimeConfiguration

trait PureharmTimeImplicits {
  implicit def defaultPureharmTimeConfiguration : TimeConfiguration = TimeConfiguration.utcTimeConfiguration

  implicit def showLocalTime(implicit config :TimeConfiguration): Show[busymachines.pureharm.time.LocalTime] = busymachines.pureharm.internals.time.LocalTime.showLocalTime(config)

  implicit def showLocalDate(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalDate] = busymachines.pureharm.internals.time.LocalDate.showLocalDate(config)

  implicit def showLocalDateTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.LocalDateTime] = busymachines.pureharm.internals.time.LocalDateTime.showLocalDateTime(config)

  implicit def showOffsetDateTime(implicit config: TimeConfiguration): Show[busymachines.pureharm.time.OffsetDateTime] = busymachines.pureharm.internals.time.OffsetDateTime.showOffsetDateTime(config)
}
