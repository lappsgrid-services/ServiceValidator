

### Tagger

Two Stanford taggers:

1. Stanford Tagger v2.0.0 - Stanford Tagger (Vassar)
2. Stanford POSTagger - Stanford Tagger (Brandeis)

They both add a third view. Here is the content of the metadata property of the new view from the Vassar version:

```
"contains": {
    "http://vocab.lappsgrid.org/Token#pos": {
        "producer": "org.anc.lapps.stanford.Tagger:2.0.0",
        "type": "tagset:penn"
    }
}
```

And for the Brandeis version:

```
"contains": {
    "http://vocab.lappsgrid.org/Token#pos": {
        "producer": "edu.brandeis.cs.lappsgrid.stanford.corenlp.POSTagger:2.0.4",
        "type": "tagger:stanford"
    }
}
```

Here is the token annotation from the view added by the Vassar version.

```
{
    "id": "tok0",
    "start": 0,
    "end": 5,
    "@type": "http://vocab.lappsgrid.org/Token",
    "label": "Token",
    "features": {
        "pos": "NNP",
        "word": "Karen" }
}
```

And here is the same for the Brandeis version.

```
{
    "id": "tk_0_0",
    "start": 0,
    "end": 5,
    "@type": "http://vocab.lappsgrid.org/Token#pos",
    "features": {
        "pos": "NNP",
        "word": "Karen" }
}
```

Obviously there is a lot of redundancy since the the part-of-speech view copies or recreates most of the tokens from the tokens view. There is no guarantee that the tokens are the same. It is perhaps best to not have a pipeline of sentence splitter, tokenizer and tagger, and leave out the tokenizer, but for now I keep them all.

Other notes:

- For the type the Vassar version focuses on the tag set while the Brandeis version focuses on the module. The former should be added in a `posTagSet` property.
- Note again the `label` property.
- The `@type` property on the Brandeis version is wrong.

For the example I am picking the Vassar version because it has the right types and because it is nice to also have the label property be part of the mix.
