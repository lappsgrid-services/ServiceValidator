#!/bin/bash

# Script to run various chunkers.
#
# Usage
#
#  $ ./entities.sh all|lingpipe|gate|dbpedia|brandeis|vassar|stanford


# settings and utilities used by script
source ../splitters/settings.sh
source ../splitters/utils.sh

# Named entity dictionary (used by one of the services)
DICTIONARY=dictionary.txt


# Now run the services in groups. Groups are determined by what the invoker
# script is and the final result is put in the output directory. The name of the
# output file is as follows:
#
#   INVOKER-SERVICE-INFORMAT-ANNOTATIONS.OUTFORMAT
#
# where INVOKER refers to the way the script was invoked, INFORMAT refers to
# the input format and OUTFORMAT to the output format.


# LingPipe NER

if [ $1 = lingpipe ] || [ $1 = all ]; then

    invoker=lingpipe
    
    # First the dictionary based NER, which is special because it requires a
    # dictionary to be handed in. Notice that the service is not handed to the
    # invoker because it is hard-coded in the invoker. It is still used by
    # parse_spec though.
    service=LingpipeDictionaryBasedNER
    echo "lingpipe/cmu.dictionaryNER.lsd"
    for spec in txt:nil gate:nil lif:nil lif:sent
    do
	parse_spec $spec
	echo "   $in"
	$LSD $TOOLS/lingpipe/cmu.dictionaryNER.lsd $DICTIONARY $in tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp $out.lif
    done
    
    # And now the regular lingpipe NER
    service=LingpipeNER
    echo "lingpipe/invoke.lsd $service"
    for spec in txt:nil gate:nil lif:nil lif:sent
    do
	parse_spec $spec
	echo "   $in"
	$LSD $TOOLS/lingpipe/invoke.lsd $service $in tmp
	$LSD $TOOLS/common/pretty_print.lsd tmp $out.lif
    done
    
fi


# DBpedia NER

if [ $1 = dbpedia ] || [ $1 = all ]; then

    invoker=dbpedia
    service=DBpediaSpotlightAnnotator
    echo "lingpipe/cmu.dictionaryNER.lsd"
    for spec in txt:nil gate:nil lif:nil lif:sent
    do
	parse_spec $spec
	echo "   $in"
	$LSD $TOOLS/dbpedia/spotlight.lsd $in tmp-$spec
	$LSD $TOOLS/common/pretty_print.lsd tmp-$spec $out.lif
    done

fi


# GATE NER

if [ $1 = gate ] || [ $1 = all ]; 
then
    invoker=gate
    services=( gate.ner_2.2.0 gate.ner_2.3.0 )
    specs=( txt:nil lif:nil lif:tok.sent gate:nil gate:tok gate:tok.sent )
    run_gate_services
fi


# Stanford and DKPRO NER via Brandeis server

if [ $1 = brandeis ] || [ $1 = all ]; 
then
    invoker=brandeis
    services=( stanfordnlp.namedentityrecognizer_2.0.4
	       opennlp.namedentityrecognizer_2.0.3
	       uima.dkpro.stanfordnlp.namedentityrecognizer_0.0.1
	       uima.dkpro.opennlp.namedentityrecognizer_0.0.1 )
    specs=( txt:nil lif:nil lif:tok gate:nil )
    run_regular_services
fi


# Standford NER via Vassar

if [ $1 = vassar ] || [ $1 = all ];
then
    invoker=vassar
    services=( stanford.ner_2.0.0 stanford.ner_2.1.0-SNAPSHOT )
    specs=( txt:nil lif:nil lif:tok lif:tok.pos.sent gate:nil )
    run_regular_services
fi


# Stanford Selectable NER via stanford

if [ $1 = stanford ] || [ $1 = all ]; then

    service=SelectableNamedEntityRecognizer
    invoker=stanford
    model=english.all.3class.distsim.crf.ser.gz
    
    echo "stanford/invoke_ner2.lsd $service"
    for spec in txt:nil lif:nil lif:tok lif:tok.pos.sent gate:nil
    do
	parse_spec $spec
	echo "   $in"
	$LSD $TOOLS/stanford/invoke_ner2.lsd $model $in tmp-$spec
	$LSD $TOOLS/common/pretty_print.lsd tmp-$spec $out.lif
    done

fi


rm -f tmp-*
