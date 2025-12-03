#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
APP_NAME="vtscan"
LINK_PATH="/usr/local/bin/$APP_NAME"

echo "=== VTScan Uninstaller ==="

# 1. Remove Global Link
if [ -L "$LINK_PATH" ]; then
    if [ -w /usr/local/bin ]; then
        rm "$LINK_PATH"
    else
        sudo rm "$LINK_PATH"
    fi
    echo "✅ Global command removed."
else
    echo "ℹ️  Global command not found."
fi

# 2. Remove Compiled Class
if [ -f "$SCRIPT_DIR/Scan.class" ]; then
    rm "$SCRIPT_DIR/Scan.class"
    rm "$CLASS_FILE"
    rm "$SCRIPT_DIR/VTScanner.class"
    rm "$SCRIPT_DIR/VTScannerDomain.class" 
    rm "$SCRIPT_DIR/VTScannerhash.class"
    rm "$SCRIPT_DIR/apikey.txt"
    echo "✅ Removed"
fi

# 3. Remove Path Config
if [ -f "$SCRIPT_DIR/path.txt" ]; then
    rm "$SCRIPT_DIR/path.txt"
    echo "✅ Removed path.txt"
fi

echo "Uninstallation complete."
