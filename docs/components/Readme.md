# Checking services

This directory contains the result of various checks on a series of services. Much of it is done on several versions of a small [sample file](input/karen-flies.txt) with minimal content that still contains enough richness so services should actually produce some output. The services tested here typically are the ones used at the LAPPS/Galaxy pages at http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org.

The following types of services were tested:

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

How services are identified:

- Most services are accessed via the Vassar or Brandeis service managers. For these, we provide the server name (brandeis or vassar, sometimes abbreviated to the first letter) followed by the service identifier. Notice that the service identifier is not the complete service identifier (which is a complete URL) because on both servers a prefix is added (in addition to the server itself): `anc:` on the Vassar server and `brandeis_eldrad_grid_1:` on the Brandeis server.

- For other services, that is, those that are not accessed via a service manager, we give the full path to the service.


### Some notes on the vocabulary

On the `type` attribute in the view metadata

- The vocab has this to say about the type: "The value type of the annotations produced." It is not clear to me what this means in case it is different from what is in the `@type` attribute of annoations (and the `contains` attributes in the metadata).
- In some cases the type is the same as in the description field of the service metadata.
- We need a theory on what we do with this types, an initial proposal is:
  - These types are all discriminators, possibly under ns/tools or ns/components.
  - They refer to the kinds of tools instead of the kinds of annotations.
  - The splitter page would have all kinds of tools that can be described on that page.

On the `label` attribute

- This is in the json schema at the top level as an optional feature, it is intended for visualization tools
- But this property is also defined on Relation as a required property, which is somewhat confusing.

On the `lapps` and `lif` discriminators

- What is the exact difference between jsonld#lapps and jsonld#lif. My interpretation is that the former is any data object passed to a service whereas the latter indicates a lapps object with LIF in its payload.

On the `DependencyStructure` type

- There is no `root` property, but maybe it should have one since we have one on `PhraseStructure`.

What does a POS tagger produce?

- Many POS taggers create their own tokens, should the `contains` attribute just say `Token#pos` or should it also have `Token`? It now just has `Token#pos` and `Token` is implied.

What about the `dependsOn` property in the view meta data?

- This was discussed a while back, but without any resolution. Some POS taggers require an existing token view, but from the results it is not clear explicitly which one that was.

<!--

Things to test

- metadata say what version of tool is wrapped

-->
