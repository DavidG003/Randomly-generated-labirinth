# Define the Java compiler
JC = javac

# Define the source directory and Java file
SRC_DIR = src/usage
SRC_FILE = $(SRC_DIR)/Prim.java

# Define the output directory
OUT_DIR = out

# Define the compiler flags
JFLAGS = -d $(OUT_DIR) -sourcepath src $(SRC_FILE)

# Target to compile the Java file
all: $(SRC_FILE)
	$(JC) $(JFLAGS) $<

# Clean up compiled files
ifeq ($(OS),Windows_NT)
clean:
	rmdir /S /Q .\out
else
clean:
	rm -rf ../../out/graph/*
endif

# Phony targets
.PHONY: all clean