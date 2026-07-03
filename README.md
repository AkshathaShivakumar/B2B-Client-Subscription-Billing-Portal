# Member B — Subscription & Checkout

This is your vertical slice of the B2B Billing Portal project: viewing the
current plan and the 3-step upgrade flow, per the project plan.

## What's in here

```
src/main/java/com/billingportal/
  dao/DBUtil.java              shared JDBC connection helper
  dao/SubscriptionDAO.java     all SQL for the subscriptions table
  model/Subscription.java      maps to one row of `subscriptions`
  model/TierCatalog.java       plan names/prices/quotas (no tiers table in schema, so it lives in code)
  model/PendingUpgrade.java    session object for the in-progress upgrade
  servlet/ViewSubscriptionServlet.java   GET /subscription
  servlet/UpgradeStep1Servlet.java       GET+POST /upgrade/step1
  servlet/UpgradeStep2Servlet.java       GET /upgrade/step2
  servlet/ConfirmUpgradeServlet.java     POST /upgrade/confirm
  util/SessionUtil.java        TEMPORARY mock-login stub (see below)

src/main/webapp/
  index.jsp
  viewSubscription.jsp
  upgradeStep1.jsp
  upgradeStep2.jsp
  upgradeConfirmed.jsp
  WEB-INF/web.xml
```

## The flow

1. **`GET /subscription`** → `ViewSubscriptionServlet` loads the client's
   active row from `subscriptions` and shows tier, status, renewal date.
2. **`GET /upgrade/step1`** → shows all tiers from `TierCatalog` with the
   current one marked. **`POST /upgrade/step1`** takes the chosen tier,
   builds a `PendingUpgrade` object and stores it in `HttpSession` —
   nothing touches the database yet.
3. **`GET /upgrade/step2`** → reads `PendingUpgrade` back out of the
   session and shows a before/after price comparison for confirmation.
   If someone hits this URL directly with nothing in session, it bounces
   them back to step 1.
4. **`POST /upgrade/confirm`** → `ConfirmUpgradeServlet` calls
   `SubscriptionDAO.upgrade()`, which in one transaction marks the old
   row `CANCELLED` and inserts a new `ACTIVE` row for the new tier. This
   is the "don't leave it only in session" write the project plan calls
   out for you. The `PendingUpgrade` is then cleared from session.

## Before you run it

1. Import `schema.sql` into your local MySQL Workbench (you should have
   already done this with the whole team on Day 1).
2. Open `dao/DBUtil.java` and set `USER` / `PASSWORD` to your local MySQL
   credentials.
3. This module currently stands alone with its own `pom.xml` so you can
   build and test it independently. When the team merges everyone's code
   into `main` on integration day, copy these `dao`, `model`, `servlet`,
   and `.jsp` files into the shared Maven project instead of keeping two
   separate `pom.xml`s.

## Build & deploy

```bash
mvn clean package
```

This produces `target/subscription-checkout.war`. Drop that into Tomcat's
`webapps/` folder (or deploy it from your IDE's Tomcat run configuration),
then visit:

```
http://localhost:8080/subscription-checkout/
```

## About `SessionUtil` (read this)

The project plan's "mock login trick" (section 4.4) is baked into
`util/SessionUtil.java` so you're not blocked waiting on Member A. It
hardcodes `clientId = 1`, which is **Acme Corp** from the seed data — so
your dashboard will show Acme's `PRO` subscription until you upgrade it.

**When Member A merges their real `AuthFilter` / `SessionUtil` into
`main`:** delete this file and point your servlets' imports at Member A's
real one instead. The method signature
`SessionUtil.getCurrentClientId(request)` is kept identical on purpose so
none of your servlet code needs to change — just the import line.

## Testing the flow manually

With the mock login active:

1. `/subscription` should show Acme Corp on `PRO`, renewing 2026-08-01
   (matches the seed data).
2. `/upgrade/step1` should show BASIC / PRO / ENTERPRISE with PRO marked
   as current and disabled.
3. Pick ENTERPRISE → step 2 should show PRO → ENTERPRISE, $99 → $299,
   difference +$200.00.
4. Confirm → should redirect to a success page, and `/subscription`
   should now show ENTERPRISE with a period end one month out.
5. In MySQL Workbench, `SELECT * FROM subscriptions WHERE client_id = 1`
   should show the old PRO row as `CANCELLED` and a new ENTERPRISE row as
   `ACTIVE` — confirming the write actually happened and isn't just held
   in session.

## Known gaps / things to flag at integration sync

- `TierCatalog` prices are placeholders (`BASIC` $29, `PRO` $99,
  `ENTERPRISE` $299) matching the seed data's `PRO`/`BASIC` invoice
  amounts — check with the team if these should change.
- No proration logic — upgrading mid-cycle just starts a fresh
  30-day period at the new price. Flag this as a simplification if
  asked in the demo.
- `DBUtil` here is a placeholder; if someone else (likely Member A)
  already has a shared `DBUtil`, delete this copy and use theirs so
  there's only one connection helper in the final merged project.
