# Parsers

Consistency checking on all named entity recognizers used on http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org/. This was done in a way similar to the [splitters](../splitters) but reported on in a less verbose way.

## Summary of Observations

Below is a table with observations on five parser services. See further down for more verbose observations.

service                                 | requires    | produces  | other
---                                     | ---         | ---       | ---
stanford/brandeis.parser.xml            | &check;     | &check;   | 1, 2, 3, 4
stanford/brandeis.dependencyparser.xml  | &check;     | &check;   | 1, 3, 5
opennlp/opennlp.parser.xml              | 6           | &check;   | 1, 7
dkpro/dkpro.stanford.parser.xml         | 8, 9        | &check;   | 1, 5, 10,
dkpro/dkpro.opennlp.parser.xml          | 8, 9        | &check;   | 1, 5, 10, 11

The `requires` column indicates whether tool requirements from metadata match its behavior when given input whereas the `produces` column indicates whether what the tool produces matches what is specified in the metadata. Any other observations are in the `other` column. Check marks indicate all is well, the numbers refer to elements from the list below.

1. New view has no identifier
2. View metadata does not have `categorySet`
3. Annotations do not have a `label` attribute
4. Missing `root` property
5. Constituent annotations do not have a `parent` attribute
6. Service requires LIF input but text is accepted (albeit with an error that there are no sentences)
7. Constituent annotations are not grounded in tokenizers and therefore are not related to offsets.
8. Service requires LIF input but text is accepted
9. Service requires PhraseStructure
10. Existing view in input is not in output
11. View metadata claims it contains Token#pos


View metadata types:

- tokenizer:stanford
- syntacticparser:stanford
- dependency-parser:stanford
- parser:opennlp
- parser:dkpro_opennlp


## Rounding up the Services

Taken from https://github.com/lappsgrid-incubator/GalaxyMods/blob/master/config/tool_conf.xml and its sister in the develop branch, with the invokers lifted from the Galaxy XML wrappers.

```
stanford/brandeis.parser.xml
stanford/brandeis.dependencyparser.xml
  common/invoke_brandeis.lsd stanfordnlp.parser_2.0.4
  common/invoke_brandeis.lsd stanfordnlp.dependencyparser_2.0.4

opennlp/opennlp.parser.xml
  common/invoke_brandeis.lsd opennlp.parser_2.0.3

dkpro/dkpro.stanford.parser.xml
dkpro/dkpro.opennlp.parser.xml
  common/invoke_brandeis.lsd uima.dkpro.stanfordnlp.parser_0.0.1
  common/invoke_brandeis.lsd uima.dkpro.opennlp.parser_0.0.1

```

## Service Metadata

Service metadata from http://api.lappsgrid.org/:

service                                | requires         | produces
---                                    | ---              | ---
stanford/brandeis.parser.xml           | text, jsonld#lif | jsonld#lif, PhraseStructure, Constituent, Token
stanford/brandeis.dependencyparser.xml | text, jsonld#lif | jsonld#lif, DependencyStructure, Dependency, Token
opennlp/opennlp.parser.xml             | jsonld#lif, Sentence        | jsonld#lif, PhraseStructure, Constituent
dkpro/dkpro.stanford.parser.xml        | jsonld#lif, PhraseStructure | jsonld#lif, PhraseStructure, Constituent
dkpro/dkpro.opennlp.parser.xml         | jsonld#lif, PhraseStructure | jsonld#lif, PhraseStructure



## Running the Services

The invokers and the service names were already listed above. We use the same input as for the splitters and tokenizers except that we add a few input files with Token and Sentence annotations since so many services claim they require those. And we again create output using a [bash script](chunkers.sh).


### Service behavior analysis

Here are observations for the tested services as of January 25th 2018, service output is stored in the [output directory](output).

stanford/brandeis.parser.xml

- New view has no identifier
- View metadata does not have `categorySet`
  (note, the vocab has this to say about the type: "The value type of the annotations produced.")
- Annotations do not have a `label` attribute (by the way, this property is defined on label, which is different from the label property used at the top level)
- Missing `root` property

stanford/brandeis.dependencyparser.xml

- New view has no identifier
- There is no `root` property, but this is not wrong because it is not defined in the vocab anyway
- Annotations do not have a `label` attribute
- Constituent annotations do not have a `parent` attribute

opennlp/opennlp.parser.xml

- Service requires LIF input but text is accepted (albeit with an error that there are no sentences)
- New view has no identifier
- Constituent annotations are not grounded in tokenizers and therefore are not related to offsets.

dkpro/dkpro.stanford.parser.xml

- Service requires LIF input but text is accepted
- Service requires PhraseStructure
- Existing view in input is not in output
- New view has no identifier
- Constituent annotations do not have a `parent` attribute
- View metadata claims it contains Token#pos

dkpro/dkpro.opennlp.parser.xml

- Service requires LIF input but text is accepted
- Service requires PhraseStructure
- Existing view in input is not in output
- New view has no identifier
- Constituent annotations do not have a `parent` attribute
