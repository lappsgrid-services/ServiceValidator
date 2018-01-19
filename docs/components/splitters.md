# Sentence Splitters

Consistency checking on all splitters used on  http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org/.


## Rounding up the Splitters

Below are the splitters we have on http://galaxy.lappsgrid.org/. The names used are the names as displayed in the `Sentence Splitters` tools submenu taking both the name and the description.

- Stanford SentenceSplitter v2.0.0 - Stanford Sentence Splitter (Vassar)
- Stanford Splitter - Stanford Splitter (Brandeis)
- GATE SentenceSplitter v2.2.0 - GATE SentenceSplitter
- OpenNLP Splitter - OpenNLP Splitter
- Lingpipe SentenceSplitter v1.0.0 - (Vassar)
- DKPro Core Stanford Splitter v1.7.0 - DKPro Core Stanford Splitter
- DKPro Core OpenNLP Splitter v1.7.0 - DKPro Core OpenNLP Splitter

Here's the relevant tool config section:

```
<section id="splitters" name="Sentence Splitters">
	<tool file="stanford/vassar.splitter_2.0.0.xml"/>
	<tool file='stanford/brandeis.splitter.xml' />
	<tool file="gate/gate.splitter_2.2.0.xml"/>
	<tool file='opennlp/opennlp.splitter.xml' />
	<tool file="lingpipe/vassar.splitter_1.0.0.xml"/>
	<tool file='dkpro/dkpro.stanford.splitter.xml'/>
	<tool file='dkpro/dkpro.opennlp.splitter.xml'/>
</section>
```

And here is a table with the service name, the server it lives on and the service identifier used by the invoker:

service name                          | server   | service identifier
--------------------------------------|----------|-------------------
Stanford SentenceSplitter v2.0.0      | vassar   | stanford.splitter_2.0.0
Stanford Splitter                     | brandeis | stanfordnlp.splitter_2.0.4
GATE SentenceSplitter v2.2.0          | vassar   | gate.splitter_2.2.0
OpenNLP Splitter                      | brandeis | opennlp.splitter_2.0.3
Lingpipe SentenceSplitter v1.0.0      | vassar   | LingpipeSentenceSplitter
DKPro Core Stanford Splitter v1.7.0   | brandeis | uima.dkpro.stanfordnlp.splitter_0.0.1
DKPro Core OpenNLP Splitter v1.7.0    | brandeis | uima.dkpro.opennlp.splitter_0.0.1

Note that the Lingpipe splitter does not appear to have a version number on it, but the invocation for this one is slightly different in that its invoker uses a path that includes the version:

```
String url = "http://grid.anc.org:9080/LingpipeServices/1.0.0-SNAPSHOT/services/$service"
```

Here is the same for the Jetstream Galaxy server at http://jetstream.lappsgrid.org/, which uses the develop branch in https://github.com/lappsgrid-incubator/GalaxyMods instead of the master branch.

First the menu items:

- Stanford SentenceSplitter v2.0.0 - Stanford Sentence Splitter (Vassar)
- Stanford Splitter - Stanford Splitter (Brandeis)
- GATE SentenceSplitter v2.2.0 GATE SentenceSplitter
- OpenNLP Splitter - OpenNLP Splitter
- Lingpipe SentenceSplitter v1.0.0 - (Vassar)
- DKPro Core Stanford Splitter v1.7.0 - DKPro Core Stanford Splitter
- DKPro Core OpenNLP Splitter v1.7.0 - DKPro Core OpenNLP Splitter

Then the tool config section:

```
<section id="splitters" name="Sentence Splitters">
  <tool file="stanford/vassar.splitter_2.1.0.xml"/>
  <tool file='stanford/brandeis.splitter.xml' />
  <tool file="gate/gate.splitter_2.3.0.xml"/>
  <tool file='opennlp/opennlp.splitter.xml' />
  <tool file="lingpipe/vassar.splitter_1.0.0.xml"/>
  <tool file='dkpro/dkpro.stanford.splitter.xml'/>
  <tool file='dkpro/dkpro.opennlp.splitter.xml'/>
</section>
```

Note how the item in the tool menu for the Vassar sentence splitter is out of sync with the tool config and that the same is the case for the gate splitter. No idea what the problem is, in both cases the xml file has the right version in its name.

And here is the table. Services marked with ** do not need to be checked because we will check those from http://galaxy.lappsgrid.org/.

