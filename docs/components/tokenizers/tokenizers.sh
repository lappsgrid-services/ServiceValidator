#!/bin/bash

# Script to run various tokenizers.
#
# Usage
#
#  $ ./tokenizers.sh all|vassar|brandeis|gate|lingpipe


# This is where all the invocation scripts live that are used by the Galaxy
# XML wrappers.
TOOLS=/Users/marc/Documents/git/lapps/lappsgrid-incubator/GalaxyMods/tools
TOOLS=/Users/marc/Desktop/lapps/code/lappsgrid-incubator/GalaxyMods/tools

# Location of the LSD script.
LSD=/Users/marc/bin/lsd

# Base name of input files
INPUT=../input/karen-flies

# utilities used by script
source ../splitters/utils.sh


# Now run the services in groups. Groups are determined by what the invoker
# script is and the final result is put in the output directory. The name of the
# output file is as follows:
#
#   INVOKER-SERVICE-INFORMAT-ANNOTATIONS.OUTFORMAT
#
# where INVOKER refers to the way the script was invoked, INFORMAT refers to
# the input format and OUTFORMAT to the output format.


# Stanford tokenizers via the Vassar invoker

if [ $1 = vassar ] || [ $1 = all ];
then
    invoker=vassar
    services=( lingpipe.tokenizer_1.1.1-SNAPSHOT
	       stanford.tokenizer_2.0.0
	       stanford.tokenizer_2.1.0-SNAPSHOT )
    specs=( txt:nil lif:nil lif:ner gate:nil )
    run_regular_services
fi


# various splitters via the Brandeis invoker

if [ $1 = brandeis ] || [ $1 = all ];
then    
    invoker=brandeis
    services=( stanfordnlp.tokenizer_2.0.4
	       opennlp.tokenizer_2.0.3
	       uima.dkpro.stanfordnlp.tokenizer_0.0.1
	       uima.dkpro.opennlp.tokenizer_0.0.1 )
    specs=( txt:nil lif:nil lif:ner gate:nil )
    run_regular_services
fi


# GATE tokenizers via the gate invoker

if [ $1 = gate ] || [ $1 = all ];
then
    invoker=gate
    services=( gate.tokenizer_2.2.0
	       gate.tokenizer_2.3.0 )
    specs=( txt:nil lif:nil lif:ner gate:nil )
    run_gate_services
fi


# LingPipe splitter via the lingpipe invoker

if [ $1 = lingpipe ] || [ $1 = all ];
then
    invoker=lingpipe
    services=( LingpipeTokenizer )
    specs=( txt:nil lif:nil lif:ner gate:nil )
    run_lingpipe_services
fi


rm -f tmp*
