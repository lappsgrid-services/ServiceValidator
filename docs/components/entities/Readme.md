# Named Entity Recognizers

Consistency checking on all named entity recognizers used on http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org/. This was done in a way similar to the [splitters](../splitters) but reported on in a less verbose way.

## Summary of Observations

Below is a table with observations on four chunker services. See further down for more verbose observations.

service                                         | requires   | produces  | other
---                                             | ---        | ---       | ---
stanford/vassar.ner_2.0.0.xml                   | 1, 2       | &check;   | 3, 4, 5
stanford/vassar.ner_2.1.0.xml                   | 1, 2       | &check;   | 3, 4, 6
stanford/vassar.ner2_2.0.0.xml                  | 1, 2       | &check;   | 7, 8
stanford/brandeis.namedentityrecognizer.xml     | 9, 10      | &check;   | 3, 11, 12, 13
gate/gate.ner_2.2.0.xml                         | 1, 14      | &check;   | 15, 16
gate/gate.ner_2.3.0.xml                         | 1, 14      | &check;   | 15, 16
opennlp/opennlp.namedentityrecognizer.xml       | 17         | &check;   | 3, 11, 12, 13
lingpipe/cmu.dictionaryNER_1.0.0.xml            | &check;    | &check;   | 3, 13, 18
lingpipe/vassar.ner_1.0.0.xml                   | 9          | &check;   | 3, 5, 18,
dkpro/dkpro.stanford.namedentityrecognizer.xml  | 9          | &check;   | 3, 15, 19, 20
dkpro/dkpro.opennlp.namedentityrecognizer.xml   | 9          | &check;   | 3, 15, 19, 21
dbpedia/spotlight.xml                           | &check;    | -         | 22

The `requires` column indicates whether tool requirements from metadata match its behavior when given input whereas the `produces` column indicates whether what the tool produces matches what is specified in the metadata. Any other observations are in the `other` column. Check marks indicate all is well, the numbers refer to elements from the list below.