service name                          | server   | service identifier
--------------------------------------|----------|-------------------
Stanford SentenceSplitter v2.0.0      | vassar   | stanford.splitter_2.1.0-SNAPSHOT
Stanford Splitter                     | brandeis | stanfordnlp.splitter_2.0.4 **
GATE SentenceSplitter v2.2.0          | vassar   | gate.splitter_2.3.0
OpenNLP Splitter                      | brandeis | opennlp.splitter_2.0.3 **
Lingpipe SentenceSplitter v1.0.0      | vassar   | LingpipeSentenceSplitter **
DKPro Core Stanford Splitter v1.7.0   | brandeis | uima.dkpro.stanfordnlp.splitter_0.0.1 **
DKPro Core OpenNLP Splitter v1.7.0    | brandeis | uima.dkpro.opennlp.splitter_0.0.1 **

Lingpipe has the same version in the path as the one on http://galaxy.lappsgrid.org/

So to be tested are:

service name                                 | server   | service identifier
---------------------------------------------|----------|-------------------
Stanford SentenceSplitter v2.0.0             | vassar   | stanford.splitter_2.0.0
Stanford SentenceSplitter v2.0.0 (jetstream) | vassar   | stanford.splitter_2.1.0-SNAPSHOT
Stanford Splitter                            | brandeis | stanfordnlp.splitter_2.0.4
GATE SentenceSplitter v2.2.0                 | vassar   | gate.splitter_2.2.0
GATE SentenceSplitter v2.2.0 (jetstream)     | vassar   | gate.splitter_2.3.0
OpenNLP Splitter                             | brandeis | opennlp.splitter_2.0.3
Lingpipe SentenceSplitter v1.0.0             | vassar   | LingpipeSentenceSplitter
DKPro Core Stanford Splitter v1.7.0          | brandeis | uima.dkpro.stanfordnlp.splitter_0.0.1
DKPro Core OpenNLP Splitter v1.7.0           | brandeis | uima.dkpro.opennlp.splitter_0.0.1


# Declared Metadata

Using

- http://api.lappsgrid.org/services/brandeis
- http://api.lappsgrid.org/services/vassar

Declared metadata are as follows:

Stanford SentenceSplitter v2.0.0 - vassar - stanford.splitter_2.0.0

```
http://api.lappsgrid.org/metadata?id=anc:stanford.splitter_2.0.0

Requires
- http://vocab.lappsgrid.org/ns/media/text
- http://vocab.lappsgrid.org/ns/media/json
- jsonld

Produces
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence
- http://vocab.lappsgrid.org/Token
```

Stanford SentenceSplitter v2.0.0 (jetstream) - vassar - stanford.splitter_2.1.0-SNAPSHOT

```
http://api.lappsgrid.org/metadata?id=anc:stanford.splitter_2.1.0-SNAPSHOT

Requires
- http://vocab.lappsgrid.org/ns/media/text
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/ns/media/json

Produces
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence
```

Stanford Splitter - brandeis - stanfordnlp.splitter_2.0.4

```
http://api.lappsgrid.org/metadata?id=brandeis_eldrad_grid_1:stanfordnlp.splitter_2.0.4

Requires
- http://vocab.lappsgrid.org/ns/media/text
- http://vocab.lappsgrid.org/ns/media/jsonld#lif

Produces
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence
```

GATE SentenceSplitter v2.2.0 - vassar - gate.splitter_2.2.0

```
http://api.lappsgrid.org/metadata?id=anc:gate.splitter_2.2.0

Requires
- http://vocab.lappsgrid.org/ns/media/xml#gate
- http://vocab.lappsgrid.org/Token

Produces
- http://vocab.lappsgrid.org/ns/media/xml#gate
- http://vocab.lappsgrid.org/Sentence
```

GATE SentenceSplitter v2.2.0 (jetstream) - vassar - gate.splitter_2.3.0

```
http://api.lappsgrid.org/metadata?id=anc:gate.splitter_2.3.0

Requires
- http://vocab.lappsgrid.org/ns/media/xml#gate
- http://vocab.lappsgrid.org/Token

Produces
- http://vocab.lappsgrid.org/ns/media/xml#gate
- http://vocab.lappsgrid.org/Sentence
```

OpenNLP Splitter - brandeis - opennlp.splitter_2.0.3

```
http://api.lappsgrid.org/metadata?id=brandeis_eldrad_grid_1:opennlp.splitter_2.0.3

Requires
- http://vocab.lappsgrid.org/ns/media/jsonld#lif

Produces
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence
```

Lingpipe SentenceSplitter v1.0.0 - vassar - LingpipeSentenceSplitter

```
Note that the following is weird because the URL says lingpipe.splitter_1.1.0 instead
of LingpipeSentenceSplitter.

http://api.lappsgrid.org/metadata?id=anc:lingpipe.splitter_1.1.0
Requires
- http://vocab.lappsgrid.org/ns/media/text
- http://vocab.lappsgrid.org/ns/media/jsonld#lif

Produces
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
-	http://vocab.lappsgrid.org/Sentence
```

