# Input Files for Service Tests

These files were created in a variety of ways, mostly manual. Some files were uploaded to LAPPS/Galaxy and then wrapped as data objects. Some were corrected versions of output of existing services. In most cases the gate versions of a file were created by running a converter on a LIF file:

```
$ lsd $GALAXY_MODS/tools/converters/invoke.lsd convert.json2gate_2.0.0 LIF_FILE true GATE_FILE
```
Notice the *true* parameter. It is not doing a lot here, but it seems to be there for parity with the gate2json converter where the that parameter would create pretty printed json.

The naming convention is as follows:

```
IDENTIFIER.ANNOTATIONS.FORMAT
```

The file name starts with some kind of identifier reflecting the content (for example `karen-flies`) and is followed by a list of annotations in the file. Annotations are typically abbreviated, so we would have `sent` instead of `Sentence`. The file extension indicates the format (`txt`, `lif` or `gate`).