1. Service requires Tokens but this is not enforced
2. Service seems to require Token#pos as well, but this is not specified
3. New view has no identifier
4. When Tokens (but no pos) are available all that happens is that an empty new view is created
5. On LIF input the `@context` attribute is lost
6. When running on text or GATE input we get a proper error message in the output file but also an error written to the standard output
7. With Tokens and pos available a new view is created, but it is empty
8. Annotations in existing view all get metadata attribute (with value set to null)
9. Service requires LIF input, but tool runs on text input
10. Service requires Tokens, but runs and creates NamedEntity annotations without it
11. Annotations do not have a `label` attribute.
12. Annotations do not have a `category` attribute.
13. View metadata has no `namedEntityCategorySet`
14. Service requires xml#gate but accepts text and LIF input
15. Output has no `@language` attribute.
16. On GATE input with Tokens, pos and Sentences no new layer is created
17. Service requires LIF, but when given text it does not reject it (but it does complain about missing tokens)
18. Output has no `@language` attribute with text input
19. Existing view in input is not in output
20. No named entities found
21. New view has wrong metadata (Token#pos)
22. No results created for LIF and text input, raise error "Received invalid response from DBpedia Spotlight API."

Types used in view metadata:

- ner:stanford
- ner:stanford
- ner:lingpipe-en-news-muc-6
- ner:dkpro_opennlp
- ner:dkpo_stanford


## Rounding up the Named Entity Recognizers

Taken from https://github.com/lappsgrid-incubator/GalaxyMods/blob/master/config/tool_conf.xml and its sister in the develop branch, with the invokers lifted from the Galaxy XML wrappers.

```
stanford/vassar.ner_2.0.0.xml
stanford/vassar.ner_2.1.0.xml
  common/invoke_vassar.lsd stanford.ner_2.0.0
  common/invoke_vassar.lsd stanford.ner_2.1.0-SNAPSHOT

stanford/vassar.ner2_2.0.0.xml (Selectable NER, no models available)
  stanford/invoke_ner2.lsd $model $input $output
  This is a service that is not registered on the Service Manager
  http://grid.anc.org:9080/StanfordServices/2.1.0-SNAPSHOT/services/SelectableNamedEntityRecognizer
  When invoking this service you give it a string as specified in tools/stanford/vassar.ner2_2.0.0.xml
  in https://github.com/lappsgrid-incubator/GalaxyMods
  So you run it as follows
  $ lsd stanford/invoke_ner2.lsd english.all.3class.distsim.crf.ser.gz INFILE OUTFILE

stanford/brandeis.namedentityrecognizer.xml
  common/invoke_brandeis.lsd stanfordnlp.namedentityrecognizer_2.0.4

gate/gate.ner_2.2.0.xml
gate/gate.ner_2.3.0.xml
  gate/invoke.lsd gate.ner_2.2.0
  gate/invoke.lsd gate.ner_2.3.0

opennlp/opennlp.namedentityrecognizer.xml
  common/invoke_brandeis.lsd opennlp.namedentityrecognizer_2.0.3

lingpipe/cmu.dictionaryNER_1.0.0.xml (requires dictionary, but none visible)
  lingpipe/cmu.dictionaryNER.lsd $dictionary $input $output
  this is a service that is not registered on the Service Manager
  http://grid.anc.org:9080/LingpipeServices/1.1.0/services/LingpipeDictionaryBasedNER
  This requires uploading a dictionary if you access the service via Galaxy or having a
  dictionary available locally. The dictionary looks as follows
    Karen | Person
    New York | Location

lingpipe/vassar.ner_1.0.0.xml
  lingpipe/invoke.lsd LingpipeNER
  (invoker includes LingpipeServices/1.0.0-SNAPSHOT/services)

dkpro/dkpro.stanford.namedentityrecognizer.xml
dkpro/dkpro.opennlp.namedentityrecognizer.xml
  common/invoke_brandeis.lsd uima.dkpro.stanfordnlp.namedentityrecognizer_0.0.1
  common/invoke_brandeis.lsd uima.dkpro.opennlp.namedentityrecognizer_0.0.1

dbpedia/spotlight.xml
  dbpedia/spotlight.lsd
  this is a service that is not registered on the Service Manager
  http://grid.anc.org:9080/DBpediaSpotlightServices/1.0.0/services/DBpediaSpotlightAnnotator

common/nerfix.xml
  (ignore, this is a normalizer and is not associated with a service)
```

## Declared Metadata

Service metadata from http://api.lappsgrid.org/:

service                                         | requires                | produces
---                                             | ---                     | ---
stanford/vassar.ner_2.0.0.xml                   | jsonld#lif, Token       | jsonld#lif, DPLO &dagger;
stanford/vassar.ner_2.1.0.xml                   | jsonld#lif, Token       | jsonld#lif, DPLO
stanford/vassar.ner2_2.0.0.xml                  | jsonld#lif, Token       | jsonld#lif, DPLO
stanford/brandeis.namedentityrecognizer.xml     | jsonld#lif, Token       | jsonld#lif, NamedEntity
gate/gate.ner_2.2.0.xml                         | xml#gate, Token         | xml#gate, DPLO
gate/gate.ner_2.3.0.xml                         | xml#gate, Token         | xml#gate, DPLO
opennlp/opennlp.namedentityrecognizer.xml       | jsonld#lif, Token       | jsonld#lif, NamedEntity
lingpipe/cmu.dictionaryNER_1.0.0.xml            | text, jsonld#lif        | jsonld#lif, NamedEntity
lingpipe/vassar.ner_1.0.0.xml                   | jsonld#lif, Token       | jsonld#lif, NamedEntity
dkpro/dkpro.stanford.namedentityrecognizer.xml  | jsonld#lif, NamedEntity | jsonld#lif, NamedEntity
dkpro/dkpro.opennlp.namedentityrecognizer.xml   | jsonld#lif, NamedEntity | jsonld#lif, NamedEntity
dbpedia/spotlight.xml                           | text, jsonld#lif        | jsonld#lif, NamedEntity

&dagger; DPLO is short for Date, Person, Location, Organization.

For some services the metadata are not available on the API because they are not registered. The metadata for those two were accessed using [metadata.lsd](../metadata.lsd):

```
$ lsd metadata.lsd SelectableNamedEntityRecognizer
$ lsd metadata.lsd DBpediaSpotlightAnnotator
$ lsd metadata.lsd LingpipeDictionaryBasedNER
```

## Running the Named Entity Recognizers

We again create output using a [bash script](entities.sh), see the script for notes on what input was used.


### Service behavior analysis

Here are observations for the tested services as of January 25th 2018, service output is stored in the [output directory](output).

stanford/vassar.ner_2.0.0.xml

- Service requires Tokens but this is not enforced
- Service seems to require Token#pos as well, but this is not specified
- When Tokens (but no pos) are available all that happens is that an empty new view is created
- Metadata type of view is ner:stanford
- New view has no identifier
- On LIF input the `@context` attribute is lost

stanford/vassar.ner_2.1.0.xml

- Same as above, except that
- `@context` attribute is not lost
- When running on text or GATE input we get a proper error message in the output file but also an error written to the standard output

stanford/vassar.ner2_2.0.0.xml

- Service requires Tokens but this is not enforced
- Service seems to require Token#pos as well, but this is not specified (but unhelpful error is raised)
- With Tokens and pos available a new view is created, but it is empty
- Annotations in existing view all get metadata attribute (with value set to null)

stanford/brandeis.namedentityrecognizer.xml

- Service requires LIF input, but tool runs on text input
- Service requires Tokens, but runs and creates NamedEntity annotations without it
- New view has no identifier
- Metadata type of view is ner:stanford
- Annotations do not have a `label` attribute.
- Annotations do not have a `category` attribute.
- View metadata has no `namedEntityCategorySet`

gate/gate.ner_2.2.0.xml

- Service requires xml#gate but accepts text and LIF input
- Service requires Tokens, but this is not enforced
- Output has no `@language` attribute.
- On GATE input with Tokens, pos and Sentences no new layer is created.

gate/gate.ner_2.3.0.xml

- Same as above

opennlp/opennlp.namedentityrecognizer.xml

- Service requires LIF, but when given text it does not reject it (but it does complain about missing tokens)
- New view has no identifier
- Annotations do not have a `label` attribute.
- Annotations do not have a `category` attribute.
- View metadata has no `namedEntityCategorySet`

lingpipe/cmu.dictionaryNER_1.0.0.xml

- View metadata has no type
- Output has no `@language` attribute with text input.
- View metadata has no `namedEntityCategorySet`

lingpipe/vassar.ner_1.0.0.xml

- Service requires LIF but accepts text
- New view has no identifier
- Output has no `@language` attribute with text input.
- On LIF input the `@context` attribute is lost
- Metadata type of view is ner:lingpipe-en-news-muc-6
- Gives scores of -Infinity

dkpro/dkpro.stanford.namedentityrecognizer.xml

- Service requires LIF but accepts text
- New view has no identifier
- Output has no `@language` attribute
- Metadata type of view is ner:dkpro_opennlp
- Existing view in input is not in output
- No named entities found

dkpro/dkpro.opennlp.namedentityrecognizer.xml

- Service requires LIF but accepts text
- New view has no identifier
- Output has no `@language` attribute
- Metadata type of view is ner:dkpro_stanford
- Existing view in input is not in output
- New view has wrong metadata (Token#pos)

dbpedia/spotlight.xml

- No results created for LIF and text input, raise error "Received invalid response from DBpedia Spotlight API."
