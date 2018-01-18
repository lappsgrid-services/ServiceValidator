### Sentence Splitter

Tried the following splitters from the Sentence Splitters tool section (the names used are the names as displayed in the Galaxy server at galaxy.lappsgrid.org):

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
