#!/bin/bash

command="$0 <BASEDIR> <IMGDIR>"

check_dir_parameter()
{
	directory="$1"
	message="$2"
	if [ ! -n "$directory" ] || [ ! -d "$directory" ]
	then
		echo "$message"
		echo "Usage: $command"
		exit 1
	fi
}

basedir="$1"
imgdir="$2"

check_dir_parameter "$basedir" "No LiveCD base-directory specified!"
check_dir_parameter "$imgdir" "No LiveCD image-directory specified!"

echo "Copy bwfla-root to the LiveCD image..."
sudo cp -r -v -t $imgdir $basedir/bwfla-root/*

