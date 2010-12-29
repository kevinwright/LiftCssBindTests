package spike.lift.cssbindtests
package snippet



import org.specs.SpecificationWithJUnit

import xml.{Elem, Node, NodeSeq, Text}

import net.liftweb.http._
import S._
import net.liftweb.util._
import Helpers._
import BindPlus._

import pimps.Identity._

class ReportsSpec extends SpecificationWithJUnit {

  "binding trees" should {
    "conserve existing nesting" in {
      case class Inner(name:String)
      case class Outer(name:String, inners:Seq[Inner])

      val testData = Seq(
        Outer("A", Seq(
          Inner("1"),
          Inner("2"),
          Inner("3")
        )),
        Outer("B", Seq(
          Inner("4"),
          Inner("5"),
          Inner("6")
        ))
      )

      val nodeseq =
        <div id="root">
          <div class="repeatContentsPerOuter">
            <h3><a href="#" class="outerName"></a></h3>
            <ul>              
               <li class="oncePerInner"><a href="" class="innerName"></a></li>
            </ul>
          </div>
        </div>

      val contents = (ns:NodeSeq) => ns match {
        case e:Elem => e.child
        case _ => ns
      }

      def doInners(data:Seq[Inner]) =
        (ns:NodeSeq) => data.flatMap{
          ns |> ".innerName *" #> _.name
        }

      def doOuters(data:Seq[Outer]) = {
        def eachOuter(o:Outer, ns:NodeSeq) = ns |>
          ".outerName *" #> o.name |>
          ".oncePerInner" #> doInners(o.inners)
        (ns:NodeSeq) => data.flatMap{eachOuter(_,ns)} flatMap contents
      }

      
      val NoString: Option[String] = None

      val bind3 =
        ".repeatContentsPerOuter" #> doOuters(testData)  &
        ".repeatContentsPerOuter [class]" #> NoString &
        ".oncePerInner [class]" #> NoString &
        ".outerName [class]" #> NoString &
        ".innerName [class]" #> NoString

      println(bind3 apply nodeseq)
    }
  }

}
