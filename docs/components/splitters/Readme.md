# Sentence Splitters

Consistency checking on all splitters used on http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org/. The first section below has the final observations on all splitters, subsequent sections go into the process followed to get there.


## Summary of Observations

The Galaxy servers listed above use nine unique sentence splitters on the Brandeis and Vassar nodes of the grid. We are looking at those services with the following requirements in mind:

- If a service says it requires one of several formats (for example `lif` or `text`), then it should work with those formats and fail gracefully with an error message (something like "Unsupported input type: gate"). We are assuming that when the discriminator says the payload is LIF that the payload actually is LIF, that is, we assume well-formed input.

- If a service says it requires a set of annotation types to be in the input (for example `Token` and `Sentence`), then all those types should be in the input, otherwise the service should fail gracefully with an error message. For now, we assuming that "available in the input" means that there is a view where the metadata says the view contains that annotation type. This may be extended to meaning that there actually are such annotations in the annotations list.

- If a service says it produces output in a certain format than it should do so.

- If a service says it produces certain kinds of annotations then it should do so.

- A service should always create well-formed output. Here, we are mostly concerned about LIF output.

- Services should not remove existing layers.

Below is a table with observations on all nine splitter services. See the "Running splitters using LSD" for slightly more verbose observations.

service                                         | requires    | produces  | other
---                                             | ---         | ---       | ---
vassar stanford.splitter_2.0.0                  | 1           | 2         | 3, 4, 5
vassar stanford.splitter_2.1.0-SNAPSHOT         | 6           | &check;   | 3, 5, 7
brandeis stanfordnlp.splitter_2.0.4             | &check;     | &check;   | 3, 8
vassar gate.splitter_2.2.0                      | 9, 10       | &check;   | 3, 11, 12
vassar gate.splitter_2.3.0                      | 9, 10       | &check;   | 3, 11, 12
brandeis opennlp.splitter_2.0.3                 | 13          | &check;   | 3, 8
vassar LingpipeSentenceSplitter                 | &check;     | &check;   | 3, 4, 14
brandeis uima.dkpro.stanfordnlp.splitter_0.0.1  | 15          | 16        | 3, 8, 11, 12
brandeis uima.dkpro.opennlp.splitter_0.0.1      | 15          | 16        | 3, 8, 11, 12

The columns are to be interpreted as follows:

The `requires` column indicates whether tool requirements from metadata match its behavior when given input whereas the `produces` column indicates whether what the tool produces matches what is specified in the metadata. Any other observations are in the `other` column. Check marks indicate all is well, the number refer to elements from the list below.

1. Format requirement has `json`, but not `json:lif`
2. Service metadata says the output includes `Token` annotations, but it does not
3. View created does not have an identifier
4. On LIF input it fails to preserve the `@context` attribute (but it does add it for text input)
5. With LIF input it will keep the `@language attribute`, but it won't add one on text input
6. Service metadata format requirement has `json`
7. It adds a `metadata` attribute to all annotations in the existing view
8. There is no `label` attribute in the annotations
9. Creates output for text and lif input even though metadata say that gate is required
10. Metadata requires Token input but none is needed nor enforced
11. There is no `@language` attribute
12. Existing layer in input is lost
13. Requirements say that text input should not be accepted, but it is
14. View metadata has type `tokenizer:lingpipe-indo-european-tokenizer`, which is weird given this is a splitter
15. Service metadata say input needs Sentence annotations
16. View metadata says the view contains Token#pos

To finish this summary, here is a list of types used in the view metadata:

- sentence:stanford
- splitter:stanford
- gate
- splitter:opennlp
- splitter:dkpro_stanford
- splitter:dkpro_opennlp

We need a bit more of a theory on how to use the type attribute. An initial proposal:

- These types are all discriminators, possibly under `ns/tools` or `ns/components`.
- The splitter page would have all kinds of tools that can be described on that page.



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


## Declared Metadata

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

Let's make this more compact (the `b` or `v` in the service column in the table below indicates whether the service runs on the Brandeis or Vassar server):

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



## Running the Splitters

### Running splitters using Galaxy

Say we run services from Galaxy and we first feed it the [karen-flies.txt](input/karen-flies.txt) sample file, which has the following content:

```
{
    "discriminator": "http://vocab.lappsgrid.org/ns/media/text",
    "payload": "Karen flies to New York and she is happy about that.\n"
}
```

Uploading this file to the Galaxy server allows you to specify what kind of file this is or you can let Galaxy make a guess. For Galaxy a number of file types have been defined with mappings to extensions: Text (.txt), LifText (.liftxt), Gate (.gate), Lif (.lif), Lapps (.lapps) and LappsJson (.json). Note that these types are separate from the input type in the discriminator.

When uploading the sample file Galaxy guesses LifText, which is unfortunate because with that guess most of the splitters above will not run. This is because of the Galaxy XML wrappers for the services which list a number of parameters that specify the input type (note the 'v' and 'b' prefixes, referring to the Vassar and Brandeis servers respectively):

service                                 | input specification in XML wrapper
--------                                | ---
v stanford.splitter_2.0.0               | format="lif" label="input" name="input" type="data"
v stanford.splitter_2.1.0-SNAPSHOT      | format="lif" label="input" name="input" type="data"
b stanfordnlp.splitter_2.0.4            | format="lif" label="input" name="input" type="data"
v gate.splitter_2.2.0                   | format='gate' name='input' type='data'
v gate.splitter_2.3.0                   | format='gate' name='input' type='data'
b opennlp.splitter_2.0.3                | format="lif" label="input" name="input" type="data"
v LingpipeSentenceSplitter              | format="lif" label="input" name="input" type="data"
b uima.dkpro.stanfordnlp.splitter_0.0.1 | format="text" label="input" name="input" type="data"
b uima.dkpro.opennlp.splitter_0.0.1     | format="text" label="input" name="input" type="data"

