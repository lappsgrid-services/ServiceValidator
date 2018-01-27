
run_regular_services () {
    # Function that runs regular services where regular means they are invoked by
    # common/invoke_vassar.lsd or common/invoke_brandeis.lsd.
    for service in "${services[@]}"
    do
	echo "common/invoke_$invoker.lsd $service"
	#for spec in txt:nil lif:nil lif:tok lif:ner gate:nil
	for spec in "${specs[@]}"
	do
	    parse_spec $spec
	    echo "   $in"
	    $LSD $TOOLS/common/invoke_$invoker.lsd $service $in tmp-$spec
	    $LSD $TOOLS/common/pretty_print.lsd tmp-$spec $out.lif
	done
    done
}


run_gate_services () {
    # Function that runs GATE service via the GATE invoker
    for service in "${services[@]}"
    do
	echo "gate/invoke.lsd $service"
	for spec in "${specs[@]}"
	do
	    parse_spec $spec
	    echo "   $in"
	    $LSD $TOOLS/gate/invoke.lsd $service $in $out.gate
	    # The true parameter for convert.gate2json produces pretty json
	    $LSD $TOOLS/converters/invoke.lsd convert.gate2json_2.0.0 $out.gate true $out.lif
	done
    done
}


run_lingpipe_services () {
    # Function that runs LingPipe service via the LingPipe invoker
    for service in "${services[@]}"
    do
	echo "lingpipe/invoke.lsd $service"
	for spec in "${specs[@]}"
	do
	    parse_spec $spec
	    echo "   $in"
	    # Note that the true parameter for convert.gate2json makes the
	    # converter produce pretty json
	    $LSD $TOOLS/lingpipe/invoke.lsd $service $in tmp-$spec
	    $LSD $TOOLS/common/pretty_print.lsd tmp-$spec $out.lif
	done
    done
}

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
