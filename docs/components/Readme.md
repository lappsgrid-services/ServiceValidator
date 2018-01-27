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

What the services were tested for:

- If a service says it requires one of several formats (for example `lif` or `text`), then it should work with those formats and fail gracefully with an error message (something like "Unsupported input type: gate"). We are assuming that when the discriminator says the payload is LIF that the payload actually is LIF, that is, we assume well-formed input.

- If a service says it requires a set of annotation types to be in the input (for example `Token` and `Sentence`), then all those types should be in the input, otherwise the service should fail gracefully with an error message. For now, we assuming that "available in the input" means that there is a view where the metadata says the view contains that annotation type. This may be extended to meaning that there actually are such annotations in the annotations list.

- If a service says it produces output in a certain format or that it produces certain kinds of annotations then it should do so.

- A service should always create well-formed output. Here, we are mostly concerned about LIF output. Well-formed means adhering to the JSON schema and the vocabulary.

- Services should not remove existing layers.

<!--
## Notes on the vocabulary

On the view's type metadata attribute
- The vocab has this to say about the type: "The value type of the annotations produced."
- In some cases the type is the same as in the description field of the service metadata

On the label attribute
- This is in the json schema at the top level as an optional feature, it is intended for visualization tools
- But this property is defined on Relation (required), which is different from the label property used at the top level)
-

Difference between jsonld#lapps and jsonld#lif

On the DependencyStructure type
- There is no `root` property, but maybe it should have one

What does a POS tagger produce?
- Say they create there own tokens, should the contains say Token#pos or should it also have Token?

-->
