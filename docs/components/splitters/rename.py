"""rename.py

Some code to rename the output of previous runs and make the filenames conform
the new naming scheme.

"""

import os

files = [f for f in os.listdir('output') if f[0] != '.']

services = (
    'stanford.splitter_2.0.0',
    'stanford.splitter_2.1.0-SNAPSHOT',
    'stanfordnlp.splitter_2.0.4',
    'opennlp.splitter_2.0.3',
    'uima.dkpro.stanfordnlp.splitter_0.0.1',
    'uima.dkpro.opennlp.splitter_0.0.1',
    'gate.splitter_2.2.0',
    'gate.splitter_2.3.0',
    'LingpipeSentenceSplitter'
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
            cmd = "git mv output/%s output/%s-%s-%s" % (f, invoker, service, rest)
            run(cmd)
            
for old, new in (
        ('brandeis-opennlp.splitter_2.0.3-ner-lif.lif',
         'brandeis-opennlp.splitter_2.0.3-lif-ner.lif'),
        ('brandeis-stanfordnlp.splitter_2.0.4-ner-lif.lif',
         'brandeis-stanfordnlp.splitter_2.0.4-lif-ner.lif'),
        ('brandeis-uima.dkpro.opennlp.splitter_0.0.1-ner-lif.lif',
         'brandeis-uima.dkpro.opennlp.splitter_0.0.1-lif-ner.lif'),
        ('brandeis-uima.dkpro.stanfordnlp.splitter_0.0.1-ner-lif.lif',
         'brandeis-uima.dkpro.stanfordnlp.splitter_0.0.1-lif-ner.lif'),
        ('gate-gate.splitter_2.2.0-ner-lif.lif',
         'gate-gate.splitter_2.2.0-lif-ner.lif'),
        ('gate-gate.splitter_2.3.0-ner-lif.lif',
         'gate-gate.splitter_2.3.0-lif-ner.lif'),
        ('lingpipe-LingpipeSentenceSplitter-ner-lif.lif',
         'lingpipe-LingpipeSentenceSplitter-lif-ner.lif'),
        ('vassar-stanford.splitter_2.0.0-ner-lif.lif',
         'vassar-stanford.splitter_2.0.0-lif-ner.lif'),
        ('vassar-stanford.splitter_2.1.0-SNAPSHOT-ner-lif.lif',
         'vassar-stanford.splitter_2.1.0-SNAPSHOT-lif-ner.lif')):
                 
    cmd = "git mv output/%s output/%s" % (old, new)
    run(cmd)
