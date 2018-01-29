#!/bin/bash

# Script to run various coreference tools.
#
# Usage
#
#  $ ./coref.sh brandeis


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


# Stanford and DKPRO Coref via Brandeis server

if [ $1 = brandeis ] || [ $1 = all ]; then

    invoker=brandeis
    services=( stanfordnlp.coref_2.0.4
	       uima.dkpro.stanfordnlp.coref_0.0.1 )
    specs=( txt:nil lif:nil lif:tok gate:nil )
    run_regular_services

    for service in "${services[@]}"
    do
	continue
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
