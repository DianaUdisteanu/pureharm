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
package busymachines.pureharm.internals.rest

/**
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 16 Jul 2020
  */
object TapirOps {
  import sttp.tapir._

  final class AuthOps(val o: TapirAuth.type) extends AnyVal {

    def xCustomAuthHeader[T: Codec[String, *, CodecFormat.TextPlain]](
      headerName: String
    ): EndpointInput.Auth.Http[T] = {
      val codec     = implicitly[Codec[List[String], T, CodecFormat.TextPlain]]
      val authCodec = Codec.list(Codec.string).map(codec).schema(codec.schema)
      EndpointInput.Auth.Http(
        "Custom",
        header[T](headerName)(authCodec).description(
          s"Authentication done with token required in header: $headerName"
        ),
      )
    }

  }

}
