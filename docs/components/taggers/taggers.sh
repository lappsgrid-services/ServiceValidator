#!/bin/bash

# Script to run various taggers.
#
# Usage
#
#  $ ./taggers.sh all|vassar|brandeis|gate|lingpipe


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


# Stanford taggers via the Vassar invoker

if [ $1 = vassar ] || [ $1 = all ];
then
    invoker=vassar
    services=( lingpipe.tagger_1.1.1-SNAPSHOT
	       stanford.tagger_2.0.0
	       stanford.tagger_2.1.0-SNAPSHOT )
    specs=( txt:nil lif:nil lif:tok lif:ner gate:nil )
    run_regular_services
fi


# Stanford, OpenNLP and DKPro taggers via the Brandeis invoker

if [ $1 = brandeis ] || [ $1 = all ];
then
    invoker=brandeis
    services=( stanfordnlp.postagger_2.0.4
	       opennlp.postagger_2.0.3
	       uima.dkpro.stanfordnlp.postagger_0.0.1
	       uima.dkpro.opennlp.postagger_0.0.1 )
    specs=( txt:nil lif:nil lif:tok lif:ner gate:nil )
    run_regular_services
fi


# GATE taggers via the gate invoker

if [ $1 = gate ] || [ $1 = all ];
then
    invoker=gate
    services=( gate.tagger_2.2.0 gate.tagger_2.3.0 )
    specs=( txt:nil lif:nil lif:tok.sent gate:nil gate:tok gate:tok.sent )
    run_gate_services
fi


# LingPipe splitter via the lingpipe invoker

if [ $1 = lingpipe ] || [ $1 = all ];
then
    invoker=lingpipe
    services=( LingpipeTagger )
    specs=( lif:nil lif:tok lif:ner txt:nil gate:nil )
    run_lingpipe_services
fi


rm -f tmp*
