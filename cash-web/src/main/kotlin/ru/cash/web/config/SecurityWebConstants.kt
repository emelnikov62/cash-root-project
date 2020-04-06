package ru.cash.web.config

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings

/**
 * Web security constants.
 * @author Sergey Valiev
 */
@SuppressFBWarnings("EI")
object SecurityWebConstants {
    val ZK_RESOURCES = arrayOf("/zkau/web/**/js/**", "/zkau/web/**/css/**", "/zkau/web/**/img/**")

    // allow desktop cleanup after logout or when reloading login page
    const val REMOVE_DESKTOP_REGEX = "/zkau\\?dtid=.*&cmd_0=rmDesktop&.*"
    const val ZUL_FILES = "/zkau/web/**/*.zul"

    const val SECURED_AREA = "/layout\\.zul.*"

    const val ROLE_PREFIX = "ROLE_"
    const val ROLE = "CASH"
}
