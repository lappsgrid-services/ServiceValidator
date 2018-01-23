#!/bin/bash

# Script to run various sentence splitters.
#
# Usage
#
#  $ ./splitters.sh all|vassar|brandeis|gate|lingpipe


# This is where all the invocation scripts live that are used by the Galaxy
# XML wrappers.
TOOLS=/Users/marc/Documents/git/lapps/lappsgrid-incubator/GalaxyMods/tools
TOOLS=/Users/marc/Desktop/lapps/code/lappsgrid-incubator/GalaxyMods/tools

# Location of the LSD script.
LSD=/Users/marc/bin/lsd

# Three kinds of input, reflecting the text, lif and gate discriminators.
TEXT_INPUT=input/karen-flies.txt
LIF_INPUT=input/karen-flies.lif
GATE_INPUT=input/karen-flies.gate


# Now run the services in groups. Groups are determined by what the invoker
# script is and the final result is put in the output directory. The name of the
# output file is as follows:
#
#   SERVICE-INVOKER-FORMAT.lif
#
# where INVOKER refers to the way the script was invoked and FORMAT refers to
# the input format.


# Stanford splitters via the Vassar invoker
# stanford.splitter_2.0.0 - text|json ==> lif, Sentence, Token
# stanford.splitter_2.1.0-SNAPSHOT - text|lif|json ==> lif, Sentence

if [ $1 = vassar ] || [ $1 = all ];
then
    for service in stanford.splitter_2.0.0 stanford.splitter_2.1.0-SNAPSHOT
    do
	echo "common/invoke_vassar.lsd $service"
	$LSD $TOOLS/common/invoke_vassar.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-vassar-lif.lif
	$LSD $TOOLS/common/invoke_vassar.lsd $service $TEXT_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-vassar-txt.lif
	$LSD $TOOLS/common/invoke_vassar.lsd $service $GATE_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-vassar-gate.lif
    done
fi

# various splitters via the Brandeis invoker

if [ $1 = brandeis ] || [ $1 = all ];
then
    for service in stanfordnlp.splitter_2.0.4 \
		       opennlp.splitter_2.0.3 \
		       uima.dkpro.stanfordnlp.splitter_0.0.1 \
		       uima.dkpro.opennlp.splitter_0.0.1
    do
	echo "common/invoke_brandeis.lsd $service"
	$LSD $TOOLS/common/invoke_brandeis.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-brandeis-lif.lif
	$LSD $TOOLS/common/invoke_brandeis.lsd $service $TEXT_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-brandeis-txt.lif
	$LSD $TOOLS/common/invoke_brandeis.lsd $service $GATE_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-brandeis-gate.lif
    done
fi


# GATE splitters via the gate invoker

# Note that the true parameter for convert.gate2json  makes the converter produce pretty json

if [ $1 = gate ] || [ $1 = all ];
then
    for service in gate.splitter_2.2.0 gate.splitter_2.3.0
    do
	echo "gate/invoke.lsd $service"
	$LSD $TOOLS/gate/invoke.lsd $service $GATE_INPUT tmp
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 tmp true output/$service-gate-gate.lif
	$LSD $TOOLS/gate/invoke.lsd $service $TEXT_INPUT tmp
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 tmp true output/$service-gate-txt.lif
	$LSD $TOOLS/gate/invoke.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 tmp true output/$service-gate-lif.lif
    done
fi


# LingPipe splitter via the lingpipe invoker

if [ $1 = lingpipe ] || [ $1 = all ];
then
    for service in LingpipeSentenceSplitter
    do
	echo "lingpipe/invoke.lsd $service"
	$LSD $TOOLS/lingpipe/invoke.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-lingpipe-lif.lif
	$LSD $TOOLS/lingpipe/invoke.lsd $service $TEXT_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-lingpipe-txt.lif
	$LSD $TOOLS/lingpipe/invoke.lsd $service $GATE_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-lingpipe-gate.lif
    done
fi


rm tmp*
