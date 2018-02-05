# ServiceValidator Design

*Initial proposal, I am open to improvements and suggestions*.

The main idea here is to split some functionality in separate classes (or even sub packages). In particular, in addition to the main class that controls processing we could have separate classes for

- service invocation
- input selection
- running a test on service output
- generating a report


### edu.brandeis.lapps.validator.ServiceValidator

This is the main entry point. As its input we give it a ServiceManager URL in some form or a list of service URLs (maybe as a filename).

It does the following:

1. Collect a list of services with for each services the metadata (creating instances of the `Service` class).

2. Decides for each service what kinds of input to use (using the `InputSelector` class).

3. Validate each service and generate a report for that service.

4. To validate a service, first create a new empty report (using the `Report` class) and then for each input:
    - execute the service and retrieve the result
    - turn the string into a JSON object
    - run all available validation tests and add errors to the report.


### edu.brandeis.lapps.validator.Service

Created from a URL or some other set of identifiers. An instance of this object also has the metadata of the service and a way to execute the service given a filename.

Probably fairly similar to the `org.lappsgrid.api.WebService` class.


### edu.brandeis.lapps.validator.InputSelector

Takes as input a `Service` instance or perhaps just the matadata of the service. Returns a list of filenames.


### edu.brandeis.lapps.validator.Report

There is one report for each service. It holds

- the service name (or maybe the `Service` object itself)
- a list of inputs (Strings)
- a list of errors with for each error the input it was generated on.

The `Report` includes code to write results to html files.


### edu.brandeis.lapps.validator.ServiceTest

A `ServiceTest` takes a JSON object as input as well as a `Report` object and it runs the test on the input, writing errors as needed.

First I thought we would have a whole bunch of subclasses, one for each test or group of tests. And I imagined to just dynamically retrieve these subclasses from the `ServiceValidator` class and then run them. This however seems to be rather hard in Java so we may have to maintain a list of all tests to be run in the `ServiceValidator` code.
