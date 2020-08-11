package busymachines.pureharm.time

import busymachines.pureharm.testkit.PureharmTest
import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.time.implicits._

class OffsetDateTimeTest extends PureharmTest{

  test("OffsetDateTime - show") {
    val value = "2020-08-11T10:15:00Z"
    for {
      odt <- OffsetDateTime.parse[IO](value)
      seen = odt.show
    } yield assert(value == seen)
  }

  test("OffsetDateTime - show now"){
    for {
      now <- OffsetDateTime.now[IO]
      value = now.show
      odt <- OffsetDateTime.parse[IO](value)
      seen = odt.show
    } yield assert(value == seen)
  }
}
