package bootstrap.liftweb

import net.liftweb.common._
import net.liftweb.util._
import net.liftweb.http._
import net.liftweb.sitemap.Loc._
import Helpers._
import net.liftweb.sitemap._

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
  def boot {
    // if content type is "application/xhtml+xml; charset=utf-8"   (lift's default)
    // then a call to: google.load(..)
    // will throw the error: Uncaught TypeError: Object #<a Document> has no method 'write'
    // from the jsapi.js source file
    LiftRules.useXhtmlMimeType = false

    LiftRules.passNotFoundToChain = true

    //default is 1.3, we want 1.4
    LiftRules.jsArtifacts = js.jquery.JQuery14Artifacts

    //output html5
//    LiftRules.htmlProperties.default.set((r: Req) =>new Html5Properties(r.userAgent))

    // where to search snippet
    LiftRules.addToPackages("spike.lift.cssbindtests")

    // Build SiteMap
    def siteMap = SiteMap(
      Menu(S ? "Home") / "index",
      Menu(S ? "DataView") / "dataView" / **
    )

    LiftRules.setSiteMap(siteMap)
  }
}

