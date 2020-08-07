package busymachines.pureharm.time

import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.testkit._
import busymachines.pureharm.time.implicits._

class LocalTimeTest extends PureharmTest {

  test("LocalTime - show") {
    val value = "09:03:32.458458"
    for {
      lt <- LocalTime.parse[IO](value)
      seen = lt.show
    } yield assert(value == seen)
  }

  test("LocalTime - show now"){
    for {
     now <- LocalTime.now[IO]
     value = now.show
      lt <- LocalTime.parse[IO](value)
      seen = lt.show
    } yield assert(value == seen)
  }
}
