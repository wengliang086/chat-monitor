#!/usr/bin/expect -f

set ip [lindex $argv 0]
set command [lindex $argv 1]
set timeout 100000

spawn ssh -p 22 root@$ip
expect "*root*"
send "$command\r"
send "exit\r"
expect eof


