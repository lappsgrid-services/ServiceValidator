#!/bin/bash

# Script to run various chunkers.
#
# Usage
#
#  $ ./chunkers.sh gate


# This is where all the invocation scripts live that are used by the Galaxy
# XML wrappers.
TOOLS=/Users/marc/Documents/git/lapps/lappsgrid-incubator/GalaxyMods/tools
TOOLS=/Users/marc/Desktop/lapps/code/lappsgrid-incubator/GalaxyMods/tools

# Location of the LSD script.
LSD=/Users/marc/bin/lsd

# Base name of input files
INPUT=../input/karen-flies


# Now run the services in groups. Groups are determined by what the invoker
# script is and the final result is put in the output directory. The name of the
# output file is as follows:
#
#   INVOKER-SERVICE-INFORMAT-ANNOTATIONS.OUTFORMAT
#
# where INVOKER refers to the way the script was invoked, INFORMAT refers to
# the input format and OUTFORMAT to the output format.


# GATE chunkers via the gate invoker

if [ $1 = gate ] || [ $1 = all ];
then

    services=( gate.npchunker_2.2.0 gate.vpchunker_2.2.0
	       gate.npchunker_2.3.0 gate.vpchunker_2.3.0 )

    for service in "${services[@]}"
    do
	echo "gate/invoke.lsd $service"

	# Running on empty gate input, text input and LIF input with sentences and tokens

	echo "   $INPUT.gate"
	OUT=output/gate-$service-gate
	$LSD $TOOLS/gate/invoke.lsd $service $INPUT.gate $OUT.gate
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $OUT.gate true $OUT.lif

	echo "   $INPUT.txt"
	OUT=output/gate-$service-txt
	$LSD $TOOLS/gate/invoke.lsd $service $INPUT.txt $OUT.gate
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $OUT.gate true $OUT.lif

	echo "   $INPUT.tok.sent.lif"
	OUT=output/gate-$service-lif-tok-sent
	$LSD $TOOLS/gate/invoke.lsd $service $INPUT.tok.sent.lif $OUT.gate
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $OUT.gate true $OUT.lif

	# Now on GATE input with tokens, sentences and both

	echo "   $INPUT.tok.gate"
	OUT=output/gate-$service-gate-tok
	$LSD $TOOLS/gate/invoke.lsd $service $INPUT.tok.gate $OUT.gate
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $OUT.gate true $OUT.lif

	echo "   $INPUT.sent.gate"
	OUT=output/gate-$service-gate-sent
	$LSD $TOOLS/gate/invoke.lsd $service $INPUT.sent.gate $OUT.gate
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $OUT.gate true $OUT.lif

	echo "   $INPUT.tok.sent.gate"
	OUT=output/gate-$service-gate-tok-sent
	$LSD $TOOLS/gate/invoke.lsd $service $INPUT.tok.sent.gate $OUT.gate
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $OUT.gate true $OUT.lif

	echo "   $INPUT.tok.pos.sent.gate"
	OUT=output/gate-$service-gate-tok-pos-sent
	$LSD $TOOLS/gate/invoke.lsd $service $INPUT.tok.sent.gate $OUT.gate
	$LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $OUT.gate true $OUT.lif

    done
fi
