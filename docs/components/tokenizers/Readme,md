### Tokenizer

Stanford tokenizers

1. Stanford Tokenizer v2.0.0 - Stanford Tokenizer (Vassar)
2. Stanford Tokenizer - Stanford Tokenizer (Brandeis)

The first one creates a text property that looks as follows

```
"text": {
    "@value": "{@value=Karen flies to New York and she is happy about that.\n, @language=en}"
},
```

This tokenizer expects text input so we are not using it (as a result, it also throws away the sentence view). Some other observations on it:

- The view's meta data use "stanford" as the type, which is not sufficient.
- The annotations all have a `label` property property (set to "Token") and they also have a `word` property in the feature dictionary. The former may need to be changed.

The Brandeis version of the Stanford tokenizer creates the following output

```
{
    "discriminator": "http://vocab.lappsgrid.org/ns/media/jsonld#lif",
    "payload": {
        "@context": "http://vocab.lappsgrid.org/context-1.0.0.jsonld",
        "metadata": {

        },
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
            },
            {
                "metadata": {
                    "contains": {
                        "http://vocab.lappsgrid.org/Token": {
                            "producer": "edu.brandeis.cs.lappsgrid.stanford.corenlp.Tokenizer:2.0.4",
                            "type": "tokenizer:stanford"
                        }
                    }
                },
                "annotations": [
                    {
                        "id": "tk_0_0",
                        "start": 0,
                        "end": 5,
                        "@type": "http://vocab.lappsgrid.org/Token",
                        "features": {
                            "word": "Karen"
                        }
                    },
                ]
            }
        ]
    }
}
```

Note that here too there are no view identifiers.
