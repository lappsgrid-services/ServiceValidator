#!/bin/bash

# Script to run various tokenizers.
#
# Usage
#
#  $ ./tokenizers.sh all|vassar|brandeis|gate|lingpipe


# This is where all the invocation scripts live that are used by the Galaxy
# XML wrappers.
TOOLS=/Users/marc/Documents/git/lapps/lappsgrid-incubator/GalaxyMods/tools
#TOOLS=/Users/marc/Desktop/lapps/code/lappsgrid-incubator/GalaxyMods/tools

# Location of the LSD script.
LSD=/Users/marc/bin/lsd

# Three kinds of input, reflecting the text, lif and gate discriminators, and
# another for the lif discriminator but with an existing view in it. Borrowed
# from the splitters.
TEXT_INPUT=../splitters/input/karen-flies.txt
GATE_INPUT=../splitters/input/karen-flies.gate
LIF_INPUT=../splitters/input/karen-flies.lif
NER_INPUT=../splitters/input/karen-flies.ner.lif


# Now run the services in groups. Groups are determined by what the invoker
# script is and the final result is put in the output directory. The name of the
# output file is as follows:
#
#   SERVICE-INVOKER-FORMAT.lif
#
# where INVOKER refers to the way the script was invoked and FORMAT refers to
# the input format.


# Stanford tokenizers via the Vassar invoker

if [ $1 = vassar ] || [ $1 = all ];
then
    for service in stanford.tokenizer_2.0.0 stanford.tokenizer_2.1.0-SNAPSHOT
    do
	echo "common/invoke_vassar.lsd $service"
	$LSD $TOOLS/common/invoke_vassar.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-vassar-lif.lif
	$LSD $TOOLS/common/invoke_vassar.lsd $service $NER_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-vassar-ner-lif.lif
	$LSD $TOOLS/common/invoke_vassar.lsd $service $TEXT_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-vassar-txt.lif
	$LSD $TOOLS/common/invoke_vassar.lsd $service $GATE_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-vassar-gate.lif
    done
fi

# various splitters via the Brandeis invoker

if [ $1 = brandeis ] || [ $1 = all ];
then
    for service in stanfordnlp.tokenizer_2.0.4 \
		       opennlp.tokenizer_2.0.3 \
		       uima.dkpro.stanfordnlp.tokenizer_0.0.1 \
		       uima.dkpro.opennlp.tokenizer_0.0.1
    do
	echo "common/invoke_brandeis.lsd $service"
	$LSD $TOOLS/common/invoke_brandeis.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-brandeis-lif.lif
	$LSD $TOOLS/common/invoke_brandeis.lsd $service $NER_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-brandeis-ner-lif.lif
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
    for service in gate.tokenizer_2.2.0 gate.tokenizer_2.3.0
    do
	echo "gate/invoke.lsd $service"
	$LSD $TOOLS/gate/invoke.lsd $service $GATE_INPUT tmp
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 tmp true output/$service-gate-gate.lif
	$LSD $TOOLS/gate/invoke.lsd $service $TEXT_INPUT tmp
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 tmp true output/$service-gate-txt.lif
	$LSD $TOOLS/gate/invoke.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 tmp true output/$service-gate-lif.lif
	$LSD $TOOLS/gate/invoke.lsd $service $NER_INPUT tmp
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 tmp true output/$service-gate-ner-lif.lif
    done
fi


# LingPipe splitter via the lingpipe invoker

if [ $1 = lingpipe ] || [ $1 = all ];
then
    for service in LingpipeTokenizer
    do
	echo "lingpipe/invoke.lsd $service"
	$LSD $TOOLS/lingpipe/invoke.lsd $service $LIF_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-lingpipe-lif.lif
	$LSD $TOOLS/lingpipe/invoke.lsd $service $NER_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-lingpipe-ner-lif.lif
	$LSD $TOOLS/lingpipe/invoke.lsd $service $TEXT_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-lingpipe-txt.lif
	$LSD $TOOLS/lingpipe/invoke.lsd $service $GATE_INPUT tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp output/$service-lingpipe-gate.lif
    done
fi


rm tmp*
