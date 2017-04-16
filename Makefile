# Make file

all:
	javac -classpath out/ -d out/ src/*
	java -classpath out/ Main
