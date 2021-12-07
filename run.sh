#! /bin/bash

while [ -n "$1" ]; do

	case "$1" in

	-c) javac -d class src/*;;

	--)
		shift
		break;;

	*)
    echo "Option $1 not recognized, remember to use --"
	echo ""
    break;;

	esac

	shift

done

java -cp class $1