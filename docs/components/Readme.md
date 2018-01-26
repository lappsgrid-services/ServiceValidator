# Checking services

This directory contains the result of various checks on a series of services. Much of it is done on several versions of a small [sample file](input/karen-flies.txt) with minimal content that still contains enough richness so services should actually produce some output. The services used are the ones used at the LAPPS/Galaxy pages at http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org.

The following services were tested:

- [Sentence Splitters](splitters)
- [Tokenizers](tokenizers)
- [POS Taggers](taggers)
- [Chunkers](chunkers)
- [Named Entity Recognizers](entities)
- [Coreference Tools](coreference)
- [Parsers](parsers)


<!--
- type  (note, the vocab has this to say about the type: "The value type of the annotations produced.")
- Annotations do not have a `label` attribute (by the way, this property is defined on label, which is different from the label property used at the top level)

stanford/brandeis.dependencyparser.xml

- There is no `root` property, but this is not wrong because it is not defined in the vocab anyway

-->
