#!/bin/bash
if [ $# -lt 3 ]; then
  echo 1>&2 "$0: not enough arguments - usage is ./era.sh <path_to_config_folder> <path_to_policy_file> <-s/-v>"
  exit 2
elif [ $# -gt 3 ]; then
  echo 1>&2 "$0: too many arguments"
  exit 2
fi
for file in "$1"configs/*
do
sed -i '/snmp/d' $file
sed -i '/pim/d' $file
sed -i '/monitor/d' $file
sed -i '/ntp/d' $file
sed -i '/^end/d' $file
cat conf >> $file
done
batfish_analyze_multipath $1 &>batfish_out

mkdir cp_facts &> rand
for file in "$1"environments/multipath-default/cp_facts/*
do
if [ $(wc -l <$file)  -ge "3" ]
then
 cp $file ~/era/cp_facts/
#fi
echo `wc -l $file`
fi
done
javac -cp jdd_105.jar:Minimize.jar era_analyser.java;
java -cp .:jdd_105.jar:Minimize.jar era_analyser $1 $2 $3 $4

