package spike.lift.cssbindtests
package snippet


import net.liftweb.http._
import S._
import net.liftweb.util._
import Helpers._
import BindPlus._
import xml.{Elem, Node, NodeSeq, Text}

import util.Identity._
import util.RepeatBindings._

object Reports {
  case class Report(id:String, name:String, visualisations:Seq[String] = Seq.empty)
  case class Category(name:String, reports:Seq[Report] = Seq.empty)

  val byCategory = Seq(
    Category(
      name = "Category One",
      reports = Seq(
        Report("ID1", "Report 1"),
        Report("ID2", "Report 2"),
        Report("ID3", "Report 3")
      )
    ),
    Category(
      name ="Category Two",
      reports = Seq(
        Report("ID4", "Report 4"),
        Report("ID5", "Report 5")
      )
    ),
    Category(name = "Category Three"),
    Category(
      name = "Category Four",
      reports = Seq(
        Report("ID6", "Report 6"),
        Report("ID7", "Report 7"),
        Report("ID8", "Report 8")
      )
    )
  )

  def all = byCategory flatMap {_.reports}
  def apply(id:String) = all find (_.id == id)
}

class Reports {
  import Reports._

  private[this] val contents = (ns:NodeSeq) => ns match {
    case e:Elem => e.child
    case _ => ns
  }

  private[this] val NoString: Option[String] = None

  private[this] def doReports(data:Seq[Report]) =
    (ns:NodeSeq) => data.flatMap { report => ns |>
      (".reportLink *" #> report.name &
      ".reportLink [href]" #> ("/reports/report?id=" + report.id))
    }

  private[this] def doCategories(data:Seq[Category]) = {
    def innerDo(ns:NodeSeq) = (catg:Category) =>
      ns |>
      ".categoryHeader *" #> catg.name |>
      ".eachReport" #> doReports(catg.reports)
    (ns:NodeSeq) => data.flatMap{innerDo(ns)} flatMap contents
  }

  
  def list =
    ".eachCategory" #> doCategories(Reports.byCategory) &
    ".categoryHeader [class]" #> NoString &
    ".eachCategory [class]" #> NoString &
    ".eachReport [class]" #> NoString &
    ".reportLink [class]" #> NoString

  def *(f: NsFunc): NsFunc = {
    case e: Elem => f(e.child)
    case x => f(x)
  }

  val categories = Reports.byCategory

  def test1 = ".eachCategory" #> repeat {
    categories map (".categoryHeader *" #> _.name)
  }

  def test2 = ".eachCategory *" #> *( repeat {
    categories map (".categoryHeader *" #> _.name)
  })

  def test3 = ".eachCategory" #> *( repeat {
    categories map (".categoryHeader *" #> _.name)
  })

  def test4 = ".eachCategory" #> repeatContents {
    categories map (".categoryHeader *" #> _.name)
  }


  def test5 = ".eachCategory" #> repeatContents {
    categories map { catg =>
      ".categoryHeader *" #> catg.name andThen
      ".eachReport" #> repeat {
        catg.reports map { report =>
          ".reportLink *" #> report.name &
          ".reportLink [href]" #> ("/reports/report?id=" + report.id)
        }
      }
    }
  }

  def test6 = ".eachCategory" #> repeatContentsFor(categories) {
    catg =>
    ".categoryHeader *" #> catg.name andThen
    ".eachReport" #> repeatFor(catg.reports) {
      report =>
      ".reportLink *" #> report.name &
      ".reportLink [href]" #> ("/reports/report?id=" + report.id)
    }
  }


}
