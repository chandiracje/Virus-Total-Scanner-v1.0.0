#!/bin/bash

# 1. Get the directory where THIS script is TRULY located
# We use 'readlink -f' to resolve the symlink (vtscan) back to the real file path
REAL_SCRIPT_PATH="$(readlink -f "${BASH_SOURCE[0]}")"
SCRIPT_LOC="$(dirname "$REAL_SCRIPT_PATH")"

# 2. Read the valid install path from path.txt
PATH_FILE="$SCRIPT_LOC/path.txt"

if [ -f "$PATH_FILE" ]; then
    # Read the content of the file into a variable
    INSTALL_DIR=$(cat "$PATH_FILE")
else
    echo "❌ Error: Configuration file 'path.txt' missing."
    echo "   Expected at: $PATH_FILE"
    echo "   Please run setup.sh again."
    exit 1
fi

# 3. Define Main Class
MAIN_CLASS="Scan"

# 4. Check if the class exists in the INSTALL_DIR
if [ ! -f "$INSTALL_DIR/$MAIN_CLASS.class" ]; then
    echo "⚠️  Class file missing in $INSTALL_DIR"
    echo "Attempting to recompile..."
    
    # We use the INSTALL_DIR from the text file to find the source
    if [ -f "$INSTALL_DIR/$MAIN_CLASS.java" ]; then
        javac "$INSTALL_DIR/$MAIN_CLASS.java"
    else
        echo "❌ Error: Source file $MAIN_CLASS.java not found in $INSTALL_DIR"
        exit 1
    fi
fi

# 5. Run Java using the read path
# "$@" passes all arguments (e.g., --help, -v) to the Java main method
java -cp "$INSTALL_DIR" "$MAIN_CLASS" "$@"
