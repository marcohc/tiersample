#!/bin/sh

exec < /dev/tty

while true; do

    read -p "Ey developer, what's gonna be today:
        1- Push
        2- Run unit test + Push
        3- Run unit test + Build Android tests + Push
Choose: " answer

    if [[ "$answer" = "" ]]; then
        answer='3'
    fi

    case ${answer} in
        [1] )
            echo "Pushing code..."
                exit 0
            break;;

        [2] )
            echo "Run unit test..."
                ./gradlew testDebugUnitTest --offline;
                RESULT=$?;
                echo ${RESULT};
                [[ ${RESULT} -ne 0 ]] && exit 1
            echo "Pushing code..."
                exit 0
            break;;

        [3] )
            echo "Run unit test..."
                ./gradlew testDebugUnitTest --offline;
                RESULT=$?;
                echo ${RESULT};
                [[ ${RESULT} -ne 0 ]] && exit 1
            echo "Build Android tests..."
                ./gradlew assembleDebugAndroidTest --offline;
                RESULT=$?;
                echo ${RESULT};
                [[ ${RESULT} -ne 0 ]] && exit 1
            echo "Pushing code..."
                exit 0
            break;;

        * ) echo "Please type 'y' or 'n' for yes or no.";;
    esac
done

exit 1
