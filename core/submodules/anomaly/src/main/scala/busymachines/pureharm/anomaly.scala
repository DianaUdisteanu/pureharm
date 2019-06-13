/**
  * Copyright (c) 2017-2019 BusyMachines
  *
  * See company homepage at: https://www.busymachines.com/
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package busymachines.pureharm

import scala.collection.immutable.Seq

/**
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 11 Jun 2019
  *
  */

/**
  *
  * You should express your business logic expected ways
  * of failure using this trait.
  *
  */
abstract class Anomaly(
  override val message: String,
  val causedBy:         Option[Throwable] = None,
) extends Exception(message, causedBy.orNull) with AnomalyBase

/**
  * Some suggested naming conventions are put here so that they're easily accessible.
  * These can also be found in the scaladoc of [[busymachines.pureharm.MeaningfulAnomalies]]
  *
  * - [[busymachines.pureharm.MeaningfulAnomalies.NotFound]]
  *   - range: 000-099; e.g. pone_001, ptwo_076, pthree_099
  *
  * - [[busymachines.pureharm.MeaningfulAnomalies.Unauthorized]]
  *   - range: 100-199; e.g. pone_100, ptwo_176, pthree_199
  *
  * - [[busymachines.pureharm.MeaningfulAnomalies.Forbidden]]
  *   - range: 200-299; e.g. pone_200, ptwo_276, pthree_299
  *
  * - [[busymachines.pureharm.MeaningfulAnomalies.Denied]]
  *   - range: 300-399; e.g. pone_300, ptwo_376, pthree_399
  *
  * - [[busymachines.pureharm.MeaningfulAnomalies.InvalidInput]]
  *   - range: 400-499; e.g. pone_400, ptwo_476, pthree_499
  */
trait AnomalyID extends Product with Serializable with Equals {
  def name: String

  final override def canEqual(that: Any): Boolean = that.isInstanceOf[AnomalyID]

  final override def equals(obj: Any): Boolean = canEqual(obj) && this.hashCode() == obj.hashCode()

  final override def hashCode(): Int = name.hashCode * 13

  override def toString: String = name
}

object AnomalyID {
  def apply(id: String): AnomalyID = AnomalyIDUnderlying(id)
}

final private[pureharm] case class AnomalyIDUnderlying(override val name: String) extends AnomalyID

object Anomaly extends AnomalyConstructors[Anomaly] {
  private[pureharm] val Anomaly: String = "Anomaly"

  type Parameter = StringOrSeqString
  def Parameter(s:   String):      StringOrSeqString = StringWrapper(s)
  def Parameter(ses: Seq[String]): StringOrSeqString = SeqStringWrapper(ses)

  type Parameters = Map[String, Parameter]

  /**
    * the reason why this type signature does not return Parameters is a pragmatic one.
    * Intellij does not infer it correctly in the IDE and yields a false negative.
    *
    * As far as the client code is concerned this is the same, and scalac properly
    * compiles both versions, so we'll keep the one which causes the least misery.
    *
    * Once intellij fixes this (need to look for issue) we can have cleaner code here
    */
  def Parameters(ps: (String, Parameter)*): Map[String, StringOrSeqString] = Map.apply(ps: _*)

  object Parameters {
    def empty: Map[String, StringOrSeqString] = Map.empty[String, Parameter]
  }

  override def apply(id: AnomalyID): Anomaly =
    AnomalyImpl(id = id)

  override def apply(message: String): Anomaly =
    AnomalyImpl(message = message)

  override def apply(parameters: Parameters): Anomaly =
    AnomalyImpl(parameters = parameters)

  override def apply(id: AnomalyID, message: String): Anomaly =
    AnomalyImpl(id = id, message = message)

  override def apply(id: AnomalyID, parameters: Parameters): Anomaly =
    AnomalyImpl(id = id, parameters = parameters)

  override def apply(message: String, parameters: Parameters): Anomaly =
    AnomalyImpl(message = message, parameters = parameters)

  override def apply(id: AnomalyID, message: String, parameters: Parameters): Anomaly =
    AnomalyImpl(id = id, message = message, parameters = parameters)

  override def apply(a: AnomalyBase): Anomaly =
    AnomalyImpl(id = a.id, message = a.message, parameters = a.parameters)
}

final private[pureharm] case class AnomalyImpl(
  override val id:         AnomalyID          = DefaultAnomalyID,
  override val message:    String             = Anomaly.Anomaly,
  override val parameters: Anomaly.Parameters = Anomaly.Parameters.empty,
) extends Anomaly(message) {}

private[pureharm] case object DefaultAnomalyID extends AnomalyID {
  override val name: String = "DA_0"
}

trait AnomalyBase extends Product with Serializable {
  def id: AnomalyID

  def message: String

  def parameters: Anomaly.Parameters = Anomaly.Parameters.empty
}

/**
  * This is a hack until dotty (scala 3.0) comes along with union types.
  * Until then, boiler plate freedom is given by the implicit
  * conversions found in the package object
  */
sealed trait StringOrSeqString extends Product with Serializable

final case class StringWrapper private (s: String) extends StringOrSeqString

final case class SeqStringWrapper private (ses: Seq[String]) extends StringOrSeqString