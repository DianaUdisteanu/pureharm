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

import busymachines.pureharm.Anomaly.Parameters

/**
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 11 Jun 2019
  *
  */
abstract class ForbiddenAnomaly(
  override val message:  String,
  override val causedBy: Option[Throwable] = None,
) extends Anomaly(message, causedBy) with MeaningfulAnomalies.Forbidden with Product with Serializable {
  override def id: AnomalyID = ForbiddenAnomalyID
}

object ForbiddenAnomaly
    extends ForbiddenAnomaly(MeaningfulAnomalies.ForbiddenMsg, None) with SingletonAnomalyProduct
    with AnomalyConstructors[ForbiddenAnomaly] {

  override def apply(id: AnomalyID): ForbiddenAnomaly =
    ForbiddenFailureImpl(id = id)

  override def apply(message: String): ForbiddenAnomaly =
    ForbiddenFailureImpl(message = message)

  override def apply(parameters: Parameters): ForbiddenAnomaly =
    ForbiddenFailureImpl(parameters = parameters)

  override def apply(id: AnomalyID, message: String): ForbiddenAnomaly =
    ForbiddenFailureImpl(id = id, message = message)

  override def apply(id: AnomalyID, parameters: Parameters): ForbiddenAnomaly =
    ForbiddenFailureImpl(id = id, parameters = parameters)

  override def apply(message: String, parameters: Parameters): ForbiddenAnomaly =
    ForbiddenFailureImpl(message = message, parameters = parameters)

  override def apply(id: AnomalyID, message: String, parameters: Parameters): ForbiddenAnomaly =
    ForbiddenFailureImpl(id = id, message = message, parameters = parameters)

  override def apply(a: AnomalyBase): ForbiddenAnomaly =
    ForbiddenFailureImpl(id = a.id, message = a.message, parameters = a.parameters)
}

final private[pureharm] case class ForbiddenFailureImpl(
  override val id:         AnomalyID         = ForbiddenAnomalyID,
  override val message:    String            = MeaningfulAnomalies.ForbiddenMsg,
  override val parameters: Parameters        = Parameters.empty,
  override val causedBy:   Option[Throwable] = None,
) extends ForbiddenAnomaly(message, causedBy) with Product with Serializable
