# Taggers


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

stanford/vassar.tagger_2.1.0.xml

stanford/brandeis.postagger.xml

gate/gate.tagger_2.2.0.xml

gate/gate.tagger_2.3.0.xml

opennlp/opennlp.postagger.xml

lingpipe/vassar.tagger_1.0.0.xml

dkpro/dkpro.stanford.postagger.xml

dkpro/dkpro.opennlp.postagger.xml


---

<!--

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
- The Vassar tagger loses the @context attribute

-->
