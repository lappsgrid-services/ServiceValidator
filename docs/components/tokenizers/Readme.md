# Tokenizers

Consistency checking on all splitters used on http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org/. This was done in a way similar to the [splitters](../splitters) but reported on in a less verbose way.


## Summary of Observations

Below is a table with observations on all nine tokenizer services (plus one added later: lingpipe.splitter_1.1.1-SNAPSHOT). See below for slightly more verbose observations.

service                                   | requires    | produces  | other
---                                       | ---         | ---       | ---
v stanford.tokenizer_2.0.0                | 1           | &check;   | 2, 3, 4, 5
v stanford.tokenizer_2.1.0.SNAPSHOT       | &check;     | &check;   | 2, 6, 7&dagger;
b stanfordnlp.tokenizer_2.0.4             | &check;     | &check;   | 2, 8
v gate.tokenizer_2.2.0                    | 9, 10       | &check;   | 2, 3, 4
v gate.tokenizer_2.3.0                    | 9, 10       | &check;   | 2, 3, 4
b opennlp.tokenizer_2.0.3                 | 11          | &check;   | 2, 8
&Dagger; LingpipeTokenizer                | &check;     | &check;   | 2, 6, 12
v lingpipe.splitter_1.1.1-SNAPSHOT        | 16          | 17        | 3
b uima.dkpro.stanfordnlp.tokenizer_0.0.1  | 13, 14      | &check;   | 2, 3, 4, 8, 15
b uima.dkpro.opennlp.tokenizer_0.0.1      | 13, 14      | &check;   | 2, 3, 4, 8, 15

&dagger; This issue was fixed in a more recent release of the snapshot.<br/>
&Dagger; http://grid.anc.org:9080/LingpipeServices/1.0.0-SNAPSHOT/services/LingpipeTokenizer

The `requires` column indicates whether tool requirements from metadata match its behavior when given input whereas the `produces` column indicates whether what the tool produces matches what is specified in the metadata. Any other observations are in the `other` column. Check marks indicate all is well, the number refer to elements from the list below.

1. Service metadata specifies input is LIF, but will run on text input
2. New view has no identifiers
3. There is no `@language` attribute
4. Existing view in input is lost
5. When running on LIF input, the `@value` attribute is set to  "{@value=Karen flies to New York and she is happy about that.\n, @language=en}"
6. There is no `@language` attribute when input is text
7. It adds a metadata attribute to all annotations in the existing view
8. Annotations have no `label` property
9. Service metadata say  input cannot be LIF, but LIF input is allowed
10. Service metadata lists xml as input type
11. Service metadata specifies input is LIF, but will run on text input
12. When running on LIF input the `@context` attribute disappears
13. Service requirements say text input is not allowed, but will accept text
14. Service requirements say Tokens are required, but they are not
15. Annotation list contains Sentence type, but these are not in the view metadata

16. Service requires text or json#lapps, but accepts only text input
17. Services creates LIF output but discriminator is lapps (as specified in metadata)

Here is the list of types used in the view metadata:

- stanford
- tokenizer:stanford
- gate
- tokenizer:opennlp
- tokenizer:lingpipe-indo-european-tokenizer
- tokenizer:dkpro_opennlp
- tokenizer:dkpro_stanford


## Rounding up the Services

