package com.billingportal.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * TEMPORARY STUB - this is the "mock login trick" from the project plan
 * (section 4.4). Member A owns the real SessionUtil / AuthFilter that
 * checks a genuine HttpSession created at login.
 *
 * Until Member A's auth is merged into main, this stub just hardcodes
 * clientId = 1 (Acme Corp from the seed data) so you can build and test
 * the subscription/upgrade flow independently.
 *
 * WHEN MEMBER A MERGES: delete this file and change the import in your
 * servlets from com.billingportal.util.SessionUtil to whatever package
 * Member A's real SessionUtil lives in. The method signature
 * getCurrentClientId(request) should stay the same, so nothing else
 * in this module needs to change.
 */
public class SessionUtil {

    public static Integer getCurrentClientId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object clientId = session.getAttribute("clientId");

        if (clientId == null) {
            // TEMPORARY - remove once real login is merged (see plan section 4.4)
            session.setAttribute("clientId", 1);
            return 1;
        }
        return (Integer) clientId;
    }
}
