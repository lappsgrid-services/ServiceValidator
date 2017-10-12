# Specifications

The goal of this tool is to ping LAPPS service and determine whether they are life (probably by calling the `getMetaData` method) and whether the actual behaviour of the service complies with the reported meta data.


The tool should be implemented in Java. First we can run it from the command line but eventually we want to hook this up to a webpage that updates itself periodically.


### Input and resources

The input could be a list of services (specified as a URL), the tool could also ping the service manager for the list of services (see https://github.com/lappsgrid-incubator/groovlets).

The tool will need example LIF input, these will be created manually and will be put in `src/main/resources`.


### Functionality

For each service, the validator should first get the metadata from the service and select the LIF input that matches the required input, run the service on that input and then determine that the service's output matches the output as specified by the metadata.

It should also try to run the service on insufficient input and check whether the service fails gracefully with an error message.

Checking the output means various things:

- Check that no data in the input was deleted or overwritten.
- Check whether the view added by the service has the appropriate metadata.
- Check whether the annotations in the view have the correct `@type`.

If a service created no new views but just added to existing views:

- Check whether the service added the correct metadata to the view.
- Check whether added annotations are of the correct `@type`.

These latter tests are not required in the first version of the tool since few tools, if any at all, add to existing views.

In addition, there are a couple of checks on the annotations themselves:

- Does an annotation have the required fields as specified by the vocabulary (this only needs to be done for types that are in the vocabulary).
- If an annotation refers to another annotation, check whether that annotation exists (either in the same view or another view).


### Reporting

The result of running the tool should be a report. For the first prototype, the actual report would be a text file that lists for each service whether it passed. If the service did not pass, the report should specify what categories of tests failed, for example:

```
lapps_service_1

   passed

lapps_service_2

   failed - output mismatch
   failed - incorrect added metadata
```

The number of categories tested for is not determined yet, but at first should include:

- input mismatch
- output mismatch
- incorrect added metadata
- incorrect annotation types added