There are nine tokenizers used at the regular galaxy server and the jetstream server (as listed in https://github.com/lappsgrid-incubator/GalaxyMods/blob/master/config/tool_conf.xml and the same file in the develop branch):

```
stanford/vassar.tokenizer_2.0.0.xml
stanford/vassar.tokenizer_2.1.0.xml
  common/invoke_vassar.lsd stanford.tokenizer_2.0.0
  common/invoke_vassar.lsd stanford.tokenizer_2.1.0-SNAPSHOT

stanford/brandeis.tokenizer.xml
  common/invoke_brandeis.lsd stanfordnlp.tokenizer_2.0.4

gate/gate.tokenizer_2.2.0.xml
gate/gate.tokenizer_2.3.0.xml         
  gate/invoke.lsd gate.tokenizer_2.2.0
  gate/invoke.lsd gate.tokenizer_2.3.0

opennlp/opennlp.tokenizer.xml
  common/invoke_brandeis.lsd opennlp.tokenizer_2.0.3

lingpipe/vassar.tokenizer_1.0.0.xml
  lingpipe/invoke.lsd LingpipeTokenizer

dkpro/dkpro.stanford.tokenizer.xml
dkpro/dkpro.opennlp.tokenizer.xml
  common/invoke_brandeis.lsd uima.dkpro.stanfordnlp.tokenizer_0.0.1
  common/invoke_brandeis.lsd uima.dkpro.opennlp.tokenizer_0.0.1
```

## Service Metadata

Service metadata from http://api.lappsgrid.org/:

service                                   | requires            | produces
---                                       | ---                 | ---
v stanford.tokenizer_2.0.0                | json#lif            | json#lif, Token
v stanford.tokenizer_2.1.0.SNAPSHOT       | text, json#lif      | json#lif, Token
b stanfordnlp.tokenizer_2.0.4             | text, json#lif      | json#lif, Token
v gate.tokenizer_2.2.0                    | text, xml, xml#gate | xml#gate, Token
v gate.tokenizer_2.3.0                    | text, xml, xml#gate | xml#gate, Token
b opennlp.tokenizer_2.0.3                 | json#lif            | json#lif, Token
LingpipeTokenizer                         | text, json#lif      | json#lif, Token
v lingpipe.splitter_1.1.1-SNAPSHOT        | text, jsonld#lapps  | jsonld#lapps, Token
b uima.dkpro.stanfordnlp.tokenizer_0.0.1	| json#lif, Token     | json#lif, Token
b uima.dkpro.opennlp.tokenizer_0.0.1	    | json#lif, Token     | json#lif, Token


## Service behavior analysis

The invokers and the service names were already listed above. We use the same input as for the splitters and create output in a similar way using a [bash script](tokenizers.sh). For output we again expect an error message or something like the following (showing only two tokens).

```
{
    "discriminator": "http://vocab.lappsgrid.org/ns/media/jsonld#lif",
    "payload": {
        "@context": "http://vocab.lappsgrid.org/context-1.0.0.jsonld",
        "metadata": { },
        "text": {
            "@value": "Karen flies to New York and she is happy about that.\n",
            "@language": "en"
        },
        "views": [
            {
                "id": "v1",
                "metadata": {
                    "contains": {
                        "http://vocab.lappsgrid.org/Token": {
                            "producer": "edu.brandeis.cs.lappsgrid.opennlp.Tokenizer:2.0.3",
                            "type": "tokenizer:opennlp"
                        }
                    }
                },
                "annotations": [
                    {
                        "id": "tok_0",
                        "start": 0,
                        "end": 5,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "label": "token",
                        "features": {
                            "word": "Karen"
                        }
                    },
                    {
                        "id": "tok_1",
                        "start": 6,
                        "end": 11,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "label": "token",
                        "features": {
                            "word": "flies"
                        }
                    }
                ]
            }
        ]
    }
}
```


Here are observations for all the tokenizers as of January 23rd 2018, service output is stored in the [output directory](output).

vassar stanford.tokenizer_2.0.0

- GATE input gives correct error message
- New view has no identifiers
- Service metadata specifies input is LIF, but will run on text input
- There is no `@language` attribute
- View metadata type is stanford
- When running on LIF input, the `@value` attribute is set to  "{@value=Karen flies to New York and she is happy about that.\n, @language=en}"
- Existing view in input is lost

vassar stanford.tokenizer_2.1.0.SNAPSHOT

- GATE input gives correct error message
- New view has no identifiers
- There is no `@language` attribute when input is text
- View metadata type is stanford
- It adds a metadata attribute to all annotations in the existing view

brandeis stanfordnlp.tokenizer_2.0.4

- GATE input gives correct error message
- New view has no identifiers
- View metadata type is tokenizer:stanford
- Annotations have no `label` property
- Existing layer in input is preserved

vassar gate.tokenizer_2.2.0

- New view has no identifiers
- There is no `@language` attribute
- View metadata type is gate
- Existing view in input is lost
- Service metadata say  input cannot be LIF, but LIF input is allowed
- Service metadata lists xml as input type

vassar gate.tokenizer_2.3.0

- Same observations as for version 2.2.0

brandeis opennlp.tokenizer_2.0.3

- GATE input gives correct error message
- New view has no identifiers
- View metadata type is tokenizer:opennlp
- Annotations have no `label` property
- Service metadata specifies input is LIF, but will run on text input
- Existing layer in input is preserved

LingpipeTokenizer

- GATE input gives correct error message
- New view has no identifiers
- View metadata type is tokenizer:lingpipe-indo-european-tokenizer
- When running on LIF input the `@context` attribute disappears
- There is no `@language` attribute when input is text

vassar lingpipe.splitter_1.1.1-SNAPSHOT

- Service requires text or json#lapps, but accepts only text input
- Services creates LIF output but discriminator is lapps (as specified in metadata)
- There is no `@language` attribute
- View metadata has type `tokenizer:lingpipe-indo-european-tokenizer`

brandeis uima.dkpro.stanfordnlp.tokenizer_0.0.1

- GATE input gives correct error message
- New view has no identifiers
- View metadata type is tokenizer:dkpro_opennlp
- Annotations have no `label` property
- Annotation list contains Sentence type, but these are not in the view metadata
- There is no `@language` attribute
- Existing view in input is lost
- Service requirements say text input is not allow, but will accept text
- Service requirements say Tokens are required, but they are not

brandeis uima.dkpro.opennlp.tokenizer_0.0.1

- Same observations as for the above, except
- View metadata type is tokenizer:dkpro_stanford
