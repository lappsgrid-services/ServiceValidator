# Coreference Tools

Consistency checking on all coreference tools used on http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org/. This was done in a way similar to the [splitters](../splitters) but reported on in a less verbose way.

## Summary of Observations

Below is a table with observations on two coreference services. See further down for more verbose observations.

service                              | requires  | produces  | other
---                                  | ---       | ---       | ---
b stanfordnlp.coref_2.0.4            | &check;   | &check;   | 1, 2
b uima.dkpro.stanfordnlp.coref_0.0.1 | 3, 7, 8   | &check;   | 1, 2, 4, 5, 6

The `requires` column indicates whether tool requirements from metadata match its behavior when given input whereas the `produces` column indicates whether what the tool produces matches what is specified in the metadata. Any other observations are in the `other` column. Check marks indicate all is well, the numbers refer to elements from the list below.

1. New view has no identifier
2. Annotations have no `label` attribute
3. Service requires LIF but will accept text
4. Missing `@language` attribute
5. Existing view in input is not in output
6. View metadata has a typo (Corefernce)
7. Service requires Coreference, which makes no sense
8. Service produces Coreference, but view also has Token and Markable

Types used in view metadata:

- tokenizer:stanford
- coreference:stanford
- markable:stanford
- coref:dkpro_stanford


## Rounding up the Services

Taken from https://github.com/lappsgrid-incubator/GalaxyMods/blob/master/config/tool_conf.xml and its sister in the develop branch, with the invokers lifted from the Galaxy XML wrappers.

```
stanford/brandeis.coref.xml
  common/invoke_brandeis.lsd stanfordnlp.coref_2.0.4

dkpro/dkpro.stanford.coref.xml
  common/invoke_brandeis.lsd uima.dkpro.stanfordnlp.coref_0.0.1
```

## Service Metadata

Service metadata from http://api.lappsgrid.org/:

service                              | requires                | produces
---                                  | ---                     | ---
b stanfordnlp.coref_2.0.4            | text, jsonld#lif        | jsonld#lif, Coreference, Token, Markable
b uima.dkpro.stanfordnlp.coref_0.0.1 | jsonld#lif, Coreference | jsonld#lif, Coreference


## Service behavior analysis

We again create output using a [bash script](coref.sh), see the script for notes on what input was used.

Here are observations for the tested services as of January 25th 2018, service output is stored in the [output directory](output).

brandeis stanfordnlp.coref_2.0.4

- New view has no identifier
- Annotations have no `label` attribute

brandeis uima.dkpro.stanfordnlp.coref_0.0.1

- Service requires LIF but will accept text
- Service requires Coreference, which makes no sense
- New view has no identifier
- Annotations have no `label` attribute
- Missing `@language` attribute
- Existing view in input is not in output
- View metadata has a typo (Corefernce)
