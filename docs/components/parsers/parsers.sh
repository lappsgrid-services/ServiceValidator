#!/bin/bash

# Script to run various parsers.
#
# Usage
#
#  $ ./parsers.sh brandeis


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


# Stanford, OpenNLP and DKPRO parsers via Brandeis server

if [ $1 = brandeis ] || [ $1 = all ];
then
    invoker=brandeis
    services=( stanfordnlp.parser_2.0.4
	       stanfordnlp.dependencyparser_2.0.4
	       opennlp.parser_2.0.3
	       uima.dkpro.stanfordnlp.parser_0.0.1
	       uima.dkpro.opennlp.parser_0.0.1 )
    specs=( txt:nil lif:nil lif:tok lif:sent gate:nil )
    run_regular_services
fi


rm -f tmp-*
