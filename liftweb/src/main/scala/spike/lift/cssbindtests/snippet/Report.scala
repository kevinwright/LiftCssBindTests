package spike.lift.cssbindtests
package snippet


import net.liftweb.http._
import S._
import net.liftweb.util._
import Helpers._
import BindPlus._

import xml.{NodeSeq, Text}

class Report {
  def howdy = ".time" #> Helpers.formattedTimeNow

  def summary = {
    val optId = (S param "source").toOption
    val id = optId getOrElse ""
    val optName = Reports(id) map (_.name)
    
    val name = optName getOrElse "[Unknown Report ID " + id + "]"
    val source = "/reportData?id=" + id
    val csvSource = source + "&format=csv"
    val tsvSource = source + "&format=tsv"

    "#reportname"           #> name       &
    "#reportid"             #> id         &
    "#reportsource"         #> source     &
    "#jsonDataLink [href]"  #> source     &
    "#csvLink [href]"       #> csvSource  &
    "#tsvLink [href]"       #> tsvSource
  }
}
