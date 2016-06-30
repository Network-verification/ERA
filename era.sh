#!/bin/bash


start=`date +%s`
for file in "$1"configs/*
do
 echo "Modifications for Batfish --" 
 sed -i '/snmp/d' $file
 sed -i '/pim/d' $file
 sed -i '/monitor/d' $file
 sed -i '/ntp/d' $file
 sed -i '/^end/d' $file
 echo $file 
 cat conf >> $file
done
echo "Running Batfish --" 
batfish_analyze_multipath $1
echo "Creating cp_facts --" 
mkdir cp_facts

echo "Run java --" 
javac -cp jdd_105.jar:Minimize.jar APCSA.java;

java APCSA  -cp jdd_105.jar:Minimize.jar $2 $3 $4 $5

runtime=$((end-start))
echo $runtime in seconds
~                                