DKPro Core Stanford Splitter v1.7.0 - brandeis - uima.dkpro.stanfordnlp.splitter_0.0.1

```
http://api.lappsgrid.org/metadata?id=brandeis_eldrad_grid_1:uima.dkpro.stanfordnlp.splitter_0.0.1

Requires
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence

Produces
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence
```

DKPro Core OpenNLP Splitter v1.7.0 - brandeis - uima.dkpro.opennlp.splitter_0.0.1

```
http://api.lappsgrid.org/metadata?id=brandeis_eldrad_grid_1:gate.opennlp.splitter_0.0.1

Requires
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence

Produces
- http://vocab.lappsgrid.org/ns/media/jsonld#lif
- http://vocab.lappsgrid.org/Sentence
```

Let's make this more compact:

service                                 | requires                | produces
--------                                |----------               |---------
v stanford.splitter_2.0.0               | text, json              | jsonld#lif, Sentence, Token
v stanford.splitter_2.1.0-SNAPSHOT      | text, jsonld#lif, json  | jsonld#lif, Sentence
b stanfordnlp.splitter_2.0.4            | text, jsonld#lif        | jsonld#lif, Sentence
v gate.splitter_2.2.0                   | xml#gate, Token         | xml#gate, Sentence
v gate.splitter_2.3.0                   | xml#gate, Token         | xml#gate, Sentence
b opennlp.splitter_2.0.3                | jsonld#lif              | jsonld#lif, Sentence
v LingpipeSentenceSplitter              | text, jsonld#lif        | jsonld#lif, Sentence
b uima.dkpro.stanfordnlp.splitter_0.0.1 | jsonld#lif, Sentence    | jsonld#lif, Sentence
b uima.dkpro.opennlp.splitter_0.0.1     | jsonld#lif, Sentence    | jsonld#lif, Sentence

# Running the Splitters

First we feed it the [karen-flies.txt](karen-flies.txt) sample file, which has the following content:

```
{
    "discriminator": "http://vocab.lappsgrid.org/ns/media/text",
    "payload": "Karen flies to New York and she is happy about that.\n"
}
```




First tried these

1. Stanford SentenceSplitter v2.0.0 - Stanford Sentence Splitter (Vassar)
2. Stanford Splitter - Stanford Splitter (Brandeis)

Output of the one at Brandeis:

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
                "metadata": {
                    "contains": {
                        "http://vocab.lappsgrid.org/Sentence": {
                            "producer": "edu.brandeis.cs.lappsgrid.stanford.corenlp.Splitter:2.0.4",
                            "type": "splitter:stanford"
                        }
                    }
                },
                "annotations": [
                    {
                        "id": "s_0",
                        "start": 0,
                        "end": 52,
                        "@type": "http://vocab.lappsgrid.org/Sentence",
                        "features": {
                            "sentence": "Karen flies to New York and she is happy about that."
                        }
                    }
                ]
            }
        ]
    }
}
```

Output of the one at Vassar:

```
{
    "discriminator": "http://vocab.lappsgrid.org/ns/media/jsonld#lif",
    "payload": {
        "@context": "http://vocab.lappsgrid.org/context-1.0.0.jsonld",
        "metadata": { },
        "text": {
            "@value": "Karen flies to New York and she is happy about that.\n"
        },
        "views": [
            {
                "metadata": {
                    "contains": {
                        "http://vocab.lappsgrid.org/Sentence": {
                            "producer": "org.anc.lapps.stanford.SentenceSplitter:2.0.0",
                            "type": "sentence:stanford"
                        }
                    }
                },
                "annotations": [
                    {
                        "id": "s0",
                        "start": 0,
                        "end": 52,
                        "@type": "http://vocab.lappsgrid.org/Sentence",
                        "label": "Sentence"
                    }
                ]
            }
        ]
    }
}
```

In both cases the splitter accepts text input in the payload and creates a LIF object in the output payload.

The view lacks an identifier in both cases.

Differences:

1. Brandeis version adds the `@language` property to the text.
1. In the metadata the Brandeis version uses `splitter:stanford` for the type and the Vassar version uses `sentence:stanford`. This is part of a bigger issue with inconsistent use of the type property.
1. The Vassar version adds a `label` property to the annotation. This use is correct according to the current schema at http://vocab.lappsgrid.org/schema/lif-schema.json, but the `label` property is not defined in the vocabulary and probably not useful here. Also, we have discussed removing `type` and `label` from the top level and adding them to the feature dictionary instead if needed.
1. The Brandeis version adds a `sentence` feature to the feature dictionary of the annotation. Another optional feature that is not in the vocabulary.
