package busymachines.pureharm.time

import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.testkit._
import busymachines.pureharm.time.implicits._

class LocalTimeTest extends PureharmTest {

  test("LocalTime - show") {
    for {
      lt <- LocalTime.now[IO]
      _  <- testLogger.info(lt.show)
    } yield succeed
  }
}
