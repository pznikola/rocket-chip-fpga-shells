package sifive.fpgashells.shell

import chisel3._
import org.chipsalliance.cde.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.tilelink._
import sifive.fpgashells.clocks._


case class DDRShellInput()
case class DDRDesignInput(
  baseAddress: BigInt,
  wrangler: ClockAdapterNode,
  corePLL: PLLNode,
  vc7074gbdimm: Boolean = false)(
  implicit val p: Parameters)
case class DDROverlayOutput(ddr: TLInwardNode)
trait DDRShellPlacer[Shell] extends ShellPlacer[DDRDesignInput, DDRShellInput, DDROverlayOutput]

case object DDROverlayKey extends Field[Seq[DesignPlacer[DDRDesignInput, DDRShellInput, DDROverlayOutput]]](Nil)

case object DDRKey extends Field[Option[Seq[DesignPlacer[DDRDesignInput, DDRShellInput, DDROverlayOutput]]]](None)

abstract class DDRPlacedOverlay[IO <: Data](val name: String, val di: DDRDesignInput, val si: DDRShellInput)
  extends IOPlacedOverlay[IO, DDRDesignInput, DDRShellInput, DDROverlayOutput]
{
  implicit val p = di.p
}

  /* Mixin to add AXI4SDFFFT to rocket config */
class WithDDR extends Config((site, here, up) => {case DDRKey => Some(DDROverlayKey)})
/*
   Copyright 2016 SiFive, Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
