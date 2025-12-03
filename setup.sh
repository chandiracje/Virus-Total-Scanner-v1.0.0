#!/bin/bash

# 1. Get current directory
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

echo "=== VTScan Installer ==="

# 2. Check for Java
if ! command -v javac &> /dev/null; then
    echo "❌ Error: Java (javac) is not installed."
    echo "   Run: sudo apt install default-jdk"
    exit 1
fi

# 3. Compile Scan.java
if [ -f "$SCRIPT_DIR/Scan.java" ]; then
    echo "Compiling Scan.java..."
    javac "$SCRIPT_DIR/VTScanner.java"
    javac "$SCRIPT_DIR/Scan.java"
    if [ $? -ne 0 ]; then
        echo "❌ Compilation failed."
        exit 1
    fi
else
    echo "❌ Error: Scan.java not found in $SCRIPT_DIR"
    exit 1
fi

# 4. Save the Installation Path to a text file
echo "Saving installation path..."
echo "$SCRIPT_DIR" > "$SCRIPT_DIR/path.txt"

if [ -f "$SCRIPT_DIR/path.txt" ]; then
    echo "Path saved: $(cat "$SCRIPT_DIR/path.txt")"
else
    echo "❌ Error: Could not create path.txt"
    exit 1
fi

# 5. Make run.sh executable
chmod +x "$SCRIPT_DIR/run.sh"

# 6. Create global symlink
APP_NAME="vtscan"
TARGET_LINK="/usr/local/bin/$APP_NAME"

echo "Creating global command '$APP_NAME'..."

if [ -w /usr/local/bin ]; then
    ln -sf "$SCRIPT_DIR/run.sh" "$TARGET_LINK"
else
    echo "Requesting sudo permission..."
    sudo ln -sf "$SCRIPT_DIR/run.sh" "$TARGET_LINK"
fi

echo "✅ Installation complete!"
echo "You can now run the tool using: $APP_NAME"
