# Checking Services

This directory contains the result of various checks on a series of services. Much of it is done on several versions of a small [sample file](input/karen-flies.txt) with minimal content that still contains enough richness so services should actually produce some output. The services tested here typically are the ones used at the LAPPS/Galaxy pages at http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org.

More than 50 services were tested manually, and for each service from 4 to 8 different kinds of input were tried. This should be done manually only once since this is tedious and error prone work. The idea is that the service validator will include all tests performed here and then some more.

The rest of this page contains the following:

1. What services were tested and what they were tested for.
2. Some observations related to the vocabulary
3. An overview of problems found.


## Services Tested

The following types of services were tested (click the links to see the reports):

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


## Some Notes on the Vocabulary

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


## Overview of Service Problems

This list is culled from the observations for each service with some manual fiddling to group some problems together. About 60 different kinds of problems were found, not all of them are listed here.


count | error description
------| -----------------
38 | New view has no identifier
26 | Missing `@language` attribute
18 | Annotations do not have a `label` attribute
17 | Service requires LIF but accepts text
14 | Existing layer in input is lost
 7 | View metadata does not have `posTagSet` attribute
 6 | The `@context` attribute is lost
 6 | Service requires Tokens, but they are not
 4 | View metadata has no `namedEntityCategorySet`
 4 | The type of all new annotations is `Token#pos`, should be `Token`
 4 | Service requirement says xml#gate but will try to run on text and LIF input
 4 | Service requirement says Token and Token#pos, but it appears to require Sentence and Token
 3 | Service requires text or json#lapps, but accepts only text input
 3 | Constituent annotations do not have a `parent` attribute
 3 | Annotations in existing view all get metadata attribute (with value set to null)
 2 | Tries to run on non-GATE input (but fails to find sentences and tokens, even if they are in the LIF)
 2 | The amended view does not have an identifier anymore
 2 | Services creates LIF output but discriminator is lapps (as specified in metadata)
 2 | Service requires xml#gate but accepts text and LIF input
 2 | Service requires Token#pos but this is not enforced
 2 | Service requirements say text input is not allowed, but will accept text
 2 | Service produces Sentence annotations, but this is not in the metadata nor is it declared in the view metadata
 2 | Service metadata say input needs Sentence annotations
 2 | Service metadata say input cannot be LIF, but LIF input is allowed
 2 | Service metadata lists xml as input type
 2 | Running it on GATE input with sentences and tokens gives you a null pointer exception
 2 | On GATE input with Tokens, pos and Sentences, no new layer is created.
 2 | No VerbChunks are added
 2 | Metadata type has tagset:penn, but tagset should be in `posTagSet`
 2 | Creates output for all three input types even though metadata say that gate is required
 2 | Annotations do not have a `category` attribute.
 2 | Annotation list contains Sentence type, but these are not in the view metadata
 1 | With Tokens and pos available a new view is created, but it is empty
 1 | When running on text or GATE input we get an error written to the standard output
 1 | When running on LIF input the `@context` attribute disappears
 1 | When given text input it complains about missing tokens (which is actually correct given the service requirements)
 1 | When Tokens (but no pos) are available all that happens is that an empty new view is created
 1 | View metadata type is weird given this is a splitter
 1 | View metadata has no type
 1 | View metadata has a typo (Corefernce)
 1 | View metadata claims it contains Token#pos
 1 | Service seems to require Token#pos as well, but this is not specified
 1 | Service requires Token#pos, but error message does not say that
 1 | Service requires Token but error message does not say that
 1 | Service requires Coreference, which makes no sense
 1 | Service metadata says the output includes `Token` annotations, but it does not
 1 | Service metadata format requirement has `json`
 1 | No results created for LIF and text input, raise error "Received invalid response from DBpedia Spotlight API."
 1 | No named entities found
 1 | New view has wrong metadata (Token#pos)
 1 | Missing `root` property
 1 | Format requirement has `json`, but not `json:lif`
 1 | Constituent annotations are not grounded in tokenizers and therefore are not related to offsets.


<!--

Things to test

metadata should say what version of tool is wrapped

-->
