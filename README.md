# POC: Spring UI Testing

It demonstrates how to run UI tests against Web application using Selenium Java.

The goal is to develop a minimal search engine web application that finds links based on what users type on a search
field. After visiting the web application the server should render a web page with a search field and a submit button
that the user can type some text and click the button to search. The web application will query a database to find links
which title or description matches the user query and present them in a list below the search form.

We want to automate the steps to verify that this process works, as well as some edge cases and business logic. The
infrastructure to provision the web application and the web browser are done automatically using TestContainers and
JUnit. We use Selenium to automate the steps of visiting the web application on the browser provisioned, find elements
on the page based on HTML elements. JUnit's assertions are used to make sure the page has the state we want in each
flow.

## How to run

| Description         | Command                         |
|:--------------------|:--------------------------------|
| Run tests           | `./gradlew test`                |
| Run application     | `./gradlew bootRun`             |
| Provision database¹ | `docker-compose up --detach`    |
| Destroy database¹   | `docker-compose down --volumes` |

> ¹Required for manual testing only, automated tests provision and destroy a database automatically. Must run
> inside `infrastructure` folder.

## Preview

Test report:

```text
$ ./gradlew test

> Task :test

ApplicationTest > Search without results display empty state PASSED
ApplicationTest > Search with at least one result display links found PASSED
ApplicationTest > First render display title and search form only PASSED
```

Logging output after running all tests:

