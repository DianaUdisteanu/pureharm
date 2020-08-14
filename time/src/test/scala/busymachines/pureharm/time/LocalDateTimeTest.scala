package busymachines.pureharm.time

import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.testkit._
import busymachines.pureharm.time.implicits._

import scala.concurrent.duration.Duration


class LocalDateTimeTest extends PureharmTest {

  test("LocalDateTime - show") {
    val value = "2020-08-10T12:11:30"
    for {
      ldt <- LocalDateTime.parse[IO](value)
      seen = ldt.show
    } yield assert(value == seen)
  }

  test("LocalDateTime - show now"){
    for {
      now <- LocalDateTime.now[IO]
      value = now.show
      ldt <- LocalDateTime.parse[IO](value)
      seen = ldt.show
    } yield assert(value == seen)
  }

  test("LocalDateTime - toOffsetDateTime"){
    for{
       now <- LocalDateTime.now[IO]
       odt <- LocalDateTime.toOffsetDateTime[IO](now)
       ldt <- OffsetDateTime.toLocalDateTime[IO](odt)
       value = now.show
       seen = ldt.show
    }yield assert(value == seen)
  }

  test("LocalDateTime - toLocalTime"){
    for{
       now <- LocalDateTime.now[IO]
       lt <- LocalDateTime.toLocalTime[IO](now)
       ldt <- LocalTime.toLocalDateTime[IO](lt)
       value = now.show
       seen = ldt.show
    }yield assert(value == seen)
  }

  test("LocalDateTime - add finite duration") {
    for {
      now <- LocalDateTime.now[IO]
      finite <- LocalDateTime.addFiniteDuration[IO](Duration("3 hours"), now)
      value = now.show
      seen = finite.show
    } yield assert(value < seen)
  }
}
