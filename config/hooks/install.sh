#!/bin/sh

mkdir -p ../../.git/hooks/

chmod -R +x pre-commit.sh
chmod -R +x pre-push.sh

echo "Installing pre-commit hook..."
ln -s -f ../../config/hooks/pre-commit.sh ../../.git/hooks/pre-commit

echo "Installing pre-push hook..."
ln -s -f ../../config/hooks/pre-push.sh ../../.git/hooks/pre-push

echo "Hooks installed successfully"
