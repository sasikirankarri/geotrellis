/*
 * Copyright (c) 2014 Azavea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package geotrellis.vector.spec

import geotrellis.vector._

import com.vividsolutions.jts.{geom=>jts}

import org.scalatest._

class MultiLineSpec extends FunSpec with Matchers {
  describe("MultiLine.union") {
    it("should merge a multi line string that has overlapping segments") {
      val ml =
        MultiLine(
          Line( (0.0, 0.0), (1.0, 1.0), (1.0, 2.0), (2.0, 3.0) ),
          Line( (0.0, 3.0), (1.0, 1.0), (1.0, 2.0), (0.0, 3.0) )
        )

      val expected = 
        MultiLine(
          Line( (0.0, 0.0), (1.0, 1.0) ),
          Line( (0.0, 3.0), (1.0, 1.0) ),
          Line( (0.0, 3.0), (1.0, 2.0) ),
          Line( (1.0, 1.0), (1.0, 2.0) ),
          Line( (1.0, 2.0), (2.0, 3.0) )
        )

      val actual = ml.union match {
        case MultiLineResult(ml) => ml
        case LineResult(l) => MultiLine(l)
        case NoResult => MultiLine()
      }

      actual.normalized should be (expected)
    }
  }
}