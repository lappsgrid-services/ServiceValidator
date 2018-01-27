"""rename.py

Some code to rename the output of previous runs and make the filenames conform
to the new naming scheme.

"""

import os

files = [f for f in os.listdir('output') if f[0] != '.']

services = (
    'stanford.tagger_2.0.0',
    'stanford.tagger_2.1.0-SNAPSHOT',
    'stanfordnlp.postagger_2.0.4',
    'opennlp.postagger_2.0.3',
    'uima.dkpro.stanfordnlp.postagger_0.0.1',
    'uima.dkpro.opennlp.postagger_0.0.1',
    'gate.tagger_2.2.0',
    'gate.tagger_2.3.0',
    'LingpipeTagger'
)


for f in files:
    for service in services:
        if f.startswith(service):
            idx = len(service)
            # split off the invoker after the service name
            (invoker, rest) = f[idx+1:].split('-', 1)
            # split off the informat and the outformat
            (informat, outformat) = rest.rsplit('.', 1)
            # flip the order in the informat if needed
            if informat.endswith('lif') and len(informat) > 3:
                rest = "lif-%s.%s" % (informat[:-4], outformat)
            # glue it all together again 
            newname = "%s-%s-%s" % (invoker, service, rest)
            print "output/%-60s ==>  output/%s" % (f, newname)
            os.system("git mv output/%s output/%s" % (f, newname))
