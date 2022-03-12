#! /bin/bash

# ---- data pipeline input from STDIN
result_string=$(
while IFS= read -r line; do
        echo "$line" | sed -r '/(^reboot)|(still\slogged\sin)/d'
done 
)
# convert string into array
IFS=$'\n'; result_arr=($result_string); unset IFS;
# remove last 2 lines 
unset 'result_arr[${#result_arr[@]}-1]'
unset 'result_arr[${#result_arr[@]}-1]'


# ---- argument parsing
positional=()
count_option=false
time_option=false

while [[ $# -gt 0 ]]; do
        case "$1" in 
                -c|--count) # count option
                        count_option=true
                        shift
                        shift
                        ;;
                -t|--time) # time option
                        time_option=true
                        shift
                        shift
                        ;;
        esac
done
set -- "${positional[@]}" # restore positional parameters


# ---- print option handling
# TODO: 
# get unique users from result array 
users=($( printf '%s\n' "${result_arr[@]}" | sed 's/ .*//' | sort -u ))

if $count_option ; then 
        # count option is activated
        # lines with user and login count, sorted by login count 
        printf 'User\t\tLogin Count\n'
        for index in ${!users[@]}; do
                login_count=$( printf '%s\n' "${result_arr[@]}" | grep "${users[index]}" | wc -l )
                printf '%-10s\t%-5s\n' "${users[index]}" "$login_count" 
        done
fi

if $time_option ; then
        # time option is activated
        # lines with user and login time, sorted by login time
        printf 'User\t\tLogin Time\n'
        for index in ${!users[@]}; do
                login_time=$( printf '%s\n' "${result_arr[@]}" | grep -m 1 "${users[index]}" | sed 's/.*(/(/' ) 
                printf '%-10s\t%-5s\n' "${users[index]}" "$login_time" | sort -V
        done
fi




