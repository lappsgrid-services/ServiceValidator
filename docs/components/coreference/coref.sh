#!/bin/bash

# Script to run various coreference tools.
#
# Usage
#
#  $ ./coref.sh brandeis


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


parse_spec () {
    # Function that allows you to write more compact invocations. It takes a
    # specification like txt:nil, lif:sent or lif:tok.pos where we have a format
    # and a list of types and then use this to automatically generate the names
    # of the input and output files. The specification characterizes the input
    # so for the output file an extension will still need to be added by the
    # calling code.
    format_and_types=(${1//:/ })
    local format=${format_and_types[0]}
    local types=${format_and_types[1]}
    if [ $types = 'nil' ]; then
	in="$INPUT.$format"
	out=output/$invoker-$service-$format
    else
	in="$INPUT.$types.$format"
	out=output/$invoker-$service-$format-$types
    fi
}


# Stanford and DKPRO Coref via Brandeis server

if [ $1 = brandeis ] || [ $1 = all ]; then

    invoker=brandeis
    services=( stanfordnlp.coref_2.0.4
	       uima.dkpro.stanfordnlp.coref_0.0.1 )

    for service in "${services[@]}"
    do
	echo "common/invoke_brandeis.lsd $service"
	for spec in txt:nil lif:nil lif:tok gate:nil
	do
	    parse_spec $spec
	    echo "   $in"
	    $LSD $TOOLS/common/invoke_brandeis.lsd $service $in tmp-$spec
	    $LSD $TOOLS/common/pretty_print.lsd tmp-$spec $out.lif
	done
    done

fi


rm -f tmp-*
