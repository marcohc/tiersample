#!/bin/bash
echo "Installing code style for Android Studio and/or IntelliJ Idea config..."

CONFIG="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/config"

for i in $HOME/Library/Preferences/IntelliJIdea*  \
         $HOME/Library/Preferences/AndroidStudio* \
         $HOME/.IntelliJIdea*/config              \
         $HOME/.AndroidStudio*/config
do
  if [[ -d $i ]]; then
    mkdir -p $i/codestyles
    cp -frv "$CONFIG"/* $i/codestyles
    cp -frv "$CONFIG"/* $i/settingsRepository/repository/codestyles
  fi
done

echo "Done."
echo "Restart Android Studio and/or IntelliJ Idea"
echo "Then go to Project Settings -> Editor -> Code Styles and apply the scheme."
echo ""
