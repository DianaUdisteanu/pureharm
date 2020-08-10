package busymachines.pureharm.timeimport
import busymachines.pureharm.testkit._
import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import busymachines.pureharm.internals.time.{LocalDateTime, LocalTime}
import busymachines.pureharm.time.implicits._


class LocalDateTimeTest extends  PureharmTest {

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

  /*test("LocalDateTime - to LocalTime"){
    for {
      now <- LocalDateTime.now[IO]
      value = now.show
      ldt = LocalDateTime.toLocaleTime(now)
      seen = ldt.show
      lt <- LocalTime.now[IO]
      see = lt.show
    } yield assert(see == seen)
  }*/
}
