# Taggers

Consistency checking on all taggers used on http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org/. This was done in a way similar to the [splitters](../splitters) but reported on in a less verbose way.


## Summary of Observations

Below is a table with observations on nine tagger services. See further down for more verbose observations.

service                             | requires    | produces  | other
---                                 | ---         | ---       | ---
stanford/vassar.tagger_2.0.0.xml    | 1           | &check;   | 2, 3, 4, 5
stanford/vassar.tagger_2.1.0.xml    | &check;     | &check;   | 2, 4, 5
stanford/brandeis.postagger.xml     | &check;     | &check;   | 2, 5, 6, 7
gate/gate.tagger_2.2.0.xml          | 8           | &check;   | 5, 9, 10
gate/gate.tagger_2.3.0.xml          | 8           | &check;   | 5, 9, 10
opennlp/opennlp.postagger.xml       | 1           | &check;   | 2, 5, 6, 7
lingpipe/vassar.tagger_1.0.0.xml    | &check;     | &check;   | 2, 3, 5, 6
dkpro/dkpro.stanford.postagger.xml  | 1, 11       | 12        | 5, 7, 9
dkpro/dkpro.opennlp.postagger.xml   | 1, 11       | 12        | 5, 6, 7, 9

The `requires` column indicates whether tool requirements from metadata match its behavior when given input whereas the `produces` column indicates whether what the tool produces matches what is specified in the metadata. Any other observations are in the `other` column. Check marks indicate all is well, the number refer to elements from the list below.

1. Service requires jsonld#lif but service tries to run on text input
2. New view has no identifier
3. When running on LIF the `@context` property is lost
4. Metadata type has tagset:penn, but tagset should be in `posTagSet`
5. View metadata does not have `posTagSet` attribute
6. The type of all new annotations is `Token#pos`, should be `Token`
7. Annotations do not have a `label` property
8. Tries to run on non-GATE input (but fails to find sentences and tokens, even if they are in the LIF)
9. There is no `@language` attribute
10. All output is in one view (LIF input before conversion had one views too), but the amended view does not have an identifier anymore
11. Service requires Token#pos but this is not enforced
12. Service produces Sentence annotations, but this is not in the metadata nor is it declared in the view metadata

The Vassar Stanford taggers copy existing tokens into the new view, correctness of the copy was not checked. Related to this we may need to pick up on introducing `dependsOn` again.

Another note is that we may have a general problem with preserving identifiers going into and coming out of GATE.

Metadata type values:

- tagger:stanford
- gate
- tagger:opennlp
- tagger:lingpipe-brown-hmm-tagger
- postagger:dkpro_opennlp
- postagger:dkpro_opennlp


## Rounding up the Taggers

Taken from https://github.com/lappsgrid-incubator/GalaxyMods/blob/master/config/tool_conf.xml and its sister in the develop branch, with the invokers lifted from the Galaxy XML wrappers.

```
stanford/vassar.tagger_2.0.0.xml
stanford/vassar.tagger_2.1.0.xml
  common/invoke_vassar.lsd stanford.tagger_2.0.0
  common/invoke_vassar.lsd stanford.tagger_2.1.0-SNAPSHOT

stanford/brandeis.postagger.xml
  common/invoke_brandeis.lsd stanfordnlp.postagger_2.0.4

gate/gate.tagger_2.2.0.xml
gate/gate.tagger_2.3.0.xml
  gate/invoke.lsd gate.tagger_2.2.0
  gate//invoke.lsd gate.tagger_2.3.0

opennlp/opennlp.postagger.xml
  common/invoke_brandeis.lsd opennlp.postagger_2.0.3

lingpipe/vassar.tagger_1.0.0.xml
  lingpipe/invoke.lsd LingpipeTagger

dkpro/dkpro.stanford.postagger.xml
dkpro/dkpro.opennlp.postagger.xml
  common/invoke_brandeis.lsd uima.dkpro.stanfordnlp.postagger_0.0.1
  common/invoke_brandeis.lsd uima.dkpro.opennlp.postagger_0.0.1
```

## Declared Metadata

Service metadata from http://api.lappsgrid.org/:

