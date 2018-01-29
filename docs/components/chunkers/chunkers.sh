#!/bin/bash

# Script to run various chunkers.
#
# Usage
#
#  $ ./chunkers.sh gate


# settings and utilities used by script
source ../splitters/settings.sh
source ../splitters/utils.sh


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
    invoker=gate
    services=( gate.npchunker_2.2.0 gate.vpchunker_2.2.0
	       gate.npchunker_2.3.0 gate.vpchunker_2.3.0 )
    specs=( txt:nil lif:nil lif:tok.sent gate:nil gate:tok gate:sent gate:tok.sent gate:tok.pos.sent )
    run_gate_services
fi

rm -f tmp-*