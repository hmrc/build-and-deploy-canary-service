
# build-and-deploy-canary-service

This service is used by the Build and Deploy team to test the build and deployment of a scala service into an environment.

## Requirements

* AdoptOpenJDK Java 1.8.0_272
* sbt - version defined in [build.properties](./project/build.properties)

## Updating dependencies and sbt

To find dependencies that may need to be updated and the latest sbt version, the [Tax Platform Catalogue](https://catalogue.tax.service.gov.uk/repositories/build-and-deploy-canary-service) will show outdated dependencies and sbt version for the running service.

For sbt version see [build.properties](./project/build.properties)

For service dependencies see [AppDependencies.scala](./project/AppDependencies.scala)

For sbt plugins see [plugins.sbt](./project/plugins.sbt)

After updating either the sbt version, sbt plugins, or service dependencies confirm the service compiles and tests pass with:

    sbt clean test

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

