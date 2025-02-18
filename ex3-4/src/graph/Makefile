# Define the Java compiler and flags
JC = javac
JFLAGS = -d ../../out -cp '.;../../lib/junit-4.12.jar;../../lib/hamcrest-core-1.3.jar'

# Define the source directory and Java files 
SOURCES = $(wildcard ./*.java)

# Default target to compile all Java files
all: $(SOURCES)
	$(JC) $(JFLAGS) $^

# Clean up compiled files
ifeq ($(OS),Windows_NT)
clean:
	rmdir /S /Q ..\..\out\graph
else
clean:
	rm -rf ../../out/graph/*
endif

# Phony targets
.PHONY: all clean