```text
2022-07-10T16:51:13.864-03:00  INFO 72060 --- [    Test worker] com.example.ApplicationTest              : Starting ApplicationTest using Java 18.0.1.1 on pc with PID 72060
2022-07-10T16:51:13.865-03:00  INFO 72060 --- [    Test worker] com.example.ApplicationTest              : No active profile set, falling back to 1 default profile: "default"
2022-07-10T16:51:14.136-03:00  INFO 72060 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2022-07-10T16:51:14.157-03:00  INFO 72060 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 17 ms. Found 1 JPA repository interfaces.
2022-07-10T16:51:14.418-03:00  INFO 72060 --- [    Test worker] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 0 (http)
2022-07-10T16:51:14.424-03:00  INFO 72060 --- [    Test worker] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-07-10T16:51:14.424-03:00  INFO 72060 --- [    Test worker] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/10.0.21]
2022-07-10T16:51:14.487-03:00  INFO 72060 --- [    Test worker] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-07-10T16:51:14.488-03:00  INFO 72060 --- [    Test worker] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 611 ms
2022-07-10T16:51:14.561-03:00  INFO 72060 --- [    Test worker] o.f.c.internal.license.VersionPrinter    : Flyway Community Edition 8.5.11 by Redgate
2022-07-10T16:51:14.561-03:00  INFO 72060 --- [    Test worker] o.f.c.internal.license.VersionPrinter    : See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#8.5.11
2022-07-10T16:51:14.561-03:00  INFO 72060 --- [    Test worker] o.f.c.internal.license.VersionPrinter    : 
2022-07-10T16:51:14.565-03:00  INFO 72060 --- [    Test worker] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2022-07-10T16:51:14.652-03:00  INFO 72060 --- [    Test worker] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@378c48c1
2022-07-10T16:51:14.653-03:00  INFO 72060 --- [    Test worker] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2022-07-10T16:51:14.662-03:00  INFO 72060 --- [    Test worker] o.f.c.i.database.base.BaseDatabaseType   : Database: jdbc:postgresql://localhost:49350/test (PostgreSQL 14.4)
2022-07-10T16:51:14.677-03:00  INFO 72060 --- [    Test worker] o.f.core.internal.command.DbValidate     : Successfully validated 1 migration (execution time 00:00.006s)
2022-07-10T16:51:14.685-03:00  INFO 72060 --- [    Test worker] o.f.c.i.s.JdbcTableSchemaHistory         : Creating Schema History table "public"."flyway_schema_history" ...
2022-07-10T16:51:14.702-03:00  INFO 72060 --- [    Test worker] o.f.core.internal.command.DbMigrate      : Current version of schema "public": << Empty Schema >>
2022-07-10T16:51:14.706-03:00  INFO 72060 --- [    Test worker] o.f.core.internal.command.DbMigrate      : Migrating schema "public" to version "1 - create link table"
2022-07-10T16:51:14.716-03:00  INFO 72060 --- [    Test worker] o.f.core.internal.command.DbMigrate      : Successfully applied 1 migration to schema "public", now at version v1 (execution time 00:00.016s)
2022-07-10T16:51:14.785-03:00  INFO 72060 --- [    Test worker] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2022-07-10T16:51:14.810-03:00  INFO 72060 --- [    Test worker] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.6.9.Final
2022-07-10T16:51:14.912-03:00  INFO 72060 --- [    Test worker] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2022-07-10T16:51:14.972-03:00  INFO 72060 --- [    Test worker] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL10Dialect
2022-07-10T16:51:15.223-03:00  INFO 72060 --- [    Test worker] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2022-07-10T16:51:15.228-03:00  INFO 72060 --- [    Test worker] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2022-07-10T16:51:15.632-03:00  WARN 72060 --- [    Test worker] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2022-07-10T16:51:15.755-03:00  INFO 72060 --- [    Test worker] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
2022-07-10T16:51:16.005-03:00  INFO 72060 --- [    Test worker] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 44097 (http) with context path ''
2022-07-10T16:51:16.010-03:00  INFO 72060 --- [    Test worker] com.example.ApplicationTest              : Started ApplicationTest in 2.275 seconds (process running for 10.027)
2022-07-10T16:51:16.102-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=1, url=http://example.com/6d9c9921-3ef9-4b7c-834c-8f9805425b69, title=Happiness. It consequences, in complete To a., description=A toil praising enjoy will to pain a actual. Exercise, But and a pleasure? procure. To rejects, who take fault. Great pleasure? teachings who not enjoy. Circumstances or pleasure toil one has. Pursue us and occur pleasure? that to but who.))
2022-07-10T16:51:16.107-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=2, url=http://example.com/e25a877d-b748-4604-aec3-63130c451355, title=But how occasionally do pain To no., description=Procure happiness. Extremely how no. Was I that to it which because. Pain it produces pleasure occasionally. Mistaken desires pain or there rationally was of actual. Procure that except again some who itself.))
2022-07-10T16:51:16.108-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=3, url=http://example.com/5ac24d9f-f05d-4af5-bfa5-c94bc84acee0, title=No in rejects, because laborious that., description=It? pleasure of expound. Pleasure no pain pleasure? the fault because there. Obtain pleasure. Who occur pleasure master-builder. Avoids has account this you. The him toil will a dislikes, pleasure. Resultant any is occur those pursue.))
2022-07-10T16:51:16.109-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=4, url=http://example.com/5af3fe1d-cc68-406a-a5ae-c5284757e3d8, title=A how who that who because one., description=Because explorer fault. Account pleasure pain pain pleasure to of teachings a. Consequences, give who him any. You obtain right of of who not.))
2022-07-10T16:51:16.111-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=5, url=http://example.com/18b33bd5-ca03-406c-9a0f-92956f050ddf, title=Loves itself, I that any., description=Laborious or rationally again of. Advantage pain the the. Again right pursue account one who. With must the. A expound who great avoids No itself, which.))
2022-07-10T16:51:16.112-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=6, url=http://example.com/cca58f53-332e-4165-a7b2-5f2eef5b9460, title=Who mistaken ever to., description=Who from the some again to who some and. Physical of toil in. Which But us. Explain trivial obtain example, of explorer.))
2022-07-10T16:51:16.113-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=7, url=http://example.com/69103810-c09b-4961-8198-fbf4aa23b756, title=Has or of avoids us., description=Complete the some. Of a system, all. Dislikes, mistaken pain who pain you anyone explain. Praising some all but has anyone except of. Ever of exercise, to happiness. Is consequences.))
2022-07-10T16:51:16.114-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=8, url=http://example.com/aeaf7aa3-0b49-4441-9655-4b41e2c693c1, title=That avoids circumstances Nor., description=Was any a how system, master-builder. With rejects, of that mistaken to it. Man pleasure example, those loves it happiness. Actual because. Consequences, some because Nor must itself, rationally. Avoids rationally who man desires. Encounter or undertakes.))
2022-07-10T16:51:16.116-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=9, url=http://example.com/34764df8-a41b-4bcd-82a3-36f2f424fc45, title=Consequences pleasure? pain pursue the has., description=Great or praising right. Or in great. Occasionally I of who is anyone extremely. Itself, with great from explorer the. Pain know annoying desires of right the of that. A a the encounter pleasure?.))
2022-07-10T16:51:16.117-03:00  INFO 72060 --- [    Test worker] com.example.Application                  : Link created (link=Link(id=10, url=http://example.com/736a3203-67b9-4343-a650-e7296efb8c65, title=That and rejects, annoying great pleasure. Of account rationally., description=Consequences of no or again explorer. Expound of occasionally obtain I man except. Which loves has denouncing a rejects, to this ever. The was to actual or encounter itself. Nor rationally anyone pain a or. Avoids must circumstances or.))
2022-07-10T16:51:16.226-03:00  INFO 72060 --- [     ducttape-1] o.o.s.r.t.o.OpenTelemetryTracer          : Using OpenTelemetry for tracing
2022-07-10T16:51:17.964-03:00  INFO 72060 --- [ null to remote] o.o.selenium.remote.ProtocolHandshake    : Detected dialect: W3C
2022-07-10T16:51:18.016-03:00  INFO 72060 --- [o-auto-1-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-07-10T16:51:18.017-03:00  INFO 72060 --- [o-auto-1-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-07-10T16:51:18.017-03:00  INFO 72060 --- [o-auto-1-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 0 ms
2022-07-10T16:51:18.033-03:00  INFO 72060 --- [o-auto-1-exec-1] com.example.search.SearchController      : Rendering index page (model={search=Search(text=null), org.springframework.validation.BindingResult.search=org.springframework.validation.BeanPropertyBindingResult: 0 errors, firstSearch=true, links=[]})
2022-07-10T16:51:18.336-03:00  INFO 72060 --- [o-auto-1-exec-3] com.example.link.LinkSpecification       : Selecting title like (string=Hello World!)
2022-07-10T16:51:18.337-03:00  INFO 72060 --- [o-auto-1-exec-3] com.example.link.LinkSpecification       : Selecting description like (string=Hello World!)
2022-07-10T16:51:18.345-03:00  INFO 72060 --- [o-auto-1-exec-3] com.example.search.SearchController      : Rendering search page (model={search=Search(text=Hello World!), org.springframework.validation.BindingResult.search=org.springframework.validation.BeanPropertyBindingResult: 0 errors, firstSearch=false, links=[]})
2022-07-10T16:51:18.441-03:00  INFO 72060 --- [o-auto-1-exec-4] com.example.search.SearchController      : Rendering index page (model={search=Search(text=null), org.springframework.validation.BindingResult.search=org.springframework.validation.BeanPropertyBindingResult: 0 errors, firstSearch=true, links=[]})
2022-07-10T16:51:18.508-03:00  INFO 72060 --- [o-auto-1-exec-5] com.example.link.LinkSpecification       : Selecting title like (string=a)
2022-07-10T16:51:18.508-03:00  INFO 72060 --- [o-auto-1-exec-5] com.example.link.LinkSpecification       : Selecting description like (string=a)
2022-07-10T16:51:18.513-03:00  INFO 72060 --- [o-auto-1-exec-5] com.example.search.SearchController      : Rendering search page (model={search=Search(text=a), org.springframework.validation.BindingResult.search=org.springframework.validation.BeanPropertyBindingResult: 0 errors, firstSearch=false, links=[Link(id=1, url=http://example.com/6d9c9921-3ef9-4b7c-834c-8f9805425b69, title=Happiness. It consequences, in complete To a., description=A toil praising enjoy will to pain a actual. Exercise, But and a pleasure? procure. To rejects, who take fault. Great pleasure? teachings who not enjoy. Circumstances or pleasure toil one has. Pursue us and occur pleasure? that to but who.), Link(id=2, url=http://example.com/e25a877d-b748-4604-aec3-63130c451355, title=But how occasionally do pain To no., description=Procure happiness. Extremely how no. Was I that to it which because. Pain it produces pleasure occasionally. Mistaken desires pain or there rationally was of actual. Procure that except again some who itself.), Link(id=3, url=http://example.com/5ac24d9f-f05d-4af5-bfa5-c94bc84acee0, title=No in rejects, because laborious that., description=It? pleasure of expound. Pleasure no pain pleasure? the fault because there. Obtain pleasure. Who occur pleasure master-builder. Avoids has account this you. The him toil will a dislikes, pleasure. Resultant any is occur those pursue.), Link(id=4, url=http://example.com/5af3fe1d-cc68-406a-a5ae-c5284757e3d8, title=A how who that who because one., description=Because explorer fault. Account pleasure pain pain pleasure to of teachings a. Consequences, give who him any. You obtain right of of who not.), Link(id=5, url=http://example.com/18b33bd5-ca03-406c-9a0f-92956f050ddf, title=Loves itself, I that any., description=Laborious or rationally again of. Advantage pain the the. Again right pursue account one who. With must the. A expound who great avoids No itself, which.), Link(id=6, url=http://example.com/cca58f53-332e-4165-a7b2-5f2eef5b9460, title=Who mistaken ever to., description=Who from the some again to who some and. Physical of toil in. Which But us. Explain trivial obtain example, of explorer.), Link(id=7, url=http://example.com/69103810-c09b-4961-8198-fbf4aa23b756, title=Has or of avoids us., description=Complete the some. Of a system, all. Dislikes, mistaken pain who pain you anyone explain. Praising some all but has anyone except of. Ever of exercise, to happiness. Is consequences.), Link(id=8, url=http://example.com/aeaf7aa3-0b49-4441-9655-4b41e2c693c1, title=That avoids circumstances Nor., description=Was any a how system, master-builder. With rejects, of that mistaken to it. Man pleasure example, those loves it happiness. Actual because. Consequences, some because Nor must itself, rationally. Avoids rationally who man desires. Encounter or undertakes.), Link(id=9, url=http://example.com/34764df8-a41b-4bcd-82a3-36f2f424fc45, title=Consequences pleasure? pain pursue the has., description=Great or praising right. Or in great. Occasionally I of who is anyone extremely. Itself, with great from explorer the. Pain know annoying desires of right the of that. A a the encounter pleasure?.), Link(id=10, url=http://example.com/736a3203-67b9-4343-a650-e7296efb8c65, title=That and rejects, annoying great pleasure. Of account rationally., description=Consequences of no or again explorer. Expound of occasionally obtain I man except. Which loves has denouncing a rejects, to this ever. The was to actual or encounter itself. Nor rationally anyone pain a or. Avoids must circumstances or.)]})
2022-07-10T16:51:19.200-03:00  INFO 72060 --- [o-auto-1-exec-6] com.example.search.SearchController      : Rendering index page (model={search=Search(text=null), org.springframework.validation.BindingResult.search=org.springframework.validation.BeanPropertyBindingResult: 0 errors, firstSearch=true, links=[]})
2022-07-10T16:51:21.169-03:00  INFO 72060 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2022-07-10T16:51:21.170-03:00  INFO 72060 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2022-07-10T16:51:21.171-03:00  INFO 72060 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
```