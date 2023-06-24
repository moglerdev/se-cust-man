package de.htwg.scm;
package tui.view

import com.google.inject.Guice

trait  ITuiView {
    val injector = Guice.createInjector(new ScmModule)
}

abstract TuiView {

    val injector = Guice.createInjector(new ScmModule)

    def prompt(): {

    }
}
