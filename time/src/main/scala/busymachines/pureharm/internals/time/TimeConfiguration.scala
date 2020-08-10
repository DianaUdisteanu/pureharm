package busymachines.pureharm.internals.time

import java.{time => jt}

import scala.annotation.implicitNotFound
@implicitNotFound (
"""
Working with pureharm time requires time configuration.

A default value is available with the import busymachines.pureharm.time.implicit.
Or if you mixt in the pureharm time traits it should be available in your project's time module.
""")
final case class TimeConfiguration(
  zoneId:          jt.ZoneId,
  localTimeFormat: jt.format.DateTimeFormatter,
  localDateFormat: jt.format.DateTimeFormatter
)

object TimeConfiguration {

  val utcTimeConfiguration: TimeConfiguration =
    TimeConfiguration(
      zoneId          = Timezones.UTC,
      localTimeFormat = jt.format.DateTimeFormatter.ISO_LOCAL_TIME,
      localDateFormat = jt.format.DateTimeFormatter.ISO_LOCAL_DATE,
    )

}