service                            | requires                  | produces
---                                | ---                       | ---
stanford/vassar.tagger_2.0.0.xml   | jsonld#lif, Token         | jsonld#lif, Token#pos
stanford/vassar.tagger_2.1.0.xml   | jsonld#lif, Token         | jsonld#lif, Token#pos
stanford/brandeis.postagger.xml    | text, jsonld#lif          | jsonld#lif, Token#pos
gate/gate.tagger_2.2.0.xml         | xml#gate, Token, Sentence | xml#gate, Token#pos
gate/gate.tagger_2.3.0.xml         | xml#gate, Token, Sentence | xml#gate, Token#pos
opennlp/opennlp.postagger.xml      | jsonld#lif, Token         | jsonld#lif, Token#pos
lingpipe/vassar.tagger_1.0.0.xml   | text, jsonld#lif, Token   | jsonld#lif, Token#pos
dkpro/dkpro.stanford.postagger.xml | jsonld#lif, Token#pos     | jsonld#lif, Token#pos
dkpro/dkpro.opennlp.postagger.xml  | jsonld#lif, Token#pos     | jsonld#lif, Token#pos


## Running the Taggers

The invokers and the service names were already listed above. We use the same input as for the splitters and tokenizers except that we add a few input files with Token and Sentence annotations since so many services claim they require those. And we again create output using a [bash script](taggers.sh). For output we again expect an error message or some well-formed LIF representation with a view that contains `Token#pos` in its metadata.

Error message are now not just reporting the wrong format, but could also indicate that some annotation type is missing in the input as with "Unable to process input: no tokens found".


### Service behavior analysis

Here are observations for all the tokenizers as of January 23rd 2018, service output is stored in the [output directory](output).

stanford/vassar.tagger_2.0.0.xml

- Correct error message on GATE input
- Service requires jsonld#lif but service tries to run on text input
- Correct error messages when there are no Tokens in the input
- When running on LIF the `@context` property is lost
- Copies existing tokens into the new view (did not check correctness of copy)
- Note: we need to pick up dependsOn again
- New view has no identifier
- Metadata type has tagset:penn, but tagset should be in `posTagSet`

stanford/vassar.tagger_2.1.0.xml

- Correct error message on GATE input
- Correct error messages when there are no Tokens in the input
- Copies existing tokens into the new view (did not check correctness of copy)
- Note: we need to pick up dependsOn again
- New view has no identifier
- Metadata type has tagset:penn, but tagset should be in `posTagSet`

stanford/brandeis.postagger.xml

- Correct error message on GATE input
- New view has no identifier
- View metadata does not have `posTagSet` attribute
- The type of all new annotations is `Token#pos`, should be `Token`
- Annotations do not have a `label` property

gate/gate.tagger_2.2.0.xml

- Tries to run on non-GATE input (but fails to find sentences and tokens, even if they are in the LIF)
- There is no `@language` attribute
- View metadata does not have `posTagSet` attribute
- All output is in one view (LIF input before conversion had one views too)
- The amended view does not have an identifier anymore
- Note: we may have a general problem with preserving identifiers going into and coming out of GATE.

gate/gate.tagger_2.3.0.xml

- Same observations as for version 2.2.0

opennlp/opennlp.postagger.xml

- Correct error message on GATE input
- Correct error messages when there are no Tokens in the input
- Service requires jsonld#lif but service tries to run on text input
- New view has no identifier
- View metadata does not have `posTagSet` attribute
- Annotations do not have a `label` property
- The type of all new annotations is `Token#pos`, should be `Token`

lingpipe/vassar.tagger_1.0.0.xml

- Correct error message on GATE input
- Correct error messages when there are no Tokens in the input
- When running on LIF the `@context` property is lost
- New view has no identifier
- View metadata does not have `posTagSet` attribute
- The type of all new annotations is `Token#pos`, should be `Token`

dkpro/dkpro.stanford.postagger.xml

- Correct error message on GATE input
- Service requires jsonld#lif but runs on text input
- Service requires Token#pos but this is not enforced
- There is no `@language` attribute
- View metadata does not have `posTagSet` attribute
- Service produces Sentence annotations, but this is not in the metadata nor is it declared in the view metadata
- Annotations do not have a `label` property

dkpro/dkpro.opennlp.postagger.xml

- Same observations as for stanford tagger, plus
- The type of all new annotations is `Token#pos`, should be `Token`
