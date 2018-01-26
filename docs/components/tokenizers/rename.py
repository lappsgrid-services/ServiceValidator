"""rename.py

Some code to rename the output of previous runs and make the filenames conform
to the new naming scheme.

"""

import os

files = [f for f in os.listdir('output') if f[0] != '.']

services = (
    'stanford.tokenizer_2.0.0',
    'stanford.tokenizer_2.1.0-SNAPSHOT',
    'stanfordnlp.tokenizer_2.0.4',
    'opennlp.tokenizer_2.0.3',
    'uima.dkpro.stanfordnlp.tokenizer_0.0.1',
    'uima.dkpro.opennlp.tokenizer_0.0.1',
    'gate.tokenizer_2.2.0',
    'gate.tokenizer_2.3.0',
    'LingpipeTokenizer'
)


def run(cmd):
    print cmd
    os.system(cmd)

    
for f in files:
    for service in services:
        if f.startswith(service):
            idx = len(service)
            (invoker, rest) = f[idx+1:].split('-', 1)
            #print "%s-%s-%s\n" % (invoker, service, rest)
            cmd = "mv output/%-60s output/%s-%s-%s" % (f, invoker, service, rest)
            cmd = "git mv output/%s output/%s-%s-%s" % (f, invoker, service, rest)
            run(cmd)


for old, new in (
        ('lingpipe-LingpipeTokenizer-ner-lif.lif',
         'lingpipe-LingpipeTokenizer-lif-ner.lif'),
        ('gate-gate.tokenizer_2.2.0-ner-lif.lif',
         'gate-gate.tokenizer_2.2.0-lif-ner.lif'),
        ('gate-gate.tokenizer_2.3.0-ner-lif.lif',
         'gate-gate.tokenizer_2.3.0-lif-ner.lif'),
        ('brandeis-opennlp.tokenizer_2.0.3-ner-lif.lif',
         'brandeis-opennlp.tokenizer_2.0.3-lif-ner.lif'),
        ('vassar-stanford.tokenizer_2.0.0-ner-lif.lif',
         'vassar-stanford.tokenizer_2.0.0-lif-ner.lif'),
        ('vassar-stanford.tokenizer_2.1.0-SNAPSHOT-ner-lif.lif',
         'vassar-stanford.tokenizer_2.1.0-SNAPSHOT-lif-ner.lif'),
        ('brandeis-stanfordnlp.tokenizer_2.0.4-ner-lif.lif',
         'brandeis-stanfordnlp.tokenizer_2.0.4-lif-ner.lif'),
        ('brandeis-uima.dkpro.opennlp.tokenizer_0.0.1-ner-lif.lif',
         'brandeis-uima.dkpro.opennlp.tokenizer_0.0.1-lif-ner.lif'),
        ('brandeis-uima.dkpro.stanfordnlp.tokenizer_0.0.1-ner-lif.lif',
         'brandeis-uima.dkpro.stanfordnlp.tokenizer_0.0.1-lif-ner.lif')):

    cmd = "mv output/%-60s output/%s" % (old, new)
    cmd = "git mv output/%s output/%s" % (old, new)
    run(cmd)
