#! /bin/bash

export B="cat /dev/stdin"
while [ -n "$1" ]; do

	case "$1" in

    -a) export B="echo -e data/tweets-100tw.txt\\ndata/out3.txt";;

	-A) export B="echo -e data/tweets-PruebaP1.txt\\ndata/out-PruebaP1.txt";;

    -r) export C="ProcesarTweets";;

    -p) export C="Pruebas";;

	-l) export A="$A -Xlint:unchecked";;

	-c) javac $A -d class src/*;;

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

$B | java -cp class $C "$@"