Only the two DKPro splitters are willing to work with input of type LifText, the other all require Lif or Gate (although the Gate splitter will work with Lif because there is an automatic converter). If we want to do these tests in Galaxy then we need to upload the sample file as a Lif file.

Note: the format listed appears to be either the extension or the type in lower case. If it is the extension then the format for the DKPro splitters may be wrong because there is no text extension.

In any case, it is better (and faster) to separate using Galaxy from testing the behavior of services. Below we use command line invocation using LSD.


### Running splitters using LSD

We run the splitters exactly the same way as specified in the GalaxyMod XML wrappers. More specifically we have the following invocation (all the be run from the `tools` directory):

```
lsd common/invoke_vassar.lsd stanford.splitter_2.0.0 INPUT OUTPUT
lsd common/invoke_vassar.lsd stanford.splitter_2.1.0-SNAPSHOT INPUT OUTPUT
lsd common/invoke_brandeis.lsd stanfordnlp.splitter_2.0.4 INPUT OUTPUT
lsd gate/invoke.lsd gate.splitter_2.2.0 INPUT OUTPUT
lsd gate/invoke.lsd gate.splitter_2.3.0 INPUT OUTPUT
lsd common/invoke_brandeis.lsd opennlp.splitter_2.0.3 INPUT OUTPUT
lsd lingpipe/invoke.lsd LingpipeSentenceSplitter INPUT OUTPUT
lsd common/invoke_brandeis.lsd uima.dkpro.stanfordnlp.splitter_0.0.1 INPUT OUTPUT
lsd common/invoke_brandeis.lsd uima.dkpro.opennlp.splitter_0.0.1 INPUT OUTPUT
```

In addition, we may run the JSON pretty print from `/common/pretty_print.lsd` and converters via `converters/invoke.lsd` as needed (for the GATE services in the case of sentence splitters). We run the services on four different versions of the input file:

- [karen-flies.txt](input/karen-flies.txt)
- [karen-flies.lif](input/karen-flies.lif)
- [karen-flies.gate](input/karen-flies.gate)
- [karen-flies.ner.lif](input/karen-flies.ner.lif)

The idea of the first three is that the service should either accept the input or fail gracefully with an error message. For the fourth, we use an unrelated annotation and test whether the service keeps it.

The invocation used are all gathered in a [bash script](splitters.sh).

Here is the kind of LIF output we expect if a service got the right kind of input:

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

And this is what we expect when the input is not as required:

```
{
    "discriminator": "http://vocab.lappsgrid.org/ns/error",
    "payload": "Unsupported input type: http://vocab.lappsgrid.org/ns/media/xml#gate"
}
```


### Service behavior analysis

Here are observations for all the splitters as of January 23rd 2018. All output is in [output](output).

vassar stanford.splitter_2.0.0

- New view has no identifier
- Format requirement has `json`, but not `json:lif`
- Service mmetadata says the output includes `Token` annotations, but it does not
- View metadata has type `sentence:stanford`
- Fails correctly on GATE input and accepts both text and LIF
- Preserves the existing layer correctly
- On LIF input it fails to preserve the `@context` attribute (but it does add it for text input)
- With LIF input it will keep the `@language attribute`, but it won't add one on text input

vassar stanford.splitter_2.1.0-SNAPSHOT

- New view has no identifier
- Service metadata format requirement has `json`
- View metadata has type `sentence:stanford`
- Fails correctly on GATE input and accepts both text and LIF
- With LIF input it will keep the `@language` attribute, but it won't add one on text input
- It adds a `metadata` attribute to all annotations in the existing view

brandeis stanfordnlp.splitter_2.0.4

- New view has no identifier
- View metadata has type `splitter:stanford`
- Fails correctly on GATE input and accepts both text and LIF
- There is no `label` attribute in the annotations
- Preserves the existing layer correctly

vassar gate.splitter_2.2.0

- New view has no identifier
- View metadata has type `gate`
- Creates output for all three input types even though metadata say that gate is required
- Output is the same for all of them
- Metadata requires Token input but none is needed nor enforced
- There is no `@language` attribute
- Existing layer in input is lost

vassar gate.splitter_2.3.0

- No differences observed compared to version 2.2.0

brandeis opennlp.splitter_2.0.3

- New view has no identifier
- View metadata has type `splitter:opennlp`
- Fails correctly on GATE input and accepts both text and LIF
- Requirements say that text input should not be accepted
- There is no `label` attribute in the annotations
- Preserves the existing layer correctly

vassar LingpipeSentenceSplitter

- New view has no identifier
- View metadata has type `tokenizer:lingpipe-indo-european-tokenizer`
- View metadata type is weird given this is a splitter
- Fails correctly on GATE input and accepts both text and LIF
- On LIF input it fails to preserve the `@context` attribute (but it does add it for text input)
- Preserves the existing layer correctly

brandeis uima.dkpro.stanfordnlp.splitter_0.0.1

- New view has no identifier
- View metadata has type `splitter:dkpro_stanford`
- There is no `@language` attribute
- Existing layer is not preserved
- There is no `label` attribute in the annotations
- Service metadata say input needs Sentence annotations
- View metadata says the view contains Token#pos

brandeis uima.dkpro.opennlp.splitter_0.0.1

- Same observations as for the above, except the following
- View metadata has type `splitter:dkpro_opennlp`
